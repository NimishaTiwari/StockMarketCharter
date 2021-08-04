package com.stockmarketcharter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stockmarketcharter.dao.UserRepository;
import com.stockmarketcharter.model.UserEntity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		UserEntity user = this.userRepository.findByUsername(username);
		if(user==null)
		{
			System.out.println("User not found");
			throw new UsernameNotFoundException("No User found");
		}
		
		if(user!=null && user.isEnabled()==false)
		{
			System.out.println("User not found");
			throw new UsernameNotFoundException("No User found");
		}
		
		else
		{
			return user;
		}		
	}

}
