package pl.hopelew.jrpg.entities;

import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.entities.data.Sprite;
import pl.hopelew.jrpg.map.GameMap;
import pl.hopelew.jrpg.utils.Res;
import pl.hopelew.jrpg.utils.eventhandlers.EventType;
import pl.hopelew.jrpg.utils.eventhandlers.KeyGameEvent;
import pl.hopelew.jrpg.utils.eventhandlers.MapSwitchedGameEvent;

public class Player extends Entity {

	public Player(String name, Sex sex) throws Exception {
		super(name, sex);
		switch (sex) {
		case MALE:
			sprite = new Sprite(Res.HERO_SPRITE_MALE);
			break;
		case FEMALE:
			// TODO: sprite = new Sprite(Res.HERO_SPRITE_FEMALE);
			break;
		}

	}

	@Override
	protected void initialize() {
		game.addListener(EventType.KEY_PRESSED, e -> {
			var ge = (KeyGameEvent) e;
			switch (ge.getKey()) {
			case D:
				position.moveX(1);
				break;
			case A:
				position.moveX(-1);
				break;
			case W:
				position.moveY(-1);
				break;
			case S:
				position.moveY(1);
				break;
			default:
				break;
			}
		}, this);
		game.addListener(EventType.MAP_SWITCHED, ge -> {
			var msge = (MapSwitchedGameEvent) ge;
			GameMap map = msge.getMap();
			var ep = map.getEntrance();
			position.set(ep.getX(), ep.getY(), ep.getDirection());
		}, this);
	}

	@Override
	protected void update(GameMap map) {
		sprite.update(position, state);
		map.canMoveTo(0, 0);
	}
}
