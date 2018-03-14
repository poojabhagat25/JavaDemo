package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.demo.interceptor.LogInterceptor;

@SpringBootApplication
@EnableTransactionManagement
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.example")
public class PoojaSpringBootDemoApplication extends WebMvcConfigurerAdapter{
	@Autowired
	LogInterceptor logInterceptor;
	
	public static void main(String[] args) {
		SpringApplication.run(PoojaSpringBootDemoApplication.class, args);
	}
	
//	Register HandlerInterceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor);
	}
}
