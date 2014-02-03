package antion19.trpg.weapons.axes;

import antion19.trpg.weapons.Axe;

public class IronAxe extends Axe {

	public IronAxe(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Iron Axe";
	}

	@Override
	public int assignRank() {
		return 2;
	}

	@Override
	public int assignDamage() {
		return 9;
	}

	@Override
	public int assignPhysicalAvoid() {
		return 1;
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
		return 40;
	}

	@Override
	public int durabilityLowerValue() {
		return 3;
	}
}
