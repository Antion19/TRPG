package antion19.trpg.jobs.tier1;

import android.content.Context;
import android.graphics.Bitmap;
import antion19.trpg.tools.AssetsGetter;
import antion19.trpg.units.Job;
import antion19.trpg.weapons.Weapon;

public class Diabolist extends Job {

	private static Bitmap image = null;

	public Diabolist(Context context, int durability) {
		super(durability);
		if (image == null) {
			image = AssetsGetter.getImageAsset(context, "diabolist.png", 2);
			image.setDensity(0);
		}
	}

	@Override
	public Bitmap getImage() {
		return image;
	}

	@Override
	public String getName() {
		return "Diabolist";
	}

	@Override
	public int minRange() {
		return 1;
	}

	@Override
	public int maxRange() {
		return 2;
	}

	@Override
	public int getAttackType(int distance) {
		return 8;
	}

	@Override
	public int accuracy(int distance) {
		return 70 + 2 * baseUnit.skill;
	}

	@Override
	public int damage(int distance) {
		return (baseUnit.magic * 8) / 5;
	}

	@Override
	public int movement() {
		return 3;
	}

	@Override
	public int HPBaseBonus() {
		return 7;
	}

	@Override
	public int strengthBaseBonus() {
		return 6;
	}

	@Override
	public int magicBaseBonus() {
		return 12;
	}

	@Override
	public int skillBaseBonus() {
		return 6;
	}

	@Override
	public int agilityBaseBonus() {
		return 3;
	}

	@Override
	public int armorBaseBonus() {
		return 3;
	}

	@Override
	public int wardingBaseBonus() {
		return 9;
	}

	@Override
	public boolean proficientWithWeapon(Weapon weapon) {
		return false;
	}

	@Override
	public int xpBaseWorth() {
		return 4;
	}

	@Override
	public int durabilityLowerValue() {
		return 4;
	}

	@Override
	public int assignType() {
		return 181;
	}

	@Override
	public int assignWeight() {
		return 7;
	}

}
