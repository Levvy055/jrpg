package pl.hopelew.jrpg.entities.data;

import lombok.Data;

public @Data class Position {
	private int x;
	private int y;
	private int worldId;
	private Direction direction;
}
