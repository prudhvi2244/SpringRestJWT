package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.jwt.JWTUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTUtil jutil;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader("Authorization");
		System.out.println(token);
		
		
		if (token != null) {
			String username=jutil.getUsername(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UserDetails user=userDetailsService.loadUserByUsername(username);
				boolean isValid=jutil.validateToken(user.getUsername(),token);
				UsernamePasswordAuthenticationToken authToken=new 
						UsernamePasswordAuthenticationToken(username,user.getPassword(),user.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
			doFilter(request, response, filterChain);
		

	}

}
