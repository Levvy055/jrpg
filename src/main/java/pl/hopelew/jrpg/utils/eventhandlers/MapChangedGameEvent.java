package pl.hopelew.jrpg.utils.eventhandlers;

import java.util.Map;

import lombok.Getter;
import pl.hopelew.jrpg.world.GameMap;

public class MapChangedGameEvent extends GameEvent {

	private @Getter GameMap map;

	public MapChangedGameEvent(Object source, GameMap map) {
		this(source, map, null);
	}

	public MapChangedGameEvent(Object source, GameMap map, Map<String, Object> values) {
		super(source, EventType.MAP_CHANGED, values);
		this.map = map;
	}

}
