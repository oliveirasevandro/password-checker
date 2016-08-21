package br.com.eso.password.checker.app.domain;

public enum Complexity {
	
	VERY_WEAK("Very Weak", 20l),
	WEAK("Weak", 40l),
	GOOD("Good", 60l),
	STRONG("Strong", 80l),
	VERY_STRONG("Very Strong", 100l);
	
	private final String description;
	
	private final long scoreLimit;
	
	private Complexity(String description, long scoreLimit) {
		this.description = description;
		this.scoreLimit = scoreLimit;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static Complexity getComplexity(long score) {
		
		for (Complexity complexity : values()) {
			if (score < complexity.scoreLimit) {
				return complexity;
			}
		}
		
		return Complexity.VERY_STRONG;
	}

}
