package com.lasm.sms.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lasm.sms.SpringApplicationContext;
import com.lasm.sms.requests.UserLoginRequest;
import com.lasm.sms.services.UserService;
import com.lasm.sms.shared.dto.UserDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			UserLoginRequest credentials = new ObjectMapper().readValue(request.getInputStream(),
					UserLoginRequest.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(),
					credentials.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String userName = ((User) authResult.getPrincipal()).getUsername();
		String token = Jwts.builder()
						   .setSubject(userName)
						   .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
						   .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
						   .compact();
		UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
		UserDto userDto = userService.getUserByEmail(userName);
		
		response.addHeader(SecurityConstants.HEADER, SecurityConstants.TOKEN_PREFIX + token);
		String objectToReturn = "{ token: '"+token+"', role: '"+userDto.getRole()+"' }";
		response.setContentType("application/json");
		response.getWriter().write(objectToReturn);
	}

}
