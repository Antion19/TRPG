package antion19.trpg.jobs.tier1;

import android.content.Context;
import android.graphics.Bitmap;
import antion19.trpg.tools.AssetsGetter;
import antion19.trpg.units.PhysicalJob;
import antion19.trpg.weapons.Weapon;

public class Mercenary extends PhysicalJob {

	private static Bitmap image = null;

	public Mercenary(Context context, int durability) {
		super(durability);
		if (image == null) {
			image = AssetsGetter.getImageAsset(context, "mercenary.png", 2);
			image.setDensity(0);
		}
	}

	@Override
	public Bitmap getImage() {
		return image;
	}

	@Override
	public String getName() {
		return "Mercenary";
	}

	@Override
	public int movement() {
		return 3;
	}

	@Override
	public int HPBaseBonus() {
		return 9;
	}

	@Override
	public int strengthBaseBonus() {
		return 13;
	}

	@Override
	public int skillBaseBonus() {
		return 5;
	}

	@Override
	public int agilityBaseBonus() {
		return 5;
	}

	@Override
	public int armorBaseBonus() {
		return 5;
	}

	@Override
	public int wardingBaseBonus() {
		return 3;
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
		return 4;
	}

	@Override
	public int durabilityLowerValue() {
		return 3;
	}

	@Override
	public int assignType() {
		return 121;
	}

	@Override
	public int assignWeight() {
		return 9;
	}
}
