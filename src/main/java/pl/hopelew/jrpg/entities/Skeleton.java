package pl.hopelew.jrpg.entities;

import pl.hopelew.jrpg.entities.data.Direction;
import pl.hopelew.jrpg.entities.data.EntityState;
import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.entities.data.Sprite;
import pl.hopelew.jrpg.map.GameMap;
import pl.hopelew.jrpg.utils.Res;

public class Skeleton extends MobEntity {

	public Skeleton(String name, Sex sex) throws Exception {
		super(name, sex);
		sprite = new Sprite(Res.MOB_SKELETON_1);
	}

	@Override
	protected void initialize() {
		position.set(2, 2, Direction.SOUTH);
		state = EntityState.WALKING;
		sprite.toBack();
	}

	@Override
	protected void update(GameMap map) {
		sprite.update(position, state);
	}

}
