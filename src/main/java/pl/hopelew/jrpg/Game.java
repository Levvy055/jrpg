package pl.hopelew.jrpg;

import lombok.Getter;

public class Game {
	@Getter
	private static Game instance;

	public Game() {
		instance = this;
	}
}
