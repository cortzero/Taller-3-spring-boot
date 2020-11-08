package com.example.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.main.model.Userr;
import com.example.main.repositories.UserrRepository;

@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	private UserrRepository userrRepository;
	
	@Autowired
	public MyCustomUserDetailsService(UserrRepository userrRepository) {
		this.userrRepository = userrRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Userr user = userrRepository.findByuserName(userName).get();
		if (user != null) {
			User.UserBuilder builder = User.withUsername(userName).password("{noop}"+user.getUserPassword()).roles("");
			return builder.build();
		}
		else {
			System.out.println("USER " + userName + " WAS NOT FOUND!!!");
			throw new UsernameNotFoundException("User not found.");
		}
	}
}