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
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.model.Institutioncampus;
import com.example.main.services.interfaces.InstitutionCampusService;
import com.example.main.services.interfaces.InstitutionService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class InstitutionCampusServiceTest {
	
	@Autowired
	private InstitutionCampusService campusService;
	
	@Autowired
	private InstitutionService instService;
	
	@BeforeAll
	public void setupInstitutions() throws URLWithoutProtocolException, InstitutionWithoutNameException {
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
	}
	
	//----------------------- PRUEBAS DE GUARDADO -----------------------
	
	/**
	 * Prueba 1: Guardar un campus con un nombre no vacío, una ocupación en cero y que esté asociado a una 
	 * institución que existe.
	 */
	@Test
	@Order(1)
	public void saveCampusCorrectly() {
		Institutioncampus newCampus = new Institutioncampus();
		newCampus.setInstcamName("Icesi pance");
		newCampus.setInstcamOccupation(new BigDecimal(0));
		newCampus.setInstitution(instService.findById(1));
		
		Assertions.assertDoesNotThrow(() -> campusService.save(newCampus));
		Assertions.assertNotNull(campusService.findById(1));
	}
	
	/**
	 * Prueba 2: Guardar un campus con un nombre vacío, una ocupación en cero y que esté asociado a una 
	 * institución que existe.
	 */
	@Test
	@Order(2)
	public void saveCampusWithoutAName() {
		Institutioncampus newCampus = new Institutioncampus();
		newCampus.setInstcamName("");
		newCampus.setInstcamOccupation(new BigDecimal(0));
		newCampus.setInstitution(instService.findById(3));
		
		Assertions.assertThrows(CampusWithoutNameException.class, () -> campusService.save(newCampus));
	}
	
	/**
	 * Prueba 3: Guardar un campus con un nombre no vacío, pero con una ocupación mayor a cero y que 
	 * esté asociado a una institución que existe.
	 */
	@Test
	@Order(3)
	public void saveCampusWithOccupationGreaterThanZero() {
		Institutioncampus newCampus = new Institutioncampus();
		newCampus.setInstcamName("Univalle norte");
		newCampus.setInstcamOccupation(new BigDecimal(2));
		newCampus.setInstitution(instService.findById(2));
		
		Assertions.assertThrows(CampusWithNoZeroOccupationException.class, () -> campusService.save(newCampus));
	}
	
	/**
	 * Prueba 4: Guardar un campus con un nombre no vacío, una ocupación en cero pero que esté 
	 * asociado a una institución que no existe.
	 */
	@Test
	@Order(4)
	public void saveCampusWithANonExistingInstitution() {
		Institution nonExistingInst = new Institution();
		nonExistingInst.setInstName("Javeriana");
		nonExistingInst.setInstAcademicserverurl("https://javeriana.com");
		
		Institutioncampus newCampus = new Institutioncampus();
		newCampus.setInstcamName("Javeriana Cali");
		newCampus.setInstcamOccupation(new BigDecimal(0));
		newCampus.setInstitution(nonExistingInst);
		
		Assertions.assertThrows(NoSuchElementException.class, () -> campusService.save(newCampus));
	}
	
	//----------------------- PRUEBAS DE EDICIÓN -----------------------
	
	/**
	 * Prueba 5: Editar la información de un campus llenando los campos necesarios correctamente.
	 */
	@Test
	@Order(5)
	public void editCampusCorrectly() {
		String newName = "Univalle Palmira";
		Institutioncampus existingCampus = campusService.findById(1);
		existingCampus.setInstcamName(newName);
		existingCampus.setInstcamOccupation(new BigDecimal(0));
		existingCampus.setInstitution(instService.findById(3));
		
		Assertions.assertDoesNotThrow(() -> campusService.update(existingCampus));
		Assertions.assertEquals(newName, campusService.findById(1).getInstcamName());
		Assertions.assertEquals("Univalle", campusService.findById(1).getInstitution().getInstName());
		
	}
	
	/**
	 * Prueba 6: Editar la información de un campus pero poniendo en la ocupación un número negativo.
	 */
	@Test
	@Order(6)
	public void editCampusWithANegativeOccupation() {
		String newName = "Javeriana Bogotá";
		Institutioncampus existingCampus = campusService.findById(1);
		existingCampus.setInstcamName(newName);
		existingCampus.setInstcamOccupation(new BigDecimal(-2));
		existingCampus.setInstitution(instService.findById(2));
		
		Assertions.assertThrows(CampusWithNoZeroOccupationException.class, () -> campusService.update(existingCampus));
	}
	
	/**
	 * Prueba 7: Editar la información de un campus pero asignándole una institución que no existe.
	 */
	@Test
	@Order(7)
	public void editCampusWithANonExistingInstitute() {
		Institution nonExistingInst = new Institution();
		nonExistingInst.setInstName("Icesi");
		nonExistingInst.setInstAcademicserverurl("https://icesi.com");
		
		String newName = "Icesi Bogotá";
		Institutioncampus existingCampus = campusService.findById(1);
		existingCampus.setInstcamName(newName);
		existingCampus.setInstcamOccupation(new BigDecimal(0));
		existingCampus.setInstitution(nonExistingInst);
		
		Assertions.assertThrows(NoSuchElementException.class, () -> campusService.update(existingCampus));
	}
	
	/**
	 * Prueba 8: Editar la información de un campus pero dejando el nombre vacío y los demás campos correctos.
	 */
	@Test
	@Order(8)
	public void editCampusWithoutName() {
		Institutioncampus existingCampus = campusService.findById(1);
		existingCampus.setInstcamName("");
		existingCampus.setInstcamOccupation(new BigDecimal(0));
		existingCampus.setInstitution(instService.findById(3));
		
		Assertions.assertThrows(CampusWithoutNameException.class, () -> campusService.update(existingCampus));
	}
	
	//----------------------- PRUEBAS DE BUSQUEDA -----------------------
		
	@Test
	@Order(9)
	public void findAllMethodTest() throws NoSuchElementException, CampusWithoutNameException, CampusWithNoZeroOccupationException {
		Institutioncampus campus1 = new Institutioncampus();
		campus1.setInstcamName("Icesi pance");
		campus1.setInstitution(instService.findById(1));
		campus1.setInstcamOccupation(new BigDecimal(0));
		campusService.save(campus1);
		
		Institutioncampus campus2 = new Institutioncampus();
		campus2.setInstcamName("Javeriana Cali");
		campus2.setInstitution(instService.findById(2));
		campus2.setInstcamOccupation(new BigDecimal(0));
		campusService.save(campus2);
		
		Institutioncampus campus3 = new Institutioncampus();
		campus3.setInstcamName("Univalle palmira");
		campus3.setInstitution(instService.findById(3));
		campus3.setInstcamOccupation(new BigDecimal(0));
		campusService.save(campus3);
		
		List<Institutioncampus> campusList = campusService.findAll();
		Assertions.assertEquals(4, campusList.size());
	}
}
