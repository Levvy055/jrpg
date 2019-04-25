package pl.hopelew.jrpg.world;

import javafx.scene.Node;
import lombok.Getter;

public abstract class MapBase {
	private @Getter String id;
	private @Getter int vSize;
	private @Getter int hSize;
	private @Getter String name;

	protected MapBase(int id, int vSize, int hSize, String name) {
		this.vSize = vSize;
		this.hSize = hSize;
		this.name = name;
	}

	public Node getGrid() {
		// TODO Auto-generated method stub
		return null;
	}
}
