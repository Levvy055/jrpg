package pl.hopelew.jrpg.map;

import java.util.List;

import org.mapeditor.core.MapObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.entities.MobEntity;

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
	private int vSize;
	private int hSize;
	private String backgroundColor;
	private List<MapTileLayer> tileLayers;
	private List<MapObject> objects;
	private int maxTileHeight;

	public GameMap() {
	}

}
