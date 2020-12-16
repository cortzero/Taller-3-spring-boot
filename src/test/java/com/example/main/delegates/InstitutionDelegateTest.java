package com.example.main.delegates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.main.delegate.interfaces.InstitutionDelegate;
import com.example.main.model.Institution;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class InstitutionDelegateTest {
	
	@Autowired
	private InstitutionDelegate instDelegate;
	
	@Test
	@Order(1)
	public void createInstitutionTest() {
		Institution institution1 = new Institution();
		institution1.setInstName("Icesi");
		institution1.setInstAcademicserverurl("https://www.icesi.com");
		institution1.setInstAcadloginpassword("12345");
		institution1.setInstAcadloginurl("https://login.icesi.com");
		
		Assertions.assertEquals(HttpStatus.CREATED, instDelegate.createInstitution(institution1));
		
		// Otra institucion
		
		Institution institution2 = new Institution();
		institution2.setInstName("Univalle");
		institution2.setInstAcademicserverurl("https://www.univalle.com");
		institution2.setInstAcadloginpassword("54675");
		institution2.setInstAcadloginurl("https://login.univalle.com");
		
		Assertions.assertEquals(HttpStatus.CREATED, instDelegate.createInstitution(institution2));
	}
	
	@Test
	@Order(2)
	public void getInstitutionTest() {
		Assertions.assertEquals("Icesi", instDelegate.getInstitution(1).getInstName());
		Assertions.assertEquals("https://www.icesi.com", instDelegate.getInstitution(1).getInstAcademicserverurl());
		Assertions.assertEquals("12345", instDelegate.getInstitution(1).getInstAcadloginpassword());
		Assertions.assertEquals("https://login.icesi.com", instDelegate.getInstitution(1).getInstAcadloginurl());
		
		Assertions.assertEquals("Univalle", instDelegate.getInstitution(2).getInstName());
		Assertions.assertEquals("https://www.univalle.com", instDelegate.getInstitution(2).getInstAcademicserverurl());
		Assertions.assertEquals("54675", instDelegate.getInstitution(2).getInstAcadloginpassword());
		Assertions.assertEquals("https://login.univalle.com", instDelegate.getInstitution(2).getInstAcadloginurl());
	}
	
	@Test
	@Order(3)
	public void getAllInstitutionsTest() {
		Assertions.assertEquals(2, instDelegate.getAllInstitutions().size());
	}
	
	@Test
	@Order(4)
	public void updateInstitutionTest() {
		Institution institution = instDelegate.getInstitution(1);
		institution.setInstName("Javeriana");
		institution.setInstAcademicserverurl("https://www.javeriana.com");
		institution.setInstAcadloginpassword("98765");
		institution.setInstAcadloginurl("https://login.javeriana.com");
		
		instDelegate.updateInstitution(1, institution);
		
		Assertions.assertEquals("Javeriana", instDelegate.getInstitution(1).getInstName());
		Assertions.assertEquals("https://www.javeriana.com", instDelegate.getInstitution(1).getInstAcademicserverurl());
		Assertions.assertEquals("98765", instDelegate.getInstitution(1).getInstAcadloginpassword());
		Assertions.assertEquals("https://login.javeriana.com", instDelegate.getInstitution(1).getInstAcadloginurl());
		
		// Otra actualizacion
		
		Institution institution2 = instDelegate.getInstitution(2);
		institution2.setInstName("Stanford");
		institution2.setInstAcademicserverurl("https://www.stanford.com");
		institution2.setInstAcadloginpassword("55555");
		institution2.setInstAcadloginurl("https://login.stanford.com");
		
		instDelegate.updateInstitution(2, institution2);
		
		Assertions.assertEquals("Stanford", instDelegate.getInstitution(2).getInstName());
		Assertions.assertEquals("https://www.stanford.com", instDelegate.getInstitution(2).getInstAcademicserverurl());
		Assertions.assertEquals("55555", instDelegate.getInstitution(2).getInstAcadloginpassword());
		Assertions.assertEquals("https://login.stanford.com", instDelegate.getInstitution(2).getInstAcadloginurl());
	}
	
	@Test
	@Order(5)
	public void deleteInstitutionTest() {
		Assertions.assertEquals(2, instDelegate.getAllInstitutions().size());
		
		instDelegate.deleteInstitution(2);
		
		Assertions.assertEquals(1, instDelegate.getAllInstitutions().size());
		Assertions.assertEquals("Javeriana", instDelegate.getInstitution(1).getInstName());
	}
}
