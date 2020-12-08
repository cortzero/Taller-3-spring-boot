package com.example.main.integration;

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

import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutInstitutionException;
import com.example.main.exceptions.PhysicalSpaceTypeWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.model.Physicalspacetype;
import com.example.main.services.implementations.InstitutionServiceImpl;
import com.example.main.services.interfaces.PhysicalSpaceTypeService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PhysicalSpaceTypeServiceTest {

	@Autowired
	private PhysicalSpaceTypeService physicalSpaceTypeService;
	
	@Autowired
	private InstitutionServiceImpl instService;
	
	@BeforeAll
	public void setInstitutions() throws URLWithoutProtocolException, InstitutionWithoutNameException {
		Institution inst = new Institution();
		inst.setInstAcademicserverurl("https://icesi.com");
		inst.setInstName("Icesi");
		instService.save(inst);
		
		Institution inst2 = new Institution();
		inst2.setInstName("Univalle");
		inst2.setInstAcademicserverurl("https://univalle.com");
		instService.save(inst2);
		
		Institution inst3 = new Institution();
		inst3.setInstName("Javeriana");
		inst3.setInstAcademicserverurl("https://javeriana.com");
		instService.save(inst3);
	}
	
	//----------------------- PRUEBAS DE GUARDADO -----------------------
	
	/**
	 * Prueba 1: Guardar un tipo de espacio físico con nombre, asociado a una institución existente y que implique comunidad.
	 * Guarda la entidad
	 */
	@Test
	@Order(1)
	public void savePhysicalSpaceTypeCorrectly() {
		Physicalspacetype newPhysicalSpTy = new Physicalspacetype();
		newPhysicalSpTy.setPhyspctypeName("Save test SP 1");
		newPhysicalSpTy.setInstitution(instService.findById(1));
		newPhysicalSpTy.setPhyspctypeImpliescomm("Estudiantes");
		Assertions.assertDoesNotThrow(() -> physicalSpaceTypeService.save(newPhysicalSpTy));
	}
	
	/**
	 * Prueba 2: Guardar un tipo de espacio físico sin nombre, asociado a una institución existente y que implique comunidad.
	 * No guarda la entidad
	 */
	@Test
	@Order(2)
	public void savePhysicalSpaceTypeWithoutName() {
		Physicalspacetype newPhysicalSpTy = new Physicalspacetype();
		newPhysicalSpTy.setInstitution(instService.findById(1));
		newPhysicalSpTy.setPhyspctypeImpliescomm("Estudiantes");
		Assertions.assertThrows(PhysicalSpaceTypeWithoutNameException.class, () -> physicalSpaceTypeService.save(newPhysicalSpTy));
	}
	
	/**
	 * Prueba 3: Guardar un tipo de espacio físico con nombre, pero sin una institución asociada y que implique comunidad.
	 * No guarda la entidad
	 */
	@Test
	@Order(3)
	public void savePhysicalSpaceTypeWithoutInstitution() {
		Physicalspacetype newPhysicalSpTy = new Physicalspacetype();
		newPhysicalSpTy.setPhyspctypeName("Save test SP 2");
		newPhysicalSpTy.setPhyspctypeImpliescomm("Estudiantes");
		Assertions.assertThrows(PhysicalSpaceTypeWithoutInstitutionException.class, () -> physicalSpaceTypeService.save(newPhysicalSpTy));
	}
	
	/**
	 * Prueba 4: Guardar un tipo de espacio físico con nombre, pero con una institución asociada que no existe y que implique comunidad.
	 * No guarda la entidad
	 */
	@Test
	@Order(4)
	public void savePhysicalSpaceTypeWithNonExistingInstitution() {
		Institution inst2 = new Institution();
		inst2.setInstAcademicserverurl("https://anonymous.com");
		inst2.setInstName("Anonymous");
		
		Physicalspacetype newPhysicalSpTy = new Physicalspacetype();
		newPhysicalSpTy.setPhyspctypeName("Save test SP 1");
		newPhysicalSpTy.setInstitution(inst2);
		newPhysicalSpTy.setPhyspctypeImpliescomm("Estudiantes");
		Assertions.assertThrows(NoSuchElementException.class, () -> physicalSpaceTypeService.save(newPhysicalSpTy));
	}
	
	/**
	 * Prueba 5: Guardar un tipo de espacio físico con nombre, asociado a una institución existente y que no implique comunidad.
	 * Guarda la entidad
	 */
	@Test
	@Order(5)
	public void savePhysicalSpaceTypeWithoutCommunity() {
		Physicalspacetype newPhysicalSpTy = new Physicalspacetype();
		newPhysicalSpTy.setPhyspctypeName("Save test SP 1");
		newPhysicalSpTy.setInstitution(instService.findById(1));
		Assertions.assertDoesNotThrow(() -> physicalSpaceTypeService.save(newPhysicalSpTy));
	}
	
	//----------------------- PRUEBAS DE EDICION -----------------------
		
	/**
	 * Prueba 6: Editar un tipo de espacio físico llenando el campo de nombre y asignándole una nueva institución.
	 */
	@Test
	@Order(6)
	public void editPhysicalSpaceTypeCorretly() {
		String newName = "Space 2";
		Physicalspacetype existingPhysSpt = physicalSpaceTypeService.findById(1);
		existingPhysSpt.setPhyspctypeName(newName);
		existingPhysSpt.setInstitution(instService.findById(1));
		Assertions.assertDoesNotThrow(() -> physicalSpaceTypeService.update(existingPhysSpt));
		Assertions.assertEquals(newName, physicalSpaceTypeService.findById(1).getPhyspctypeName());
		Assertions.assertEquals("Icesi", physicalSpaceTypeService.findById(1).getInstitution().getInstName());
	}
	
	/**
	 * Prueba 7: Editar un tipo de espacio físico sin llenar el campo de nombre y asignándole una nueva institución.
	 */
	@Test
	@Order(7)
	public void editPhysicalSpaceTypeWithoutName() {
		Physicalspacetype existingPhysSpt = physicalSpaceTypeService.findById(1);
		existingPhysSpt.setPhyspctypeName("");
		existingPhysSpt.setInstitution(instService.findById(1));
		Assertions.assertThrows(PhysicalSpaceTypeWithoutNameException.class, () -> physicalSpaceTypeService.update(existingPhysSpt));
	}
	
	/**
	 * Prueba 8: Editar un tipo de espacio físico llenando el campo de nombre pero sin asignarle una nueva institución.
	 */
	@Test
	@Order(8)
	public void editPhysicalSpaceTypeWithoutInstitution() {
		Physicalspacetype existingPhysSpt = physicalSpaceTypeService.findById(1);
		existingPhysSpt.setPhyspctypeName("Space 3");
		existingPhysSpt.setInstitution(null);
		Assertions.assertThrows(PhysicalSpaceTypeWithoutInstitutionException.class, () -> physicalSpaceTypeService.update(existingPhysSpt));
	}
	
	/**
	 * Prueba 9: Editar un tipo de espacio físico llenando el campo de nombre y asignándole una institución que no está en 
	 * la base de datos.
	 */
	@Test
	@Order(9)
	public void editPhysicalSpaceTypeWithANonExistingInstitution() {
		Institution inst2 = new Institution();
		inst2.setInstAcademicserverurl("https://anonymous.com");
		inst2.setInstName("Anonymous");
		
		Physicalspacetype existingPhysSpt = physicalSpaceTypeService.findById(1);
		existingPhysSpt.setPhyspctypeName("Space 3");
		existingPhysSpt.setInstitution(inst2);
		Assertions.assertThrows(NoSuchElementException.class, () -> physicalSpaceTypeService.update(existingPhysSpt));
	}
	
	//----------------------- PRUEBAS DE BUSQUEDA -----------------------
	
	@Test
	@Order(10)
	public void setPhysicalSpaceType() throws NoSuchElementException, PhysicalSpaceTypeWithoutNameException, PhysicalSpaceTypeWithoutInstitutionException {
		Physicalspacetype newPhysicalSpTy1 = new Physicalspacetype();
		newPhysicalSpTy1.setPhyspctypeName("Physical SP 1");
		newPhysicalSpTy1.setInstitution(instService.findById(3));
		newPhysicalSpTy1.setPhyspctypeExtid("12345");
		newPhysicalSpTy1.setPhyspctypeImpliescomm("Estudiantes");
		physicalSpaceTypeService.save(newPhysicalSpTy1);
		
		Physicalspacetype newPhysicalSpTy2 = new Physicalspacetype();
		newPhysicalSpTy2.setPhyspctypeName("Physical SP 2");
		newPhysicalSpTy2.setInstitution(instService.findById(1));
		newPhysicalSpTy2.setPhyspctypeExtid("98765");
		newPhysicalSpTy2.setPhyspctypeImpliescomm("Estudiantes 2");
		physicalSpaceTypeService.save(newPhysicalSpTy2);
		
		Physicalspacetype newPhysicalSpTy3 = new Physicalspacetype();
		newPhysicalSpTy3.setPhyspctypeName("Physical SP REPEATED");
		newPhysicalSpTy3.setInstitution(instService.findById(2));
		newPhysicalSpTy3.setPhyspctypeExtid("55555");
		newPhysicalSpTy3.setPhyspctypeImpliescomm("Estudiantes");
		physicalSpaceTypeService.save(newPhysicalSpTy3);
		
		Physicalspacetype newPhysicalSpTy4 = new Physicalspacetype();
		newPhysicalSpTy4.setPhyspctypeName("Physical SP REPEATED");
		newPhysicalSpTy4.setInstitution(instService.findById(1));
		newPhysicalSpTy4.setPhyspctypeExtid("55555");
		newPhysicalSpTy4.setPhyspctypeImpliescomm("Profesores");
		physicalSpaceTypeService.save(newPhysicalSpTy4);
	}
	
	@Test
	@Order(11)
	public void findByNameTest() {
		String name = "Physical SP 1";
		List<Physicalspacetype> list = physicalSpaceTypeService.findByName(name);
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals(name, list.get(0).getPhyspctypeName());
	}
	
	@Test
	@Order(12)
	public void findByARepeatedNameTest() {
		String name = "Physical SP REPEATED";
		List<Physicalspacetype> list = physicalSpaceTypeService.findByName(name);
		Assertions.assertEquals(2, list.size());
		Assertions.assertEquals("Estudiantes", list.get(0).getPhyspctypeImpliescomm());
		Assertions.assertEquals("Profesores", list.get(1).getPhyspctypeImpliescomm());
	}
	
	@Test
	@Order(13)
	public void findByExtIdTest() {
		String extId = "98765";
		List<Physicalspacetype> list = physicalSpaceTypeService.findByExtId(extId);
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals(extId, list.get(0).getPhyspctypeExtid());
	}
	
	@Test
	@Order(14)
	public void findByARepeatedExtIdTest() {
		String extId = "55555";
		List<Physicalspacetype> list = physicalSpaceTypeService.findByExtId(extId);
		Assertions.assertEquals(2, list.size());
		Assertions.assertEquals("Estudiantes", list.get(0).getPhyspctypeImpliescomm());
		Assertions.assertEquals("Profesores", list.get(1).getPhyspctypeImpliescomm());
	}
	
	@Test
	@Order(15)
	public void findAllTest() {
		List<Physicalspacetype> list = physicalSpaceTypeService.findAll();
		Assertions.assertEquals(6, list.size()); // Se cuentan también las dos instancias que fueron creadas en las pruebas 1 y 5
	}
}
