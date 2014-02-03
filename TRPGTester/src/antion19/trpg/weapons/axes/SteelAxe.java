package antion19.trpg.weapons.axes;

import antion19.trpg.weapons.Axe;

public class SteelAxe extends Axe {

	public SteelAxe(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Steel Axe";
	}

	@Override
	public int assignRank() {
		return 3;
	}

	@Override
	public int assignDamage() {
		return 11;
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
		return 80;
	}

	@Override
	public int durabilityLowerValue() {
		return 2;
	}
}
