package com.example.main.integration;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.services.implementations.InstitutionServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class InstitutionServiceTest {
	
	@Autowired
	private InstitutionServiceImpl institutionService;
	
	//----------------------- PRUEBAS DE GUARDADO -----------------------

	/**
	 * Prueba 1: Guardar una institución con la URL correcta: con el protocolo https y el nombre del dominio que es el
	 * nombre de la institución.
	 * Guarda la entidad
	 */
	@Test
	@Order(1)
	public void saveInstitutionWithACorrectURL() {
		Institution newInstitution = new Institution();
		newInstitution.setInstAcademicserverurl("https://icesi.com");
		newInstitution.setInstName("Icesi");
		Assertions.assertDoesNotThrow(() -> institutionService.save(newInstitution));
		Assertions.assertNotNull(institutionService.findById(1));
	}
	
	/**
	 * Prueba 2: Guardar una institución con nombre pero con la URL sin el protocolo https.
	 * No guarda la entidad
	 */
	@Test
	@Order(2)
	public void saveInstitutionWithoutHTTTPSInURL() {
		Institution newInstitution = new Institution();
		newInstitution.setInstAcademicserverurl("icesi.com");
		newInstitution.setInstName("Icesi");
		Assertions.assertThrows(URLWithoutProtocolException.class, () -> institutionService.save(newInstitution));
	}
	
	/**
	 * Prueba 3: Guardar una institución sin un nombre pero con la URL con el protocolo https.
	 * No guarda la entidad
	 */
	@Test
	@Order(3)
	public void saveInstitutionWithoutName() {
		Institution newInstitution = new Institution();
		newInstitution.setInstAcademicserverurl("https://icesi.com");
		Assertions.assertThrows(InstitutionWithoutNameException.class, () -> institutionService.save(newInstitution));
	}

	//----------------------- PRUEBAS DE EDICION -----------------------
	
	/**
	 * Prueba 4: Editar una institución llenando el campo de nombre y colocando en la URL el protocolo https.
	 * @throws Exception 
	 */
	@Test
	@Order(4)
	public void editAnInstitutionCorrectly() throws URLWithoutProtocolException, InstitutionWithoutNameException {
		String newName = "Univalle";
		String newURL = "https://univalle.com";
		Institution currentInst = institutionService.findById(1);
		currentInst.setInstName(newName);
		currentInst.setInstAcademicserverurl(newURL);
		Assertions.assertDoesNotThrow(() -> institutionService.update(currentInst));
		Assertions.assertEquals(newName, institutionService.findById(1).getInstName());
		Assertions.assertEquals(newURL, institutionService.findById(1).getInstAcademicserverurl());
	}
	
	/**
	 * Prueba 5: Editar una institución sin llenar el campo de nombre pero colocando en la URL el protocolo https.
	 * @throws URLWithoutProtocolException
	 * @throws InstitutionWithoutNameException
	 */
	@Test
	@Order(5)
	public void editAnInstitutionWithoutName() throws URLWithoutProtocolException, InstitutionWithoutNameException {
		Institution currentInst = institutionService.findById(1);
		currentInst.setInstName("");
		currentInst.setInstAcademicserverurl("https://univalle.com");
		Assertions.assertThrows(InstitutionWithoutNameException.class, () -> institutionService.update(currentInst));
	}
	
	/**
	 * Prueba 6: Editar una institución llenando el campo de nombre pero dejando la URL sin el protocolo https.
	 * @throws URLWithoutProtocolException
	 * @throws InstitutionWithoutNameException
	 */
	@Test
	@Order(6)
	public void editAnInstitutionWithoutHttpProtocol() throws URLWithoutProtocolException, InstitutionWithoutNameException {
		Institution currentInst = institutionService.findById(1);
		currentInst.setInstName("Univalle");
		currentInst.setInstAcademicserverurl("univalle.com");
		Assertions.assertThrows(URLWithoutProtocolException.class, () -> institutionService.update(currentInst));
	}
	
	//----------------------- PRUEBAS DE BUSQUEDA -----------------------
	
	@Test
	@Order(7)
	public void findAllInstitutionsTest() throws URLWithoutProtocolException, InstitutionWithoutNameException {
		Institution institution1 = new Institution();
		institution1.setInstName("Icesi");
		institution1.setInstAcademicserverurl("https://www.icesi.com");
		institution1.setInstAcadloginpassword("12345");
		institution1.setInstAcadloginurl("https://login.icesi.com");
		institution1.setInstAcadloginusername("icesi user 1");
		institution1.setInstAcadphysicalspacesurl("https://space.icesi.com");
		institutionService.save(institution1);
		
		Institution institution2 = new Institution();
		institution2.setInstName("Javeriana");
		institution2.setInstAcademicserverurl("https://www.javeriana.com");
		institution2.setInstAcadloginpassword("12345");
		institution2.setInstAcadloginurl("https://login.javeriana.com");
		institution2.setInstAcadloginusername("Javeriana user 1");
		institution2.setInstAcadphysicalspacesurl("https://space.javeriana.com");
		institutionService.save(institution2);
		
		Institution institution3 = new Institution();
		institution3.setInstName("Univalle");
		institution3.setInstAcademicserverurl("https://www.univalle.com");
		institution3.setInstAcadloginpassword("12345");
		institution3.setInstAcadloginurl("https://login.univalle.com");
		institution3.setInstAcadloginusername("Univalle user 1");
		institution3.setInstAcadphysicalspacesurl("https://space.univalle.com");
		institutionService.save(institution3);
		
		Institution institution4 = new Institution();
		institution4.setInstName("UAO");
		institution4.setInstAcademicserverurl("https://www.uao.com");
		institution4.setInstAcadloginpassword("12345");
		institution4.setInstAcadloginurl("https://login.uao.com");
		institution4.setInstAcadloginusername("UAO user 1");
		institution4.setInstAcadphysicalspacesurl("https://space.uao.com");
		institutionService.save(institution4);
		
		List<Institution> institutions = institutionService.findAll();
		Assertions.assertEquals(5, institutions.size()); // También cuenta a la institucion guardada en la prueba 1 de guardar.
	}
}
