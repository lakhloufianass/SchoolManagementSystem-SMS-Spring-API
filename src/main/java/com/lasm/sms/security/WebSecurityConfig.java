package com.lasm.sms.security;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lasm.sms.services.UserService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

	private final UserService userDetailService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurityConfig(UserService userDetailService,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userDetailService = userDetailService;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and() //enable cors to permit the API to communicate with different systems and different domains
			.csrf().disable() //disable csrf because there is no form
			.authorizeRequests() 
			.antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL).permitAll() // autorized acces to /users with POST request to everyone
			.anyRequest().authenticated() //Any other request require authentication
			.and()
			.addFilter(getAuthenticationFilter()) // to use the authentication filter that we have implement.
			//.addFilter(new AuthorizationFilter(authenticationManager()))
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);//manage stateless session 
	}
	
	public AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/signin");
		return filter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
	}
}
