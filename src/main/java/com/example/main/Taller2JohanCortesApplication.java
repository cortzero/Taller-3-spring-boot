package com.example.main;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.main.model.Person;
import com.example.main.model.PersonRole;
import com.example.main.model.PersonRolePK;
import com.example.main.model.Rolee;
import com.example.main.model.Userr;
import com.example.main.repositories.PersonRepository;
import com.example.main.repositories.PersonRoleRepository;
import com.example.main.repositories.RoleeRepository;
import com.example.main.repositories.UserrRepository;

@SpringBootApplication
public class Taller2JohanCortesApplication {

	public static void main(String[] args) {
		SpringApplication.run(Taller2JohanCortesApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner dummy(UserrRepository userRepository, PersonRepository personRepository,
			RoleeRepository roleeRepository, PersonRoleRepository personRoleRepository) {
		return (args) -> {
			Rolee roleAdministrador = new Rolee();
			roleAdministrador.setRoleName("Administrador");
			roleeRepository.save(roleAdministrador);
			Rolee roleOperador = new Rolee();
			roleOperador.setRoleName("Operador");
			roleeRepository.save(roleOperador);
			
			Person admin = new Person();
			personRepository.save(admin);
			Person op = new Person();
			personRepository.save(op);
			
			PersonRole roleAdmin = new PersonRole();
			
			PersonRolePK pkAdmin = new PersonRolePK();
			pkAdmin.setPersPersId(admin.getPersId());
			pkAdmin.setRoleRoleId(roleAdministrador.getRoleId());
			roleAdmin.setId(pkAdmin);
			personRoleRepository.save(roleAdmin);
			
			PersonRole roleOp = new PersonRole();
			
			PersonRolePK pkOp = new PersonRolePK();
			pkOp.setPersPersId(op.getPersId());
			pkOp.setRoleRoleId(roleOperador.getRoleId());
			roleOp.setId(pkOp);
			personRoleRepository.save(roleOp);
			
			Userr userAdmin = new Userr();
			userAdmin.setUserName("Admin");
			userAdmin.setUserPassword("1234");
			userAdmin.setPerson(admin);
			userAdmin.setInstInstId(new BigDecimal(1));
			
			Userr userOp = new Userr();
			userOp.setUserName("Operator");
			userOp.setUserPassword("9876");
			userOp.setPerson(op);
			userOp.setInstInstId(new BigDecimal(1));
			
			userRepository.save(userAdmin);
			userRepository.save(userOp);
		};
	}

}
