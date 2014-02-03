package antion19.trpg.armor;

import antion19.trpg.item.Item;

public abstract class Armor extends Item {

	/**
	 * 
	 */
	public int armor;

	/**
	 * 
	 */
	public int warding;

	/**
	 * 
	 */
	public int maxDurability;

	/**
	 * 
	 */
	public int currentDurability;

	/**
	 * 
	 * @return
	 */
	public float getPercentageDurability() {
		return ((float) currentDurability) / ((float) maxDurability);
	}

	public Armor loseDurability(int amount) {
		currentDurability -= amount;

		if (currentDurability <= 0) {
			return armorBreak();
		}
		
		return this;
	}
	
	public void gainDurability(int amount) {
		currentDurability += amount;

		if (currentDurability > maxDurability) {
			currentDurability = maxDurability;
		}
	}

	public abstract Armor armorBreak();

}
