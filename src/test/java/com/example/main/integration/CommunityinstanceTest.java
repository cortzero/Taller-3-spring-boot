package com.example.main.integration;

import java.util.Date;
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

import com.example.main.model.Communityinstance;
import com.example.main.services.interfaces.CommunityinstanceService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CommunityinstanceTest {
	
	@Autowired
	private CommunityinstanceService communityServiceTest;
	
	@SuppressWarnings("deprecation")
	@Test
	@Order(1)
	public void saveCommunityTest() {
		Communityinstance comm1 = new Communityinstance();
		comm1.setComminstDescription("Community 1 Icesi");
		comm1.setComminstExtid("12345");
		comm1.setComminstStartdatehour(new Date(2020, 4, 14));
		comm1.setComminstEnddatehour(new Date(2020, 4, 24));
		
		communityServiceTest.save(comm1);
		Assertions.assertNotNull(communityServiceTest.findById(1));
	}
	
	@Test
	@Order(2)
	public void setScenario() {
		Communityinstance comm1 = new Communityinstance();
		comm1.setComminstDescription("Community 1 Icesi");
		comm1.setComminstExtid("12345");
		comm1.setComminstStartdatehour(new Date(2020, 4, 14));
		comm1.setComminstEnddatehour(new Date(2020, 4, 24));
		communityServiceTest.save(comm1);
		
		Communityinstance comm2 = new Communityinstance();
		comm2.setComminstDescription("Community 2 Icesi");
		comm2.setComminstExtid("3456");
		comm2.setComminstStartdatehour(new Date(2020, 5, 2));
		comm2.setComminstEnddatehour(new Date(2020, 5, 10));
		communityServiceTest.save(comm2);
		
		Communityinstance comm3 = new Communityinstance();
		comm3.setComminstDescription("Community 1 Univalle");
		comm3.setComminstExtid("1222");
		comm3.setComminstStartdatehour(new Date(2020, 5, 20));
		comm3.setComminstEnddatehour(new Date(2020, 5, 28));
		communityServiceTest.save(comm3);
		
		Communityinstance comm4 = new Communityinstance();
		comm4.setComminstDescription("Community 1 Javeriana");
		comm4.setComminstExtid("7777");
		comm4.setComminstStartdatehour(new Date(2020, 6, 1));
		comm4.setComminstEnddatehour(new Date(2020, 6, 15));
		communityServiceTest.save(comm4);
	}
	
	@Test
	@Order(3)
	public void findByDateRangeTest() {
		@SuppressWarnings("deprecation")
		Date startDate = new Date(2020, 5, 1);
		@SuppressWarnings("deprecation")
		Date endDate = new Date(2020, 6, 20);
		List<Communityinstance> list = communityServiceTest.findByDateRange(startDate, endDate);
		Assertions.assertEquals(3, list.size());
	}
	
	@Test
	@Order(4)
	public void findAllTest() {
		List<Communityinstance> communities = communityServiceTest.findAll();
		Assertions.assertEquals(5, communities.size());
	}
}
