package antion19.trpg.weapons.swords;

import antion19.trpg.weapons.Sword;

public class HunkOfMetal extends Sword {

	public HunkOfMetal() {
		super(100);
		hit -= 20;
	}

	@Override
	public String getName() {
		return "Hunk of Metal";
	}

	@Override
	public int assignRank() {
		return 0;
	}

	@Override
	public int assignDamage() {
		return 2;
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
		return 5;
	}

	@Override
	public float getPercentageDurability() {
		return 0;
	}

	@Override
	public int durabilityLowerValue() {
		return 0;
	}

}
