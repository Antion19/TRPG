package antion19.trpg.units;

import android.graphics.Bitmap;
import antion19.trpg.weapons.Weapon;

public abstract class Job {
	
	protected Unit baseUnit;

	/**
	 * first digit = tier, second digit = main weapon type, third digit has the following special properties:
	 * 1 = army type unit, 2 = bandit type unit, 3 = boss type unit, 4 = armored type unit, 5 = has heal node,
	 * 7 = has steal node
	 * 
	 */
	public int type;
	
	/**
	 * 
	 */
	final static public int maxDurability = 100;
	
	/**
	 * 
	 */
	public int durability;
	
	/**
	 * 
	 */
	public int weight;

	/**
	 * 
	 */
	public boolean hasAOE() {
		return false;
	}
	
	/**
	 * 
	 */
	public boolean canHeal() {
		return false;
	}
	
	/**
	 * 
	 */
	public StealNode stealNode = null;
	
	/**
	 * 
	 * @param name
	 */
	protected Job(int durability) {
		this.durability = durability;
		this.type = assignType();
		this.weight = assignWeight();
	}
	
	public void damageArmor(int damage) {
		switch(damage % 5) {
		case 0:
		case 1:
			durability -= durabilityLowerValue();
			break;
		case 2:
			break;
		case 3:
			durability -= (durabilityLowerValue()/2);
			break;
		case 4:
			durability -= (2*durabilityLowerValue());
			break;
		}
		
		if (durability <= 0) {
			durability = 0;
		}
	}
	
	public void repairArmor(int amount) {
		durability += amount;

		if (durability > maxDurability) {
			durability = maxDurability;
		}
	}

	public abstract Bitmap getImage();

	public abstract String getName();

	public abstract int minRange();

	public abstract int maxRange();

	public abstract int getAttackType(int distance);

	public abstract int accuracy(int distance);

	public abstract int damage(int distance);

	public int evadeBonus(int distance, int opponentattackType) {
		if (baseUnit.weapon != null) {
			if (opponentattackType <= 5) {
				return baseUnit.weapon.physicalAvoid;
			} else {
				return baseUnit.weapon.magicAvoid;
			}
		} else {
			return 0;
		}
	}

	public int extraPhysicalDamageReduction(int opponentattackType) {
		return 0;
	}

	public int extraMagicalDamageReduction(int opponentattackType) {
		return 0;
	}

	public int critBonus() {
		return 0;
	}

	public int critDefenceBonus() {
		return 0;
	}
	
	public abstract int durabilityLowerValue();

	public abstract int movement();

	public abstract int HPBaseBonus();

	public abstract int strengthBaseBonus();

	public abstract int magicBaseBonus();

	public abstract int skillBaseBonus();

	public abstract int agilityBaseBonus();

	public abstract int armorBaseBonus();

	public abstract int wardingBaseBonus();

	public abstract boolean proficientWithWeapon(Weapon weapon);

	public abstract int xpBaseWorth();
	
	public abstract int assignType();
	
	public abstract int assignWeight();

}
