package antion19.trpg.weapons.swords;

import antion19.trpg.weapons.Sword;

public class SteelSword extends Sword {

	public SteelSword(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Steel Sword";
	}

	@Override
	public int assignRank() {
		return 3;
	}

	@Override
	public int assignDamage() {
		return 9;
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
		return 90;
	}

	@Override
	public int durabilityLowerValue() {
		return 2;
	}

}
