package com.example.onlinebookstore.service;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.onlinebookstore.controller.AuthenticationController;

public class CustomOAuth2User implements OAuth2User {

	Logger LOG = LoggerFactory.getLogger(CustomOAuth2User.class);

	private final OAuth2User oauth2User;
	private final Collection<? extends GrantedAuthority> authorities;

	public CustomOAuth2User(OAuth2User oauth2User, Collection<? extends GrantedAuthority> authorities) {
		this.oauth2User = oauth2User;
		this.authorities = authorities;
	}

	@Override
	public Map<String, Object> getAttributes() {
		LOG.info("getAttributes called");
		return oauth2User.getAttributes();
	}

	@Override
	public String getName() {
		LOG.info("getName called");

		String name = oauth2User.getName();

		LOG.info("getName called: {}", name);
		return name;

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		LOG.info("getAuthorities called: {}", authorities);

		return authorities;
	}

	public String getEmail() {
		LOG.info("getEmail called");

		String email = oauth2User.getAttribute("email");

		LOG.info("getEmail called: {}", email);
		return email;

	}

}
