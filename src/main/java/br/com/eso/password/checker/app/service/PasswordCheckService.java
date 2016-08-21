package br.com.eso.password.checker.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.eso.password.checker.app.domain.Complexity;
import br.com.eso.password.checker.app.domain.PasswordStrength;

@Service
public class PasswordCheckService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PasswordCheckService.class);
	
	private List<Function<String, Long>> checkers = new ArrayList<>();
	
	public PasswordCheckService() {
		checkers.add(new PasswordAdditionsCheckFunction());
		checkers.add(new PasswordDeductionsCheckFunction());
	}
	
	public PasswordStrength calculateStrength(String password) {
		
		LOG.info("---- Calculating Password Strength ----");
		long score = checkers.stream()
			.mapToLong(function -> function.apply(password))
			.sum();
		LOG.info("---- Password Strength is : {}", score);
		
		PasswordStrength strength = new PasswordStrength();
		strength.setScore(score);
		strength.setComplexity(Complexity.getComplexity(score).getDescription());
		return strength;
	}
	
}
