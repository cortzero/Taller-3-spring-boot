package com.example.main;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.model.Institutioncampus;
import com.example.main.model.Person;
import com.example.main.model.PersonRole;
import com.example.main.model.PersonRolePK;
import com.example.main.model.Rolee;
import com.example.main.model.Userr;
import com.example.main.repositories.PersonRepository;
import com.example.main.repositories.PersonRoleRepository;
import com.example.main.repositories.RoleeRepository;
import com.example.main.repositories.UserrRepository;
import com.example.main.services.implementations.InstitutionCampusServiceImpl;
import com.example.main.services.implementations.InstitutionServiceImpl;

@SpringBootApplication(scanBasePackages = "com.example")
public class Taller3JohanCortesApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext c = SpringApplication.run(Taller3JohanCortesApplication.class, args);
//		InstitutionServiceImpl i = c.getBean(InstitutionServiceImpl.class);
//		InstitutionCampusServiceImpl ic = c.getBean(InstitutionCampusServiceImpl.class);
		
//		Institution inst1 = new Institution();
//		inst1.setInstName("Icesi");
//		inst1.setInstAcademicserverurl("https://banner.icesi.edu.co");
//		inst1.setInstAcadprogrammedcoursesurl("https://ic_rest_interfaces_test/api/programmed-course/");
//		inst1.setInstAcadphysicalspacesurl("https://ic_rest_interfaces/api/programmed-phys/");
//		inst1.setInstAcadloginurl("https://ic_rest_interfaces/api/login");
//		inst1.setInstAcadloginusername("UCCARE_USER");
//		inst1.setInstAcadloginpassword("a3L5m7jj*+BQpgsm");
//		inst1.setInstLdapurl("https://ldap://192.168.218.100:389/");
//		inst1.setInstLdapbasedn("dc=icesi,dc=edu,dc=co");
//		inst1.setInstLdapusername("cn=covidapp,ou=dsistemas,ou=users,dc=icesi,dc=edu,dc=co");
//		inst1.setInstLdappassword("vH2KAW6V5F85drJ");
//		inst1.setInstLdapusersearchbase("ou=users");
//		inst1.setInstLdapusersearchfilter("cn={0}");
//		inst1.setInstAcadpersoninfodocurl("https://ic_rest_interfaces_test/api/personsByCedSorted/");
//		inst1.setInstAcadpersoninfoidurl("https://ic_rest_interfaces_test/api/personsByPidmSorted/");
//		inst1.setInstAcadextradataurl("https://ic_rest_interfaces_test/api/getBannerPersonsExtraDataByIdSorted/");
//
//		try {
//			i.save(inst1);
//		} catch (URLWithoutProtocolException | InstitutionWithoutNameException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//
//		Institution inst2 = new Institution();
//		inst2.setInstName("Javeriana");
//		inst2.setInstAcademicserverurl("https://banner.javeriana.edu.co");
//		inst2.setInstAcadprogrammedcoursesurl("https://jv_rest_interfaces_test/api/programmed-course/");
//		inst2.setInstAcadphysicalspacesurl("https://jv_rest_interfaces/api/programmed-phys/");
//		inst2.setInstAcadloginurl("https://jv_rest_interfaces/api/login");
//		inst2.setInstAcadloginusername("UCCARE_USER");
//		inst2.setInstAcadloginpassword("a3L5m7jj*+BQpgsm");
//		inst2.setInstLdapurl("https://ldap://192.168.218.100:389/");
//		inst2.setInstLdapbasedn("dc=javeriana,dc=edu,dc=co");
//		inst2.setInstLdapusername("cn=covidapp,ou=dsistemas,ou=users,dc=javeriana,dc=edu,dc=co");
//		inst2.setInstLdappassword("vH2KAW6V5F85drJ");
//		inst2.setInstLdapusersearchbase("ou=users");
//		inst2.setInstLdapusersearchfilter("cn={0}");
//		inst2.setInstAcadpersoninfodocurl("https://jv_rest_interfaces_test/api/personsByCedSorted/");
//		inst2.setInstAcadpersoninfoidurl("https://jv_rest_interfaces_test/api/personsByPidmSorted/");
//		inst2.setInstAcadextradataurl("https://jv_rest_interfaces_test/api/getBannerPersonsExtraDataByIdSorted/");
//
//		try {
//			i.save(inst2);
//		} catch (URLWithoutProtocolException | InstitutionWithoutNameException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		Institution inst3 = new Institution();
//		inst3.setInstName("Autónoma");
//		inst3.setInstAcademicserverurl("https://banner.uao.edu.co");
//		inst3.setInstAcadprogrammedcoursesurl("https://uao_rest_interfaces_test/api/programmed-course/");
//		inst3.setInstAcadphysicalspacesurl("https://uao_rest_interfaces/api/programmed-phys/");
//		inst3.setInstAcadloginurl("https://uao_rest_interfaces/api/login");
//		inst3.setInstAcadloginusername("UCCARE_USER");
//		inst3.setInstAcadloginpassword("a3L5m7jj*+BQpgsm");
//		inst3.setInstLdapurl("https://ldap://192.168.218.100:389/");
//		inst3.setInstLdapbasedn("dc=uao,dc=edu,dc=co");
//		inst3.setInstLdapusername("cn=covidapp,ou=dsistemas,ou=users,dc=icesi,dc=edu,dc=co");
//		inst3.setInstLdappassword("vH2KAW6V5F85drJ");
//		inst3.setInstLdapusersearchbase("ou=users");
//		inst3.setInstLdapusersearchfilter("cn={0}");
//		inst3.setInstAcadpersoninfodocurl("https://uao_rest_interfaces_test/api/personsByCedSorted/");
//		inst3.setInstAcadpersoninfoidurl("https://uao_rest_interfaces_test/api/personsByPidmSorted/");
//		inst3.setInstAcadextradataurl("https://uao_rest_interfaces_test/api/getBannerPersonsExtraDataByIdSorted/");
//
//		try {
//			i.save(inst3);
//		} catch (URLWithoutProtocolException | InstitutionWithoutNameException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Institutioncampus ic1 = new Institutioncampus();
//		ic1.setInstcamName("Campus Universidad Icesi");
//		ic1.setInstitution(inst1);
//		try {
//			ic.save(ic1);
//		} catch (NoSuchElementException | CampusWithoutNameException | CampusWithNoZeroOccupationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
