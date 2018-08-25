package org.vbazurtob.hrrecruitapp.rest.conf;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.vbazurtob.hrrecruitapp.rest.conf.ControllerEndpoints.*;

/**
 * @author voltaire
 *
 */

//@EnableWebSecurity
public class AppSecurityConfig  {


		
	public AppSecurityConfig() {		
	}
	
	@Configuration
	public static class PublicSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception{

			http
			.authorizeRequests()
			.antMatchers( INDEX_PAGE).permitAll()
			.antMatchers(ROOT_API  +  "/**").permitAll()
			//.antMatchers(CSS_FOLDER + "**", JS_FOLDER + "**", IMG_FOLDER + "**").permitAll()
			.anyRequest().authenticated()

			.and().csrf().disable()
			;

		}
	}


}
