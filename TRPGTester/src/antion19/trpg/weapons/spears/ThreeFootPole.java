package antion19.trpg.weapons.spears;

import antion19.trpg.weapons.Spear;

public class ThreeFootPole extends Spear {

	public ThreeFootPole() {
		super(100);
		hit -= 20;
	}

	@Override
	public String getName() {
		return "Three Foot Pole";
	}

	@Override
	public int assignRank() {
		return 0;
	}

	@Override
	public int assignDamage() {
		return 2;
	}

	@Override
	public int assignPhysicalAvoid() {
		return 0;
	}

	@Override
	public int assignMagicAvoid() {
		return 0;
	}

	@Override
	public int assignCritChance() {
		return 0;
	}

	@Override
	public int assignWeight() {
		return 4;
	}

	@Override
	public int assignPrice() {
		return 5;
	}

	@Override
	public float getPercentageDurability() {
		return 0;
	}

	@Override
	public int maxRange() {
		return 1;
	}

	@Override
	public int durabilityLowerValue() {
		return 0;
	}

}
