package antion19.trpg.tiles;

import android.content.Context;
import android.graphics.Bitmap;
import antion19.trpg.tools.AssetsGetter;


public class Pit extends Tile{

	/**
	 * 
	 */
	public static Bitmap image = null;

	/**
	 * 
	 * @param context
	 */
	public Pit(Context context) {
		if (image == null) {
			image = AssetsGetter.getImageAsset(context, "pit.png", 1);
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
		return true;
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
		return "Pit";
	}

	
}
