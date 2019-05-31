package pl.hopelew.jrpg.entities;

import pl.hopelew.jrpg.entities.data.Sex;

public abstract class MobEntity extends Entity{

	protected MobEntity(String name, Sex sex) {
		super(name, sex);
	}

}
