package br.com.eso.password.checker.app.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		packages("br.com.eso.password.checker.app.rest");
	}

}
