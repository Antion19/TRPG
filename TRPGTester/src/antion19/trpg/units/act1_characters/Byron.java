package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Byron extends Unit {

	public Byron(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "Byron", job, skillVs, weapon);
	}

	@Override
	protected int getBaseHP() {
		return 17;
	}

	@Override
	protected int getBaseStrength() {
		return 18;
	}

	@Override
	protected int getBaseMagic() {
		return 0;
	}

	@Override
	protected int getBaseSkill() {
		return 14;
	}

	@Override
	protected int getBaseAgility() {
		return 12;
	}

	@Override
	protected int getBaseArmor() {
		return 12;
	}

	@Override
	protected int getBaseWarding() {
		return 7;
	}

	@Override
	protected int assignType() {
		return 174;
	}

	@Override
	protected int assignAffinity() {
		return 7;
	}

	// preTimeSkip stats: Age: 19 Height: 6'5"
	// postTimeSkip stats: Age: 21 Height: 6'5"
	@Override
	protected int assignWeight() {
		if (job.type > 200) {
			return 203;
		} else {
			return 199;
		}
	}

}
