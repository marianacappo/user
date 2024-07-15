package com.bciexercise.exercise.util;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokensManagement {
	
	private static final String SECRET_KEY = "mySecretKey";	
	private static final String TOKEN_BEARER_PREFIX = "Bearer";
	private static final String HEADER = "Authorization";
	
	public static String generateJwtToken(final String email) {
		
		final long expiration = 600000; // 10min

		String token = Jwts.builder().setId(UUID.randomUUID().toString())
									.setSubject(email)
									.setIssuedAt(new Date(System.currentTimeMillis()))
									.setExpiration(new Date(System.currentTimeMillis() + expiration))
									.signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes()).compact();

		return token;
	}
	
	public static String getUsernameFromJwtToken(final String token) {
		
		return Jwts.parser()
			        .setSigningKey(SECRET_KEY.getBytes())
			        .parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
			        .getBody()
			        .getSubject();
	}
	
	public static boolean validateToken(final HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(TOKEN_BEARER_PREFIX, "");
		return Optional.of(Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(jwtToken).getBody()).isPresent();
	}
	
	public static boolean existsJWTToken(final HttpServletRequest request) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(TOKEN_BEARER_PREFIX))
			return false;
		return true;
	}
}
