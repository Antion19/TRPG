package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Zaan extends Unit {

	public Zaan(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "Zaan", job, skillVs, weapon);
	}

	@Override
	protected int getBaseHP() {
		return 16;
	}

	@Override
	protected int getBaseStrength() {
		return 16;
	}

	@Override
	protected int getBaseMagic() {
		return 30;
	}

	@Override
	protected int getBaseSkill() {
		return 20;
	}

	@Override
	protected int getBaseAgility() {
		return 18;
	}

	@Override
	protected int getBaseArmor() {
		return 13;
	}

	@Override
	protected int getBaseWarding() {
		return 22;
	}

	@Override
	protected int assignType() {
		return 191;
	}

	@Override
	protected int assignAffinity() {
		return 9;
	}

	// preTimeSkip stats: Age: 17 Height: 5'10"
	// postTimeSkip stats: Age: 19 Height: 6'4"
	@Override
	protected int assignWeight() {
		if (job.type > 200) {
			return 200;
		} else {
			return 145;
		}
	}

}
