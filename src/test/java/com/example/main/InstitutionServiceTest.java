package com.example.main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.repositories.InstitutionRepository;
import com.example.main.services.implementations.InstitutionServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class InstitutionServiceTest {
	
	@Mock
	private InstitutionRepository repository;
	
	
	@Autowired
	private InstitutionServiceImpl service;
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class Creation {
		
		/**
		 * Prueba 1: Guardar una institución con la URL correcta: con el protocolo https y el nombre del dominio que es el
		 * nombre de la institución.
		 */
		@Test
		public void saveInstitutionWithACorrectURL() {
			Institution newInstitution = new Institution();
			newInstitution.setInstId(1);
			newInstitution.setInstAcademicserverurl("https://icesi.com");
			newInstitution.setInstName("Icesi");
			Assertions.assertDoesNotThrow(() -> service.saveInstitution(newInstitution));
			Assertions.assertEquals(1, service.getNumberOfInstitutions());
		}
		
		/**
		 * Prueba 2: Guardar una institución con nombre pero con la URL sin el protocolo https.
		 */
		@Test
		public void saveInstitutionWithoutHTTTPSInURL() {
			Institution newInstitution = new Institution();
			newInstitution.setInstId(2);
			newInstitution.setInstAcademicserverurl("icesi.com");
			newInstitution.setInstName("Icesi");
			Assertions.assertThrows(URLWithoutProtocolException.class, () -> service.saveInstitution(newInstitution));
		}
		
		/**
		 * Prueba 3: Guardar una institución sin un nombre pero con la URL con el protocolo https.
		 */
		@Test
		public void saveInstitutionWithoutName() {
			Institution newInstitution = new Institution();
			newInstitution.setInstId(3);
			newInstitution.setInstAcademicserverurl("https://icesi.com");
			Assertions.assertThrows(InstitutionWithoutNameException.class, () -> service.saveInstitution(newInstitution));
		}
		
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class Edition {
		
		@BeforeAll
		public void setScenario() throws URLWithoutProtocolException, InstitutionWithoutNameException {
			Institution newInstitution = new Institution();
			newInstitution.setInstId(1);
			newInstitution.setInstAcademicserverurl("https://javeriana.com");
			newInstitution.setInstName("Javeriana");
			service.saveInstitution(newInstitution);
		}
		
		/**
		 * Prueba 4: Editar una institución llenando el campo de nombre y colocando en la URL el protocolo https.
		 * @throws Exception 
		 */
		@Test
		public void editAnInstitutionCorrectly() throws URLWithoutProtocolException, InstitutionWithoutNameException {
			String newName = "Univalle";
			String newURL = "https://univalle.com";
			Institution currentInst = service.getInstitution(1).get();
			currentInst.setInstName(newName);
			currentInst.setInstAcademicserverurl(newURL);
			Assertions.assertDoesNotThrow(() -> service.editInstitution(currentInst));
			Assertions.assertEquals(newName, service.getInstitution(1).get().getInstName());
			Assertions.assertEquals(newURL, service.getInstitution(1).get().getInstAcademicserverurl());
		}
		
		/**
		 * Prueba 5: Editar una institución sin llenar el campo de nombre pero colocando en la URL el protocolo https.
		 * @throws URLWithoutProtocolException
		 * @throws InstitutionWithoutNameException
		 */
		@Test
		public void editAnInstitutionWithoutName() throws URLWithoutProtocolException, InstitutionWithoutNameException {
			Institution currentInst = service.getInstitution(1).get();
			currentInst.setInstName("");
			currentInst.setInstAcademicserverurl("https://univalle.com");
			Assertions.assertThrows(InstitutionWithoutNameException.class, () -> service.editInstitution(currentInst));
		}
		
		/**
		 * Prueba 6: Editar una institución llenando el campo de nombre pero dejando la URL sin el protocolo https.
		 * @throws URLWithoutProtocolException
		 * @throws InstitutionWithoutNameException
		 */
		@Test
		public void editAnInstitutionWithoutHttpProtocol() throws URLWithoutProtocolException, InstitutionWithoutNameException {
			Institution currentInst = service.getInstitution(1).get();
			currentInst.setInstName("Univalle");
			currentInst.setInstAcademicserverurl("univalle.com");
			Assertions.assertThrows(URLWithoutProtocolException.class, () -> service.editInstitution(currentInst));
		}
	}
}
