package antion19.trpg.weapons.swords;

import antion19.trpg.weapons.Sword;

public class RustySword extends Sword {

	public RustySword(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Rusty Sword";
	}

	@Override
	public int assignRank() {
		return 1;
	}

	@Override
	public int assignDamage() {
		return 5;
	}

	@Override
	public int assignPhysicalAvoid() {
		return 2;
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
		return 25;
	}

	@Override
	public int durabilityLowerValue() {
		return 6;
	}

}
