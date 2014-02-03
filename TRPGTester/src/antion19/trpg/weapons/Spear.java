package antion19.trpg.weapons;

import antion19.trpg.weapons.spears.ThreeFootPole;

public abstract class Spear extends Weapon {

	/**
	 * 
	 */
	public Spear(int durability) {
		super(durability);
	}

	@Override
	public Weapon weaponBreak() {
		return new ThreeFootPole();
	}

	@Override
	public int minRange() {
		return 1;
	}

	@Override
	public int maxRange() {
		return 2;
	}

	@Override
	public int assignType() {
		return 4;
	}

	@Override
	public int assignHit() {
		return 75;
	}

}
