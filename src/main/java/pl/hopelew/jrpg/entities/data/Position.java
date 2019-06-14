package pl.hopelew.jrpg.entities.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import pl.hopelew.jrpg.map.GameMap;

public @Data class Position {
	@SerializedName("x")
	@Expose
	private int x;
	@SerializedName("y")
	@Expose
	private int y;
	@SerializedName("direction")
	@Expose
	private Direction direction;
	private GameMap map;

	public Position() {
		x = 0;
		y = 0;
		direction = Direction.NORTH;
	}

	public synchronized void moveX(int delta) {
		if (map.canMoveTo(x + delta, y)) {
			x += delta;
		}
		direction = delta > 0 ? Direction.EAST : Direction.WEST;
		System.out.println(x + "/" + y);
	}

	public synchronized void moveY(int delta) {
		if (map.canMoveTo(x, y + delta)) {
			y += delta;
		}
		direction = delta > 0 ? Direction.SOUTH : Direction.NORTH;
		System.out.println(x + "/" + y);
	}

	public synchronized void set(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
}
