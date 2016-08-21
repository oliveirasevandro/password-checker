package br.com.eso.password.checker.app.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PasswordDeductionsCheckFunctionTest {
	
	private PasswordDeductionsCheckFunction function = new PasswordDeductionsCheckFunction();
	
	@Test
	public void lettersOnly() {
		
		// -n
		assertEquals(new Long(-6), function.lettersOnly.apply("abcdef"));
		assertEquals(new Long(0), function.lettersOnly.apply("123456"));
		assertEquals(new Long(0), function.lettersOnly.apply("abcde1"));
		assertEquals(new Long(0), function.lettersOnly.apply(""));
	}
	
	@Test
	public void numbersOnly() {
		
		// -n
		assertEquals(new Long(-6), function.numbersOnly.apply("123456"));
		assertEquals(new Long(0), function.numbersOnly.apply("abcdef"));
		assertEquals(new Long(0), function.numbersOnly.apply("12345a"));
		assertEquals(new Long(0), function.numbersOnly.apply(""));
	}
	
	@Test
	public void consecutiveUppercaseLetters() {
		
		// -(n*2)
		assertEquals(new Long(-2), function.consecutiveUpperCase.apply("ABcDef"));
		assertEquals(new Long(-2), function.consecutiveUpperCase.apply("abCDef"));
		assertEquals(new Long(0), function.consecutiveUpperCase.apply("aBcDeF"));
		assertEquals(new Long(-6), function.consecutiveUpperCase.apply("ABCD"));
		assertEquals(new Long(0), function.consecutiveUpperCase.apply("abcd"));
		assertEquals(new Long(0), function.consecutiveUpperCase.apply(""));
	}
	
	@Test
	public void consecutiveLowercaseLetters() {
		
		// -(n*2)
		assertEquals(new Long(-2), function.consecutiveLowerCase.apply("abCdEf"));
		assertEquals(new Long(-2), function.consecutiveLowerCase.apply("ABcdEF"));
		assertEquals(new Long(0), function.consecutiveLowerCase.apply("aBcDeF"));
		assertEquals(new Long(-6), function.consecutiveLowerCase.apply("xxxx"));
		assertEquals(new Long(0), function.consecutiveLowerCase.apply("XXXX"));
		assertEquals(new Long(0), function.consecutiveLowerCase.apply(""));
	}
	
	@Test
	public void consecutiveNumbers() {
		
		// -(n*2)
		assertEquals(new Long(-2), function.consecutiveNumbers.apply("11abc2e"));
		assertEquals(new Long(-2), function.consecutiveNumbers.apply("ab49cde"));
		assertEquals(new Long(0), function.consecutiveNumbers.apply("a1b2c3d4"));
		assertEquals(new Long(-4), function.consecutiveNumbers.apply("a123bc"));
		assertEquals(new Long(-6), function.consecutiveNumbers.apply("1234"));
		assertEquals(new Long(0), function.consecutiveNumbers.apply(""));
	}
	
	@Test
	public void sequentialLetters() {
		
		// -(n*3)
		assertEquals(new Long(0), function.sequentialLetters.apply("abababab"));
		assertEquals(new Long(-9), function.sequentialLetters.apply("abc1ghi2lmn"));
		assertEquals(new Long(-9), function.sequentialLetters.apply("aBc1ghI2LMn"));
		assertEquals(new Long(-21), function.sequentialLetters.apply("AB1ABC2abcd3ABCDEF"));
		assertEquals(new Long(-3), function.sequentialLetters.apply("abc123456"));
	}
	
	@Test
	public void sequentialNumbers() {
		
		// -(n*3)
		assertEquals(new Long(0), function.sequentialNumbers.apply("12121212"));
		assertEquals(new Long(-9), function.sequentialNumbers.apply("123a345b789"));
		assertEquals(new Long(-18), function.sequentialNumbers.apply("12345678"));
	}
	
	@Test
	public void sequentialSymbols() {
		
		// -(n*3)
		assertEquals(new Long(0), function.sequentialSymbols.apply("#$#$#$"));
		assertEquals(new Long(-6), function.sequentialSymbols.apply("#$%#$%"));
		assertEquals(new Long(-6), function.sequentialSymbols.apply("abcd#$%abcd#$%"));
	}

}
