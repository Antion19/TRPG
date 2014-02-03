package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Heath extends Unit {

	public Heath(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "Heath", job, skillVs, weapon);
	}

	@Override
	protected int getBaseHP() {
		return 15;
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
		return 17;
	}

	@Override
	protected int getBaseAgility() {
		return 16;
	}

	@Override
	protected int getBaseArmor() {
		return 10;
	}

	@Override
	protected int getBaseWarding() {
		return 16;
	}

	@Override
	protected int assignType() {
		return 162;
	}

	@Override
	protected int assignAffinity() {
		return 6;
	}

	// preTimeSkip stats: Age: 16 Height: 5'8"
	// postTimeSkip stats: Age: 18 Height: 5'9"
	@Override
	protected int assignWeight() {
		if (job.type > 200) {
			return 148;
		} else {
			return 144;
		}
	}

}
