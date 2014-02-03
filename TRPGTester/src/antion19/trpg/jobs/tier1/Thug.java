package antion19.trpg.jobs.tier1;

import android.content.Context;
import android.graphics.Bitmap;
import antion19.trpg.tools.AssetsGetter;
import antion19.trpg.units.PhysicalJob;
import antion19.trpg.weapons.Weapon;

public class Thug extends PhysicalJob {

	private static Bitmap image = null;

	public Thug(Context context, int durability) {
		super(durability);
		if (image == null) {
			image = AssetsGetter.getImageAsset(context, "thug.png", 2);
			image.setDensity(0);
		}
	}

	@Override
	public Bitmap getImage() {
		return image;
	}

	@Override
	public String getName() {
		return "Thug";
	}

	@Override
	public int movement() {
		return 2;
	}

	@Override
	public int HPBaseBonus() {
		return 12;
	}

	@Override
	public int strengthBaseBonus() {
		return 18;
	}

	@Override
	public int skillBaseBonus() {
		return 0;
	}

	@Override
	public int agilityBaseBonus() {
		return 0;
	}

	@Override
	public int armorBaseBonus() {
		return 4;
	}

	@Override
	public int wardingBaseBonus() {
		return 2;
	}

	@Override
	public boolean proficientWithWeapon(Weapon weapon) {
		if (weapon.type == 2) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int xpBaseWorth() {
		return 3;
	}

	@Override
	public int durabilityLowerValue() {
		return 2;
	}

	@Override
	public int assignType() {
		return 122;
	}

	@Override
	public int assignWeight() {
		return 7;
	}

}
