package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private SecurityFilter securityFilter;
	
	@Autowired
	private InvalidAuthenticationEntryPoint invalidAuthenticationEntryPoint;
	
	/*
	 * Authentication Manager
	 */
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	
	/*
	 * Authorization
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.cors().and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/user/login","/user/save").permitAll()
			.antMatchers("/welcome").authenticated()
			.antMatchers("/books").hasAuthority("ADMIN")
			.anyRequest().authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(invalidAuthenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(securityFilter,UsernamePasswordAuthenticationFilter.class)
			;
	}

}
