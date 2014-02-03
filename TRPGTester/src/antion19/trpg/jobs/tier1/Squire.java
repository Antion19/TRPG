package antion19.trpg.jobs.tier1;

import android.content.Context;
import android.graphics.Bitmap;
import antion19.trpg.tools.AssetsGetter;
import antion19.trpg.units.PhysicalJob;
import antion19.trpg.weapons.Weapon;

public class Squire extends PhysicalJob {

	private static Bitmap image = null;

	@Override
	public int accuracy(int distance) {
		if (baseUnit.weapon != null) {
			if (distance == 2) {
				return baseUnit.weapon.hit + baseUnit.skill * 2;
			} else {
				return baseUnit.weapon.hit + baseUnit.skill * 2 - 20;
			}
		} else {
			return 40 + baseUnit.skill * 2;
		}
	}

	public Squire(Context context, int durability) {
		super(durability);
		if (image == null) {
			image = AssetsGetter.getImageAsset(context, "squire.png", 2);
			image.setDensity(0);
		}
	}

	@Override
	public Bitmap getImage() {
		return image;
	}

	@Override
	public String getName() {
		return "Squire";
	}

	@Override
	public int movement() {
		return 2;
	}

	@Override
	public int HPBaseBonus() {
		return 8;
	}

	@Override
	public int strengthBaseBonus() {
		return 8;
	}

	@Override
	public int skillBaseBonus() {
		return 7;
	}

	@Override
	public int agilityBaseBonus() {
		return 1;
	}

	@Override
	public int armorBaseBonus() {
		return 13;
	}

	@Override
	public int wardingBaseBonus() {
		return 3;
	}

	@Override
	public boolean proficientWithWeapon(Weapon weapon) {
		if (weapon.type == 4) {
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
		return 5;
	}

	@Override
	public int assignType() {
		return 144;
	}

	@Override
	public int assignWeight() {
		return 30;
	}
}
