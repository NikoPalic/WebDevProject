package eu.pracenjetroskova.app.repository;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;

import eu.pracenjetroskova.app.model.Role;



public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	 Role findByRole(String name);

	 @Override
	 void delete(Role role);


}
