package com.example.main.daos;

import java.util.List;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.main.daos.interfaces.InstitutionCampusDAO;
import com.example.main.model.Institutioncampus;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class IntitutionCampusDAOTest {
	
	@Autowired
	private InstitutionCampusDAO campusDAO;
	
	@Test
	@Order(1)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveMethodTest() {
		Assertions.assertNotNull(campusDAO);
		Institutioncampus campus = new Institutioncampus();
		campus.setInstcamName("Icesi pance");
		campusDAO.save(campus);
		
		Assertions.assertNotNull(campusDAO.findById(1));
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateMethodTest() {
		String newCampusName = "Javeriana Cali";
		Institutioncampus existingCampus = campusDAO.findById(1);
		existingCampus.setInstcamName(newCampusName);
		campusDAO.update(existingCampus);
		Assertions.assertEquals(newCampusName, campusDAO.findById(1).getInstcamName());
	}
	
	@Test
	@Order(3)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteMethodTest() {
		campusDAO.delete(campusDAO.findById(1));
		Assertions.assertNull(campusDAO.findById(1));
	}
	
	@Test
	@Order(4)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findAllMethodTest() {
		Institutioncampus campus1 = new Institutioncampus();
		campus1.setInstcamName("Icesi pance");
		campusDAO.save(campus1);
		
		Institutioncampus campus2 = new Institutioncampus();
		campus2.setInstcamName("Javeriana Cali");
		campusDAO.save(campus2);
		
		Institutioncampus campus3 = new Institutioncampus();
		campus3.setInstcamName("Univalle palmira");
		campusDAO.save(campus3);
		
		List<Institutioncampus> campusList = campusDAO.findAll();
		Assertions.assertEquals(3, campusList.size());
	}
}
