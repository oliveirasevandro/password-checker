package br.com.eso.password.checker.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PasswordCheckService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PasswordCheckService.class);
	
	public long calculateStrength(String password) {
		
		LOG.info("---- Calculating Password Strength ----");
		long strength = new PasswordAdditionsCheckFunction().apply(password);
		LOG.info("---- Password Strength is : {}", strength);
		
		return strength;
	}
	
}
