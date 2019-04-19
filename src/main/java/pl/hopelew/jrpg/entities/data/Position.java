package pl.hopelew.jrpg.entities.data;

import lombok.Data;
import pl.hopelew.jrpg.world.World;

public @Data class Position {
	private int x;
	private int y;
	private World world;
	private Direction direction;
}
