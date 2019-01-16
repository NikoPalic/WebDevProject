package eu.pracenjetroskova.app.controller;



import java.util.Calendar;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import eu.pracenjetroskova.app.dto.UserDto;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.model.VerificationToken;
import eu.pracenjetroskova.app.registration.OnRegistrationCompleteEvent;
import eu.pracenjetroskova.app.service.UserService;
import eu.pracenjetroskova.app.validation.EmailExistsException;


@Controller
public class RegistrationController {
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	@Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Environment env;
	
	private final UserService userService;
	
	
	@Autowired
	public RegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/signup")
	public String showRegistrationForm(WebRequest request, Model model) {
	    UserDto userDto = new UserDto();
	    model.addAttribute("user", userDto);
	    return "registration";
	}
	
//	@PostMapping("/signup")
//	public User register(@RequestBody User user) {
//		userService.insertUser(user);
//		return user;
//	}
	
	@RequestMapping(value = "/signup/registrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration
	  (WebRequest request, Model model, @RequestParam("token") String token) {
	  
	    Locale locale = request.getLocale();
	     
	    VerificationToken verificationToken = userService.getVerificationToken(token);
	    if (verificationToken == null) {
	        String message = messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("message", message);
	        return "badUser";
	    }
	     
	    User user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        
	        model.addAttribute("message", "auth.message.expired");
	        return "badUser";
	    } 
	    
	    user.setActive(true); 
	    userService.updateUser(user);
	    return "redirect:/login"; 
	}
	
	@PostMapping("/signup")
	public ModelAndView registerUserAccount(
			  @ModelAttribute("user")  @Valid UserDto accountDto, 
			  BindingResult result, 
			  WebRequest request, 
			  Errors errors) {
			     
			    User registered = new User();
			    if (userService.usernameExist(accountDto.getUsername())) {
			    	result.rejectValue("username", "message.regError.name");
			    }
			    if (userService.emailExist(accountDto.getEmail())) {
			        result.rejectValue("email", "message.regError");
			    }
			    if (!result.hasErrors()) {
			        registered = createUserAccount(accountDto, result);
			    }
			   
			    if (result.hasErrors()) {
			        return new ModelAndView("registration", "user", accountDto);
			    } 
			    else {
			    	try {
			            String appUrl = "https://pracenje-troskova.herokuapp.com/signup";
			            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
			              (registered, request.getLocale(), appUrl));
			        } catch (Exception me) {
			            return new ModelAndView("emailError", "user", accountDto);
			        }
			        return new ModelAndView("successRegister", "user", accountDto);
			    }
			}
			
	private User createUserAccount(UserDto accountDto, BindingResult result) {
	    User registered = null;
	    try {
	        registered = userService.registerNewUserAccount(accountDto);
	    } catch (EmailExistsException e) {
	        return null;
	    }
	    return registered;
	}
	
}
