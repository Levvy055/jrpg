package pl.hopelew.jrpg.utils.eventhandlers;

import java.util.Map;

import lombok.Getter;

public abstract class GameEvent {

	private @Getter Object source;
	private @Getter Map<String, Object> values;
	private @Getter EventType type;
	private @Getter Object target;

	protected GameEvent(Object source, EventType type) {
		this(source, type, null, null);
	}

	protected GameEvent(Object source, EventType type, Map<String, Object> values) {
		this(source, type, values, null);
	}

	protected GameEvent(Object source, EventType type, Object target) {
		this(source, type, null, target);
	}

	protected GameEvent(Object source, EventType type, Map<String, Object> values, Object target) {
		this.values = values;
		this.target = target;
		this.source = source;
		this.type = type;
	}

	protected Object getValue(String key) {
		if (values != null && values.containsKey(key)) {
			return values.get(key);
		}
		return null;
	}
}
