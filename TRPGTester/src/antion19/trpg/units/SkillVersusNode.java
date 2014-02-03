package antion19.trpg.units;

public class SkillVersusNode {

	/**
	 * 
	 */
	public int vsFistsXp = 0;

	/**
	 * 
	 */
	public int vsSwordsXp = 0;

	/**
	 * 
	 */
	public int vsAxesXp = 0;

	/**
	 * 
	 */
	public int vsStaffsXp = 0;

	/**
	 * 
	 */
	public int vsSpearsXp = 0;

	/**
	 * 
	 */
	public int vsBowsXp = 0;

	/**
	 * 
	 */
	public int vsAetherXp = 0;

	/**
	 * 
	 */
	public int vsHolyXp = 0;

	/**
	 * 
	 */
	public int vsDeathXp = 0;

	/**
	 * 
	 * @param weaponType
	 * @return
	 */
	public int getSkillLevelVs(int weaponType) {

		// if this original value is ever given
		// I will know there is a problem
		int skillLevel = -10000;

		switch (weaponType) {
		case 0:
			skillLevel = vsFistsXp / 100;
			break;
		case 1:
			skillLevel = vsSwordsXp / 100;
			break;
		case 2:
			skillLevel = vsAxesXp / 100;
			break;
		case 3:
			skillLevel = vsStaffsXp / 100;
			break;
		case 4:
			skillLevel = vsSpearsXp / 100;
			break;
		case 5:
			skillLevel = vsBowsXp / 100;
			break;
		case 6:
			skillLevel = vsAetherXp / 100;
			break;
		case 7:
			skillLevel = vsHolyXp / 100;
			break;
		case 8:
			skillLevel = vsDeathXp / 100;
			break;
		}

		return skillLevel;
	}

	public void addXPToCorrectVsType(int XPtoAdd, int attackType) {

		switch (attackType) {
		case 0:
			vsFistsXp += XPtoAdd;
			break;
		case 1:
			vsSwordsXp += XPtoAdd;
			break;
		case 2:
			vsAxesXp += XPtoAdd;
			break;
		case 3:
			vsStaffsXp += XPtoAdd;
			break;
		case 4:
			vsSpearsXp += XPtoAdd;
			break;
		case 5:
			vsBowsXp += XPtoAdd;
			break;
		case 6:
			vsAetherXp += XPtoAdd;
			break;
		case 7:
			vsHolyXp += XPtoAdd;
			break;
		case 8:
			vsDeathXp += XPtoAdd;
			break;
		}
	}

}
