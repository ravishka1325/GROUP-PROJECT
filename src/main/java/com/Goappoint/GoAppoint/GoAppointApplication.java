package com.Goappoint.GoAppoint;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GoAppointApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoAppointApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper (){
		return new ModelMapper();
	}

	@Configuration
	public static class WebMvcConfig implements WebMvcConfigurer {
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/images/**")
					.addResourceLocations("file:///C:/xampp/htdocs/GoAppoint/images/");
		}
	}

}
