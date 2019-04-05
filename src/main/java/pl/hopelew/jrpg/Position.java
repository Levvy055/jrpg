package pl.hopelew.jrpg;

import lombok.Data;

public @Data class Position {
	private int x;
	private int y;
	private World world;
	private Direction direction;
}
