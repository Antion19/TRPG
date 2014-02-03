package antion19.trpg.weapons;

import antion19.trpg.weapons.staves.UselessStick;

public abstract class Stave extends Weapon {

	/**
	 * 
	 */
	public Stave(int durability) {
		super(durability);
	}

	@Override
	public Weapon weaponBreak() {
		return new UselessStick();
	}

	@Override
	public int minRange() {
		return 1;
	}

	@Override
	public int maxRange() {
		return 1;
	}

	@Override
	public int assignType() {
		return 3;
	}

	@Override
	public int assignHit() {
		return 85;
	}

}
