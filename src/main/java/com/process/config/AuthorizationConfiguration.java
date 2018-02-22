package com.process.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.process.security.AuthorizationInterceptor;

@Component
public class AuthorizationConfiguration extends WebMvcConfigurerAdapter{
	
	@Bean
    public AuthorizationInterceptor interceptor() {
        return new AuthorizationInterceptor();
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(interceptor()).addPathPatterns("/offers/**", "/tasks/**").excludePathPatterns("/users/**").pathMatcher(new AntPathMatcher());
	}
	
}
