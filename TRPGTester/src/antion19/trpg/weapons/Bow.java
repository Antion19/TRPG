package antion19.trpg.weapons;

import antion19.trpg.weapons.bows.StickNString;

public abstract class Bow extends Weapon {

	/**
	 * 
	 */
	protected Bow(int durability) {
		super(durability);
	}

	@Override
	public Weapon weaponBreak() {
		return new StickNString();
	}

	@Override
	public int minRange() {
		return 2;
	}

	@Override
	public int maxRange() {
		return 4;
	}

	@Override
	public int assignType() {
		return 5;
	}

}
