package pl.hopelew.jrpg.entities.data;

import lombok.Getter;

public enum SpriteImageGroup {
	BATTLER(9, 6, 9, 6), DMG(3, 1, 12, 8), WALK(3, 4, 12, 8), MOB(3, 3, 3, 3);

	private @Getter int maxCols, cols;
	private @Getter int maxRows, rows;

	private SpriteImageGroup(int cols, int rows, int maxCols, int maxRows) {
		this.cols = cols;
		this.rows = rows;
		this.maxCols = maxCols;
		this.maxRows = maxRows;
	}
}
