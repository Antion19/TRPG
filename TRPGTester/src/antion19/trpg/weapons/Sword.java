package antion19.trpg.weapons;

import antion19.trpg.weapons.swords.HunkOfMetal;

public abstract class Sword extends Weapon {

	/**
	 * 
	 */
	public Sword(int durability) {
		super(durability);
	}

	@Override
	public Weapon weaponBreak() {
		return new HunkOfMetal();
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
		return 1;
	}

	@Override
	public int assignHit() {
		return 80;
	}

}
