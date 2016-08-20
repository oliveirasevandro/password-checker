package br.com.eso.password.checker.app.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Component;

import br.com.eso.password.checker.app.service.PasswordCheckService;

@Component
@Path("v1/check")
public class PasswordCheckerRS {
	
	@Inject
	private PasswordCheckService service;
	
	@GET
	public long checkPassword(@QueryParam(value = "password") String password) {
		
		return service.calculateStrength(password);
	}

}
