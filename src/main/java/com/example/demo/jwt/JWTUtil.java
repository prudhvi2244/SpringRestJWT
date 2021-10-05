package com.example.demo.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${app.secret-key}")
	private String secretKey;
	
	
	public boolean validateToken(String username,String token)
	{
		String tokenUsername=getUsername(token);
		return tokenUsername.equals(username) && !isTokenExpired(token) ;
	}
	
	public boolean isTokenExpired(String token)
	{
		return tokenExpirationDate(token).before(new Date(System.currentTimeMillis()));
	}
	
	public Date tokenExpirationDate(String token)
	{
		return getClaims(token).getExpiration();
	}
	
	public String getUsername(String token)
	{
			return getClaims(token).getSubject();
	}
	
	public Claims getClaims(String token)
	{
		return Jwts.parser().setSigningKey(Base64.getEncoder().encode(secretKey.getBytes()))
		.parseClaimsJws(token).getBody();
	}
	
	public String generateToken(String username)
	{
		return Jwts.builder().setId("PRUDHVI RAJ")
					  .setSubject(username)
					  .setIssuer("RAJ")
					  .setIssuedAt(new Date(System.currentTimeMillis()))
					  .setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(3)))
					  .signWith(SignatureAlgorithm.HS256,Base64.getEncoder().encode(secretKey.getBytes()))
					  .compact();
	}
	
}
