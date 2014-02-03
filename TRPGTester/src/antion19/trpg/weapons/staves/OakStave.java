package antion19.trpg.weapons.staves;

import antion19.trpg.weapons.Stave;

public class OakStave extends Stave {

	public OakStave(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Oak Stave";
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
		return 3;
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
		return 15;
	}

	@Override
	public int durabilityLowerValue() {
		return 5;
	}

}
