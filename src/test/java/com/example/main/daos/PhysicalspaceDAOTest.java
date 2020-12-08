package com.example.main.daos;

import java.util.ArrayList;
import java.util.Date;
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

import com.example.main.daos.implementations.VisitDAOImpl;
import com.example.main.daos.interfaces.CommunityinstanceDAO;
import com.example.main.daos.interfaces.InstitutionCampusDAO;
import com.example.main.daos.interfaces.PhysicalspaceDAO;
import com.example.main.daos.interfaces.VisitDAO;
import com.example.main.model.Communityinstance;
import com.example.main.model.Institutioncampus;
import com.example.main.model.Physicalspace;
import com.example.main.model.Visit;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class PhysicalspaceDAOTest {
	
	@Autowired
	private PhysicalspaceDAO physicalspaceDAO;
	
	@Autowired
	private CommunityinstanceDAO communityDAO;
	
	@Autowired
	private InstitutionCampusDAO campusDAO;
	
	@Autowired
	private VisitDAO visitDAO;
	
	@Test
	@Order(1)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveMethodTest() {
		Assertions.assertNotNull(physicalspaceDAO);
		Physicalspace physicalspace = new Physicalspace();
		physicalspace.setPhyspcName("Icesi espacio 1");
		physicalspaceDAO.save(physicalspace);
		Assertions.assertNotNull(physicalspaceDAO.findById(1));
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateMethodTest() {
		String newName = "Javeriana espacio 1";
		Physicalspace existingPhysicalspace = physicalspaceDAO.findById(1);
		existingPhysicalspace.setPhyspcName(newName);
		physicalspaceDAO.update(existingPhysicalspace);
		Assertions.assertEquals(newName, physicalspaceDAO.findById(1).getPhyspcName());
	}
	
	@Test
	@Order(3)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteMethodTest() {
		physicalspaceDAO.delete(physicalspaceDAO.findById(1));
		Assertions.assertNull(physicalspaceDAO.findById(1));
		Assertions.assertEquals(0, physicalspaceDAO.findAll().size());
	}
	
	@Test
	@Order(4)
	public void setScenarioForFinding() {
		Physicalspace physicalspacetype1 = new Physicalspace();
		physicalspacetype1.setPhyspcName("Space 1");
		physicalspacetype1.setPhyspcExtid("1");
		physicalspaceDAO.save(physicalspacetype1);
		
		Physicalspace physicalspacetype2 = new Physicalspace();
		physicalspacetype2.setPhyspcName("Space 2");
		physicalspacetype2.setPhyspcExtid("2");
		physicalspaceDAO.save(physicalspacetype2);
		
		Physicalspace physicalspacetype3 = new Physicalspace();
		physicalspacetype3.setPhyspcName("Space 3");
		physicalspacetype3.setPhyspcExtid("3");
		physicalspaceDAO.save(physicalspacetype3);
		
		Physicalspace physicalspacetype4 = new Physicalspace();
		physicalspacetype4.setPhyspcName("Space 4");
		physicalspacetype4.setPhyspcExtid("4");
		physicalspaceDAO.save(physicalspacetype4);
		
		Physicalspace physicalspacetype5 = new Physicalspace();
		physicalspacetype5.setPhyspcName("Space 5");
		physicalspacetype5.setPhyspcExtid("5");
		physicalspaceDAO.save(physicalspacetype5);
	}
	
	@Test
	@Order(5)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByNameMethodTest() {
		List<Physicalspace> list = physicalspaceDAO.findByName("Space 3");
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals("Space 3", list.get(0).getPhyspcName());
	}
	
	@Test
	@Order(6)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByExtIdMethodTest() {
		List<Physicalspace> list = physicalspaceDAO.findByExtId("5");
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals("Space 5", list.get(0).getPhyspcName());
	}
	
	@Test
	@Order(7)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findAllMethodTest() {
		List<Physicalspace> list = physicalspaceDAO.findAll();
		Assertions.assertEquals(5, list.size());
	}
}
