package antion19.trpg.game_main;

import android.content.Context;
import antion19.trpg.tiles.Column;
import antion19.trpg.tiles.Dirt;
import antion19.trpg.tiles.Grass;
import antion19.trpg.tiles.IndoorFloorBasic;
import antion19.trpg.tiles.IndoorFloorFancy;
import antion19.trpg.tiles.Pit;
import antion19.trpg.tiles.Tile;
import antion19.trpg.tiles.Tree;
import antion19.trpg.tiles.Wall;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.Unit;
import antion19.trpg.weapons.Weapon;
import antion19.trpg.weapons.axes.AxeHandle;
import antion19.trpg.weapons.axes.IronAxe;
import antion19.trpg.weapons.axes.RustyAxe;
import antion19.trpg.weapons.axes.SteelAxe;
import antion19.trpg.weapons.bows.CompositeBow;
import antion19.trpg.weapons.bows.RecurveBow;
import antion19.trpg.weapons.bows.SelfBow;
import antion19.trpg.weapons.bows.StickNString;
import antion19.trpg.weapons.spears.IronSpear;
import antion19.trpg.weapons.spears.SteelSpear;
import antion19.trpg.weapons.spears.ThreeFootPole;
import antion19.trpg.weapons.spears.WoodenSpear;
import antion19.trpg.weapons.staves.IronStave;
import antion19.trpg.weapons.staves.OakStave;
import antion19.trpg.weapons.staves.SteelStave;
import antion19.trpg.weapons.staves.UselessStick;
import antion19.trpg.weapons.swords.HunkOfMetal;
import antion19.trpg.weapons.swords.IronSword;
import antion19.trpg.weapons.swords.RustySword;
import antion19.trpg.weapons.swords.SteelSword;

public class GameHelper {

	public static Weapon getWeaponFromTypeNRank(int type, int rank,
			int durability) {

		Weapon weapon = null;

		switch (type) {
		case 1:
			switch (rank) {
			case 0:
				weapon = new HunkOfMetal();
				break;
			case 1:
				weapon = new RustySword(durability);
				break;
			case 2:
				weapon = new IronSword(durability);
				break;
			case 3:
				weapon = new SteelSword(durability);
				break;
			}
			break;
		case 2:
			switch (rank) {
			case 0:
				weapon = new AxeHandle();
				break;
			case 1:
				weapon = new RustyAxe(durability);
				break;
			case 2:
				weapon = new IronAxe(durability);
				break;
			case 3:
				weapon = new SteelAxe(durability);
				break;
			}
			break;
		case 3:
			switch (rank) {
			case 0:
				weapon = new UselessStick();
				break;
			case 1:
				weapon = new OakStave(durability);
				break;
			case 2:
				weapon = new IronStave(durability);
				break;
			case 3:
				weapon = new SteelStave(durability);
				break;
			}
			break;
		case 4:
			switch (rank) {
			case 0:
				weapon = new ThreeFootPole();
				break;
			case 1:
				weapon = new WoodenSpear(durability);
				break;
			case 2:
				weapon = new IronSpear(durability);
				break;
			case 3:
				weapon = new SteelSpear(durability);
				break;
			}
			break;
		case 5:
			switch (rank) {
			case 0:
				weapon = new StickNString();
				break;
			case 1:
				weapon = new SelfBow(durability);
				break;
			case 2:
				weapon = new RecurveBow(durability);
				break;
			case 3:
				weapon = new CompositeBow(durability);
				break;
			}
			break;
		}

		return weapon;
	}

	public static Tile chooseTile(char character, Context context) {

		Tile tile = null;

		switch (character) {
		case 'b':
			tile = new IndoorFloorBasic(context);
			break;
		case 'c':
			tile = new Column(context);
			break;
		case 'd':
			tile = new Dirt(context);
			break;
		case 'f':
			tile = new IndoorFloorFancy(context);
			break;
		case 'g':
			tile = new Grass(context);
			break;
		case 'p':
			tile = new Pit(context);
			break;
		case 't':
			tile = new Tree(context);
			break;
		case 'w':
			tile = new Wall(context);
			break;
		default:
			System.out.println("Error: Invalid tile");
			System.exit(1);
		}
		return tile;
	}

	public static SkillVersusNode setupSkillVsNode(int[] s) {
		SkillVersusNode vs = new SkillVersusNode();
		vs.vsFistsXp = s[0];
		vs.vsSwordsXp = s[1];
		vs.vsAxesXp = s[2];
		vs.vsStaffsXp = s[3];
		vs.vsSpearsXp = s[4];
		vs.vsBowsXp = s[5];
		vs.vsAetherXp = s[6];
		vs.vsHolyXp = s[7];
		vs.vsDeathXp = s[8];
		return vs;
	}

	public static int triangleAndSkillVsBonus(Unit A, Unit B, int d) {

		int s = 0;

		switch (A.getAttackType(d)) {
		case 1:
			switch (B.getAttackType(d)) {
			case 2:
				s++;
				break;
			case 3:
				s--;
				break;
			}
			break;
		case 2:
			switch (B.getAttackType(d)) {
			case 3:
				s++;
				break;
			case 1:
				s--;
				break;
			}
			break;
		case 3:
			switch (B.getAttackType(d)) {
			case 1:
				s++;
				break;
			case 2:
				s--;
				break;
			}
			break;
		case 6:
			switch (B.getAttackType(d)) {
			case 7:
				s++;
				break;
			case 8:
				s--;
				break;
			}
			break;
		case 7:
			switch (B.getAttackType(d)) {
			case 8:
				s++;
				break;
			case 6:
				s--;
				break;
			}
			break;
		case 8:
			switch (B.getAttackType(d)) {
			case 6:
				s++;
				break;
			case 7:
				s--;
				break;
			}
			break;
		}

		int v = A.skillVs.getSkillLevelVs(B.getAttackType(d))
				- B.skillVs.getSkillLevelVs(A.getAttackType(d));

		if (v > 0) {
			if (v > 2) {
				s += 2;
			} else {
				s++;
			}
		} else if (v < 0) {
			if (v < -2) {
				s -= 2;
			} else {
				s--;
			}
		}

		return s;
	}

}
