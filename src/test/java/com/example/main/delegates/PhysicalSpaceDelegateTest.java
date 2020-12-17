package com.example.main.delegates;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import com.example.main.delegate.interfaces.InstitutionDelegate;
import com.example.main.delegate.interfaces.PhysicalSpaceDelegate;
import com.example.main.delegate.interfaces.PhysicalSpaceTypeDelegate;
import com.example.main.model.Institution;
import com.example.main.model.Institutioncampus;
import com.example.main.model.Physicalspace;
import com.example.main.model.Physicalspacetype;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class PhysicalSpaceDelegateTest {
	
	@Autowired
	private PhysicalSpaceDelegate delegate;
	
	@Autowired
	private PhysicalSpaceTypeDelegate pstDelegate;
	
	@Autowired
	private InstitutionCampusDelegate campusDelegate;
	
	@Autowired
	private InstitutionDelegate intDelegate;
	
	@BeforeAll
	public void setScenario() {
		Institution newInst1 = new Institution();
		newInst1.setInstName("Icesi");
		newInst1.setInstAcademicserverurl("https://icesi.com");
		intDelegate.createInstitution(newInst1);
		
		Institution newInst2 = new Institution();
		newInst2.setInstName("Javeriana");
		newInst2.setInstAcademicserverurl("https://javeriana.com");
		intDelegate.createInstitution(newInst2);
		
		Institution newInst3 = new Institution();
		newInst3.setInstName("Univalle");
		newInst3.setInstAcademicserverurl("https://univalle.com");
		intDelegate.createInstitution(newInst2);
		
		Institutioncampus newCampus = new Institutioncampus();
		newCampus.setInstcamName("Icesi pance");
		newCampus.setInstcamOccupation(new BigDecimal(0));
		newCampus.setInstitution(intDelegate.getInstitution(1));
		campusDelegate.createCampus(newCampus);
		
		Institutioncampus newCampus2 = new Institutioncampus();
		newCampus2.setInstcamName("Javeriana Cali");
		newCampus2.setInstcamOccupation(new BigDecimal(0));
		newCampus2.setInstitution(intDelegate.getInstitution(2));
		campusDelegate.createCampus(newCampus2);
		
		Institutioncampus newCampus3 = new Institutioncampus();
		newCampus3.setInstcamName("Univalle Palmira");
		newCampus3.setInstcamOccupation(new BigDecimal(0));
		newCampus3.setInstitution(intDelegate.getInstitution(3));
		campusDelegate.createCampus(newCampus3);
		
		Physicalspacetype newPhysicalSpTy1 = new Physicalspacetype();
		newPhysicalSpTy1.setPhyspctypeName("PSType 1");
		newPhysicalSpTy1.setInstitution(intDelegate.getInstitution(1));
		newPhysicalSpTy1.setPhyspctypeImpliescomm("Estudiantes");
		pstDelegate.createPhysicalSpaceType(newPhysicalSpTy1);
		
		Physicalspacetype newPhysicalSpTy2 = new Physicalspacetype();
		newPhysicalSpTy2.setPhyspctypeName("PSType 2");
		newPhysicalSpTy2.setInstitution(intDelegate.getInstitution(2));
		newPhysicalSpTy2.setPhyspctypeImpliescomm("Estudiantes");
		pstDelegate.createPhysicalSpaceType(newPhysicalSpTy2);
	}
	
	@Test
	@Order(1)
	public void createPhysicalSpaceTypeTest() {
		Physicalspace physicalSpace1 = new Physicalspace();
		physicalSpace1.setPhyspcName("Physical Space 1");
		physicalSpace1.setPhyspcExtid("1");
		
		Assertions.assertEquals(HttpStatus.CREATED, delegate.createPhysicalSpace(physicalSpace1));
		
		Physicalspace physicalSpace2 = new Physicalspace();
		physicalSpace2.setPhyspcName("Physical Space 2");
		physicalSpace2.setPhyspcExtid("1");
		
		Assertions.assertEquals(HttpStatus.CREATED, delegate.createPhysicalSpace(physicalSpace2));
		
		Physicalspace physicalSpace3 = new Physicalspace();
		physicalSpace3.setPhyspcName("Physical Space 1");
		physicalSpace3.setPhyspcExtid("2");
		
		Assertions.assertEquals(HttpStatus.CREATED, delegate.createPhysicalSpace(physicalSpace3));
		
		Physicalspace physicalSpace4 = new Physicalspace();
		physicalSpace4.setPhyspcName("Physical Space 55");
		physicalSpace4.setPhyspcExtid("3");
		
		Assertions.assertEquals(HttpStatus.CREATED, delegate.createPhysicalSpace(physicalSpace4));
	}
	
	@Test
	@Order(2)
	public void getPhysicalSpaceTypeTest() {
		Physicalspace physicalSpace1 = delegate.getPhysicalSpace(1);
		
		Assertions.assertEquals("Physical Space 1", physicalSpace1.getPhyspcName());
		Assertions.assertEquals("1", physicalSpace1.getPhyspcExtid());
	}
	
	@Test
	@Order(3)
	public void getAllPhysicalSpaceTypesTest() {
		Assertions.assertEquals(4, delegate.getAllPhysicalSpaces().size());
	}
	
	@Test
	@Order(4)
	public void findByNameTest() {
		List<Physicalspace> listPhysicalSpace1 = delegate.findByName("Physical Space 1");
		
		Assertions.assertEquals(2, listPhysicalSpace1.size());
		Assertions.assertEquals("Physical Space 1", listPhysicalSpace1.get(0).getPhyspcName());
		Assertions.assertEquals("1", listPhysicalSpace1.get(0).getPhyspcExtid());
		Assertions.assertEquals("Physical Space 1", listPhysicalSpace1.get(1).getPhyspcName());
		Assertions.assertEquals("2", listPhysicalSpace1.get(1).getPhyspcExtid());
		
		listPhysicalSpace1 = delegate.findByName("Physical Space 55");
		
		Assertions.assertEquals(1, listPhysicalSpace1.size());
		Assertions.assertEquals("Physical Space 55", listPhysicalSpace1.get(0).getPhyspcName());
		Assertions.assertEquals("3", listPhysicalSpace1.get(0).getPhyspcExtid());
	}
	
	@Test
	@Order(5)
	public void findByExtIdTest() {
		List<Physicalspace> listPhysicalSpace1 = delegate.findByExtId("1");
		
		Assertions.assertEquals(2, listPhysicalSpace1.size());
		Assertions.assertEquals("Physical Space 1", listPhysicalSpace1.get(0).getPhyspcName());
		Assertions.assertEquals("1", listPhysicalSpace1.get(0).getPhyspcExtid());
		Assertions.assertEquals("Physical Space 2", listPhysicalSpace1.get(1).getPhyspcName());
		Assertions.assertEquals("1", listPhysicalSpace1.get(1).getPhyspcExtid());
		
		listPhysicalSpace1 = delegate.findByExtId("3");
		
		Assertions.assertEquals(1, listPhysicalSpace1.size());
		Assertions.assertEquals("Physical Space 55", listPhysicalSpace1.get(0).getPhyspcName());
		Assertions.assertEquals("3", listPhysicalSpace1.get(0).getPhyspcExtid());
	}
	
	@Test
	@Order(6)
	public void findPhysicalSpacesWithADateRangeTest() {
		
	}
	
	@Test
	@Order(7)
	public void updatePhysicalSpaceTypeTest() {
		Physicalspace physicalSpace = delegate.getPhysicalSpace(4);
		physicalSpace.setPhyspcName("NEW PHYSICAL SPACE NAME");
		delegate.updatePhysicalSpace(4, physicalSpace);
		
		Assertions.assertEquals("NEW PHYSICAL SPACE NAME",  delegate.getPhysicalSpace(4).getPhyspcName());
	}
	
	@Test
	@Order(8)
	public void deletePhysicalSpaceTypeTest() {
		Assertions.assertEquals(4, delegate.getAllPhysicalSpaces().size());
		
		delegate.deletePhysicalSpace(2);
		
		Assertions.assertEquals(3, delegate.getAllPhysicalSpaces().size());
		
		delegate.deletePhysicalSpace(4);
		
		Assertions.assertEquals(2, delegate.getAllPhysicalSpaces().size());
	}
}
