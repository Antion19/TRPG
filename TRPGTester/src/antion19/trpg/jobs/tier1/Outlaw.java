package antion19.trpg.jobs.tier1;

import android.content.Context;
import android.graphics.Bitmap;
import antion19.trpg.tools.AssetsGetter;
import antion19.trpg.units.PhysicalJob;
import antion19.trpg.weapons.Weapon;

public class Outlaw extends PhysicalJob {

	private static Bitmap image = null;

	public Outlaw(Context context, int durability) {
		super(durability);
		if (image == null) {
			image = AssetsGetter.getImageAsset(context, "outlaw.png", 2);
			image.setDensity(0);
		}
	}

	@Override
	public Bitmap getImage() {
		return image;
	}

	@Override
	public String getName() {
		return "OutLaw";
	}

	@Override
	public int movement() {
		return 3;
	}

	@Override
	public int HPBaseBonus() {
		return 5;
	}

	@Override
	public int strengthBaseBonus() {
		return 8;
	}

	@Override
	public int skillBaseBonus() {
		return 9;
	}

	@Override
	public int agilityBaseBonus() {
		return 8;
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
		if (weapon.type == 3) {
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
		return 132;
	}

	@Override
	public int assignWeight() {
		return 7;
	}
}
