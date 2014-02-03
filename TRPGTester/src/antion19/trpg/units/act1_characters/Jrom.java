package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Jrom extends Unit {

	public Jrom(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "Jrom", job, skillVs, weapon);
	}

	@Override
	protected int getBaseHP() {
		return 20;
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
		return 14;
	}

	@Override
	protected int getBaseAgility() {
		return 8;
	}

	@Override
	protected int getBaseArmor() {
		return 19;
	}

	@Override
	protected int getBaseWarding() {
		return 12;
	}

	@Override
	protected int assignType() {
		return 173;
	}

	@Override
	protected int assignAffinity() {
		return 7;
	}

	// preTimeSkip stats: Age: 20 Height: 6'2"
	// postTimeSkip stats: Age: 22 Height: 6'2"
	@Override
	protected int assignWeight() {
		return 186;
	}

}
