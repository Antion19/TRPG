package antion19.trpg.game_main;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import antion19.trpg.R;
import antion19.trpg.mapcomponents.MapNode;
import antion19.trpg.mapcomponents.MapView;
import antion19.trpg.mapcomponents.NonPlayableCharacterNode;
import antion19.trpg.units.Unit;

public class TestLevel extends MapActivity {

	static boolean placingCharacter = false;

	static int placingCharacterLoyalty = 0;

	static Unit placingCharacterUnit = null;

	@Override
	protected int mapID() {
		return R.raw.test_map;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (placingCharacter) {
			x = ((int) event.getX()) / MapView.imageDimension;
			y = ((int) event.getY()) / MapView.imageDimension;
			if (!mapView.map[x][y].tile.isInaccessible()) {
				switch (placingCharacterLoyalty) {
				case 0:
					addPlayerCharacter(x, y, placingCharacterUnit);
					playerCharactersLeftToUse = playerCharacterNum;
					break;
				case 1:
					addAlly(x, y, placingCharacterUnit, 0, null, null);
					break;
				case 2:
					addEnemy(x, y, placingCharacterUnit, 0, null, null);
					break;
				case 3:
					addThirdPartyCharacter(x, y, placingCharacterUnit, 0, null,
							null);
					break;
				}
				mapView.invalidate();
				placingCharacter = false;
			}
			return true;
		} else {
			return super.onTouch(v, event);
		}
	}

	@Override
	protected void levelStartInstructions() {
	}

	@Override
	protected void dealWithUnconsciousUnit(MapNode node) {
		System.out.println(node.unit.name + " died\n");
		switch (node.unit.loyalty) {
		case 0:
			playerCharacterNum--;
			System.out.println("he/she was a player character\n");
			break;
		case 1:
			alliesNum--;
			allies.set(node.unit.hashLink, null);
			node.unit = null;
			System.out.println("he/she was a ally\n");
			break;
		case 2:
			enemiesNum--;
			enemies.set(node.unit.hashLink, null);
			node.unit = null;
			System.out.println("he/she was an enemy\n");
			break;
		case 3:
			thirdPartyCharactersNum--;
			thirdPartyCharacters.set(node.unit.hashLink, null);
			node.unit = null;
			System.out.println("he/she was a third party character\n");
			break;
		}

		mapView.invalidate();
	}

	// called when someone presses bCharacterPlace
	/**
	 * 
	 * @param view
	 */
	@Override
	public void changeableNormalPerformance() {

		Intent intent = new Intent(this, CharacterPlacer.class);
		startActivity(intent);
		turn = 0;
		aiCurrent = 0;

	}

	@Override
	protected void specificRoundEndInstructions() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void aiFollowLevelSpecificRules(NonPlayableCharacterNode tp) {
		// TODO Auto-generated method stub

	}

	@Override
	protected float getLevel() {
		return 1.0f;
	}

	@Override
	protected String normalChangeableText() {
		return "Add";
	}
}
