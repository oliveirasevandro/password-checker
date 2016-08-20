package br.com.eso.password.checker.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Component;

@Component
@Path("v1/hello")
public class HelloRS {
	
	@GET
	public String hello(@QueryParam("name") String name) {
		
		return "Hello " + name;
	}

}
