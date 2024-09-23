package com.example.onlinebookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.onlinebookstore.filter.JwtAuthenticationFilter;
import com.example.onlinebookstore.security.CustomAuthoritiesMapper;
import com.example.onlinebookstore.service.CustomOAuth2UserService;
import com.example.onlinebookstore.service.CustomUserDetailsService;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {
	
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired 
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                		        .requestMatchers("/oauth2/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/users/**").hasRole("ADMIN")
                                .requestMatchers("/api/books/**").hasAnyRole("USER", "ADMIN", "OIDC_USER")
                                //.requestMatchers("/api/books/**").hasAnyAuthority("USER","ADMIN", "OIDC_USER")
                                .requestMatchers("/api/cartitems/**").hasAnyRole("USER", "ADMIN")
                                //.requestMatchers("/api/cartitems/**").hasAnyAuthority("USER","ADMIN", "OIDC_USER")
                                .anyRequest().authenticated()
                )
        .httpBasic(Customizer.withDefaults())
        //.oauth2Login(Customizer.withDefaults())
        .oauth2Login(oauth2Login -> oauth2Login
            .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                .userService(customOAuth2UserService)
                .userAuthoritiesMapper(customAuthoritiesMapper())
            )
        )
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
        .formLogin(formLogin -> formLogin.permitAll())
        .logout(logout -> logout.permitAll());
        
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//        @Bean
//        public PasswordEncoder passwordEncoder() { 
//            return new BCryptPasswordEncoder(); 
//        }
        @Bean
        public PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance(); // No password encoding
        }
        
        @Bean	
        public AuthenticationManager authManager(HttpSecurity http) throws Exception {
            AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);
            
            authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                                        .passwordEncoder(passwordEncoder());
            return authenticationManagerBuilder.build();
        }
        
        
        
        
//        @Bean
//        public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
//        	http
//        		// ...
//        		.oauth2Login((oauth2Login) -> oauth2Login
//        			.userInfoEndpoint((userInfo) -> userInfo
//        				.userAuthoritiesMapper(grantedAuthoritiesMapper())
//        			)
//        		);
//        	return http.build();
//        }
@Bean
public GrantedAuthoritiesMapper customAuthoritiesMapper() {
            return new CustomAuthoritiesMapper();
        }
//@Bean
//public GrantedAuthoritiesMapper customAuthoritiesMapper() {
//            return authorities -> {
//                Set<SimpleGrantedAuthority> mappedAuthorities = new HashSet<>();
//                authorities.forEach(authority -> {
//                    if (authority instanceof OAuth2UserAuthority || authority instanceof OidcUserAuthority) {
//                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//                    } else {
//                        mappedAuthorities.add((SimpleGrantedAuthority) authority);
//                    }
//                });
//                return mappedAuthorities;
//            };
//        }
//        private GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
//        	return (authorities) -> {
//        		Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//
//        		authorities.forEach((authority) -> {
//        			GrantedAuthority mappedAuthority;
//
//        			if (authority instanceof OidcUserAuthority) {
//        				OidcUserAuthority userAuthority = (OidcUserAuthority) authority;
//        				mappedAuthority = new OidcUserAuthority(
//        					"OIDC_USER", userAuthority.getIdToken(), userAuthority.getUserInfo());
//        			} else if (authority instanceof OAuth2UserAuthority) {
//        				OAuth2UserAuthority userAuthority = (OAuth2UserAuthority) authority;
//        				mappedAuthority = new OAuth2UserAuthority(
//        					"OAUTH2_USER", userAuthority.getAttributes());
//        			} else {
//        				mappedAuthority = authority;
//        			}
//
//        			mappedAuthorities.add(mappedAuthority);
//        		});
//
//        		return mappedAuthorities;
//        	};
//        }
}
    