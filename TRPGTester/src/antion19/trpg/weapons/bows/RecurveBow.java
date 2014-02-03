package antion19.trpg.weapons.bows;

import antion19.trpg.weapons.Bow;

public class RecurveBow extends Bow {

	public RecurveBow(int durability) {
		super(durability);
	}
	
	@Override
	public String getName() {
		return "Recurve Bow";
	}
	
	@Override
	public int assignRank() {
		return 2;
	}

	@Override
	public int assignHit() {
		return 70;
	}

	@Override
	public int assignDamage() {
		return 5;
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
		return 70;
	}

	@Override
	public int durabilityLowerValue() {
		return 3;
	}

}
