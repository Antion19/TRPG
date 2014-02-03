package antion19.trpg.weapons.axes;

import antion19.trpg.weapons.Axe;

public class RustyAxe extends Axe {

	public RustyAxe(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Rusty Axe";
	}

	@Override
	public int assignRank() {
		return 1;
	}

	@Override
	public int assignDamage() {
		return 7;
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
		return 20;
	}

	@Override
	public int durabilityLowerValue() {
		return 6;
	}
}
