package antion19.trpg.weapons.staves;

import antion19.trpg.weapons.Stave;

public class SteelStave extends Stave {

	public SteelStave(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Steel Stave";
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
		return 85;
	}

	@Override
	public int durabilityLowerValue() {
		return 2;
	}

}
