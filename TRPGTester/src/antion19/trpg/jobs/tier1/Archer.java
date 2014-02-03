package antion19.trpg.jobs.tier1;

import android.content.Context;
import android.graphics.Bitmap;
import antion19.trpg.tools.AssetsGetter;
import antion19.trpg.units.PhysicalJob;
import antion19.trpg.weapons.Weapon;

public class Archer extends PhysicalJob {

	private static Bitmap image = null;

	@Override
	public int accuracy(int distance) {
		int accuracy = 0;
		if (baseUnit.weapon != null) {
			accuracy = baseUnit.weapon.hit + baseUnit.skill * 2;
		} else {
			accuracy = 40 + baseUnit.skill * 2;
		}

		switch (distance) {
		case 4:
			accuracy -= 20;
			break;
		case 3:
			accuracy -= 10;
			break;
		}
		return accuracy;
	}

	public Archer(Context context, int durability) {
		super(durability);
		if (image == null) {
			image = AssetsGetter.getImageAsset(context, "archer.png", 2);
			image.setDensity(0);
		}
	}

	@Override
	public Bitmap getImage() {
		return image;
	}

	@Override
	public String getName() {
		return "Archer";
	}

	@Override
	public int movement() {
		return 3;
	}

	@Override
	public int HPBaseBonus() {
		return 8;
	}

	@Override
	public int strengthBaseBonus() {
		return 7;
	}

	@Override
	public int skillBaseBonus() {
		return 10;
	}

	@Override
	public int agilityBaseBonus() {
		return 7;
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
		if (weapon.type == 5) {
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
		return 151;
	}

	@Override
	public int assignWeight() {
		return 9;
	}
}
