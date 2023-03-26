package br.com.lenox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//diz ao spring q quando tiver subindo a aplicação ele tem que vir aki antes
@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		// via QUERY PARAM para http://localhost:8080/api/person/v1?mediaType=xml
		/*
		configurer.favorParameter(true).parameterName("mediaType")
		.ignoreAcceptHeader(true)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
		.mediaType("json",MediaType.APPLICATION_JSON )
		.mediaType("xml",MediaType.APPLICATION_XML );
		//ajustar controler
		*/
		
		// Via HEADER PARAM para http://localhost:8080/api/person/v1
		
		configurer.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json",MediaType.APPLICATION_JSON )
			.mediaType("xml",MediaType.APPLICATION_XML );
	}

}
