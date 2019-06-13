package pl.hopelew.jrpg.utils.eventhandlers;

import javafx.scene.input.KeyCode;
import lombok.Getter;

public class KeyGameEvent extends GameEvent {
	private @Getter KeyCode key;
	private @Getter boolean isShiftDown;
	private @Getter boolean isShortcutDown;
	private @Getter boolean isAltDown;

	public KeyGameEvent(KeyCode key, EventType type, boolean isShiftDown, boolean isShortcutDown,
			boolean isAltDown) {
		super(null, type);
		this.key = key;
		this.isShiftDown = isShiftDown;
		this.isShortcutDown = isShortcutDown;
		this.isAltDown = isAltDown;
	}

}
