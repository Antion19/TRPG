package antion19.trpg.jobs.tier1;

import android.content.Context;
import android.graphics.Bitmap;
import antion19.trpg.tools.AssetsGetter;
import antion19.trpg.units.Healing;
import antion19.trpg.units.Job;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Healer extends Job implements Healing {

	private static Bitmap image = null;

	public Healer(Context context, int durability) {
		super(durability);
		if (image == null) {
			image = AssetsGetter.getImageAsset(context, "healer.png", 2);
			image.setDensity(0);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public boolean canHeal() {
		return true;
	}

	@Override
	public Bitmap getImage() {
		return image;
	}

	@Override
	public String getName() {
		return "Healer";
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
		return 7;
	}

	@Override
	public int accuracy(int distance) {
		return 60 + 2 * baseUnit.skill;
	}

	@Override
	public int damage(int distance) {
		return (baseUnit.magic * 7) / 5;
	}

	@Override
	public int movement() {
		return 3;
	}

	@Override
	public int HPBaseBonus() {
		return 10;
	}

	@Override
	public int strengthBaseBonus() {
		return 5;
	}

	@Override
	public int magicBaseBonus() {
		return 11;
	}

	@Override
	public int skillBaseBonus() {
		return 4;
	}

	@Override
	public int agilityBaseBonus() {
		return 2;
	}

	@Override
	public int armorBaseBonus() {
		return 5;
	}

	@Override
	public int wardingBaseBonus() {
		return 8;
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
		return 5;
	}

	@Override
	public int assignType() {
		return 175;
	}

	@Override
	public int assignWeight() {
		return 8;
	}

	@Override
	public int healRange() {
		return 1;
	}

	@Override
	public void heal(int distance, Unit unitToHeal) {
		unitToHeal.gainHP(baseUnit.magic);
	}
}
