package pl.hopelew.jrpg.utils.eventhandlers;

import java.util.Map;

import lombok.Getter;

public class GameEvent {

	private @Getter Object source;
	private @Getter Map<String, Object> values;
	private @Getter EventType type;

	public GameEvent(Object source, EventType type) {
		this.source = source;
		this.type = type;
	}

	public GameEvent(Object source, EventType type, Map<String, Object> values) {
		this(source, type);
		this.values = values;
	}

	public Object getValue(String key) {
		if (values != null && values.containsKey(key)) {
			return values.get(key);
		}
		return null;
	}
}
