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

public class Player {
	private @Getter String name;
	private @Getter Sex sex;
	private @Getter double hp = 100;
	private @Getter double mp;
	private List<EventHandler> listeners = new ArrayList<>();

	public Player(String name, Sex sex) {
		this.name = name;
		this.sex = sex;

	}

	public void addListener(EventHandler l) {
		listeners.add(l);
	}

	public double changeHp(double hpChange) {
		var oldHp = hp;
		hp += hpChange;
		if (hp < 0) {
			hp = 0;
		}
		System.out.println("Player HP: " + oldHp + "->" + hp);
		Map<String, Object> values = new HashMap<>();
		values.put("oldV", oldHp);
		values.put("newV", hp);
		var ge = new GameEvent(this, EventType.HP_CHANGED, values);
		fireEvent(ge);
		return hp;
	}

	private void fireEvent(GameEvent ge) {
		for (EventHandler h : listeners) {
			h.actionPerformed(ge);
		}
	}
}
