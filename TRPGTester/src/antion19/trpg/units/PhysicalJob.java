package antion19.trpg.units;

public abstract class PhysicalJob extends Job {

	protected PhysicalJob(int durability) {
		super(durability);
	}

	@Override
	public int magicBaseBonus() {
		return 0;
	}

	@Override
	public int minRange() {
		if (baseUnit.weapon != null) {
			return baseUnit.weapon.minRange();
		} else {
			return 1;
		}
	}

	@Override
	public int maxRange() {
		if (baseUnit.weapon != null) {
			return baseUnit.weapon.maxRange();
		} else {
			return 1;
		}
	}

	@Override
	public int getAttackType(int distance) {
		if (baseUnit.weapon != null) {
			return baseUnit.weapon.type;
		} else {
			return 0;
		}
	}

	@Override
	public int accuracy(int distance) {
		if (baseUnit.weapon != null) {
			return baseUnit.weapon.hit + baseUnit.skill * 2;
		} else {
			return 40 + baseUnit.skill * 2;
		}
	}

	@Override
	public int damage(int distance) {
		if (baseUnit.weapon != null) {
			return baseUnit.strength + baseUnit.weapon.damage();
		} else {
			return baseUnit.strength / 2;
		}
	}

}
