package com.example.main.unittests;

import static org.hamcrest.CoreMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.main.daos.interfaces.InstitutionDAO;
import com.example.main.exceptions.InstitutionWithoutNameException;
import com.example.main.exceptions.URLWithoutProtocolException;
import com.example.main.model.Institution;
import com.example.main.repositories.InstitutionRepository;
import com.example.main.services.implementations.InstitutionServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class InstitutionServiceTest {
	
	@Autowired
	private InstitutionServiceImpl service;
	
	private InstitutionDAO institutionDAOMock = Mockito.mock(InstitutionDAO.class);
	
	@BeforeAll
	public void init() {
		service = new InstitutionServiceImpl(institutionDAOMock);
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class Creation {
		
		@BeforeAll
		public void init() {
			service = new InstitutionServiceImpl(institutionDAOMock);
			
			Institution correctInstitution = new Institution();
			correctInstitution.setInstName("Icesi");
			
			Mockito.doAnswer((i) -> {
				Assertions.assertTrue(correctInstitution.equals(i.getArgument(0)));
				return null;
			}).when(institutionDAOMock).save((Institution) any(Institution.class));
		}
		
		/**
		 * Prueba 1: Guardar una institución con la URL correcta: con el protocolo https y el nombre del dominio que es el
		 * nombre de la institución.
		 */
		@Test
		public void saveInstitutionWithACorrectURL() {
			Institution newInstitution = new Institution();
			//newInstitution.setInstId(1);
			newInstitution.setInstAcademicserverurl("https://icesi.com");
			newInstitution.setInstName("Icesi");
			Assertions.assertDoesNotThrow(() -> service.save(newInstitution));
		}
		
		/**
		 * Prueba 2: Guardar una institución con nombre pero con la URL sin el protocolo https.
		 */
		@Test
		public void saveInstitutionWithoutHTTTPSInURL() {
			Institution newInstitution = new Institution();
			//newInstitution.setInstId(2);
			newInstitution.setInstAcademicserverurl("icesi.com");
			newInstitution.setInstName("Icesi");
			Assertions.assertThrows(URLWithoutProtocolException.class, () -> service.save(newInstitution));
		}
		
		/**
		 * Prueba 3: Guardar una institución sin un nombre pero con la URL con el protocolo https.
		 */
		@Test
		public void saveInstitutionWithoutName() {
			Institution newInstitution = new Institution();
			//newInstitution.setInstId(3);
			newInstitution.setInstAcademicserverurl("https://icesi.com");
			Assertions.assertThrows(InstitutionWithoutNameException.class, () -> service.save(newInstitution));
		}
		
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class Edition {
		
		@BeforeAll
		public void setScenario() throws URLWithoutProtocolException, InstitutionWithoutNameException {
			Institution newInstitution = new Institution();
			//newInstitution.setInstId(1);
			newInstitution.setInstAcademicserverurl("https://javeriana.com");
			newInstitution.setInstName("Javeriana");
			service.save(newInstitution);
		}
		
		/**
		 * Prueba 4: Editar una institución llenando el campo de nombre y colocando en la URL el protocolo https.
		 * @throws Exception 
		 */
		@Test
		public void editAnInstitutionCorrectly() throws URLWithoutProtocolException, InstitutionWithoutNameException {
			String newName = "Univalle";
			String newURL = "https://univalle.com";
			Institution currentInst = service.findById(1);
			currentInst.setInstName(newName);
			currentInst.setInstAcademicserverurl(newURL);
			Assertions.assertDoesNotThrow(() -> service.update(currentInst));
			Assertions.assertEquals(newName, service.findById(1).getInstName());
			Assertions.assertEquals(newURL, service.findById(1).getInstAcademicserverurl());
		}
		
		/**
		 * Prueba 5: Editar una institución sin llenar el campo de nombre pero colocando en la URL el protocolo https.
		 * @throws URLWithoutProtocolException
		 * @throws InstitutionWithoutNameException
		 */
		@Test
		public void editAnInstitutionWithoutName() throws URLWithoutProtocolException, InstitutionWithoutNameException {
			Institution currentInst = service.findById(1);
			currentInst.setInstName("");
			currentInst.setInstAcademicserverurl("https://univalle.com");
			Assertions.assertThrows(InstitutionWithoutNameException.class, () -> service.update(currentInst));
		}
		
		/**
		 * Prueba 6: Editar una institución llenando el campo de nombre pero dejando la URL sin el protocolo https.
		 * @throws URLWithoutProtocolException
		 * @throws InstitutionWithoutNameException
		 */
		@Test
		public void editAnInstitutionWithoutHttpProtocol() throws URLWithoutProtocolException, InstitutionWithoutNameException {
			Institution currentInst = service.findById(1);
			currentInst.setInstName("Univalle");
			currentInst.setInstAcademicserverurl("univalle.com");
			Assertions.assertThrows(URLWithoutProtocolException.class, () -> service.update(currentInst));
		}
	}
}
