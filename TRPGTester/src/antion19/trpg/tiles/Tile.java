package antion19.trpg.tiles;

import android.graphics.Bitmap;

/**
 * 
 * @author andrewleinbach
 *
 */
public abstract class Tile {

	public abstract Bitmap getImage();
	
	public abstract boolean isInaccessible();
	
	public abstract boolean BlocksRangedAttacks();

	public abstract int getCoverAvoidance();

	public abstract int getCoverDefence();

	public abstract int getMoveImpediment();

	public abstract String getType();

}
