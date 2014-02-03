package antion19.trpg.units;

public interface AOE {

	public abstract int rangeOfAOE();

	public abstract void enemyEffectingAOE(Unit u);

	public abstract void allyEffectingAOE(Unit u);
	
}
