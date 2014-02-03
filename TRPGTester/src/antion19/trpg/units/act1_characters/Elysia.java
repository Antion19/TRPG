package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Elysia extends Unit {

	public Elysia(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "Elysia", job, skillVs, weapon);
	}

	@Override
	protected int getBaseHP() {
		return 14;
	}

	@Override
	protected int getBaseStrength() {
		return 12;
	}

	@Override
	protected int getBaseMagic() {
		return 16;
	}

	@Override
	protected int getBaseSkill() {
		return 20;
	}

	@Override
	protected int getBaseAgility() {
		return 20;
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
		return 181;
	}

	@Override
	protected int assignAffinity() {
		return 8;
	}

	// preTimeSkip stats: Age: 17 Height: 5'10"
	// postTimeSkip stats: Age: 19 Height: 5'10"
	@Override
	protected int assignWeight() {
		if (job.type > 200) {
			return 140;
		} else {
			return 132;
		}
	}

}
