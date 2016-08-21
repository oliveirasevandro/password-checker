package br.com.eso.password.checker.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Chars;

public class PasswordDeductionsCheckFunction implements Function<String, Long> {
	
	private static final Logger LOG = LoggerFactory.getLogger(PasswordDeductionsCheckFunction.class);
	
	private static final String ONLY_LETTERS_PATTERN = "[a-zA-Z]+";
	
	private static final String ONLY_DIGITS_PATTERN = "\\d+";
	
	private static final String ONLY_SYMBOLS_PATTERN = "[^A-Za-z0-9]+";
	
	Function<String, Long> lettersOnly = password -> {
		long letters = countLetters(password);
		long result = letters == password.length() ? Math.negateExact(password.length()) : 0;
		LOG.info("Letters Only: {}", result);
		return result;
	};
	
	Function<String, Long> numbersOnly = password -> {
		long numbers = countNumbers(password);
		long result = numbers == password.length() ? Math.negateExact(password.length()) : 0;
		LOG.info("Numbers Only: {}", result);
		return result;
	};
	
	Function<String, Long> consecutiveUpperCase = password -> {
		long consecutiveUpperCases = countConsecutiveUpperCase(password, null);
		long result = Math.negateExact(consecutiveUpperCases * 2);
		LOG.info("Consecutive Uppercase Letters: {}", result);
		return result;
	};
	
	Function<String, Long> consecutiveLowerCase = password -> {
		long consecutiveLowerCases = countConsecutiveLowerCase(password, null);
		long result = Math.negateExact(consecutiveLowerCases * 2);
		LOG.info("Consecutive Lowercase Letters: {}", result);
		return result;
	};
	
	Function<String, Long> consecutiveNumbers = password -> {
		long consecutiveNumbers = countConsecutiveNumber(password, null);
		long result = Math.negateExact(consecutiveNumbers * 2);
		LOG.info("Consecutive Numbers: {}", result);
		return result;
	};
	
	Function<String, Long> sequentialLetters = password -> {

		long sequentialLetters = getStringMatches(password, ONLY_LETTERS_PATTERN)
			.stream()
			.mapToLong(this::calculateSequentialCharacters)
			.sum();
		
		long result = Math.negateExact(sequentialLetters * 3);
		
		LOG.info("Sequential letters (3+): {}", result);
		return result;
	};
	
	Function<String, Long> sequentialNumbers = password -> {
		
		long sequentialNumbers = getStringMatches(password, ONLY_DIGITS_PATTERN)
			.stream()
			.mapToLong(this::calculateSequentialCharacters)
			.sum();
		
		long result = Math.negateExact(sequentialNumbers * 3);
		
		LOG.info("Sequential numbers (3+): {}", result);
		return result;
	};
	
	Function<String, Long> sequentialSymbols = password -> {
		
		long sequentialSymbols = getStringMatches(password, ONLY_SYMBOLS_PATTERN)
				.stream()
				.mapToLong(this::calculateSequentialCharacters)
				.sum();
		
		long result = Math.negateExact(sequentialSymbols * 3);
		
		LOG.info("Sequential symbols (3+): {}", result);
		return result;
	};
	
	private List<Function<String, Long>> deductions = new ArrayList<>();
	
	public PasswordDeductionsCheckFunction() {
		deductions.add(lettersOnly);
		deductions.add(numbersOnly);
		deductions.add(consecutiveUpperCase);
		deductions.add(consecutiveLowerCase);
		deductions.add(consecutiveNumbers);
		deductions.add(sequentialLetters);
		deductions.add(sequentialNumbers);
		deductions.add(sequentialSymbols);
	}

	@Override
	public Long apply(String password) {
		
		LOG.info("---- Applying Deductions ----");
		return deductions.stream()
			.mapToLong(function -> function.apply(password))
			.sum();
	}
	
	private long countLetters(String password) {
		return Chars.asList(password.toCharArray())
			.stream()
			.filter(Character::isLetter)
			.count();
	}
	
	private long countNumbers(String password) {
		return Chars.asList(password.toCharArray())
			.stream()
			.filter(Character::isDigit)
			.count();
	}
	
	private long countConsecutiveUpperCase(String password, Character last) {
		
		if (!Strings.isNullOrEmpty(password)) {
			if (last != null && Character.isUpperCase(last) && Character.isUpperCase(password.charAt(0))) {
				return 1 + countConsecutiveUpperCase(password.substring(1), password.charAt(0));
			} else {
				return 0 + countConsecutiveUpperCase(password.substring(1), password.charAt(0));
			}
		}
		return 0;
	}
	
	private long countConsecutiveLowerCase(String password, Character last) {
		
		if (!Strings.isNullOrEmpty(password)) {
			if (last != null && Character.isLowerCase(last) && Character.isLowerCase(password.charAt(0))) {
				return 1 + countConsecutiveLowerCase(password.substring(1), password.charAt(0));
			} else {
				return 0 + countConsecutiveLowerCase(password.substring(1), password.charAt(0));
			}
		}
		return 0;
	}
	
	private long countConsecutiveNumber(String password, Character last) {
		
		if (!Strings.isNullOrEmpty(password)) {
			if (last != null && Character.isDigit(last) && Character.isDigit(password.charAt(0))) {
				return 1 + countConsecutiveNumber(password.substring(1), password.charAt(0));
			} else {
				return 0 + countConsecutiveNumber(password.substring(1), password.charAt(0));
			}
		}
		return 0;
	}
	
	private long calculateSequentialCharacters(String password) {
		
		final List<Integer> positionDiff = getPositionDiff(password);
		final Map<Integer, List<Integer>> sequences = getSequences(positionDiff);
		
		return sequences.entrySet()
			.stream()
			.map(entry -> entry.getValue().size())
			.filter(value -> value > 1) //remove small sequences
			.map(value -> value - 1)
			.reduce((acum, value) -> acum + value)
			.orElseGet(() -> 0);
	}
	
	private Map<Integer, List<Integer>> getSequences(List<Integer> positionDiff) {
		
		int i = 0;
		int sequences = 1;
		
		Map<Integer, List<Integer>> sequencesMap = Maps.newHashMap();
		while (i < positionDiff.size()) {
			if (positionDiff.get(i).equals(1)) {
				List<Integer> seq = sequencesMap.get(sequences);
				if (seq != null) {
					seq.add(i);
				} else {
					sequencesMap.put(sequences, Lists.newArrayList(i));
				}
			} else {
				sequences++;
			}
			
			i++;
		}
		
		return sequencesMap;
		
	}

	private List<Integer> getPositionDiff(String password) {
		
		List<Integer> asIntegerList = Chars.asList(password.toCharArray())
			.stream()
			.map(Character::toUpperCase)
			.map(c -> (int) c)
			.collect(Collectors.toList());
		
		List<Integer> positionDiff = Lists.newArrayList(0);
		for (int i = 1; i < asIntegerList.size(); i++) {
			positionDiff.add(asIntegerList.get(i) - asIntegerList.get(i - 1));
		}
		
		return positionDiff;
			
	}
	
	private List<String> getStringMatches(String value, String pattern) {
		
		final Pattern p = Pattern.compile(pattern);
		final Matcher matcher = p.matcher(value);
		
		List<String> matches = Lists.newArrayList();
		while (matcher.find()) {
			matches.add(matcher.group());
		}
		
		return matches;
	}
	
}
