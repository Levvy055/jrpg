package pl.hopelew.jrpg.utils.eventhandlers;

import java.util.Map;

import lombok.Getter;
import pl.hopelew.jrpg.world.MapBase;

public class MapChangedGameEvent extends GameEvent {

	private @Getter MapBase map;

	public MapChangedGameEvent(Object source, MapBase map) {
		this(source, map, null);
	}

	public MapChangedGameEvent(Object source, MapBase map, Map<String, Object> values) {
		super(source, EventType.MAP_CHANGED, values);
		this.map = map;
	}

}
