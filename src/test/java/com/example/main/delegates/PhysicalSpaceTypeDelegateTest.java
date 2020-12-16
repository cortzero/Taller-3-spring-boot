package com.example.main.delegates;

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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.main.delegate.interfaces.PhysicalSpaceTypeDelegate;
import com.example.main.model.Physicalspacetype;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class PhysicalSpaceTypeDelegateTest {
	
	@Autowired
	private PhysicalSpaceTypeDelegate delegate;
	
	@Test
	@Order(1)
	public void createPhysicalSpaceTypeTest() {
		Physicalspacetype psType1 = new Physicalspacetype();
		psType1.setPhyspctypeName("PS Type 1");
		psType1.setPhyspctypeExtid("1");
		Assertions.assertEquals(HttpStatus.CREATED, delegate.createPhysicalSpaceType(psType1));
		
		Physicalspacetype psType2 = new Physicalspacetype();
		psType2.setPhyspctypeName("PS Type 2");
		psType2.setPhyspctypeExtid("2");
		Assertions.assertEquals(HttpStatus.CREATED, delegate.createPhysicalSpaceType(psType2));
		
		Physicalspacetype psType3 = new Physicalspacetype();
		psType3.setPhyspctypeName("PS Type 1.2");
		psType3.setPhyspctypeExtid("1");
		Assertions.assertEquals(HttpStatus.CREATED, delegate.createPhysicalSpaceType(psType3));
		
		Physicalspacetype psType4 = new Physicalspacetype();
		psType4.setPhyspctypeName("PS Type 2");
		psType4.setPhyspctypeExtid("44");
		Assertions.assertEquals(HttpStatus.CREATED, delegate.createPhysicalSpaceType(psType4));
	}
	
	@Test
	@Order(2)
	public void getPhysicalSpaceTypeTest() {
		Physicalspacetype psType = delegate.getPhysicalSpaceType(1);
		
		Assertions.assertEquals("PS Type 1", psType.getPhyspctypeName());
		Assertions.assertEquals("1", psType.getPhyspctypeExtid());
	}
	
	@Test
	@Order(3)
	public void findByNameTest() {
		List<Physicalspacetype> listPSType = delegate.findByName("PS Type 1");
		
		Assertions.assertEquals(1, listPSType.size());
		Assertions.assertEquals("PS Type 1", listPSType.get(0).getPhyspctypeName());
		Assertions.assertEquals("1", listPSType.get(0).getPhyspctypeExtid());
		
		listPSType = delegate.findByName("PS Type 2");
		
		Assertions.assertEquals(2, listPSType.size());
		Assertions.assertEquals("PS Type 2", listPSType.get(0).getPhyspctypeName());
		Assertions.assertEquals("2", listPSType.get(0).getPhyspctypeExtid());
		Assertions.assertEquals("PS Type 2", listPSType.get(1).getPhyspctypeName());
		Assertions.assertEquals("44", listPSType.get(1).getPhyspctypeExtid());
	}
	
	@Test
	@Order(4)
	public void findByExtIdTest() {
		List<Physicalspacetype> listPSType = delegate.findByExtId("2");
		
		Assertions.assertEquals(1, listPSType.size());
		Assertions.assertEquals("PS Type 2", listPSType.get(0).getPhyspctypeName());
		Assertions.assertEquals("2", listPSType.get(0).getPhyspctypeExtid());
		
		listPSType = delegate.findByExtId("1");
		
		Assertions.assertEquals(2, listPSType.size());
		Assertions.assertEquals("PS Type 1", listPSType.get(0).getPhyspctypeName());
		Assertions.assertEquals("1", listPSType.get(0).getPhyspctypeExtid());
		Assertions.assertEquals("PS Type 1.2", listPSType.get(1).getPhyspctypeName());
		Assertions.assertEquals("1", listPSType.get(1).getPhyspctypeExtid());
	}
	
	@Test
	@Order(5)
	public void getAllPhysicalSpaceTypesTest() {
		Assertions.assertEquals(4, delegate.getAllPhysicalSpaceTypes().size());
	}
	
	@Test
	@Order(6)
	public void updatePhysicalSpaceTypeTest() {
		Physicalspacetype psType = delegate.getPhysicalSpaceType(1);
		psType.setPhyspctypeName("NEW PS TYPE NAME");
		delegate.updatePhysicalSpaceType(1, psType);
		
		Assertions.assertEquals("NEW PS TYPE NAME", delegate.getPhysicalSpaceType(1).getPhyspctypeName());
	}
	
	@Test
	@Order(7)
	public void deletePhysicalSpaceTypeTest() {
		Assertions.assertEquals(4, delegate.getAllPhysicalSpaceTypes().size());
		
		delegate.deletePhysicalSpaceType(3);
		
		Assertions.assertEquals(3, delegate.getAllPhysicalSpaceTypes().size());
	}
}
