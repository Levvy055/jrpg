package pl.hopelew.jrpg.world;

import java.util.List;

import org.mapeditor.core.MapObject;

import lombok.Builder;
import lombok.Getter;

public class MapObjectLayer {
	private @Getter List<MapObject> objects;

	@Builder
	private MapObjectLayer(List<MapObject> objects) {
		this.objects = objects;
	}
}
