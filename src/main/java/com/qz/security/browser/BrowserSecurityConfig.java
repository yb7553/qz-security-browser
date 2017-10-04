/**
 * 
 */
package com.qz.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.qz.security.core.properties.SecurityProperties;

/**
 * Author:yb
 *
 * Time:2017年9月18日下午6:35:23
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private AuthenticationSuccessHandler qzAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler qzAuthenticationFailureHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.formLogin()
		.loginPage("/authentication/require")
		.loginProcessingUrl("/authentication/form")
		.successHandler(qzAuthenticationSuccessHandler)
		.failureHandler(qzAuthenticationFailureHandler)
//	http.httpBasic()
		.and()
		.authorizeRequests()
		.antMatchers("/authentication/require",
				securityProperties.getBrowser().getLoginPage()).permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.csrf().disable();
		
	}

}
