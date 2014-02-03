package antion19.trpg.weapons.spears;

import antion19.trpg.weapons.Spear;

public class SteelSpear extends Spear {

	public SteelSpear(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Steel Spear";
	}

	@Override
	public int assignRank() {
		return 3;
	}

	@Override
	public int assignDamage() {
		return 8;
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
		return 6;
	}

	@Override
	public int assignPrice() {
		return 75;
	}

	@Override
	public int durabilityLowerValue() {
		return 3;
	}
}
