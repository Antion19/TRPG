package antion19.trpg.units;

import java.util.ArrayList;
import antion19.trpg.item.Item;
import antion19.trpg.weapons.Weapon;

public abstract class Unit {

	/**
	 * character name
	 */
	public String name;

	/**
	 * first digit = type of unit: 1 = playable, 2 = named ai unit, 3 = general
	 * ai unit; second digit = affinity for playable, ; third digit = rank: 1 =
	 * future god, 2 = best act1 unit non-Bonus, 3 = bonus act1 unit, 4 = bad
	 * act1 unit
	 */
	public int type;

	/**
	 * 
	 */
	public int XP;

	/**
	 * 
	 */
	public int level;

	/**
	 * 
	 */
	public int HP;

	/**
	 * 
	 */
	public int maxHP;

	/**
	 * 
	 */
	public int strength;

	/**
	 * 
	 */
	public int magic;

	/**
	 * 
	 */
	public int skill;

	/**
	 * 
	 */
	public int agility;

	/**
	 * 
	 */
	public int armor;

	/**
	 * 
	 */
	public int warding;

	/**
	 * how much the character weights
	 */
	public int weight;

	/**
	 * 0 light load, i.e. unencumbered; 1 medium load, slightly encumbered; 2
	 * heavy load, can barely move; 3 overload, cannot move without dropping
	 * something
	 */
	public int encumbrance;

	/**
	 * 6 for aether, 7 for holy, 8 for death, 9 for all
	 */
	public int affinity;

	/**
	 * 0 for playerCharacter, 1 for ally, 2 for enemy, and 3 for third party
	 */
	public int loyalty;

	/**
	 * value to connect character to CharacterNode hash
	 */
	public int hashLink;

	/**
	 * 
	 */
	public boolean moved = false;

	/**
	 * 
	 */
	public boolean attacked = false;

	/**
	 * 
	 */
	public boolean conscious = true;

	/**
	 * 
	 */
	public ArrayList<Item> items = null;

	/**
	 * character's weapon
	 */
	public Weapon weapon = null;

	/**
	 * 
	 */
	public SkillVersusNode skillVs;

	/**
	 * 
	 */
	public Job job;

	/**
	 * 
	 * @param XP
	 * @param name
	 * @param job
	 * @param skillVs
	 * @param weapon
	 */
	protected Unit(int XP, String name, Job job, SkillVersusNode skillVs,
			Weapon weapon) {
		this.XP = XP;
		this.name = name;
		job.baseUnit = this;
		this.job = job;
		this.skillVs = skillVs;
		weight = assignWeight();
		this.weapon = weapon;
		level = XP / 100;
		HP = maxHP = calculateMaxHP(getBaseHP() + job.HPBaseBonus());
		strength = calculateNonHPStat(getBaseStrength()
				+ job.strengthBaseBonus());
		magic = calculateNonHPStat(getBaseMagic() + job.magicBaseBonus());
		skill = calculateNonHPStat(getBaseSkill() + job.skillBaseBonus());
		agility = calculateNonHPStat(getBaseAgility() + job.agilityBaseBonus());
		armor = calculateNonHPStat(getBaseArmor() + job.armorBaseBonus());
		warding = calculateNonHPStat(getBaseWarding() + job.wardingBaseBonus());
		type = assignType();
		affinity = assignAffinity();
		weight = assignWeight();
		calculateEncumbrance();
	}

	/**
	 * Method to calculate max HP using the pokemon HP formula
	 * 
	 * @param baseHP
	 *            base HP stat equivalent to that used in pokemon; normal range
	 *            between 5 and 40
	 */
	protected int calculateMaxHP(int baseHP) {
		return (baseHP * level / 50) + 10 + level;
	}

	/**
	 * Method to calculate any nonHP stat using the pokemon nonHP formula
	 * 
	 * @param baseStat
	 *            base stat equivalent to that used in pokemon; normal range
	 *            between 5 and 40
	 */
	protected int calculateNonHPStat(int baseStat) {
		return (baseStat * level / 50) + (baseStat / 5) + 2;
	}

	/**
	 * 
	 * @param XPtoAdd
	 */
	public void gainXP(int XPtoAdd, int attackType) {
		XP += XPtoAdd;

		if (attackType < 10) {
			skillVs.addXPToCorrectVsType(XPtoAdd, attackType);
		}

		if ((XP / 100) > level) {
			this.levelup();
		}
	}

	/**
	 * 
	 */
	protected void levelup() {
		level = XP / 100;
		maxHP = calculateMaxHP(getBaseHP() + job.HPBaseBonus());
		strength = calculateNonHPStat(getBaseStrength()
				+ job.strengthBaseBonus());
		skill = calculateNonHPStat(getBaseSkill() + job.skillBaseBonus());
		agility = calculateNonHPStat(getBaseAgility() + job.agilityBaseBonus());
		armor = calculateNonHPStat(getBaseArmor() + job.armorBaseBonus());
		warding = calculateNonHPStat(getBaseWarding() + job.wardingBaseBonus());
	}

	/**
	 * 
	 * @param amount
	 */
	public void gainHP(int amount) {

		if (conscious) {
			HP += amount;
		} else {
			HP = 1 + amount / 4;
			conscious = true;
		}
		if (HP > maxHP) {
			HP = maxHP;
		}
	}

	/**
	 * 
	 * @param amount
	 */
	public void loseHP(int amount) {
		HP -= amount;

		if (HP <= 0) {
			HP = 0;
			conscious = false;
		}

	}

	public float getPercentageHP() {
		return ((float) HP) / ((float) maxHP);
	}

	/**
	 * 
	 */
	public void resetMoveAndAttackIfConscious() {
		if (this.conscious) {
			this.moved = false;
			this.attacked = false;
		}
	}

	public void addItem(Item item) {

		if (items == null) {
			items = new ArrayList<Item>();
		}
		items.add(item);
		calculateEncumbrance();
	}

	public void addItems(ArrayList<Item> newItems) {
		if (items == null) {
			items = new ArrayList<Item>();
		}
		items.addAll(newItems);
		calculateEncumbrance();
	}

	public boolean assignWeapon(Weapon assignedWeapon) {
		if (job.proficientWithWeapon(assignedWeapon)) {
			items.add(weapon);
			weapon = assignedWeapon;
			calculateEncumbrance();
			return true;
		} else {
			return false;
		}
	}

	protected int getEffectiveArmor() {
		if (job.durability >= 70) {
			return armor;
		} else if (job.durability >= 50) {
			return (armor * 3 / 4);
		} else if (job.durability >= 30) {
			return (armor * 5 / 8);
		} else {
			return (armor / 2);
		}
	}

	protected int getEffectiveWarding() {
		if (job.durability >= 70) {
			return warding;
		} else if (job.durability >= 50) {
			return (warding * 3 / 4);
		} else if (job.durability >= 30) {
			return (warding * 5 / 8);
		} else {
			return (warding / 2);
		}
	}

	protected void calculateEncumbrance() {
		int totalWeight = job.weight;

		if (items != null) {
			for (Item i : items) {
				totalWeight += i.weight;
			}
		}

		if (weapon != null) {
			totalWeight += weapon.weight;
		}

		if (totalWeight > ((weight + strength) / 4)) {
			if (totalWeight > ((weight + strength) / 2)) {
				if (totalWeight > (weight + strength)) {
					encumbrance = 4;
				} else {
					encumbrance = 3;
				}
			} else {
				encumbrance = 2;
			}
		} else {
			if (totalWeight > ((weight + strength) / 8)) {
				encumbrance = 1;
			} else {
				encumbrance = 0;
			}
		}
	}

	public int movement() {

		int move = 0;

		switch (encumbrance) {
		case 0:
		case 1:
		case 2:
			move = job.movement();
			break;
		case 3:
			move = job.movement() - 1;
			break;
		case 4:
			move = 1;
			break;
		}

		return move;
	}

	public String getClassName() {
		return job.getName();
	}

	public int minRange() {
		return job.minRange();
	}

	public int maxRange() {
		return job.maxRange();
	}

	public int getAttackType(int distance) {
		return job.getAttackType(distance);
	}

	public int attackSpeed() {
		int attackSpeed = agility;

		switch (encumbrance) {
		case 0:
			break;
		case 1:
			attackSpeed -= 1;
			break;
		case 2:
			attackSpeed -= 4;
			break;
		case 3:
			attackSpeed -= 10;
			break;
		case 4:
			attackSpeed -= 20;
			break;
		}

		return attackSpeed;
	}

	public int accuracy(int distance) {

		int accuracy = job.accuracy(distance);

		switch (encumbrance) {
		case 0:
		case 1:
			break;
		case 2:
			accuracy -= 10;
			break;
		case 3:
			accuracy -= 30;
			break;
		case 4:
			accuracy = 0;
			break;
		}
		return accuracy;
	}

	public int damage(int distance) {
		return job.damage(distance);
	}

	public int evade(int distance, int opponentattackType) {

		int evade = agility * 2 + job.evadeBonus(distance, opponentattackType);

		switch (encumbrance) {
		case 0:
		case 1:
			break;
		case 2:
			evade -= 10;
			break;
		case 3:
			evade -= 30;
			break;
		case 4:
			evade -= 50;
			break;
		}
		return evade;
	}

	public int damageReduction(int opponentattackType) {
		if (opponentattackType <= 5) {
			return getEffectiveArmor()
					+ job.extraPhysicalDamageReduction(opponentattackType);
		} else {
			return getEffectiveWarding()
					+ job.extraMagicalDamageReduction(opponentattackType);
		}
	}

	public int critChance(int distance) {
		int critChance = agility / 2 + skill / 3 + job.critBonus();

		switch (encumbrance) {
		case 0:
		case 1:
			break;
		case 2:
			critChance -= 10;
			break;
		case 3:
			critChance -= 20;
			break;
		case 4:
			critChance -= 40;
			break;
		}
		return critChance;
	}

	/**
	 * If heavily encumbered, will be much more likely to be hit by a critical
	 * attack
	 * 
	 * @param distance
	 * @return
	 */
	public int critDefence(int distance) {
		int critDefence = agility / 4 + skill / 4 + job.critDefenceBonus();

		switch (encumbrance) {
		case 0:
		case 1:
			break;
		case 2:
			critDefence -= 10;
			break;
		case 3:
			critDefence -= 25;
			break;
		case 4:
			critDefence -= 50;
			break;
		}
		return critDefence;

	}

	public int xpGainedFrom(int opponentLevel) {

		int tempXPGain = job.xpBaseWorth() + level - opponentLevel;

		if (conscious) {
			return tempXPGain;
		} else {
			return tempXPGain * 3;
		}
	}

	protected abstract int getBaseHP();

	protected abstract int getBaseStrength();

	protected abstract int getBaseMagic();

	protected abstract int getBaseSkill();

	protected abstract int getBaseAgility();

	protected abstract int getBaseArmor();

	protected abstract int getBaseWarding();

	protected abstract int assignType();

	protected abstract int assignAffinity();

	protected abstract int assignWeight();

}
