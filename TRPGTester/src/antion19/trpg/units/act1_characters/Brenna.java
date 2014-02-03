package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Brenna extends Unit {

	public Brenna(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "Brenna", job, skillVs, weapon);
	}

	@Override
	protected int getBaseHP() {
		return 12;
	}

	@Override
	protected int getBaseStrength() {
		return 13;
	}

	@Override
	protected int getBaseMagic() {
		return 0;
	}

	@Override
	protected int getBaseSkill() {
		return 17;
	}

	@Override
	protected int getBaseAgility() {
		return 18;
	}

	@Override
	protected int getBaseArmor() {
		return 12;
	}

	@Override
	protected int getBaseWarding() {
		return 8;
	}

	@Override
	protected int assignType() {
		return 184;
	}

	@Override
	protected int assignAffinity() {
		return 8;
	}

	// preTimeSkip stats: Age: 17 Height: 5'6"
	// postTimeSkip stats: Age: 19 Height: 5'6"
	@Override
	protected int assignWeight() {
		if (job.type > 200) {
			return 124;
		} else {
			return 120;
		}
	}

}
