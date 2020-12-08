package com.example.main.daos;

import java.util.Date;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.main.daos.interfaces.CommunityinstanceDAO;
import com.example.main.model.Communityinstance;
import com.example.main.model.Physicalspace;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class CommunityinstanceDAOTest {
	
	@Autowired
	private CommunityinstanceDAO communityDAO;
	
	@Test
	@Order(1)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveMethodTest() {
		Assertions.assertNotNull(communityDAO);
		Communityinstance community = new Communityinstance();
		community.setComminstDescription("Community 1");
		communityDAO.save(community);
		Assertions.assertNotNull(communityDAO.findById(1));
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateMethodTest() {
		String newDescription = "New community";
		Communityinstance communityinstance = communityDAO.findById(1);
		communityinstance.setComminstDescription(newDescription);
		communityDAO.update(communityinstance);
	}
	
	@Test
	@Order(3)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteMethodTest() {
		communityDAO.delete(communityDAO.findById(1));
		Assertions.assertNull(communityDAO.findById(1));
	}
	
	@Test
	@Order(5)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByDateRangeMethodTest() {
		Communityinstance comm1 = new Communityinstance();
		comm1.setComminstDescription("Community 1 Icesi");
		comm1.setComminstExtid("12345");
		comm1.setComminstStartdatehour(new Date(2020, 4, 14));
		comm1.setComminstEnddatehour(new Date(2020, 4, 24));
		communityDAO.save(comm1);
		
		Communityinstance comm2 = new Communityinstance();
		comm2.setComminstDescription("Community 2 Icesi");
		comm2.setComminstExtid("3456");
		comm2.setComminstStartdatehour(new Date(2020, 5, 2));
		comm2.setComminstEnddatehour(new Date(2020, 5, 10));
		communityDAO.save(comm2);
		
		Communityinstance comm3 = new Communityinstance();
		comm3.setComminstDescription("Community 1 Univalle");
		comm3.setComminstExtid("1222");
		comm3.setComminstStartdatehour(new Date(2020, 5, 20));
		comm3.setComminstEnddatehour(new Date(2020, 5, 28));
		communityDAO.save(comm3);
		
		Communityinstance comm4 = new Communityinstance();
		comm4.setComminstDescription("Community 1 Javeriana");
		comm4.setComminstExtid("7777");
		comm4.setComminstStartdatehour(new Date(2020, 6, 1));
		comm4.setComminstEnddatehour(new Date(2020, 6, 15));
		communityDAO.save(comm4);
		
		@SuppressWarnings("deprecation")
		Date startDate = new Date(2020, 5, 1);
		@SuppressWarnings("deprecation")
		Date endDate = new Date(2020, 6, 20);
		List<Communityinstance> list = communityDAO.findByDateRange(startDate, endDate);
		Assertions.assertEquals(3, list.size());
	}
	
	@Test
	@Order(6)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findAllMethodTest() {
		List<Communityinstance> list = communityDAO.findAll();
		Assertions.assertEquals(4, list.size());
	}

}
