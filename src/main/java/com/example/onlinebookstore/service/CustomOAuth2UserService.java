package com.example.onlinebookstore.service;

import java.util.HashSet;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	Logger LOG = LoggerFactory.getLogger(CustomOAuth2UserService.class);

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		LOG.info("loadUser called");
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		Set<GrantedAuthority> authorities = new HashSet<>(oauth2User.getAuthorities());

		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new CustomOAuth2User(oauth2User, authorities);
	}

}
