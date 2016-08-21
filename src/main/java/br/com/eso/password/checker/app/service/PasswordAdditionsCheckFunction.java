package br.com.eso.password.checker.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.primitives.Chars;

public class PasswordAdditionsCheckFunction implements Function<String, Long> {
	
	private static final Logger LOG = LoggerFactory.getLogger(PasswordAdditionsCheckFunction.class);
	
	Function<String, Long> calculateLength = password -> {
		long result = new Long(password.length() * 4);
		LOG.info("Number of Characters: {}", result);
		return result;
	};
	
	Function<String, Long> calculateUpperCase = password -> {
		long countUpperCase = countUpperCase(password);
		long result = countUpperCase > 0 ? (password.length() - countUpperCase) * 2 : 0;
		LOG.info("Uppercase Letters: {}", result);
		return result;
	};
	
	Function<String, Long> calculateLowerCase = password -> {
		long countLowerCase = countLowerCase(password);
		long result = countLowerCase > 0 ? (password.length() - countLowerCase) * 2 : 0;
		LOG.info("Lowercase Letters: {}", result);
		return result;
	};

	Function<String, Long> calculateNumbers = password -> {
		long result = countNumbers(password) * 4;
		LOG.info("Numbers: {}", result);
		return result;
	};
	
	Function<String, Long> calculateSymbols = password -> {
		long result = countSymbols(password) * 6;
		LOG.info("Symbols: {}", result);
		return result;
	};
	
	Function<String, Long> calculateMiddleNumbersOrSymbols = password -> {
		final String subString = password.substring(1, password.length() - 1);
		long result = (countNumbers(subString) + countSymbols(subString)) * 2;
		LOG.info("Middle Numbers or Symbols: {}", result);
		return result;
	};
	
	Function<String, Long> calculateRequirements = password -> {
		int requirements = countUpperCase(password) > 0 ? 1 : 0;
		requirements += countLowerCase(password) > 0 ? 1 : 0;
		requirements += countNumbers(password) > 0 ? 1 : 0;
		requirements += countSymbols(password) > 0 ? 1 : 0;
		
		long result = requirements >= 3 && password.length() >= 8 ? 
			(requirements + 1) * 2 : 0;
		LOG.info("Requirements: {}", result);
		return result;
	};
	
	private List<Function<String, Long>> additions = new ArrayList<>();
	
	public PasswordAdditionsCheckFunction() {
		additions.add(calculateLength);
		additions.add(calculateUpperCase);
		additions.add(calculateLowerCase);
		additions.add(calculateNumbers);
		additions.add(calculateSymbols);
		additions.add(calculateMiddleNumbersOrSymbols);
		additions.add(calculateRequirements);
	}

	@Override
	public Long apply(String password) {
		LOG.info("---- Applying Additions ----");
		return additions.stream()
			.mapToLong(function -> function.apply(password))
			.sum();
	}


	private long countUpperCase(String password) {
		return Chars.asList(password.toCharArray())
			.stream()
			.filter(Character::isUpperCase)
			.count();
	}

	private long countLowerCase(String password) {
		return Chars.asList(password.toCharArray())
				.stream()
				.filter(Character::isLowerCase)
				.count();
	}
	
	private long countNumbers(String password) {
		return Chars.asList(password.toCharArray())
			.stream()
			.filter(Character::isDigit)
			.count();
	}
	
	private long countSymbols(String password) {
		return Chars.asList(password.toCharArray())
			.stream()
			.filter(c -> Pattern.compile("[^A-Za-z0-9]").matcher(String.valueOf(c)).matches())
			.count();
	}

}
