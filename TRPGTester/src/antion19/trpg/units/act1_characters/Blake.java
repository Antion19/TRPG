package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Blake extends Unit {

	public Blake(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "Blake", job, skillVs, weapon);
	}

	@Override
	protected int getBaseHP() {
		return 16;
	}

	@Override
	protected int getBaseStrength() {
		return 17;
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
		return 17;
	}

	@Override
	protected int getBaseArmor() {
		return 15;
	}

	@Override
	protected int getBaseWarding() {
		return 9;
	}

	@Override
	protected int assignType() {
		return 182;
	}

	@Override
	protected int assignAffinity() {
		return 8;
	}

	// preTimeSkip stats: Age: 18 Height: 6'
	// postTimeSkip stats: Age: 20 Height: 6'
	@Override
	protected int assignWeight() {
		if (job.type > 200) {
			return 177;
		} else {
			return 173;
		}
	}

}
