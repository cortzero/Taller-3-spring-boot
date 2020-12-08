package com.example.main.integration;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.main.exceptions.CampusWithNoZeroOccupationException;
import com.example.main.exceptions.CampusWithoutNameException;
import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.No5DigitsExternalIDException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutInstitutionException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.model.Institutioncampus;
import com.example.main.model.Physicalspace;
import com.example.main.model.Physicalspacetype;
import com.example.main.services.interfaces.InstitutionCampusService;
import com.example.main.services.interfaces.InstitutionService;
import com.example.main.services.interfaces.PhysicalSpaceService;
import com.example.main.services.interfaces.PhysicalSpaceTypeService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PhysicalSpaceServiceTest {
	
	@Autowired
	private PhysicalSpaceService phyService;
	
	@Autowired
	private InstitutionCampusService campusService;
	
	@Autowired
	private PhysicalSpaceTypeService phyTyService;
	
	@Autowired
	private InstitutionService instService;
	
	@BeforeAll
	public void setupCampusAndPhysicalSpTys() throws NoSuchElementException, CampusWithoutNameException, CampusWithNoZeroOccupationException, PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException, URLWithoutProtocolException, InstitutionWithoutNameException {
		Institution newInst1 = new Institution();
		newInst1.setInstName("Icesi");
		newInst1.setInstAcademicserverurl("https://icesi.com");
		instService.save(newInst1);
		
		Institution newInst2 = new Institution();
		newInst2.setInstName("Javeriana");
		newInst2.setInstAcademicserverurl("https://javeriana.com");
		instService.save(newInst2);
		
		Institution newInst3 = new Institution();
		newInst3.setInstName("Univalle");
		newInst3.setInstAcademicserverurl("https://univalle.com");
		instService.save(newInst3);
		
		Institutioncampus newCampus = new Institutioncampus();
		newCampus.setInstcamName("Icesi pance");
		newCampus.setInstcamOccupation(new BigDecimal(0));
		newCampus.setInstitution(newInst1);
		campusService.save(newCampus);
		
		Institutioncampus newCampus2 = new Institutioncampus();
		newCampus2.setInstcamName("Javeriana Cali");
		newCampus2.setInstcamOccupation(new BigDecimal(0));
		newCampus2.setInstitution(newInst2);
		campusService.save(newCampus2);
		
		Institutioncampus newCampus3 = new Institutioncampus();
		newCampus3.setInstcamName("Univalle Palmira");
		newCampus3.setInstcamOccupation(new BigDecimal(0));
		newCampus3.setInstitution(newInst3);
		campusService.save(newCampus3);
		
		Physicalspacetype newPhysicalSpTy1 = new Physicalspacetype();
		newPhysicalSpTy1.setPhyspctypeName("PSType 1");
		newPhysicalSpTy1.setInstitution(newInst1);
		newPhysicalSpTy1.setPhyspctypeImpliescomm("Estudiantes");
		phyTyService.save(newPhysicalSpTy1);
		
		Physicalspacetype newPhysicalSpTy2 = new Physicalspacetype();
		newPhysicalSpTy2.setPhyspctypeName("PSType 2");
		newPhysicalSpTy2.setInstitution(newInst2);
		newPhysicalSpTy2.setPhyspctypeImpliescomm("Estudiantes");
		phyTyService.save(newPhysicalSpTy2);
	}

	//----------------------- PRUEBAS DE GUARDADO -----------------------
	
	/**
	 * Prueba 1: Guardar un espacio físico asociado a un campus y un tipo de espacio físicos existentes en 
	 * la base de datos y con un external id de 5 dígitos.
	 * Guarda la entidad
	 */
	@Test
	@Order(1)
	public void savePhysicalSpaceCorrectly() {
		Physicalspace physSpace = new Physicalspace();
		physSpace.setInstitutioncampus(campusService.findById(1));
		physSpace.setPhysicalspacetype(phyTyService.findById(1));
		physSpace.setPhyspcExtid("12345");
		
		Assertions.assertDoesNotThrow(() -> phyService.save(physSpace));
	}
	
	/**
	 * Prueba 2: Guardar un espacio físico asociado a un campus no existente en la base de datos pero un 
	 * tipo de espacio físicos que sí está registrado, además de un external id de 5 dígitos.
	 * No guarda la entidad
	 */
	@Test
	@Order(2)
	public void savePhysicalSpaceWithNonExistingCampus() {
		Institutioncampus nonExistingCampus = new Institutioncampus();
		
		Physicalspace physSpace = new Physicalspace();
		physSpace.setPhyspcId(2);
		physSpace.setInstitutioncampus(nonExistingCampus);
		physSpace.setPhysicalspacetype(phyTyService.findById(1));
		physSpace.setPhyspcExtid("12345");
		
		Assertions.assertThrows(NoSuchElementException.class, () -> phyService.save(physSpace));
	}
	
	/**
	 * Prueba 3: Guardar un espacio físico asociado a un campus y un tipo de espacio físicos no existentes 
	 * en la base de datos y con un external id de 5 dígitos.
	 * No guarda la entidad
	 */
	@Test
	@Order(3)
	public void savePhysicalSpaceWithNonExistingCampusAndPhysicalSpaceType() {
		Institutioncampus nonExistingCampus = new Institutioncampus();
		
		Physicalspacetype nonExistingPhySpTy = new Physicalspacetype();
		
		Physicalspace physSpace = new Physicalspace();
		physSpace.setInstitutioncampus(nonExistingCampus);
		physSpace.setPhysicalspacetype(nonExistingPhySpTy);
		physSpace.setPhyspcExtid("12345");
		
		Assertions.assertThrows(NoSuchElementException.class, () -> phyService.save(physSpace));
	}
	
	/**
	 * Prueba 4: Guardar un espacio físico asociado a un campus y un tipo de espacio físicos existentes 
	 * en la base de datos y con un external id de más de 5 dígitos.
	 * No guarda la entidad
	 */
	@Test
	@Order(4)
	public void savePhysicalSpaceWithMoreThan5DigitsExtID() {
		Physicalspace physSpace = new Physicalspace();
		physSpace.setInstitutioncampus(campusService.findById(1));
		physSpace.setPhysicalspacetype(phyTyService.findById(1));
		physSpace.setPhyspcExtid("123456789");
		
		Assertions.assertThrows(No5DigitsExternalIDException.class, () -> phyService.save(physSpace));
	}
	
	/**
	 * Prueba 5: Guardar un espacio físico asociado a un campus y un tipo de espacio físicos existentes 
	 * en la base de datos y con un external id con menos de 5 dígitos.
	 * No guarda la entidad
	 */
	@Test
	@Order(5)
	public void savePhysicalSpaceWithLessThan5DigitsExtID() {
		Physicalspace physSpace = new Physicalspace();
		physSpace.setInstitutioncampus(campusService.findById(2));
		physSpace.setPhysicalspacetype(phyTyService.findById(2));
		physSpace.setPhyspcExtid("12");
		
		Assertions.assertThrows(No5DigitsExternalIDException.class, () -> phyService.save(physSpace));
	}
	
	/**
	 * Prueba 6: Guardar un espacio físico asociado a un campus y un tipo de espacio físicos existentes 
	 * en la base de datos pero sin external id.
	 * Guarda la entidad
	 */
	@Test
	@Order(6)
	public void savePhysicalSpaceWithNoExtID() {
		Physicalspace physSpace = new Physicalspace();
		physSpace.setInstitutioncampus(campusService.findById(1));
		physSpace.setPhysicalspacetype(phyTyService.findById(2));
		physSpace.setPhyspcExtid("");
		
		Assertions.assertDoesNotThrow(() -> phyService.save(physSpace));
	}
	
	//----------------------- PRUEBAS DE EDICION -----------------------
	
//	@BeforeEach
//	public void setPhysicalSpace() throws NumberFormatException, NoSuchElementException, No5DigitsExternalIDException {
//		Physicalspace physSpace = new Physicalspace();
//		physSpace.setInstitutioncampus(campusService.findById(1));
//		physSpace.setPhysicalspacetype(phyTyService.findById(1));
//		physSpace.setPhyspcExtid("98765");
//		phyService.save(physSpace);
//	}
	
	/**
	 * Prueba 7: Editar la información de un espacio físico llenándo los campos necesarios correctamente.
	 */
	@Test
	@Order(7)
	public void editPhysicalSpaceCorrectly() {
		Physicalspace existingPhySp = phyService.findById(1);
		existingPhySp.setInstitutioncampus(campusService.findById(3));
		existingPhySp.setPhysicalspacetype(phyTyService.findById(2));
		existingPhySp.setPhyspcExtid("56789");
		
		Assertions.assertDoesNotThrow(() -> phyService.update(existingPhySp));
		Assertions.assertEquals("56789", phyService.findById(1).getPhyspcExtid());
		Assertions.assertEquals("Univalle Palmira", phyService.findById(1).getInstitutioncampus().getInstcamName());
		Assertions.assertEquals("PSType 2", phyService.findById(1).getPhysicalspacetype().getPhyspctypeName());
	}
	
	/**
	 * Prueba 8: Editar la información de un espacio físico llenándo los campos necesarios correctamente 
	 * pero dejando vacío el external id.
	 */
	@Test
	@Order(8)
	public void editPhysicalSpaceWithNoExtID() {
		Physicalspace existingPhySp = phyService.findById(1);
		existingPhySp.setInstitutioncampus(campusService.findById(3));
		existingPhySp.setPhysicalspacetype(phyTyService.findById(2));
		existingPhySp.setPhyspcExtid("");
		
		Assertions.assertDoesNotThrow(() -> phyService.update(existingPhySp));
		Assertions.assertEquals("", phyService.findById(1).getPhyspcExtid());
		Assertions.assertEquals("Univalle Palmira", phyService.findById(1).getInstitutioncampus().getInstcamName());
		Assertions.assertEquals("PSType 2", phyService.findById(1).getPhysicalspacetype().getPhyspctypeName());
	}
	
	/**
	 * Prueba 9: Editar la información de un espacio físico sin asignar el campus pero asignándo un 
	 * espacio físico y un external id con 5 dígitos.
	 */
	@Test
	@Order(9)
	public void editPhysicalSpaceWithoutCampus() {
		Physicalspace existingPhySp = phyService.findById(1);
		existingPhySp.setInstitutioncampus(null);
		existingPhySp.setPhysicalspacetype(phyTyService.findById(2));
		existingPhySp.setPhyspcExtid("56789");
		
		Assertions.assertThrows(NoSuchElementException.class, () -> phyService.update(existingPhySp));
	}
	
	/**
	 * Prueba 10: Editar la información de un espacio físico llenándo los campos necesarios 
	 * correctamente pero asignándo un external id con menos de 5 dígitos.
	 */
	@Test
	@Order(10)
	public void editPhysicalSpaceWithLessThan5DigitsExtID() {
		Physicalspace existingPhySp = phyService.findById(1);
		existingPhySp.setInstitutioncampus(campusService.findById(3));
		existingPhySp.setPhysicalspacetype(phyTyService.findById(2));
		existingPhySp.setPhyspcExtid("56");
		
		Assertions.assertThrows(No5DigitsExternalIDException.class, () -> phyService.update(existingPhySp));
	}
	
	//----------------------- PRUEBAS DE BUSQUEDA -----------------------
	
	@Test
	@Order(11)
	public void setScenarioForFinding() throws NumberFormatException, NoSuchElementException, No5DigitsExternalIDException {
		Physicalspace physSpace = new Physicalspace();
		physSpace.setInstitutioncampus(campusService.findById(1));
		physSpace.setPhysicalspacetype(phyTyService.findById(1));
		physSpace.setPhyspcName("Physical space 1");
		physSpace.setPhyspcExtid("56743");
		phyService.save(physSpace);
		
		Physicalspace physSpace2 = new Physicalspace();
		physSpace2.setInstitutioncampus(campusService.findById(2));
		physSpace2.setPhysicalspacetype(phyTyService.findById(2));
		physSpace2.setPhyspcName("REPEATED space 1");
		physSpace2.setPhyspcExtid("00000");
		phyService.save(physSpace2);
		
		Physicalspace physSpace3 = new Physicalspace();
		physSpace3.setInstitutioncampus(campusService.findById(3));
		physSpace3.setPhysicalspacetype(phyTyService.findById(1));
		physSpace3.setPhyspcName("REPEATED space 1");
		physSpace3.setPhyspcExtid("11111");
		phyService.save(physSpace3);
		
		Physicalspace physSpace4 = new Physicalspace();
		physSpace4.setInstitutioncampus(campusService.findById(2));
		physSpace4.setPhysicalspacetype(phyTyService.findById(2));
		physSpace4.setPhyspcName("Space 2");
		physSpace4.setPhyspcExtid("11111");
		phyService.save(physSpace4);
	}
	
	@Test
	@Order(12)
	public void findByNameTest() {
		String name = "Physical space 1";
		List<Physicalspace> list = phyService.findByName(name);
		Assertions.assertEquals(1, list.size());
	}
	
	@Test
	@Order(13)
	public void findByARepeatedNameTest() {
		String name = "REPEATED space 1";
		List<Physicalspace> list = phyService.findByName(name);
		Assertions.assertEquals(2, list.size());
	}
	
	@Test
	@Order(14)
	public void findByExtIdTest() {
		String extId = "00000";
		List<Physicalspace> list = phyService.findByExtId(extId);
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals("REPEATED space 1", list.get(0).getPhyspcName());
	}
	
	@Test
	@Order(15)
	public void findByARepeatedExtIdTest() {
		String extId = "11111";
		List<Physicalspace> list = phyService.findByExtId(extId);
		Assertions.assertEquals(2, list.size());
	}
	
	@Test
	@Order(16)
	public void findAllTest() {
		List<Physicalspace> list = phyService.findAll();
		Assertions.assertEquals(6, list.size());
	}
}
