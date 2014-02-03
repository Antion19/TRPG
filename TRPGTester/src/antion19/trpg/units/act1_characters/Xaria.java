package antion19.trpg.units.act1_characters;

import antion19.trpg.units.Job;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;

public class Xaria extends Unit {

	public Xaria(int XP, Job job, SkillVersusNode skillVs, Weapon weapon) {
		super(XP, "Xaria", job, skillVs, weapon);
	}

	@Override
	protected int getBaseHP() {
		return 14;
	}

	@Override
	protected int getBaseStrength() {
		return 20;
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
		return 18;
	}

	@Override
	protected int getBaseArmor() {
		return 13;
	}

	@Override
	protected int getBaseWarding() {
		return 18;
	}

	@Override
	protected int assignType() {
		return 163;
	}

	@Override
	protected int assignAffinity() {
		return 6;
	}

	// preTimeSkip stats: Age: 20 Height: 5'11"
	// postTimeSkip stats: Age: 22 Height: 5'11"
	@Override
	protected int assignWeight() {
		return 142;
	}

}
