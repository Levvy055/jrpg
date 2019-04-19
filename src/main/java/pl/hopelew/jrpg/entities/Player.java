package pl.hopelew.jrpg.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.utils.eventhandlers.EventHandler;
import pl.hopelew.jrpg.utils.eventhandlers.EventType;
import pl.hopelew.jrpg.utils.eventhandlers.GameEvent;
import pl.hopelew.jrpg.utils.eventhandlers.ValueChangedGameEvent;

public class Player {
	private @Getter String name;
	private @Getter Sex sex;
	private @Getter double hp = 100;
	private @Getter double mp;
	private Map<EventType, List<EventHandler>> listeners = new HashMap<>();

	public Player(String name, Sex sex) {
		this.name = name;
		this.sex = sex;

	}

	public void addListener(EventType event, EventHandler l) {
		if (!listeners.containsKey(event)) {
			listeners.put(event, new ArrayList<>());
		}
		listeners.get(event).add(l);
	}

	public double changeHp(double hpChange) {
		var oldHp = hp;
		hp += hpChange;
		if (hp < 0) {
			hp = 0;
		}
		System.out.println("Player HP: " + oldHp + "->" + hp);
		var ge = new ValueChangedGameEvent(EventType.HP_CHANGED,this, oldHp, hp);
		fireEvent(ge);
		return hp;
	}

	private void fireEvent(GameEvent ge) {
		if (!listeners.containsKey(ge.getType())) {
			return;
		}
		listeners.get(ge.getType()).forEach(eh -> eh.actionPerformed(ge));
	}
}
