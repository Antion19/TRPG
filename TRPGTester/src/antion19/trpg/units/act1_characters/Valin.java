package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Valin extends Unit {

	public Valin(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "Valin", job, skillVs, weapon);
	}
	
	@Override
	public int getAttackType(int distance) {
		return 6;
	}

	@Override
	protected int getBaseHP() {
		return 18;
	}

	@Override
	protected int getBaseStrength() {
		return 18;
	}

	@Override
	protected int getBaseMagic() {
		return 20;
	}

	@Override
	protected int getBaseSkill() {
		return 18;
	}

	@Override
	protected int getBaseAgility() {
		return 15;
	}

	@Override
	protected int getBaseArmor() {
		return 14;
	}

	@Override
	protected int getBaseWarding() {
		return 20;
	}

	@Override
	protected int assignType() {
		return 161;
	}

	@Override
	protected int assignAffinity() {
		return 6;
	}

	// preTimeSkip stats: Age: 18 Height: 6'6"
	// postTimeSkip stats: Age: 20 Height: 6'6"
	@Override
	protected int assignWeight() {
		if (job.type > 200) {
			return 210;
		} else {
			return 202;
		}
	}

}
