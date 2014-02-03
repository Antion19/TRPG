package antion19.trpg.weapons.swords;

import antion19.trpg.weapons.Sword;

public class IronSword extends Sword {

	public IronSword(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Iron Sword";
	}

	@Override
	public int assignRank() {
		return 2;
	}

	@Override
	public int assignDamage() {
		return 7;
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
		return 50;
	}

	@Override
	public int durabilityLowerValue() {
		return 3;
	}

}
