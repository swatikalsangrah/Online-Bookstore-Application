package com.example.onlinebookstore.service;

import java.util.Collection;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.onlinebookstore.controller.BookController;
import com.example.onlinebookstore.entity.User;
import com.example.onlinebookstore.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		LOG.info("loadUserByUsername called {}", username);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not Found");
		}

		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), 
				user.getPassword(),
				user.isEnabled(),
				true, // Whether the account is non-expired (could be user.isAccountNonExpired())
				true, // Whether the credentials are non-expired (could be user.isCredentialsNonExpired())
				!user.isAccountLocked(), // Whether the account is non-locked (invert the locked status)
				getAuthorities(user.getRoles())
		);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Set<String> roles) {
		
		LOG.info("getAuthorities called {}", roles);

		return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	// return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), authorities);
}
