package br.com.eso.password.checker.app.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.eso.password.checker.app.domain.ErrorResponse;

@Provider
public class PasswordCheckerExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

	@Override
	public Response toResponse(IllegalArgumentException exception) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatus(Status.BAD_REQUEST.getStatusCode());
		errorResponse.setMessage(exception.getMessage());
		
		return Response.status(Status.BAD_REQUEST.getStatusCode())
			.entity(errorResponse)
			.build();
	}

}
