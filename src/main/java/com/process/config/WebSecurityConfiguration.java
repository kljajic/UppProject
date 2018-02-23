package com.process.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import com.process.service.UserDetailsCustomService;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages="com")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsCustomService userDetailsService;
	
	public WebSecurityConfiguration() {}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.cors().and().csrf().disable();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.authenticationProvider(authenticationProvider());
	}

	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authProvider;
    }
	
	//JWT configuration
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.cors().and().csrf().disable().authorizeRequests()
	     .antMatchers(HttpMethod.POST, "/users/login").permitAll()
	     .anyRequest().authenticated()
	     .and()
	     .addFilterBefore(new JWTAuthenticationFilter(authenticationManager(), jwtTokenGenerator()), UsernamePasswordAuthenticationFilter.class)
	     .addFilterBefore(new JWTAuthorizationFilter(authenticationManager(), jwtTokenGenerator()), BasicAuthenticationFilter.class)
	     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);	
	     
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	}
	
	@Bean
	JWTTokenGenerator jwtTokenGenerator() {
		return new JWTTokenGenerator();
	}
	*/
	
}
