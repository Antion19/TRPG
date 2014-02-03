package antion19.trpg.weapons.bows;

import antion19.trpg.weapons.Bow;

public class SelfBow extends Bow {

	public SelfBow(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Self Bow";
	}

	@Override
	public int assignRank() {
		return 1;
	}

	@Override
	public int assignHit() {
		return 65;
	}

	@Override
	public int assignDamage() {
		return 3;
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
		return 2;
	}

	@Override
	public int assignPrice() {
		return 35;
	}

	@Override
	public int durabilityLowerValue() {
		return 6;
	}

}
