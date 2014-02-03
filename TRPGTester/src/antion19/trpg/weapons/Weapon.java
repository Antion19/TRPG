package antion19.trpg.weapons;

import antion19.trpg.item.Item;

public abstract class Weapon extends Item {
	
	/**
	 * 
	 */
	protected int damage;

	/**
	 * 
	 */
	public int hit;

	/**
	 * 
	 */
	public int physicalAvoid;

	/**
	 * 
	 */
	public int magicAvoid;

	/**
	 * 
	 */
	public int critChance;

	/**
	 * 
	 */
	final static public int maxDurability = 100;
	
	/**
	 * 
	 */
	public int durability;
	
	public Weapon(int durability) {
		super();
		this.durability = durability;
		damage = assignDamage();
		hit = assignHit();
		physicalAvoid = assignPhysicalAvoid();
		magicAvoid = assignMagicAvoid();
		critChance = assignCritChance();
	}

	/**
	 * 
	 * @return
	 */
	public float getPercentageDurability() {
		return ((float) durability) / ((float) maxDurability);
	}
	
	public Weapon damageWeapon(int damage) {
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
			return weaponBreak();
		} else {
			return this;
		}
		
	}
	
	public void repairWeapon(int amount) {
		durability += amount;

		if (durability > maxDurability) {
			durability = maxDurability;
		}
	}
	
	public int damage() {
		if (durability >= 70) {
			return damage;
		} else if (durability >= 50) {
			return (damage - 1);
		} else if (durability >= 30) {
			return (damage - 2);
		} else {
			return (damage/2);
		}
	}
	
	public abstract Weapon weaponBreak();
	
	public abstract int minRange();

	public abstract int maxRange();
	
	public abstract int durabilityLowerValue();
	
	public abstract int assignDamage();
	
	public abstract int assignHit();
	
	public abstract int assignPhysicalAvoid();
	
	public abstract int assignMagicAvoid();
	
	public abstract int assignCritChance();

}
