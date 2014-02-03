package antion19.trpg.weapons;

import antion19.trpg.weapons.axes.AxeHandle;

public abstract class Axe extends Weapon {

	/**
	 * 
	 */
	public Axe(int durability) {
		super(durability);
	}

	@Override
	public Weapon weaponBreak() {
		return new AxeHandle();
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
		return 2;
	}

	@Override
	public int assignHit() {
		return 70;
	}

}
