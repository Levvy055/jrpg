package pl.hopelew.jrpg.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.entities.data.EntityState;
import pl.hopelew.jrpg.entities.data.Position;
import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.entities.data.Sprite;
import pl.hopelew.jrpg.map.GameMap;
import pl.hopelew.jrpg.utils.eventhandlers.EventType;
import pl.hopelew.jrpg.utils.eventhandlers.GameEvent;
import pl.hopelew.jrpg.utils.eventhandlers.GameEventHandler;
import pl.hopelew.jrpg.utils.eventhandlers.ValueChangedGameEvent;

@Log4j2
public abstract class Entity {
	protected @Getter String name;
	protected @Getter Sex sex;
	protected @Getter double hp = 100;
	protected @Getter double mp = 10;
	protected Map<EventType, List<GameEventHandler>> listeners = new HashMap<>();
	protected @Getter Image avatar;
	protected Sprite sprite;
	protected Position position;
	protected EntityState state;

	protected Entity(String name, Sex sex) {
		this.name = name;
		this.sex = sex;
		position = new Position();
		state = EntityState.DEFAULT;
	}

	/**
	 * Adds Event Handler to specified entities event.
	 * 
	 * @param event Event which entity will fire
	 * @param l     event handler
	 */
	public void addListener(EventType event, GameEventHandler l) {
		if (!listeners.containsKey(event)) {
			listeners.put(event, new ArrayList<>());
		}
		listeners.get(event).add(l);
	}

	/**
	 * Fires event to all listeners
	 * 
	 * @param ge
	 */
	protected void fireEvent(GameEvent ge) {
		if (!listeners.containsKey(ge.getType())) {
			return;
		}
		listeners.get(ge.getType()).forEach(eh -> eh.actionPerformed(ge));
	}

	public double changeHp(double hpChange) {
		var oldHp = hp;
		hp += hpChange;
		if (hp < 0) {
			hp = 0;
		}
		log.debug("Entity [{}] HP: {}->{}", name, oldHp, hp);
		var ge = new ValueChangedGameEvent(this, EventType.HP_CHANGED, oldHp, hp);
		fireEvent(ge);
		return hp;
	}

	public double changeMp(double mpChange) {
		if (mpChange < 0 && -mpChange > mp) {
			log.debug("Not enough MP! " + -mpChange + "/" + mp);
			return mp + mpChange;
		}
		var oldMp = mp;
		mp += mpChange;
		if (mp < 0) {
			mp = 0;
		}
		log.debug("Entity [{}] MP: {}->{}", name, oldMp, mp);
		var ge = new ValueChangedGameEvent(this, EventType.MP_CHANGED, oldMp, mp);
		fireEvent(ge);
		return mp;
	}

	public void render(Pane entitiesLayer) {
		entitiesLayer.getChildren().add(sprite);
	}

	public final void updateEntity(GameMap map) {
		update();
	}

	protected abstract void update();
}
