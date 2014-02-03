package antion19.trpg.item;

public abstract class Item {

	/**
	 * an integer that represents the type of item; single digits
	 * are for weapons
	 */
	public int type;

	/**
	 * rank represents how powerful an item is compared to others of
	 * the same type, with higher values representing stronger items
	 */
	public int rank;

	/**
	 * integer representing the price of this item
	 */
	public int price;

	/**
	 * 
	 */
	public int weight;
	
	public Item() {
		type = assignType();
		rank = assignRank();
		price = assignPrice();
		weight = assignWeight();
		
	}
	
	@Override
	public String toString() {
		return getName() + ": " + weight + "lbs";
	}

	// for getting item name
	public abstract String getName();
	
	public abstract int assignType();
	
	public abstract int assignRank();
	
	public abstract int assignPrice();
	
	public abstract int assignWeight();

}
