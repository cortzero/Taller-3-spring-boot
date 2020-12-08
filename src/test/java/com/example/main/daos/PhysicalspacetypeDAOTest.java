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

import com.example.main.daos.interfaces.PhysicalspacetypeDAO;
import com.example.main.model.Physicalspacetype;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class PhysicalspacetypeDAOTest {

	@Autowired
	private PhysicalspacetypeDAO physicalSpaceTypeDAO;
	
	@Test
	@Order(1)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveMethodTest() {
		Assertions.assertNotNull(physicalSpaceTypeDAO);
		Physicalspacetype physicalspacetype = new Physicalspacetype();
		physicalspacetype.setPhyspctypeName("Type 1");
		physicalSpaceTypeDAO.save(physicalspacetype);
		Assertions.assertNotNull(physicalSpaceTypeDAO.findById(1));
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateMethodTest() {
		String newName = "Type II";
		Physicalspacetype physicalspacetype = physicalSpaceTypeDAO.findById(1);
		physicalspacetype.setPhyspctypeName(newName);
		physicalSpaceTypeDAO.update(physicalspacetype);
		Assertions.assertEquals(newName, physicalSpaceTypeDAO.findById(1).getPhyspctypeName());
	}
	
	@Test
	@Order(3)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteMethodTest() {
		physicalSpaceTypeDAO.delete(physicalSpaceTypeDAO.findById(1));
		Assertions.assertNull(physicalSpaceTypeDAO.findById(1));
	}
	
	@Test
	@Order(4)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByNameMethodTest() {
		Physicalspacetype physicalspacetype1 = new Physicalspacetype();
		physicalspacetype1.setPhyspctypeName("Type 1");
		physicalSpaceTypeDAO.save(physicalspacetype1);
		
		Physicalspacetype physicalspacetype2 = new Physicalspacetype();
		physicalspacetype2.setPhyspctypeName("Type 2");
		physicalSpaceTypeDAO.save(physicalspacetype2);
		
		Physicalspacetype physicalspacetype3 = new Physicalspacetype();
		physicalspacetype3.setPhyspctypeName("Type 3");
		physicalSpaceTypeDAO.save(physicalspacetype3);
		
		Physicalspacetype physicalspacetype4 = new Physicalspacetype();
		physicalspacetype4.setPhyspctypeName("Type 4");
		physicalSpaceTypeDAO.save(physicalspacetype4);
		
		Physicalspacetype physicalspacetype5 = new Physicalspacetype();
		physicalspacetype5.setPhyspctypeName("Type 5");
		physicalSpaceTypeDAO.save(physicalspacetype5);
		
		List<Physicalspacetype> list = physicalSpaceTypeDAO.findByName("Type 4");
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals("Type 4", list.get(0).getPhyspctypeName());
	}
	
	@Test
	@Order(5)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByExtIdMethodTest() {
		Physicalspacetype physicalspacetype1 = new Physicalspacetype();
		physicalspacetype1.setPhyspctypeName("Type 1");
		physicalspacetype1.setPhyspctypeExtid("1");
		physicalSpaceTypeDAO.save(physicalspacetype1);
		
		Physicalspacetype physicalspacetype2 = new Physicalspacetype();
		physicalspacetype2.setPhyspctypeName("Type 2");
		physicalspacetype2.setPhyspctypeExtid("2");
		physicalSpaceTypeDAO.save(physicalspacetype2);
		
		Physicalspacetype physicalspacetype3 = new Physicalspacetype();
		physicalspacetype3.setPhyspctypeName("Type 3");
		physicalspacetype3.setPhyspctypeExtid("3");
		physicalSpaceTypeDAO.save(physicalspacetype3);
		
		Physicalspacetype physicalspacetype4 = new Physicalspacetype();
		physicalspacetype4.setPhyspctypeName("Type 4");
		physicalspacetype4.setPhyspctypeExtid("4");
		physicalSpaceTypeDAO.save(physicalspacetype4);
		
		Physicalspacetype physicalspacetype5 = new Physicalspacetype();
		physicalspacetype5.setPhyspctypeName("Type 5");
		physicalspacetype5.setPhyspctypeExtid("5");
		physicalSpaceTypeDAO.save(physicalspacetype5);
		
		List<Physicalspacetype> list = physicalSpaceTypeDAO.findByExtId("2");
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals("Type 2", list.get(0).getPhyspctypeName());
	}
	
	@Test
	@Order(6)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findAllMethodTest() {
		Physicalspacetype physicalspacetype1 = new Physicalspacetype();
		physicalspacetype1.setPhyspctypeName("Type 1");
		physicalspacetype1.setPhyspctypeExtid("1");
		physicalSpaceTypeDAO.save(physicalspacetype1);
		
		Physicalspacetype physicalspacetype2 = new Physicalspacetype();
		physicalspacetype2.setPhyspctypeName("Type 2");
		physicalspacetype2.setPhyspctypeExtid("2");
		physicalSpaceTypeDAO.save(physicalspacetype2);
		
		Physicalspacetype physicalspacetype3 = new Physicalspacetype();
		physicalspacetype3.setPhyspctypeName("Type 3");
		physicalspacetype3.setPhyspctypeExtid("3");
		physicalSpaceTypeDAO.save(physicalspacetype3);
		
		Physicalspacetype physicalspacetype4 = new Physicalspacetype();
		physicalspacetype4.setPhyspctypeName("Type 4");
		physicalspacetype4.setPhyspctypeExtid("4");
		physicalSpaceTypeDAO.save(physicalspacetype4);
		
		Physicalspacetype physicalspacetype5 = new Physicalspacetype();
		physicalspacetype5.setPhyspctypeName("Type 5");
		physicalspacetype5.setPhyspctypeExtid("5");
		physicalSpaceTypeDAO.save(physicalspacetype5);
		
		List<Physicalspacetype> list = physicalSpaceTypeDAO.findAll();
		Assertions.assertEquals(15, list.size());
	}
}
