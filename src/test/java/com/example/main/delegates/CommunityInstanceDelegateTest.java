package com.example.main.delegates;

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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.main.delegate.interfaces.CommunityInstanceDelegate;
import com.example.main.model.Communityinstance;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class CommunityInstanceDelegateTest {
	
	@Autowired
	private CommunityInstanceDelegate communityDelegate;
	
	@Test
	@Order(1)
	public void createPhysicalSpaceTypeTest() {
		Communityinstance comm1 = new Communityinstance();
		comm1.setComminstDescription("Community 1 Icesi");
		comm1.setComminstExtid("12345");
		comm1.setComminstStartdatehour(new Date(2020, 4, 14));
		comm1.setComminstEnddatehour(new Date(2020, 4, 24));
		
		Assertions.assertEquals(HttpStatus.CREATED, communityDelegate.createCommunity(comm1));
		
		Communityinstance comm2 = new Communityinstance();
		comm2.setComminstDescription("Community 2 Icesi");
		comm2.setComminstExtid("3456");
		comm2.setComminstStartdatehour(new Date(2020, 5, 2));
		comm2.setComminstEnddatehour(new Date(2020, 5, 10));
		
		Assertions.assertEquals(HttpStatus.CREATED, communityDelegate.createCommunity(comm2));
		
		Communityinstance comm3 = new Communityinstance();
		comm3.setComminstDescription("Community 1 Univalle");
		comm3.setComminstExtid("1222");
		comm3.setComminstStartdatehour(new Date(2020, 5, 20));
		comm3.setComminstEnddatehour(new Date(2020, 5, 28));
		
		Assertions.assertEquals(HttpStatus.CREATED, communityDelegate.createCommunity(comm3));
		
		Communityinstance comm4 = new Communityinstance();
		comm4.setComminstDescription("Community 1 Javeriana");
		comm4.setComminstExtid("7777");
		comm4.setComminstStartdatehour(new Date(2020, 6, 1));
		comm4.setComminstEnddatehour(new Date(2020, 6, 15));
		
		Assertions.assertEquals(HttpStatus.CREATED, communityDelegate.createCommunity(comm4));
	}
	
	@Test
	@Order(2)
	public void getPhysicalSpaceTypeTest() {
		Communityinstance comm1 = communityDelegate.getCommunity(2);
		
		Assertions.assertEquals("Community 2 Icesi", comm1.getComminstDescription());
		Assertions.assertEquals("3456", comm1.getComminstExtid());
	}
	
	@Test
	@Order(3)
	public void getAllPhysicalSpaceTypesTest() {
		Assertions.assertEquals(4, communityDelegate.getAllCommunities().size());
	}
	
	@Test
	@Order(4)
	public void findByDateRange() {
		// Start date = 2020 year, 5 month, 1 day
		// End date = 2020 year, 6 month, 20 day
		List<Communityinstance> list = communityDelegate.findByDateRange(1, 5, 2020, 20, 6, 2020);
		Assertions.assertEquals(3, list.size());
	}
	
	@Test
	@Order(5)
	public void updatePhysicalSpaceTypeTest() {
		Communityinstance comm = communityDelegate.getCommunity(3);
		comm.setComminstDescription("NEW DESCRIPTION COMMUNITY 3");
		comm.setComminstExtid("3333");
		communityDelegate.updateCommunity(3, comm);
		
		Assertions.assertEquals("NEW DESCRIPTION COMMUNITY 3", communityDelegate.getCommunity(3).getComminstDescription());
		Assertions.assertEquals("3333", communityDelegate.getCommunity(3).getComminstExtid());
	}
	
	@Test
	@Order(6)
	public void deletePhysicalSpaceTypeTest() {
		Assertions.assertEquals(4, communityDelegate.getAllCommunities().size());
		
		communityDelegate.deleteCommunity(1);
		
		Assertions.assertEquals(3, communityDelegate.getAllCommunities().size());
	}
}
