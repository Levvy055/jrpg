package pl.hopelew.jrpg.utils.eventhandlers;

import java.util.Map;

import lombok.Getter;

public abstract class GameEvent {

	private @Getter Object source;
	private @Getter Map<String, Object> values;
	private @Getter EventType type;

	protected GameEvent(Object source, EventType type) {
		this.source = source;
		this.type = type;
	}

	protected GameEvent(Object source, EventType type, Map<String, Object> values) {
		this(source, type);
		this.values = values;
	}

	protected Object getValue(String key) {
		if (values != null && values.containsKey(key)) {
			return values.get(key);
		}
		return null;
	}
}
