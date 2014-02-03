package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Lily extends Unit {

	public Lily(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "Lily", job, skillVs, weapon);
	}

	@Override
	protected int getBaseHP() {
		return 13;
	}

	@Override
	protected int getBaseStrength() {
		return 7;
	}

	@Override
	protected int getBaseMagic() {
		return 18;
	}

	@Override
	protected int getBaseSkill() {
		return 18;
	}

	@Override
	protected int getBaseAgility() {
		return 19;
	}

	@Override
	protected int getBaseArmor() {
		return 12;
	}

	@Override
	protected int getBaseWarding() {
		return 18;
	}

	@Override
	protected int assignType() {
		return 171;
	}

	@Override
	protected int assignAffinity() {
		return 7;
	}

	// preTimeSkip stats: Age: 15 Height: 5'7"
	// postTimeSkip stats: Age: 17 Height: 5'8"
	@Override
	protected int assignWeight() {
		if (job.type > 200) {
			return 130;
		} else {
			return 127;
		}
	}

}
