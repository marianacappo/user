package com.bciexercise.exercise.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bciexercise.exercise.util.JwtTokensManagement;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	Logger logger = LogManager.getLogger(JWTAuthorizationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			if (!JwtTokensManagement.existsJWTToken(request) || !JwtTokensManagement.validateToken(request))
				SecurityContextHolder.clearContext();
				
			filterChain.doFilter(request, response);
			
		} catch (final ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | MissingRequestHeaderException | SignatureException e) {
			logger.error(e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}

}
