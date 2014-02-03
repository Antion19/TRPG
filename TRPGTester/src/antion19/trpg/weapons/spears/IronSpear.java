package antion19.trpg.weapons.spears;

import antion19.trpg.weapons.Spear;

public class IronSpear extends Spear {

	public IronSpear(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Iron Spear";
	}

	@Override
	public int assignRank() {
		return 2;
	}

	@Override
	public int assignDamage() {
		return 6;
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
		return 35;
	}

	@Override
	public int durabilityLowerValue() {
		return 5;
	}

}
