package com.example.main.delegates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.main.delegate.interfaces.InstitutionDelegate;
import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.services.interfaces.InstitutionService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class InstitutionDelegateTest {
	
	@Autowired
	private InstitutionDelegate instDelegate;
	
	@Autowired
	private InstitutionService service;
	
	@BeforeAll
	public void setInstitutions() throws URLWithoutProtocolException, InstitutionWithoutNameException {
		Institution institution1 = new Institution();
		institution1.setInstName("Icesi");
		institution1.setInstAcademicserverurl("https://www.icesi.com");
		institution1.setInstAcadloginpassword("12345");
		institution1.setInstAcadloginurl("https://login.icesi.com");
		service.save(institution1);
		
		Institution institution2 = new Institution();
		institution2.setInstName("Javeriana");
		institution2.setInstAcademicserverurl("https://www.javeriana.com");
		institution2.setInstAcadloginpassword("12345");
		institution2.setInstAcadloginurl("https://login.javeriana.com");
		service.save(institution2);
	}
	
	@Test
	public void createInstitutionTest() {
		Institution institution1 = new Institution();
		institution1.setInstName("Icesi");
		institution1.setInstAcademicserverurl("https://www.icesi.com");
		institution1.setInstAcadloginpassword("12345");
		institution1.setInstAcadloginurl("https://login.icesi.com");
		instDelegate.createInstitution(institution1);
	}
	
	@Test
	public void updateInstitutionTest() {
		
	}
	
	@Test
	public void getInstitutionTest() {
		Assertions.assertEquals(service.findById(1), instDelegate.getInstitution(1));
	}
	
	@Test
	public void getAllInstitutionsTest() {
		Assertions.assertEquals(2, instDelegate.getAllInstitutions().size());
	}

}
