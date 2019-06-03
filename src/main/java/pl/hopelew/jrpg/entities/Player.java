package pl.hopelew.jrpg.entities;

import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.entities.data.Sprite;
import pl.hopelew.jrpg.utils.Res;

public class Player extends Entity {

	public Player(String name, Sex sex) throws Exception {
		super(name, sex);
		switch (sex) {
		case MALE:
			sprite = new Sprite(Res.HERO_SPRITE_MALE);
			break;
		case FEMALE:
			//TODO: sprite = new Sprite(Res.HERO_SPRITE_FEMALE);
			break;
		}
		
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		
	}

}
