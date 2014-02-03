package antion19.trpg.weapons.bows;

import antion19.trpg.weapons.Bow;

public class CompositeBow extends Bow {

	public CompositeBow(int durability) {
		super(durability);
	}

	@Override
	public String getName() {
		return "Composite Bow";
	}

	@Override
	public int assignRank() {
		return 3;
	}

	@Override
	public int assignHit() {
		return 75;
	}

	@Override
	public int assignDamage() {
		return 6;
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
		return 3;
	}

	@Override
	public int assignPrice() {
		return 110;
	}

	@Override
	public int durabilityLowerValue() {
		return 2;
	}

}
