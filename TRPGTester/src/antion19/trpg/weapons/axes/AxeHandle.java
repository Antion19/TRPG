package antion19.trpg.weapons.axes;

import antion19.trpg.weapons.Axe;

public class AxeHandle extends Axe {

	public AxeHandle() {
		super(100);
		hit -= 20;
	}

	@Override
	public String getName() {
		return "Axe Handle";
	}

	@Override
	public int assignRank() {
		return 0;
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
		return 5;
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
