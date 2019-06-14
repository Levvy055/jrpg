package pl.hopelew.jrpg.entities.data;

import com.google.gson.annotations.SerializedName;

public enum Direction {
	@SerializedName("SOUTH")
	SOUTH, @SerializedName("WEST")
	WEST, @SerializedName("NORTH")
	NORTH, @SerializedName("EAST")
	EAST;

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
