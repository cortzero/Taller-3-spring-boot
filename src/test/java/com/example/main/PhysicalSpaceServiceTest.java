package com.example.main;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import com.example.main.repositories.PhysicalSpaceRepository;
import com.example.main.services.interfaces.InstitutionCampusService;
import com.example.main.services.interfaces.InstitutionService;
import com.example.main.services.interfaces.PhysicalSpaceService;
import com.example.main.services.interfaces.PhysicalSpaceTypeService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PhysicalSpaceServiceTest {
	
	@Autowired
	private PhysicalSpaceService phyService;
	
	@Mock
	private PhysicalSpaceRepository phyRepository;
	
	@Autowired
	private InstitutionCampusService campusService;
	
	@Autowired
	private PhysicalSpaceTypeService phyTyService;
	
	@Autowired
	private InstitutionService instService;
	
	@BeforeAll
	public void setupCampusAndPhysicalSpTys() throws NoSuchElementException, CampusWithoutNameException, CampusWithNoZeroOccupationException, PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException, URLWithoutProtocolException, InstitutionWithoutNameException {
		Institution newInst1 = new Institution();
		newInst1.setInstId(1);
		newInst1.setInstName("Icesi");
		newInst1.setInstAcademicserverurl("https://icesi.com");
		instService.saveInstitution(newInst1);
		
		Institution newInst2 = new Institution();
		newInst2.setInstId(2);
		newInst2.setInstName("Javeriana");
		newInst2.setInstAcademicserverurl("https://javeriana.com");
		instService.saveInstitution(newInst2);
		
		Institution newInst3 = new Institution();
		newInst3.setInstId(3);
		newInst3.setInstName("Univalle");
		newInst3.setInstAcademicserverurl("https://univalle.com");
		instService.saveInstitution(newInst3);
		
		Institutioncampus newCampus = new Institutioncampus();
		newCampus.setInstcamId(1);
		newCampus.setInstcamName("Icesi pance");
		newCampus.setInstcamOccupation(new BigDecimal(0));
		newCampus.setInstitution(newInst1);
		campusService.saveInstitutionCampus(newCampus);
		
		Institutioncampus newCampus2 = new Institutioncampus();
		newCampus2.setInstcamId(2);
		newCampus2.setInstcamName("Javeriana Cali");
		newCampus2.setInstcamOccupation(new BigDecimal(0));
		newCampus2.setInstitution(newInst2);
		campusService.saveInstitutionCampus(newCampus2);
		
		Institutioncampus newCampus3 = new Institutioncampus();
		newCampus3.setInstcamId(3);
		newCampus3.setInstcamName("Univalle Palmira");
		newCampus3.setInstcamOccupation(new BigDecimal(0));
		newCampus3.setInstitution(newInst3);
		campusService.saveInstitutionCampus(newCampus3);
		
		Physicalspacetype newPhysicalSpTy1 = new Physicalspacetype();
		newPhysicalSpTy1.setPhyspctypeId(1);
		newPhysicalSpTy1.setPhyspctypeName("PSType 1");
		newPhysicalSpTy1.setInstitution(newInst1);
		newPhysicalSpTy1.setPhyspctypeImpliescomm("Estudiantes");
		phyTyService.savePhysicalSpaceType(newPhysicalSpTy1);
		
		Physicalspacetype newPhysicalSpTy2 = new Physicalspacetype();
		newPhysicalSpTy2.setPhyspctypeId(2);
		newPhysicalSpTy2.setPhyspctypeName("PSType 2");
		newPhysicalSpTy2.setInstitution(newInst2);
		newPhysicalSpTy2.setPhyspctypeImpliescomm("Estudiantes");
		phyTyService.savePhysicalSpaceType(newPhysicalSpTy2);
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class Creation {
		
		/**
		 * Prueba 1: Guardar un espacio físico asociado a un campus y un tipo de espacio físicos existentes en 
		 * la base de datos y con un external id de 5 dígitos.
		 */
		@Test
		public void savePhysicalSpaceCorrectly() {
			Physicalspace physSpace = new Physicalspace();
			physSpace.setPhyspcId(1);
			physSpace.setInstitutioncampus(campusService.findById(1).get());
			physSpace.setPhysicalspacetype(phyTyService.findById(1).get());
			physSpace.setPhyspcExtid("12345");
			
			Assertions.assertDoesNotThrow(() -> phyService.savePhysicalSpace(physSpace));
			Assertions.assertEquals(1, phyService.getAmountOfPhysicalSpaces());
		}
		
		/**
		 * Prueba 2: Guardar un espacio físico asociado a un campus no existente en la base de datos pero un 
		 * tipo de espacio físicos que sí está registrado, además de un external id de 5 dígitos.
		 */
		@Test
		public void savePhysicalSpaceWithNonExistingCampus() {
			Institutioncampus nonExistingCampus = new Institutioncampus();
			nonExistingCampus.setInstcamId(33);
			
			Physicalspace physSpace = new Physicalspace();
			physSpace.setPhyspcId(2);
			physSpace.setInstitutioncampus(nonExistingCampus);
			physSpace.setPhysicalspacetype(phyTyService.findById(1).get());
			physSpace.setPhyspcExtid("12345");
			
			Assertions.assertThrows(NoSuchElementException.class, () -> phyService.savePhysicalSpace(physSpace));
		}
		
		/**
		 * Prueba 3: Guardar un espacio físico asociado a un campus y un tipo de espacio físicos no existentes 
		 * en la base de datos y con un external id de 5 dígitos.
		 */
		@Test
		public void savePhysicalSpaceWithNonExistingCampusAndPhysicalSpaceType() {
			Institutioncampus nonExistingCampus = new Institutioncampus();
			nonExistingCampus.setInstcamId(11);
			
			Physicalspacetype nonExistingPhySpTy = new Physicalspacetype();
			nonExistingPhySpTy.setPhyspctypeId(22);
			
			Physicalspace physSpace = new Physicalspace();
			physSpace.setPhyspcId(2);
			physSpace.setInstitutioncampus(nonExistingCampus);
			physSpace.setPhysicalspacetype(nonExistingPhySpTy);
			physSpace.setPhyspcExtid("12345");
			
			Assertions.assertThrows(NoSuchElementException.class, () -> phyService.savePhysicalSpace(physSpace));
		}
		
		/**
		 * Prueba 4: Guardar un espacio físico asociado a un campus y un tipo de espacio físicos existentes 
		 * en la base de datos y con un external id de más de 5 dígitos.
		 */
		@Test
		public void savePhysicalSpaceWithMoreThan5DigitsExtID() {
			Physicalspace physSpace = new Physicalspace();
			physSpace.setPhyspcId(4);
			physSpace.setInstitutioncampus(campusService.findById(1).get());
			physSpace.setPhysicalspacetype(phyTyService.findById(1).get());
			physSpace.setPhyspcExtid("123456789");
			
			Assertions.assertThrows(No5DigitsExternalIDException.class, () -> phyService.savePhysicalSpace(physSpace));
		}
		
		/**
		 * Prueba 5: Guardar un espacio físico asociado a un campus y un tipo de espacio físicos existentes 
		 * en la base de datos y con un external id con menos de 5 dígitos.
		 */
		@Test
		public void savePhysicalSpaceWithLessThan5DigitsExtID() {
			Physicalspace physSpace = new Physicalspace();
			physSpace.setPhyspcId(5);
			physSpace.setInstitutioncampus(campusService.findById(2).get());
			physSpace.setPhysicalspacetype(phyTyService.findById(2).get());
			physSpace.setPhyspcExtid("12");
			
			Assertions.assertThrows(No5DigitsExternalIDException.class, () -> phyService.savePhysicalSpace(physSpace));
		}
		
		/**
		 * Prueba 6: Guardar un espacio físico asociado a un campus y un tipo de espacio físicos existentes 
		 * en la base de datos pero sin external id.
		 */
		@Test
		public void savePhysicalSpaceWithNoExtID() {
			Physicalspace physSpace = new Physicalspace();
			physSpace.setPhyspcId(6);
			physSpace.setInstitutioncampus(campusService.findById(1).get());
			physSpace.setPhysicalspacetype(phyTyService.findById(2).get());
			physSpace.setPhyspcExtid("");
			
			Assertions.assertDoesNotThrow(() -> phyService.savePhysicalSpace(physSpace));
			Assertions.assertEquals(2, phyService.getAmountOfPhysicalSpaces());
		}
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class Edition {
		
		@BeforeEach
		public void setPhysicalSpace() throws NumberFormatException, NoSuchElementException, No5DigitsExternalIDException {
			Physicalspace physSpace = new Physicalspace();
			physSpace.setPhyspcId(1);
			physSpace.setInstitutioncampus(campusService.findById(1).get());
			physSpace.setPhysicalspacetype(phyTyService.findById(1).get());
			physSpace.setPhyspcExtid("12345");
			phyService.savePhysicalSpace(physSpace);
		}
		
		/**
		 * Prueba 7: Editar la información de un espacio físico llenándo los campos necesarios correctamente.
		 */
		@Test
		public void editPhysicalSpaceCorrectly() {
			Physicalspace existingPhySp = phyService.findById(1).get();
			existingPhySp.setInstitutioncampus(campusService.findById(3).get());
			existingPhySp.setPhysicalspacetype(phyTyService.findById(2).get());
			existingPhySp.setPhyspcExtid("56789");
			
			Assertions.assertDoesNotThrow(() -> phyService.editPhysicalSpace(existingPhySp));
			Assertions.assertEquals("56789", phyService.findById(1).get().getPhyspcExtid());
			Assertions.assertEquals("Univalle Palmira", phyService.findById(1).get().getInstitutioncampus().getInstcamName());
			Assertions.assertEquals("PSType 2", phyService.findById(1).get().getPhysicalspacetype().getPhyspctypeName());
		}
		
		/**
		 * Prueba 8: Editar la información de un espacio físico llenándo los campos necesarios correctamente 
		 * pero dejando vacío el external id.
		 */
		@Test
		public void editPhysicalSpaceWithNoExtID() {
			Physicalspace existingPhySp = phyService.findById(1).get();
			existingPhySp.setInstitutioncampus(campusService.findById(3).get());
			existingPhySp.setPhysicalspacetype(phyTyService.findById(2).get());
			existingPhySp.setPhyspcExtid("");
			
			Assertions.assertDoesNotThrow(() -> phyService.editPhysicalSpace(existingPhySp));
			Assertions.assertEquals("", phyService.findById(1).get().getPhyspcExtid());
			Assertions.assertEquals("Univalle Palmira", phyService.findById(1).get().getInstitutioncampus().getInstcamName());
			Assertions.assertEquals("PSType 2", phyService.findById(1).get().getPhysicalspacetype().getPhyspctypeName());
		}
		
		/**
		 * Prueba 9: Editar la información de un espacio físico sin asignar el campus pero asignándo un 
		 * espacio físico y un external id con 5 dígitos.
		 */
		@Test
		public void editPhysicalSpaceWithoutCampus() {
			Physicalspace existingPhySp = phyService.findById(1).get();
			existingPhySp.setInstitutioncampus(null);
			existingPhySp.setPhysicalspacetype(phyTyService.findById(2).get());
			existingPhySp.setPhyspcExtid("56789");
			
			Assertions.assertThrows(NoSuchElementException.class, () -> phyService.editPhysicalSpace(existingPhySp));
		}
		
		/**
		 * Prueba 10: Editar la información de un espacio físico llenándo los campos necesarios 
		 * correctamente pero asignándo un external id con menos de 5 dígitos.
		 */
		@Test
		public void editPhysicalSpaceWithLessThan5DigitsExtID() {
			Physicalspace existingPhySp = phyService.findById(1).get();
			existingPhySp.setInstitutioncampus(campusService.findById(3).get());
			existingPhySp.setPhysicalspacetype(phyTyService.findById(2).get());
			existingPhySp.setPhyspcExtid("56");
			
			Assertions.assertThrows(No5DigitsExternalIDException.class, () -> phyService.editPhysicalSpace(existingPhySp));
		}
	}

}
