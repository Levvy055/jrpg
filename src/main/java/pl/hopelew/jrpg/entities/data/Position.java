package pl.hopelew.jrpg.entities.data;

import lombok.Data;

public @Data class Position {
	private double x;
	private double y;
	private Direction direction;

	public Position() {
		x = 0;
		y = 0;
		direction = Direction.NORTH;
	}
}
