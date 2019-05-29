package pl.hopelew.jrpg.utils.eventhandlers;

import java.util.Map;

import lombok.Getter;
import pl.hopelew.jrpg.map.GameMap;

public class MapSwitchedGameEvent extends GameEvent {

	private @Getter GameMap map;

	public MapSwitchedGameEvent(Object source, GameMap map) {
		this(source, map, null);
	}

	public MapSwitchedGameEvent(Object source, GameMap map, Map<String, Object> values) {
		super(source, EventType.MAP_SWITCHED, values);
		this.map = map;
	}

}
