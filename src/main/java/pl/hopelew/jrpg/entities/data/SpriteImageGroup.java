package pl.hopelew.jrpg.entities.data;

public enum SpriteImageGroup {
	BATTLER(9, 6), DMG(12, 8), WALK(12, 8);

	private int cols;
	private int rows;

	private SpriteImageGroup(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;
	}
}
