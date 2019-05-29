package pl.hopelew.jrpg.map;

import java.util.List;

import org.mapeditor.core.MapObject;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.utils.MapGenException;

@Data
@Log4j2
public class GameMap {
	private int id;
	private String name;
	private int vSize;
	private int hSize;
	private String backgroundColor;
	private List<MapTileLayer> tileLayers;
	private List<MapObject> objects;
	private int maxTileHeight;

	public GameMap(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Gets game map from .map file
	 * 
	 * @param id
	 * @return a game map
	 * @throws MapGenException
	 */
	public static GameMap getMap(String id) throws MapGenException {
		var pathname = "/maps/" + id + ".tmx";
		return GameMapBuilder.build(pathname);
	}
}
