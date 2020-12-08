package com.example.main.daos;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.main.daos.interfaces.InstitutionDAO;
import com.example.main.model.Institution;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class InstitutionDAOTest {
	
	@Autowired
	private InstitutionDAO institutionDAO;

	@Test
	@Order(1)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveMethodTest() {
		Assertions.assertNotNull(institutionDAO);
		Institution institution = new Institution();
		institution.setInstName("Icesi");
		institution.setInstAcademicserverurl("https://www.icesi.com");
		institution.setInstAcadloginpassword("12345");
		institution.setInstAcadloginurl("https://login.icesi.com");
		institution.setInstAcadloginusername("icesi user 1");
		institution.setInstAcadphysicalspacesurl("https://space.icesi.com");
		institutionDAO.save(institution);
		Assertions.assertNotNull(institutionDAO.findById(1));
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateMethodTest() {
		String newName = "Javeriana";		
		Institution existingInstitution = institutionDAO.findById(1);
		existingInstitution.setInstName(newName);
		institutionDAO.update(existingInstitution);
		Assertions.assertEquals(newName, institutionDAO.findById(1).getInstName());
	}
	
	@Test
	@Order(3)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteMethodTest() {
		institutionDAO.delete(institutionDAO.findById(1));
		Assertions.assertNull(institutionDAO.findById(1));
	}
	
	@Test
	@Order(4)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findAllMethodTest() {
		Institution institution1 = new Institution();
		institution1.setInstName("Icesi");
		institution1.setInstAcademicserverurl("https://www.icesi.com");
		institution1.setInstAcadloginpassword("12345");
		institution1.setInstAcadloginurl("https://login.icesi.com");
		institution1.setInstAcadloginusername("icesi user 1");
		institution1.setInstAcadphysicalspacesurl("https://space.icesi.com");
		institutionDAO.save(institution1);
		
		Institution institution2 = new Institution();
		institution2.setInstName("Javeriana");
		institution2.setInstAcademicserverurl("https://www.javeriana.com");
		institution2.setInstAcadloginpassword("12345");
		institution2.setInstAcadloginurl("https://login.javeriana.com");
		institution2.setInstAcadloginusername("Javeriana user 1");
		institution2.setInstAcadphysicalspacesurl("https://space.javeriana.com");
		institutionDAO.save(institution2);
		
		Institution institution3 = new Institution();
		institution3.setInstName("Univalle");
		institution3.setInstAcademicserverurl("https://www.univalle.com");
		institution3.setInstAcadloginpassword("12345");
		institution3.setInstAcadloginurl("https://login.univalle.com");
		institution3.setInstAcadloginusername("Univalle user 1");
		institution3.setInstAcadphysicalspacesurl("https://space.univalle.com");
		institutionDAO.save(institution3);
		
		Institution institution4 = new Institution();
		institution4.setInstName("UAO");
		institution4.setInstAcademicserverurl("https://www.uao.com");
		institution4.setInstAcadloginpassword("12345");
		institution4.setInstAcadloginurl("https://login.uao.com");
		institution4.setInstAcadloginusername("UAO user 1");
		institution4.setInstAcadphysicalspacesurl("https://space.uao.com");
		institutionDAO.save(institution4);
		
		List<Institution> institutions = institutionDAO.findAll();
		Assertions.assertEquals(4, institutions.size());
	}
}
