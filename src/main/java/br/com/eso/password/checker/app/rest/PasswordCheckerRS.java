package br.com.eso.password.checker.app.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import br.com.eso.password.checker.app.service.PasswordCheckService;

@Component
@Path("v1/check")
public class PasswordCheckerRS {
	
	@Inject
	private PasswordCheckService service;
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkPassword(String password) {
		
		Preconditions.checkArgument(!Strings.isNullOrEmpty(password),
			"password must be informed");
		
		return Response.ok(service.calculateStrength(password)).build();
	}

}
