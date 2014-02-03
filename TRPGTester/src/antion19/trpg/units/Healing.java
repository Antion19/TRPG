package antion19.trpg.units;

public interface Healing {
	
	public abstract int healRange();
	
	public abstract void heal(int distance, Unit unitToHeal);

}
