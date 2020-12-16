package com.example.main.delegates;

import java.math.BigDecimal;

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

import com.example.main.delegate.interfaces.InstitutionCampusDelegate;
import com.example.main.model.Institutioncampus;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class InstitutionCampusDelegateTest {
	
	@Autowired
	private InstitutionCampusDelegate campusDelegate;

	@Test
	@Order(1)
	public void createInstitutionCampusTest() {
		Institutioncampus campus1 = new Institutioncampus();
		campus1.setInstcamName("Icesi sur");
		campus1.setInstcamOccupation(new BigDecimal(0));
		
		Assertions.assertEquals(HttpStatus.CREATED, campusDelegate.createCampus(campus1));
		
		Institutioncampus campus2 = new Institutioncampus();
		campus2.setInstcamName("Javeriana Cali");
		campus2.setInstcamOccupation(new BigDecimal(0));
		
		Assertions.assertEquals(HttpStatus.CREATED, campusDelegate.createCampus(campus2));
	}
	
	@Test
	@Order(2)
	public void getInstitutionCampusTest() {
		Assertions.assertEquals("Icesi sur", campusDelegate.getCampus(1).getInstcamName());
		Assertions.assertEquals(0, campusDelegate.getCampus(1).getInstcamOccupation().intValue());
		
		Assertions.assertEquals("Javeriana Cali", campusDelegate.getCampus(2).getInstcamName());
		Assertions.assertEquals(0, campusDelegate.getCampus(2).getInstcamOccupation().intValue());
	}
	
	@Test
	@Order(3)
	public void getAllInstitutionCampusTest() {
		Assertions.assertEquals(2, campusDelegate.getAllCampus().size());
	}
	
	@Test
	@Order(4)
	public void updateInstitutionCampusTest() {
		Institutioncampus campus = campusDelegate.getCampus(1);
		campus.setInstcamName("Univalle palmira");
		
		campusDelegate.updateCampus(1, campus);
		
		Assertions.assertEquals("Univalle palmira", campusDelegate.getCampus(1).getInstcamName());
	}
	
	@Test
	@Order(5)
	public void deleteInstitutionCampusTest() {
		Assertions.assertEquals(2, campusDelegate.getAllCampus().size());
		
		campusDelegate.deleteCampus(1);
		
		Assertions.assertEquals(1, campusDelegate.getAllCampus().size());
		Assertions.assertEquals("Javeriana Cali", campusDelegate.getCampus(2).getInstcamName());
	}
}
