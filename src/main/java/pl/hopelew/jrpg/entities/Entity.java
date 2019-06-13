package pl.hopelew.jrpg.entities;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.Game;
import pl.hopelew.jrpg.entities.data.EntityState;
import pl.hopelew.jrpg.entities.data.Position;
import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.entities.data.Sprite;
import pl.hopelew.jrpg.map.GameMap;
import pl.hopelew.jrpg.utils.eventhandlers.EventType;
import pl.hopelew.jrpg.utils.eventhandlers.ValueChangedGameEvent;

@Log4j2
public abstract class Entity {
	protected @Getter String name;
	protected @Getter Sex sex;
	protected @Getter double hp = 100;
	protected @Getter double mp = 10;
	protected @Getter Image avatar;
	protected @Getter Sprite sprite;
	protected @Getter Position position;
	protected @Getter EntityState state;
	private @Getter boolean initialized;
	protected Game game;

	protected Entity(String name, Sex sex) {
		this.name = name;
		this.sex = sex;
		position = new Position();
		state = EntityState.DEFAULT;
	}

	public void initializeEntity(Game game) {
		if (initialized) {
			return;
		}
		this.game = game;
		initialize();
		initialized = true;
	}

	protected abstract void initialize();

	public double changeHp(double hpChange) {
		var oldHp = hp;
		hp += hpChange;
		if (hp < 0) {
			hp = 0;
		}
		log.debug("Entity [{}] HP: {}->{}", name, oldHp, hp);
		var ge = new ValueChangedGameEvent(this, EventType.HP_CHANGED, oldHp, hp);
		game.fireEvent(ge);
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
		game.fireEvent(ge);
		return mp;
	}

	public void render(Pane entitiesLayer) {
		if (!entitiesLayer.getChildren().contains(sprite)) {
			entitiesLayer.getChildren().add(sprite);
		}
	}

	public final void updateEntity(GameMap map) {
		update(map);
	}

	protected abstract void update(GameMap map);
}
