package pl.hopelew.jrpg.utils.eventhandlers;

import lombok.Getter;

public class ValueChangedGameEvent extends GameEvent {
	private @Getter Object oldValue;
	private @Getter Object newValue;

	public ValueChangedGameEvent(EventType type, Object source, Object oldValue, Object newValue) {
		super(source, type);
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

}
