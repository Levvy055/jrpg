package pl.hopelew.jrpg.map;

import java.util.ArrayList;
import java.util.List;

import org.mapeditor.core.MapObject;
import org.mapeditor.core.Tile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.entities.MobEntity;
import pl.hopelew.jrpg.entities.data.Position;

@Data
@Log4j2
public class GameMap {
	@SerializedName("id")
	@Expose
	private int id;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("entities")
	@Expose
	private List<MobEntity> entities;
	@SerializedName("entrance")
	@Expose
	private Position entrance;
	private int vSize;
	private int hSize;
	private String backgroundColor;
	private List<MapTileLayer> tileLayers;
	private List<MapObject> objects;
	private int maxTileHeight;
	private boolean changed = true;

	public GameMap() {
	}

	/**
	 * Checks if a player can go onto {@link Tile} on position x, y
	 * 
	 * @param x
	 * @param y
	 * @return true if can move to the Tile at x,y
	 */
	public boolean canMoveTo(int x, int y) {
		var tiles = getTilesAt(x, y);
		for (Tile tile : tiles) {
			String type = tile.getType();
			if (type != null && type.equals("11")) {
				return false;
			}
		}
		return true;
	}

	private List<Tile> getTilesAt(int x, int y) {
		var list = new ArrayList<Tile>();
		for (MapTileLayer tileLayer : tileLayers) {
			var tile = tileLayer.getTileAt(x, y);
			if (tile != null) {
				var tile2 = tile.getTileSet().getTile(tile.getId());
				list.add(tile2);
			}
		}
		return list;
	}
}
