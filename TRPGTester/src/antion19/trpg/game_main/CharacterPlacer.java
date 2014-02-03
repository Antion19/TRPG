package antion19.trpg.game_main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import antion19.trpg.R;
import antion19.trpg.jobs.tier1.Archer;
import antion19.trpg.jobs.tier1.Diabolist;
import antion19.trpg.jobs.tier1.Healer;
import antion19.trpg.jobs.tier1.Mage;
import antion19.trpg.jobs.tier1.Mercenary;
import antion19.trpg.jobs.tier1.Noble;
import antion19.trpg.jobs.tier1.Spearman;
import antion19.trpg.jobs.tier1.Squire;
import antion19.trpg.jobs.tier1.Swordsman;
import antion19.trpg.jobs.tier1.Thief;
import antion19.trpg.jobs.tier1.Thug;
import antion19.trpg.units.SkillVersusNode;
import antion19.trpg.units.act1_characters.Blake;
import antion19.trpg.units.act1_characters.Brenna;
import antion19.trpg.units.act1_characters.Byron;
import antion19.trpg.units.act1_characters.Cyra;
import antion19.trpg.units.act1_characters.Elysia;
import antion19.trpg.units.act1_characters.Heath;
import antion19.trpg.units.act1_characters.Jrom;
import antion19.trpg.units.act1_characters.Lily;
import antion19.trpg.units.act1_characters.Stuart;
import antion19.trpg.units.act1_characters.Valin;
import antion19.trpg.units.act1_characters.Xaria;
import antion19.trpg.units.act1_characters.Zaan;
import antion19.trpg.units.act1_characters.___;

public class CharacterPlacer extends Activity implements
		OnItemSelectedListener, OnClickListener {

	Spinner character, level, tier, weaponRank, loyalty;

	Button done, add;

	String[] loyalty_array = { "Player", "Ally", "Enemy", "3rd Party" };

	String[] tier_array = { "tier1" };

	String[] level_array = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
			"22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
			"33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43",
			"44", "45", "46", "47", "48", "49", "50" };

	String[] weaponRank_array = { "0", "1", "2", "3" };

	String[] character_array = { "Zaan: mage", "Elysia: thief",
			"Valin: archer", "Lily: healer", "Blake: Spearman", "Heath: mage",
			"Cyra : noble", "___: diabolist", "Xaria: Thug", "Jrom: squire",
			"Brenna: swordsman", "Stuart: archer", "Byron: Mercenary" };

	int XP = 101, weaponRankValue = 0, tierValue = 0, characterValue = 0;

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.character_placer);

		character = (Spinner) findViewById(R.id.spinnerCharacter);
		ArrayAdapter<String> c = new ArrayAdapter<String>(this,
				R.layout.spinner_item, character_array);
		character.setAdapter(c);
		character.setOnItemSelectedListener(this);

		level = (Spinner) findViewById(R.id.spinnerLevel);
		ArrayAdapter<String> lvl = new ArrayAdapter<String>(this,
				R.layout.spinner_item, level_array);
		level.setAdapter(lvl);
		level.setOnItemSelectedListener(this);

		tier = (Spinner) findViewById(R.id.spinnerTier);
		ArrayAdapter<String> t = new ArrayAdapter<String>(this,
				R.layout.spinner_item, tier_array);
		tier.setAdapter(t);
		tier.setOnItemSelectedListener(this);

		weaponRank = (Spinner) findViewById(R.id.spinnerWeaponRank);
		ArrayAdapter<String> wr = new ArrayAdapter<String>(this,
				R.layout.spinner_item, weaponRank_array);
		weaponRank.setAdapter(wr);
		weaponRank.setOnItemSelectedListener(this);

		loyalty = (Spinner) findViewById(R.id.spinnerLoyalty);
		ArrayAdapter<String> lyl = new ArrayAdapter<String>(this,
				R.layout.spinner_item, loyalty_array);
		loyalty.setAdapter(lyl);
		loyalty.setOnItemSelectedListener(this);

		add = (Button) findViewById(R.id.add);
		add.setOnClickListener(this);

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		switch (parent.getId()) {
		case R.id.spinnerCharacter:
			characterValue = pos;
			break;
		case R.id.spinnerLevel:
			XP = 101 + pos * 100;
			break;
		case R.id.spinnerTier:
			tierValue = 0;
			break;
		case R.id.spinnerWeaponRank:
			weaponRankValue = pos;
			break;
		case R.id.spinnerLoyalty:
			TestLevel.placingCharacterLoyalty = pos;
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onClick(View v) {

		switch (characterValue) {
		case 0:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Zaan(XP, new Mage(this,
						100), new SkillVersusNode(), null);
				break;
			}
			break;
		case 1:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Elysia(XP, new Thief(this,
						100), new SkillVersusNode(),
						GameHelper.getWeaponFromTypeNRank(1, weaponRankValue,
								100));
				break;
			}
			break;
		case 2:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Valin(XP, new Archer(this,
						100), new SkillVersusNode(),
						GameHelper.getWeaponFromTypeNRank(5, weaponRankValue,
								100));
				break;
			}
			break;
		case 3:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Lily(XP, new Healer(this,
						100), new SkillVersusNode(), null);
				break;
			}
			break;
		case 4:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Blake(XP, new Spearman(
						this, 100), new SkillVersusNode(),
						GameHelper.getWeaponFromTypeNRank(4, weaponRankValue,
								100));
				break;
			}
			break;
		case 5:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Heath(XP, new Mage(this,
						100), new SkillVersusNode(), null);
				break;
			}
			break;
		case 6:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Cyra(XP, new Noble(this,
						100), new SkillVersusNode(),
						GameHelper.getWeaponFromTypeNRank(3, weaponRankValue,
								100));
				break;
			}
			break;
		case 7:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new ___(XP, new Diabolist(
						this, 100), new SkillVersusNode(), null);
				break;
			}
			break;
		case 8:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Xaria(XP, new Thug(this,
						100), new SkillVersusNode(),
						GameHelper.getWeaponFromTypeNRank(2, weaponRankValue,
								100));
				break;
			}
			break;
		case 9:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Jrom(XP, new Squire(this,
						100), new SkillVersusNode(),
						GameHelper.getWeaponFromTypeNRank(4, weaponRankValue,
								100));
				break;
			}
			break;
		case 10:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Brenna(XP, new Swordsman(
						this, 100), new SkillVersusNode(),
						GameHelper.getWeaponFromTypeNRank(1, weaponRankValue,
								100));
				break;
			}
			break;
		case 11:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Stuart(XP, new Archer(
						this, 100), new SkillVersusNode(),
						GameHelper.getWeaponFromTypeNRank(5, weaponRankValue,
								100));
				break;
			}
			break;
		case 12:
			switch (tierValue) {
			case 0:
				TestLevel.placingCharacterUnit = new Byron(XP, new Mercenary(
						this, 100), new SkillVersusNode(),
						GameHelper.getWeaponFromTypeNRank(2, weaponRankValue,
								100));
				break;
			}
			break;
		}

		TestLevel.placingCharacter = true;
		finish();
	}
}
