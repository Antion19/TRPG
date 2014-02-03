package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class ___ extends Unit {

	public ___(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "___", job, skillVs, weapon);

	}

	@Override
	protected int getBaseHP() {
		return 13;
	}

	@Override
	protected int getBaseStrength() {
		return 8;
	}

	@Override
	protected int getBaseMagic() {
		return 19;
	}

	@Override
	protected int getBaseSkill() {
		return 19;
	}

	@Override
	protected int getBaseAgility() {
		return 14;
	}

	@Override
	protected int getBaseArmor() {
		return 14;
	}

	@Override
	protected int getBaseWarding() {
		return 17;
	}

	@Override
	protected int assignType() {
		return 183;
	}

	@Override
	protected int assignAffinity() {
		return 8;
	}

	// preTimeSkip stats: Age: 18 Height: 4'11"
	// postTimeSkip stats: Age: 20 Height: 4'11"
	@Override
	protected int assignWeight() {
		if (job.type > 200) {
			return 98;
		} else {
			return 99;
		}
	}

}
