package pl.hopelew.jrpg;

public enum Direction {
	NORTH, EAST, SOUTH, WEST;

	public Direction rotateLeft() {
		switch (this) {
		case NORTH:
			return WEST;
		case WEST:
			return SOUTH;
		case SOUTH:
			return EAST;
		case EAST:
			return NORTH;
		}
		return null;
	}

	public Direction rotateRight() {
		switch (this) {
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		}
		return null;
	}
}
