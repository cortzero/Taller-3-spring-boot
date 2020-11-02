package com.example.main;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutInstitutionException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.model.Physicalspacetype;
import com.example.main.repositories.PhysicalSpaceTypeRepository;
import com.example.main.services.implementations.InstitutionServiceImpl;
import com.example.main.services.interfaces.PhysicalSpaceTypeService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PhysicalSpaceTypeServiceTest {

	@Autowired
	private PhysicalSpaceTypeService service;
	
	@Mock
	private PhysicalSpaceTypeRepository repository;
	
	@Autowired
	private InstitutionServiceImpl instService;
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class Creation {
		
		@BeforeAll
		public void setScene() throws URLWithoutProtocolException, InstitutionWithoutNameException {
			Institution inst = new Institution();
			inst.setInstId(1);
			inst.setInstAcademicserverurl("https://icesi.com");
			inst.setInstName("Icesi");
			instService.saveInstitution(inst);
		}
		
		/**
		 * Prueba 1: Guardar un tipo de espacio físico con nombre, asociado a una institución existente y que implique comunidad.
		 */
		@Test
		public void savePhysicalSpaceTypeCorrectly() {
			Physicalspacetype newPhysicalSpTy = new Physicalspacetype();
			newPhysicalSpTy.setPhyspctypeId(1);
			newPhysicalSpTy.setPhyspctypeName("Physical SP 1");
			newPhysicalSpTy.setInstitution(instService.findById(1).get());
			newPhysicalSpTy.setPhyspctypeImpliescomm("Estudiantes");
			Assertions.assertDoesNotThrow(() -> service.savePhysicalSpaceType(newPhysicalSpTy));
		}
		
		/**
		 * Prueba 2: Guardar un tipo de espacio físico sin nombre, asociado a una institución existente y que implique comunidad.
		 */
		@Test
		public void savePhysicalSpaceTypeWithoutName() {
			Physicalspacetype newPhysicalSpTy = new Physicalspacetype();
			newPhysicalSpTy.setPhyspctypeId(2);
			newPhysicalSpTy.setInstitution(instService.findById(1).get());
			newPhysicalSpTy.setPhyspctypeImpliescomm("Estudiantes");
			Assertions.assertThrows(PhysicalSpaceTypeWithoutNameException.class, () -> service.savePhysicalSpaceType(newPhysicalSpTy));
		}
		
		/**
		 * Prueba 3: Guardar un tipo de espacio físico con nombre, pero sin una institución asociada y que implique comunidad.
		 */
		@Test
		public void savePhysicalSpaceTypeWithoutInstitution() {
			Physicalspacetype newPhysicalSpTy = new Physicalspacetype();
			newPhysicalSpTy.setPhyspctypeId(3);
			newPhysicalSpTy.setPhyspctypeName("Physical SP 2");
			newPhysicalSpTy.setPhyspctypeImpliescomm("Estudiantes");
			Assertions.assertThrows(PhysicalSpaceTypeWithoutInstitutionException.class, () -> service.savePhysicalSpaceType(newPhysicalSpTy));
		}
		
		/**
		 * Prueba 4: Guardar un tipo de espacio físico con nombre, pero con una institución asociada que no existe y que implique comunidad.
		 */
		@Test
		public void savePhysicalSpaceTypeWithNonExistingInstitution() {
			Institution inst2 = new Institution();
			inst2.setInstId(2);
			inst2.setInstAcademicserverurl("https://anonymous.com");
			inst2.setInstName("Anonymous");
			
			Physicalspacetype newPhysicalSpTy = new Physicalspacetype();
			newPhysicalSpTy.setPhyspctypeId(4);
			newPhysicalSpTy.setPhyspctypeName("Physical SP 1");
			newPhysicalSpTy.setInstitution(inst2);
			newPhysicalSpTy.setPhyspctypeImpliescomm("Estudiantes");
			Assertions.assertThrows(NoSuchElementException.class, () -> service.savePhysicalSpaceType(newPhysicalSpTy));
		}
		
		/**
		 * Prueba 5: Guardar un tipo de espacio físico con nombre, asociado a una institución existente y que no implique comunidad.
		 */
		@Test
		public void savePhysicalSpaceTypeWithoutCommunity() {
			Physicalspacetype newPhysicalSpTy = new Physicalspacetype();
			newPhysicalSpTy.setPhyspctypeId(5);
			newPhysicalSpTy.setPhyspctypeName("Physical SP 1");
			newPhysicalSpTy.setInstitution(instService.findById(1).get());
			Assertions.assertDoesNotThrow(() -> service.savePhysicalSpaceType(newPhysicalSpTy));
		}
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class Edition {
		
		@BeforeAll
		public void setScene() throws URLWithoutProtocolException, InstitutionWithoutNameException, NoSuchElementException, PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException {
			Institution inst = new Institution();
			inst.setInstId(1);
			inst.setInstName("Icesi");
			inst.setInstAcademicserverurl("https://icesi.com");
			instService.saveInstitution(inst);
			
			Institution inst2 = new Institution();
			inst2.setInstId(2);
			inst2.setInstName("Univalle");
			inst2.setInstAcademicserverurl("https://univalle.com");
			instService.saveInstitution(inst2);
			
			Institution inst3 = new Institution();
			inst3.setInstId(3);
			inst3.setInstName("Javeriana");
			inst3.setInstAcademicserverurl("https://javeriana.com");
			instService.saveInstitution(inst3);			
		}
		
		@BeforeEach
		public void setPhysicalSpaceType() throws NoSuchElementException, PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException {
			Physicalspacetype newPhysicalSpTy1 = new Physicalspacetype();
			newPhysicalSpTy1.setPhyspctypeId(1);
			newPhysicalSpTy1.setPhyspctypeName("Physical SP 1");
			newPhysicalSpTy1.setInstitution(instService.findById(3).get());
			newPhysicalSpTy1.setPhyspctypeImpliescomm("Estudiantes");
			service.savePhysicalSpaceType(newPhysicalSpTy1);
		}
		
		/**
		 * Prueba 6: Editar un tipo de espacio físico llenando el campo de nombre y asignándole una nueva institución.
		 */
		@Test
		public void editPhysicalSpaceTypeCorretly() {
			String newName = "Space 2";
			Physicalspacetype existingPhysSpt = service.findById(1).get();
			existingPhysSpt.setPhyspctypeName(newName);
			existingPhysSpt.setInstitution(instService.findById(1).get());
			Assertions.assertDoesNotThrow(() -> service.editPhysicalSpaceType(existingPhysSpt));
			Assertions.assertEquals(newName, service.findById(1).get().getPhyspctypeName());
			Assertions.assertEquals("Icesi", service.findById(1).get().getInstitution().getInstName());
		}
		
		/**
		 * Prueba 7: Editar un tipo de espacio físico sin llenar el campo de nombre y asignándole una nueva institución.
		 */
		@Test
		public void editPhysicalSpaceTypeWithoutName() {
			Physicalspacetype existingPhysSpt = service.findById(1).get();
			existingPhysSpt.setPhyspctypeName("");
			existingPhysSpt.setInstitution(instService.findById(1).get());
			Assertions.assertThrows(PhysicalSpaceTypeWithoutNameException.class, () -> service.editPhysicalSpaceType(existingPhysSpt));
		}
		
		/**
		 * Prueba 8: Editar un tipo de espacio físico llenando el campo de nombre pero sin asignarle una nueva institución.
		 */
		@Test
		public void editPhysicalSpaceTypeWithoutInstitution() {
			Physicalspacetype existingPhysSpt = service.findById(1).get();
			existingPhysSpt.setPhyspctypeName("Space 3");
			existingPhysSpt.setInstitution(null);
			Assertions.assertThrows(PhysicalSpaceTypeWithoutInstitutionException.class, () -> service.editPhysicalSpaceType(existingPhysSpt));
		}
		
		/**
		 * Prueba 9: Editar un tipo de espacio físico llenando el campo de nombre y asignándole una institución que no está en 
		 * la base de datos.
		 */
		@Test
		public void editPhysicalSpaceTypeWithANonExistingInstitution() {
			Institution inst2 = new Institution();
			inst2.setInstId(4);
			inst2.setInstAcademicserverurl("https://anonymous.com");
			inst2.setInstName("Anonymous");
			
			Physicalspacetype existingPhysSpt = service.findById(1).get();
			existingPhysSpt.setPhyspctypeName("Space 3");
			existingPhysSpt.setInstitution(inst2);
			Assertions.assertThrows(NoSuchElementException.class, () -> service.editPhysicalSpaceType(existingPhysSpt));
		}
	}
	
}
