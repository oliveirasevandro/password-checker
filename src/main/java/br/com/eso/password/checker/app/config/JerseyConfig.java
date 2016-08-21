package br.com.eso.password.checker.app.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import br.com.eso.password.checker.app.rest.PasswordCheckerRS;

@Component
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		register(PasswordCheckerRS.class);
	}

}
