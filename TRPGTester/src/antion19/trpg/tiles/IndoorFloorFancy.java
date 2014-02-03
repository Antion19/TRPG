package antion19.trpg.tiles;


import android.content.Context;
import android.graphics.Bitmap;
import antion19.trpg.tools.AssetsGetter;

/**
 * 
 * @author andrewleinbach
 * 
 */
public class IndoorFloorFancy extends Tile {

	/**
	 * 
	 */
	public static Bitmap image = null;

	/**
	 * 
	 * @param context
	 */
	public IndoorFloorFancy(Context context) {
		if (image == null) {
			image = AssetsGetter.getImageAsset(context, "indoorfloorfancy.png",
					1);
			image.setDensity(0);
		}
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public Bitmap getImage() {
		return image;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isInaccessible() {
		return false;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean BlocksRangedAttacks() {
		return false;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public int getCoverAvoidance() {
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public int getCoverDefence() {
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public int getMoveImpediment() {
		return 1;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String getType() {
		return "Fancy Floor";
	}

}