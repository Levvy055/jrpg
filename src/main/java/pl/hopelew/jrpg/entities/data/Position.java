package pl.hopelew.jrpg.entities.data;

import lombok.Data;
import pl.hopelew.jrpg.map.GameMap;

public @Data class Position {
	private double x;
	private double y;
	private Direction direction;
	private GameMap map;

	public Position() {
		x = 0;
		y = 0;
		direction = Direction.NORTH;
	}

	public synchronized void moveX(int delta) {
		
		x += delta;
		if (x < 0) {
			x = 0;
		}
		direction = delta > 0 ? Direction.EAST : Direction.WEST;
	}

	public synchronized void moveY(int delta) {
		y += delta;
		if (y < 0) {
			y = 0;
		}
		direction = delta > 0 ? Direction.SOUTH : Direction.NORTH;
	}
}
