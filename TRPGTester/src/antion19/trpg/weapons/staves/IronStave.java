package antion19.trpg.weapons.staves;

import antion19.trpg.weapons.Stave;

public class IronStave extends Stave {

	public IronStave(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Iron Stave";
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
		return 5;
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
		return 45;
	}

	@Override
	public int durabilityLowerValue() {
		return 3;
	}

}
