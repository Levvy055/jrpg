package pl.hopelew.jrpg;

public class Player {
	private String name;
	private Sex sex;

	public Player(String name, Sex sex) {
		this.name = name;
		this.sex = sex;
		
	}

	public enum Sex {
		MALE, FEMALE
	}
}
