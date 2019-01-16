package eu.pracenjetroskova.app.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import eu.pracenjetroskova.app.dto.UserDto;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.service.UserService;
import eu.pracenjetroskova.app.validation.EmailExistsException;


@Controller
public class RegistrationController {
	
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
	
	@PostMapping("/signup")
	public ModelAndView registerUserAccount(
			  @ModelAttribute("user") @Valid UserDto accountDto, 
			  BindingResult result, 
			  WebRequest request, 
			  Errors errors) {
			     
			    User registered = new User();
			    if (!result.hasErrors()) {
			        registered = createUserAccount(accountDto, result);
			    }
			    if (userService.usernameExist(accountDto.getUsername())) {
			    	result.rejectValue("username", "message.regError.name");
			    }
			    if (userService.emailExist(accountDto.getEmail())) {
			        result.rejectValue("email", "message.regError");
			        
			    }
			    if (result.hasErrors()) {
			        return new ModelAndView("registration", "user", accountDto);
			    } 
			    else {
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
