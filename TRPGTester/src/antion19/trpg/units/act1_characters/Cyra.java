package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Cyra extends Unit {

	public Cyra(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "Cyra", job, skillVs, weapon);
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
		return 15;
	}

	@Override
	protected int getBaseSkill() {
		return 14;
	}

	@Override
	protected int getBaseAgility() {
		return 17;
	}

	@Override
	protected int getBaseArmor() {
		return 20;
	}

	@Override
	protected int getBaseWarding() {
		return 18;
	}

	@Override
	protected int assignType() {
		return 172;
	}

	@Override
	protected int assignAffinity() {
		return 7;
	}

	// preTimeSkip stats: Age: 18 Height: 5'5"
	// postTimeSkip stats: Age: 20 Height: 5'5"
	@Override
	protected int assignWeight() {
		if (job.type > 200) {
			return 123;
		} else {
			return 118;
		}
	}

}
