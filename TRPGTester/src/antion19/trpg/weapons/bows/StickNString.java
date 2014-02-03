package antion19.trpg.weapons.bows;

import antion19.trpg.weapons.Bow;

public class StickNString extends Bow {

	public StickNString() {
		super(100);
	}

	@Override
	public String getName() {
		return "Stick n' String";
	}

	@Override
	public int assignRank() {
		return 0;
	}

	@Override
	public int assignHit() {
		return 50;
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
		return 3;
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
	public int minRange() {
		return 1;
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
