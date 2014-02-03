package antion19.trpg.weapons.spears;

import antion19.trpg.weapons.Spear;

public class WoodenSpear extends Spear {

	public WoodenSpear(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Wooden Spear";
	}

	@Override
	public int assignRank() {
		return 1;
	}

	@Override
	public int assignDamage() {
		return 4;
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
		return 5;
	}

	@Override
	public int assignPrice() {
		return 15;
	}

	@Override
	public int durabilityLowerValue() {
		return 10;
	}
}
