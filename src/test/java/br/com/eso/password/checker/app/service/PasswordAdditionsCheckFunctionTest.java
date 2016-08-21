package br.com.eso.password.checker.app.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PasswordAdditionsCheckFunctionTest {
	
	private PasswordAdditionsCheckFunction function = new PasswordAdditionsCheckFunction();
	
	@Test
	public void uppercaseLetters() {
		
		//+((len-n)*2)
		assertEquals(new Long(8), function.calculateUpperCase.apply("PaSSw0rD"));
		assertEquals(new Long(0), function.calculateUpperCase.apply("ABC"));
		assertEquals(new Long(0), function.calculateUpperCase.apply("abc"));
		assertEquals(new Long(0), function.calculateUpperCase.apply(""));
	}
	
	@Test
	public void lowercaseLetters() {
		
		//+((len-n)*2)
		assertEquals(new Long(10), function.calculateLowerCase.apply("PaSSw0rD"));
		assertEquals(new Long(4), function.calculateLowerCase.apply("ABcd"));
		assertEquals(new Long(0), function.calculateLowerCase.apply("abc"));
		assertEquals(new Long(0), function.calculateLowerCase.apply("ABC"));
	}
	
	@Test
	public void numbers() {
		
		//+(n*4)
		assertEquals(new Long(4), function.calculateNumbers.apply("PaSSw0rD"));
		assertEquals(new Long(0), function.calculateNumbers.apply("ABcd"));
		assertEquals(new Long(0), function.calculateNumbers.apply("abc"));
		assertEquals(new Long(0), function.calculateNumbers.apply("ABC"));
	}
	
	@Test
	public void symbols() {
		
		//+(n*6)
		assertEquals(new Long(12), function.calculateSymbols.apply("pa$$word"));
		assertEquals(new Long(0), function.calculateSymbols.apply("ABcd"));
		assertEquals(new Long(0), function.calculateSymbols.apply("abc"));
		assertEquals(new Long(0), function.calculateSymbols.apply("ABC"));
	}

	@Test
	public void middleNumbersOrSymbols() {
		
		//+(n*2)
		assertEquals(new Long(8), function.calculateMiddleNumbersOrSymbols.apply("1a1!2@b0"));
		assertEquals(new Long(2), function.calculateMiddleNumbersOrSymbols.apply("AB!cd"));
		assertEquals(new Long(2), function.calculateMiddleNumbersOrSymbols.apply("a1bc"));
		assertEquals(new Long(0), function.calculateMiddleNumbersOrSymbols.apply("!a4"));
	}
	
	@Test
	public void passwordContainsAllRequirements() {
		
		//8 char length, uppercase, lowercase, symbols, numbers
		assertEquals(new Long(10), function.calculateRequirements.apply("A1b2C3d$"));
	}
	
	@Test
	public void passwordShorterThanRequired() {
		
		//7 char length, uppercase, lowercase, symbols, numbers
		assertEquals(new Long(0), function.calculateRequirements.apply("1b2C3d$"));
	}
	
	@Test
	public void passwordAttendsMinimumRequirements() {
		
		//8 char length, uppercase, lowercase, numbers
		assertEquals(new Long(8), function.calculateRequirements.apply("A1b2C3d4"));
	}
	
	@Test
	public void passwordDoesNotAttendMinimumRequirements() {
		
		//8 char length, uppercase, numbers
		assertEquals(new Long(0), function.calculateRequirements.apply("A1B2C3D4"));
	}

}
