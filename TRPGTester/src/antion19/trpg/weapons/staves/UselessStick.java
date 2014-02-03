package antion19.trpg.weapons.staves;

import antion19.trpg.weapons.Stave;

public class UselessStick extends Stave {

	public UselessStick() {
		super(100);
		hit -= 20;
	}

	@Override
	public String getName() {
		return "Useless Stick";
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
