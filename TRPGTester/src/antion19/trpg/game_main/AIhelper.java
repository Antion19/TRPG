package antion19.trpg.game_main;

public class AIhelper {

	public static final int AI_FLAG_WILL_NOT_ATTACK_PLAYERCHARACTERS = 1;
	public static final int AI_FLAG_WILL_NOT_ATTACK_ALLIES = 2;
	public static final int AI_FLAG_WILL_NOT_ATTACK_ENEMIES = 4;
	public static final int AI_FLAG_WILL_NOT_ATTACK_THIRDPARTYCHARACTERS = 8;
	public static final int AI_FLAG_WILL_NOT_MOVE = 16;
	public static final int AI_FLAG_WILL_NOT_ATTACK = 32;
	public static final int AI_FLAG_ONLY_ATTACKS_SPECIAL_TARGETS = 64;
	public static final int AI_FLAG_CARES_ABOUT_RANGE = 128;
	public static final int AI_FLAG_ONLY_ATTACKS_THOSE_WHO_CANNOT_DEFEND = 256;
	public static final int AI_FLAG_CARES_ABOUT_FOE_HEALTH = 512;
	public static final int AI_FLAG_PREFERS_ATTACKING_STRONG_FOES = 1024;
	public static final int l = 2048;
	public static final int m = 4096;
	public static final int AI_FlAG_IS_A_THIEF = 8192;
	public static final int AI_FLAG_IS_A_HEALER = 16384;
	public static final int p = 32768;
	public static final int q = 65536;
	public static final int r = 131072;
	public static final int s = 262144;
	public static final int t = 524288;
	public static final int u = 1048576;
	public static final int v = 2097152;
	public static final int w = 4194304;
	public static final int x = 8388608;
	public static final int y = 16777216;
	public static final int z = 33554432;

	/**
	 * Method to see if the AI type used by this character does not allow the
	 * character to move
	 * 
	 * @param type
	 *            the type of AI used by this character, as an integer made of
	 *            flags
	 * @return true if it doesn't, false if it does
	 */
	public static boolean willNotMove(int type) {
		return ((AI_FLAG_WILL_NOT_MOVE & type) == AI_FLAG_WILL_NOT_MOVE);
	}

	/**
	 * Method to see if the AI type used by this character does not allow the
	 * character to attack
	 * 
	 * @param type
	 *            the type of AI used by this character, as an integer made of
	 *            flags
	 * @return true if it doesn't, false if it does
	 */
	public static boolean willNotAttack(int type) {
		return ((AI_FLAG_WILL_NOT_ATTACK & type) == AI_FLAG_WILL_NOT_ATTACK);
	}

	/**
	 * Method to return an array of 4 booleans representing whether this AI will
	 * attack the given loyalties; if one of the flags is present, this AI will
	 * not attack members of that loyalty
	 * 
	 * @param type
	 *            the type of AI used by this character, as an integer made of
	 *            flags
	 * @return an array of 4 booleans representing the 4 possible loyalties, if
	 *         true, character will attack members of that loyalty
	 */
	public static boolean[] attacksWho(int type) {
		boolean[] attackWho = new boolean[4];
		attackWho[0] = !((AI_FLAG_WILL_NOT_ATTACK_PLAYERCHARACTERS & type) == AI_FLAG_WILL_NOT_ATTACK_PLAYERCHARACTERS);
		attackWho[1] = !((AI_FLAG_WILL_NOT_ATTACK_ALLIES & type) == AI_FLAG_WILL_NOT_ATTACK_ALLIES);
		attackWho[2] = !((AI_FLAG_WILL_NOT_ATTACK_ENEMIES & type) == AI_FLAG_WILL_NOT_ATTACK_ENEMIES);
		attackWho[3] = !((AI_FLAG_WILL_NOT_ATTACK_THIRDPARTYCHARACTERS & type) == AI_FLAG_WILL_NOT_ATTACK_THIRDPARTYCHARACTERS);

		return attackWho;
	}

	/**
	 * Method to see if the AI type used by this character has the character
	 * targeting special targets
	 * 
	 * @param type
	 *            the type of AI used by this character, as an integer made of
	 *            flags
	 * @return true if it does, false if it does not
	 */
	public static boolean onlyAttacksSpecialTargets(int type) {
		return ((AI_FLAG_ONLY_ATTACKS_SPECIAL_TARGETS & type) == AI_FLAG_ONLY_ATTACKS_SPECIAL_TARGETS);
	}

	/**
	 * Method to see if the AI type used by this character has the character
	 * care about ranges
	 * 
	 * @param type
	 *            the type of AI used by this character, as an integer made of
	 *            flags
	 * @return true if it does, false if it does not
	 */
	public static boolean caresAboutRange(int type) {
		return ((AI_FLAG_CARES_ABOUT_RANGE & type) == AI_FLAG_CARES_ABOUT_RANGE);
	}

	/**
	 * Method to see if the AI type used by this character has the character
	 * prefer to attack characters unable to fight back due to the range of
	 * their weapons
	 * 
	 * @param type
	 *            the type of AI used by this character, as an integer made of
	 *            flags
	 * @return true if it does, false if it does not
	 */
	public static boolean onlyAttackFoesWhoCannotDefend(int type) {
		return ((AI_FLAG_ONLY_ATTACKS_THOSE_WHO_CANNOT_DEFEND & type) == AI_FLAG_ONLY_ATTACKS_THOSE_WHO_CANNOT_DEFEND);
	}

	/**
	 * Method to see if the AI type used by this character has the character
	 * caring about their foes' health when deciding who to fight
	 * 
	 * @param type
	 *            the type of AI used by this character, as an integer made of
	 *            flags
	 * @return true if it does, false if it does not
	 */
	public static boolean caresAboutFoeHealth(int type) {
		return ((AI_FLAG_CARES_ABOUT_FOE_HEALTH & type) == AI_FLAG_CARES_ABOUT_FOE_HEALTH);
	}

	/**
	 * Method to see if the AI type used by this character has the character
	 * prefer to target foes full of health rather than characters low on health
	 * 
	 * @param type
	 *            the type of AI used by this character, as an integer made of
	 *            flags
	 * @return true if it does, false if it does not
	 */
	public static boolean prefersAttackingStrongFoes(int type) {
		return ((AI_FLAG_PREFERS_ATTACKING_STRONG_FOES & type) == AI_FLAG_PREFERS_ATTACKING_STRONG_FOES);
	}

	public static boolean caresAboutOwnHealth(int aiType) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean runsAwayWhenBelow50Percent(int aiType) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isAThief(int type) {
		return ((AI_FlAG_IS_A_THIEF & type) == AI_FlAG_IS_A_THIEF);
	}

	public static boolean isAHealer(int type) {
		return ((AI_FLAG_IS_A_HEALER & type) == AI_FLAG_IS_A_HEALER);
	}

}
