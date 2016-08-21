package br.com.eso.password.checker.app.domain;

public class PasswordStrength {
	
	private String score;
	
	private String complexity;

	public String getScore() {
		return score;
	}

	public void setScore(long score) {
		if (score > 100) {
			score = 100;
		}
		this.score = score + " %";
	}

	public String getComplexity() {
		return complexity;
	}

	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}
	
}
