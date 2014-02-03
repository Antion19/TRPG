package antion19.trpg.game_main;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import antion19.newcomponents.TwoDScrollView;
import antion19.trpg.R;
import antion19.trpg.mapcomponents.CharacterNode;
import antion19.trpg.mapcomponents.MapNode;
import antion19.trpg.mapcomponents.MapView;
import antion19.trpg.mapcomponents.NonPlayableCharacterNode;
import antion19.trpg.units.AOE;
import antion19.trpg.units.Healing;
import antion19.trpg.units.StealNode;
import antion19.trpg.units.Unit;

public abstract class MapActivity extends FragmentActivity implements
		OnTouchListener {

	// arrays of characters

	/**
	 * 
	 */
	ArrayList<CharacterNode> playerCharacters = null;

	/**
	 * 
	 */
	ArrayList<NonPlayableCharacterNode> allies = null;

	/**
	 * 
	 */
	ArrayList<NonPlayableCharacterNode> enemies = null;

	/**
	 * 
	 */
	ArrayList<NonPlayableCharacterNode> thirdPartyCharacters = null;

	/**
	 * 
	 */
	ArrayList<CharacterNode> locationsFromWhichItemsCanBeStollen = null;

	// values to control game mechanics

	/**
	 * 
	 */
	int playerCharacterNum = 0;

	/**
	 * 
	 */
	int enemiesNum = 0;

	/**
	 * 
	 */
	int alliesNum = 0;

	/**
	 * 
	 */
	int thirdPartyCharactersNum = 0;

	/**
	 * 
	 */
	int playerCharactersLeftToUse;

	/**
	 * 
	 */
	int roundNumber = 0;

	/**
	 * 
	 */
	int x;

	/**
	 * 
	 */
	int y;

	/**
	 * 0 if writeToTextView should work, 1 if fight information should be shown
	 */
	int textViewControlVariable = 0;

	/**
	 * integer value representing the current turn of the current round. 0 for
	 * player character, 1 for allies, 2 for enemies, and 3 for third party
	 */
	int turn = 0;

	/**
	 * 
	 */
	int aiCurrent = 0;

	CharacterNode cc = null;

	/**
	 * 0 for normal state, 1 for currentUnit ready to move, 2 for currentUnit
	 * ready to attack
	 */
	int currentUnitReadiness = 0;

	// setting up layout items

	/**
	 * 
	 */
	TwoDScrollView scroll2D;

	/**
	 * 
	 */
	MapView mapView;

	/**
	 * 
	 */
	ScrollView scroll1D;

	/**
	 * 
	 */
	TextView textView;

	/**
	 * 
	 */
	Button bMove;

	/**
	 * 
	 */
	Button bAttack;

	/**
	 * 
	 */
	Button bNextCharacter;

	/**
	 * 
	 */
	Button bChangeable;

	protected int changeValue = 0;

	/**
	 * 
	 */
	Button bEndRound;

	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen);

		// setup TwoDScrollView
		scroll2D = (TwoDScrollView) findViewById(R.id.gameScrollView2D);

		// setup MapView
		mapView = (MapView) findViewById(R.id.gameMapView);
		mapView.initialize(this, mapID());
		mapView.setOnTouchListener(this);

		// setup normal vertical scroll view 1
		scroll1D = (ScrollView) findViewById(R.id.gameScrollView1D);

		// setup text view
		textView = (TextView) findViewById(R.id.gameInfo);

		// setup buttons
		bMove = (Button) findViewById(R.id.bMove);
		bAttack = (Button) findViewById(R.id.bAttack);
		bNextCharacter = (Button) findViewById(R.id.bNextCharacter);
		bEndRound = (Button) findViewById(R.id.bEndRound);
		bChangeable = (Button) findViewById(R.id.bChangeable);

		levelStartInstructions();
		playerCharactersLeftToUse = playerCharacterNum;

	}

	/**
		 * 
		 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/**
		 * 
		 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	// Use to deal with onTouch Events in mapView
	/**
	 * 
	 * @param v
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Date d = new Date();
			System.out.println("Touch Down Event!!" + d.getTime());

			if (turn == 0) {
				if (playerCharactersLeftToUse == 0) {
					nextTurn();
				}

				x = ((int) event.getX()) / MapView.imageDimension;
				y = ((int) event.getY()) / MapView.imageDimension;

				if (mapView.map[x][y].unit != null) {
					writeToTextView(0);
					if (currentUnitReadiness == 2) {
						if (mapView.map[x][y].unit.loyalty >= 2) {
							attackWithCurrentUnit();
						} else if (mapView.map[x][y].unit.loyalty == 0) {
							resetAllAttackValues(cc.x, cc.y,
									mapView.map[cc.x][cc.y].unit.maxRange());
							if (mapView.map[x][y].unit.conscious) {
								assignCharacterAsCurrent(x, y);
							}
						} else {
							resetAllAttackValues(cc.x, cc.y,
									mapView.map[cc.x][cc.y].unit.maxRange());
						}
						mapView.invalidate();
						currentUnitReadiness = 0;
					} else if (currentUnitReadiness == 1) {
						if (mapView.map[x][y].unit.loyalty == 0) {
							resetAllMoveValues(cc.x, cc.y,
									mapView.map[cc.x][cc.y].unit.movement());
							if (mapView.map[x][y].unit.conscious) {
								assignCharacterAsCurrent(x, y);
							}
						} else {
							resetAllMoveValues(cc.x, cc.y,
									mapView.map[cc.x][cc.y].unit.movement());
						}
						mapView.invalidate();
						currentUnitReadiness = 0;
					} else if (currentUnitReadiness == 3) {
						healWithCurrentUnit();
						mapView.invalidate();
					} else if (currentUnitReadiness == 4) {
						stealWithCurrentUnit();
						mapView.invalidate();
					} else {
						if (mapView.map[x][y].unit.loyalty == 0) {
							if (mapView.map[x][y].unit.conscious) {
								assignCharacterAsCurrent(x, y);
							}
						}
					}
				} else if (!mapView.map[x][y].tile.isInaccessible()) {
					writeToTextView(1);
					if (currentUnitReadiness == 1) {
						moveCurrentUnit();
						mapView.invalidate();
					} else if (currentUnitReadiness == 2) {
						currentUnitReadiness = 0;
						resetAllAttackValues(cc.x, cc.y,
								mapView.map[cc.x][cc.y].unit.maxRange());
						mapView.invalidate();
						cc = null;
					} else if (currentUnitReadiness == 3) {
						currentUnitReadiness = 0;
						if (mapView.map[cc.x][cc.y].unit.job.canHeal()) {
							resetAllAttackValues(
									cc.x,
									cc.y,
									((Healing) mapView.map[cc.x][cc.y].unit.job)
											.healRange());
							mapView.invalidate();
						}
						cc = null;
					} else if (currentUnitReadiness == 4) {
						currentUnitReadiness = 0;
						if (mapView.map[cc.x][cc.y].unit.job.stealNode != null) {
							resetAllAttackValues(cc.x, cc.y,
									mapView.map[cc.x][cc.y].unit.maxRange());
							mapView.invalidate();
						}
						cc = null;
					} else {
						cc = null;
					}
					currentUnitReadiness = 0;
				} else {
					if (currentUnitReadiness == 1) {
						currentUnitReadiness = 0;
						resetAllMoveValues(cc.x, cc.y,
								mapView.map[cc.x][cc.y].unit.movement());
						mapView.invalidate();
						cc = null;
					} else if (currentUnitReadiness == 2) {
						currentUnitReadiness = 0;
						resetAllAttackValues(cc.x, cc.y,
								mapView.map[cc.x][cc.y].unit.maxRange());
						mapView.invalidate();
						cc = null;
					} else if (currentUnitReadiness == 3) {
						currentUnitReadiness = 0;
						if (mapView.map[cc.x][cc.y].unit.job.canHeal()) {
							resetAllAttackValues(
									cc.x,
									cc.y,
									((Healing) mapView.map[cc.x][cc.y].unit.job)
											.healRange());
							mapView.invalidate();
						}
						cc = null;
					} else if (currentUnitReadiness == 4) {
						currentUnitReadiness = 0;
						if (mapView.map[cc.x][cc.y].unit.job.stealNode != null) {
							resetAllAttackValues(cc.x, cc.y,
									mapView.map[cc.x][cc.y].unit.maxRange());
							mapView.invalidate();
						}
						cc = null;
					}

					writeToTextView(2);
				}

			} else if (turn == 1) {

				for (int i = aiCurrent; i < allies.size(); i++) {
					if (allies.get(i) != null
							&& mapView.map[allies.get(i).x][allies.get(i).y].unit.conscious) {
						makeScroll2DHoldSquare(allies.get(i).x, allies.get(i).y);
						aiMoveAndAttack(allies.get(i));
						aiCurrent = i;
						break;
					}
				}

				aiCurrent++;
				if (aiCurrent == allies.size()) {
					aiCurrent = 0;
					nextTurn();
				}

			} else if (turn == 2) {

				for (int i = aiCurrent; i < enemies.size(); i++) {
					if (enemies.get(i) != null
							&& mapView.map[enemies.get(i).x][enemies.get(i).y].unit.conscious) {
						makeScroll2DHoldSquare(enemies.get(i).x,
								enemies.get(i).y);
						aiMoveAndAttack(enemies.get(i));
						aiCurrent = i;
						break;
					}
				}

				aiCurrent++;
				if (aiCurrent == enemies.size()) {
					aiCurrent = 0;
					nextTurn();
				}

			} else if (turn == 3) {

				for (int i = aiCurrent; i < thirdPartyCharacters.size(); i++) {
					if (thirdPartyCharacters.get(i) != null
							&& mapView.map[thirdPartyCharacters.get(i).x][thirdPartyCharacters
									.get(i).y].unit.conscious) {
						makeScroll2DHoldSquare(thirdPartyCharacters.get(i).x,
								thirdPartyCharacters.get(i).y);
						aiMoveAndAttack(thirdPartyCharacters.get(i));
						aiCurrent = i;
						break;
					}
				}

				aiCurrent++;
				if (aiCurrent == thirdPartyCharacters.size()) {
					aiCurrent = 0;
					nextTurn();
				}

			} else {
				System.out.println("Danger!!! Danger, Will Robinson!!!");
			}
		}

		return true;

	}

	private void assignCharacterAsCurrent(int i, int j) {
		cc = new CharacterNode(i, j);

		if (mapView.map[cc.x][cc.y].unit.job.canHeal()) {
			bChangeable.setText("Heal");
			changeValue = 1;
		} else if (mapView.map[cc.x][cc.y].unit.job.stealNode != null) {
			bChangeable.setText("Steal");
			changeValue = 2;
		} else {
			bChangeable.setText(normalChangeableText());
			changeValue = 0;
		}

	}

	private void makeScroll2DHoldSquare(int rectX, int rectY) {
		Rect rect = new Rect(rectX * MapView.imageDimension, rectY
				* MapView.imageDimension, (rectX + 1) * MapView.imageDimension,
				(rectY + 1) * MapView.imageDimension);
		scroll2D.scrollToChildRect(rect, true);
		mapView.invalidate();
	}

	protected void nextTurn() {
		switch (turn) {
		case 0:

			for (CharacterNode k : playerCharacters) {
				AOEPlayer(k);
			}

			if (alliesNum > 0) {
				turn = 1;
				break;
			}
		case 1:

			for (NonPlayableCharacterNode k : allies) {
				AOEAI(k);
			}

			if (enemiesNum > 0) {
				turn = 2;
				break;
			}
		case 2:

			for (NonPlayableCharacterNode k : enemies) {
				AOEAI(k);
			}

			if (thirdPartyCharactersNum > 0) {
				turn = 3;
				break;
			}
		case 3:

			/*
			 * for (NonPlayableCharacterNode k : thirdPartyCharacters) {
			 * AOEAI(k); }
			 */

			if (playerCharacterNum > 0) {
				turn = 0;
				roundEnd();
				break;
			}
		default:
			System.out.println("turn error");
		}
	}

	/**
		 * 
		 */
	private void moveCurrentUnit() {
		if (mapView.map[x][y].moveValue != 0) {
			resetAllMoveValues(cc.x, cc.y,
					mapView.map[cc.x][cc.y].unit.movement());
			mapView.moveCharacter(cc.x, cc.y, x, y);
			playerCharacters.get(mapView.map[x][y].unit.hashLink).x = x;
			playerCharacters.get(mapView.map[x][y].unit.hashLink).y = y;
			mapView.map[x][y].unit.moved = true;
			if (mapView.map[x][y].unit.attacked) {
				playerCharactersLeftToUse--;
				cc = null;
			} else {
				cc.x = x;
				cc.y = y;
			}
		} else {
			resetAllMoveValues(cc.x, cc.y,
					mapView.map[cc.x][cc.y].unit.movement());
		}
	}

	/**
	 * 
	 */
	private void attackWithCurrentUnit() {
		if (mapView.map[x][y].attackValue == 200) {
			fight(cc.x, cc.y, x, y);
			mapView.map[cc.x][cc.y].unit.attacked = true;
			resetAllAttackValues(cc.x, cc.y,
					mapView.map[cc.x][cc.y].unit.maxRange());
			if (mapView.map[cc.x][cc.y].unit.moved
					|| !mapView.map[cc.x][cc.y].unit.conscious) {
				playerCharactersLeftToUse--;
				cc = null;
			}
		} else {
			resetAllAttackValues(cc.x, cc.y,
					mapView.map[cc.x][cc.y].unit.maxRange());
		}
	}

	/**
	 * 
	 */
	private void healWithCurrentUnit() {
		if (mapView.map[x][y].attackValue == 300) {
			((Healing) mapView.map[cc.x][cc.y].unit.job).heal(
					Math.abs(cc.x - x) + Math.abs(cc.y - y),
					mapView.map[x][y].unit);
			mapView.map[cc.x][cc.y].unit.attacked = true;
			resetAllAttackValues(cc.x, cc.y,
					((Healing) mapView.map[cc.x][cc.y].unit.job).healRange());
			if (mapView.map[cc.x][cc.y].unit.moved
					|| !mapView.map[cc.x][cc.y].unit.conscious) {
				playerCharactersLeftToUse--;
				cc = null;
			}
		} else {
			resetAllAttackValues(cc.x, cc.y,
					((Healing) mapView.map[cc.x][cc.y].unit.job).healRange());
		}
	}

	/**
	 * 
	 */
	private void stealWithCurrentUnit() {
		if (mapView.map[x][y].attackValue == 400) {
			mapView.map[cc.x][cc.y].unit.job.stealNode.chooseItemToTryAndSteal(
					this, mapView.map[x][y].unit);
			if (mapView.map[cc.x][cc.y].unit.job.stealNode
					.stealSuccessful(mapView.map[x][y].unit)) {
				mapView.map[cc.x][cc.y].unit.items
						.add(mapView.map[x][y].unit.items.get(StealNode.q));
				mapView.map[x][y].unit.items.remove(StealNode.q);
			} else {

			}
			mapView.map[cc.x][cc.y].unit.attacked = true;
			resetAllAttackValues(cc.x, cc.y,
					mapView.map[cc.x][cc.y].unit.maxRange());
			if (mapView.map[cc.x][cc.y].unit.moved
					|| !mapView.map[cc.x][cc.y].unit.conscious) {
				playerCharactersLeftToUse--;
				cc = null;
			}
		} else {
			resetAllAttackValues(cc.x, cc.y,
					mapView.map[cc.x][cc.y].unit.maxRange());
		}
	}

	/**
	 * 
	 * @param value
	 */
	private void writeToTextView(int value) {

		if (textViewControlVariable > 0) {
			if (textViewControlVariable == 1 && value == 0) {
				textViewControlVariable = 0;
			}
		} else {

			textView.setText("Grid Element: (" + x + "," + y + ")\n"
					+ playerCharactersLeftToUse + "/" + playerCharacterNum
					+ "\n");
			if (value == 0) {
				textView.append("Name: " + mapView.map[x][y].unit.name
						+ "\nClass: " + mapView.map[x][y].unit.getClassName()
						+ "\nLevel: " + mapView.map[x][y].unit.level + "\n");

				// if (mapView.map[x][y].unit.getLoyalty() == 0) {
				textView.append("HP: " + mapView.map[x][y].unit.HP + "/"
						+ mapView.map[x][y].unit.maxHP + "\nStrength: "
						+ mapView.map[x][y].unit.strength + "\nMagic: "
						+ mapView.map[x][y].unit.magic + "\nSkill: "
						+ mapView.map[x][y].unit.skill + "\nAgility: "
						+ mapView.map[x][y].unit.agility + "\nArmor: "
						+ mapView.map[x][y].unit.armor + "\nWarding: "
						+ mapView.map[x][y].unit.warding + "\nMovement: "
						+ mapView.map[x][y].unit.movement() + "\n");
				// }

				if (mapView.map[x][y].unit.weapon != null) {
					textView.append("Weapon: "
							+ mapView.map[x][y].unit.weapon.getName() + "\n");
				} else {
					textView.append("Weapon: NA\n");
				}

				// remove once finished testing
				textView.append("Conscious?: "
						+ mapView.map[x][y].unit.conscious + "\nMoved?: "
						+ mapView.map[x][y].unit.moved + "\nAttacked?: "
						+ mapView.map[x][y].unit.attacked + "\nXP: "
						+ mapView.map[x][y].unit.XP + "\n");
			}

			if (value <= 2) {
				textView.append("Tile Type: "
						+ mapView.map[x][y].tile.getType() + "\n");
			}

			if (value <= 1) {
				textView.append("Blocks Ranged Attacks: "
						+ mapView.map[x][y].tile.BlocksRangedAttacks()
						+ "\nExtraMoveImpediment: "
						+ mapView.map[x][y].tile.getMoveImpediment()
						+ "\nDefence Bonus: "
						+ mapView.map[x][y].tile.getCoverDefence()
						+ "\nAvoidance Bonus: "
						+ mapView.map[x][y].tile.getCoverAvoidance() + "\n");
			}

			textView.append("moveValue: " + mapView.map[x][y].moveValue + "\n"
					+ "attackValue: " + mapView.map[x][y].attackValue + "\n");
		}
	}

	// called when someone presses bMove
	/**
	 * 
	 * @param view
	 */
	public void bMovePressed(View view) {
		if (turn == 0) {
			if (cc != null) {
				if (currentUnitReadiness >= 2) {
					resetAllAttackValues(cc.x, cc.y,
							mapView.map[cc.x][cc.y].unit.maxRange());
				} else if (currentUnitReadiness == 3) {
					if (mapView.map[cc.x][cc.y].unit.job.canHeal()) {
						resetAllAttackValues(cc.x, cc.y,
								((Healing) mapView.map[cc.x][cc.y].unit.job)
										.healRange());
					}
				} else if (currentUnitReadiness == 4) {
					if (mapView.map[cc.x][cc.y].unit.job.stealNode != null) {
						resetAllAttackValues(cc.x, cc.y,
								mapView.map[cc.x][cc.y].unit.maxRange());
					}
				}
				if (!mapView.map[cc.x][cc.y].unit.moved) {
					setPossibleMoveSquaresBFS(cc);
					mapView.invalidate();
					currentUnitReadiness = 1;
					System.out.println("Move Pressed");
				}
			}
		}
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param speed
	 */
	private void setPossibleMoveSquaresBFS(CharacterNode cc) {
		LinkedList<BSFnode> queue = new LinkedList<BSFnode>();
		queue.add(new BSFnode(cc.x, cc.y, mapView.map[cc.x][cc.y].unit
				.movement() + 1));
		BSFnode current;

		while ((current = queue.poll()) != null) {
			if (current.v > mapView.map[current.x][current.y].moveValue) {
				if (mapView.map[current.x][current.y].unit != null) {
					if (mapView.map[current.x][current.y].unit.loyalty <= 1
							|| !mapView.map[current.x][current.y].unit.conscious) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(
									current.x + 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(
									current.x - 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(
									current.x,
									current.y + 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(
									current.x,
									current.y - 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}

					}

				} else {
					if (!mapView.map[current.x][current.y].tile
							.isInaccessible()) {
						mapView.map[current.x][current.y].moveValue = current.v;
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(
									current.x + 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(
									current.x - 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(
									current.x,
									current.y + 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(
									current.x,
									current.y - 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
					}
				}
			}
		}
	}

	// called when someone presses bAttack
	/**
	 * 
	 * @param view
	 */
	public void bAttackPressed(View view) {
		if (turn == 0) {
			if (cc != null) {
				if (currentUnitReadiness == 1) {
					resetAllMoveValues(cc.x, cc.y,
							mapView.map[cc.x][cc.y].unit.movement());
				} else if (currentUnitReadiness == 3) {
					if (mapView.map[cc.x][cc.y].unit.job.canHeal()) {
						resetAllAttackValues(cc.x, cc.y,
								((Healing) mapView.map[cc.x][cc.y].unit.job)
										.healRange());
					}
				} else if (currentUnitReadiness == 4) {
					if (mapView.map[cc.x][cc.y].unit.job.stealNode != null) {
						resetAllAttackValues(cc.x, cc.y,
								mapView.map[cc.x][cc.y].unit.maxRange());
					}
				}
				if (!mapView.map[cc.x][cc.y].unit.attacked) {
					setPossibleAttackSquaresBFS(cc,
							mapView.map[cc.x][cc.y].unit.maxRange());
					mapView.invalidate();
					currentUnitReadiness = 2;
					System.out.println("Attack Pressed");
				}
			}
		}
	}

	private void setPossibleAttackSquaresBFS(CharacterNode cc, int max) {
		LinkedList<BSFnode> queue = new LinkedList<BSFnode>();
		queue.add(new BSFnode(cc.x, cc.y, max + 1));
		BSFnode current;

		while ((current = queue.poll()) != null) {
			if (current.v > mapView.map[current.x][current.y].attackValue) {
				if (mapView.map[current.x][current.y].unit != null) {
					if ((current.x == cc.x) && (current.y == cc.y)) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					} else if (mapView.map[current.x][current.y].unit.loyalty >= 2) {
						if (mapView.map[current.x][current.y].unit.conscious) {
							if (attackAllowed(cc, current)) {
								mapView.map[current.x][current.y].attackValue = 200;
							}
						} else {
							if (current.x != (mapView.xTileLength - 1)) {
								queue.add(new BSFnode(current.x + 1, current.y,
										current.v - 1));
							}
							if (current.x != 0) {
								queue.add(new BSFnode(current.x - 1, current.y,
										current.v - 1));
							}
							if (current.y != (mapView.yTileLength - 1)) {
								queue.add(new BSFnode(current.x, current.y + 1,
										current.v - 1));
							}
							if (current.y != 0) {
								queue.add(new BSFnode(current.x, current.y - 1,
										current.v - 1));
							}
						}
					}

				} else {
					mapView.map[current.x][current.y].attackValue = current.v;
					if (!mapView.map[current.x][current.y].tile
							.BlocksRangedAttacks()) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					}
				}
			}
		}
	}

	private boolean attackAllowed(CharacterNode z, BSFnode current) {

		if (mapView.map[z.x][z.y].unit.minRange() == 2
				&& mapView.map[z.x][z.y].unit.maxRange() == 4) {
			if (current.v == 4) {
				return false;
			} else if (current.v == 1) {
				int leftRight = current.x - z.x;
				int upDown = current.y - z.y;

				switch (leftRight) {
				case -2:
					switch (upDown) {
					case -2:
						return checkIfNodeBlocksRangedAttacks(z.x - 1, z.y - 1);
					case 0:
						return checkIfNodeBlocksRangedAttacks(z.x - 1, z.y);
					case 2:
						return checkIfNodeBlocksRangedAttacks(z.x - 1, z.y + 1);
					}
					break;
				case 0:
					switch (upDown) {
					case -2:
						return checkIfNodeBlocksRangedAttacks(z.x, z.y - 1);
					case 2:
						return checkIfNodeBlocksRangedAttacks(z.x, z.y + 1);
					}
					break;
				case 2:
					switch (upDown) {
					case -2:
						return checkIfNodeBlocksRangedAttacks(z.x + 1, z.y - 1);
					case 0:
						return checkIfNodeBlocksRangedAttacks(z.x + 1, z.y);
					case 2:
						return checkIfNodeBlocksRangedAttacks(z.x + 1, z.y + 1);
					}
					break;
				}
			}
		}
		return true;
	}

	private boolean checkIfNodeBlocksRangedAttacks(int i, int j) {
		if (mapView.map[i][j].tile.BlocksRangedAttacks()
				|| (mapView.map[i][j].unit != null && mapView.map[i][j].unit.conscious)) {
			return false;
		} else {
			return true;
		}
	}

	// called when someone presses bNextCharacter
	/**
	 * 
	 * @param view
	 */
	public void bNextCharacterPressed(View view) {
		if (turn == 0) {
			if (cc != null) {
				mapView.map[cc.x][cc.y].unit.moved = true;
				mapView.map[cc.x][cc.y].unit.attacked = true;
				playerCharactersLeftToUse--;

				// sure the following might not always be necessary, but this
				// method should barely ever be called
				if (currentUnitReadiness == 1) {
					resetAllMoveValues(cc.x, cc.y,
							mapView.map[cc.x][cc.y].unit.movement());
				} else if (currentUnitReadiness == 2) {
					resetAllAttackValues(cc.x, cc.y,
							mapView.map[cc.x][cc.y].unit.maxRange());
				} else if (currentUnitReadiness == 3) {
					if (mapView.map[cc.x][cc.y].unit.job.canHeal()) {
						resetAllAttackValues(cc.x, cc.y,
								((Healing) mapView.map[cc.x][cc.y].unit.job)
										.healRange());
					}
				} else if (currentUnitReadiness == 4) {
					if (mapView.map[cc.x][cc.y].unit.job.stealNode != null) {
						resetAllAttackValues(cc.x, cc.y,
								mapView.map[cc.x][cc.y].unit.maxRange());
					}
				}
				mapView.invalidate();

				currentUnitReadiness = 0;
				cc = null;
				System.out.println("Next Pressed");

				if (playerCharactersLeftToUse == 0) {
					nextTurn();
				}
			}
		}
	}

	public void bChangeable(View view) {
		switch (changeValue) {
		case 0:
			changeableNormalPerformance();
			break;
		// healing
		case 1:
			if (turn == 0) {
				if (cc != null) {
					if (currentUnitReadiness == 1) {
						resetAllMoveValues(cc.x, cc.y,
								mapView.map[cc.x][cc.y].unit.movement());
					} else if (currentUnitReadiness == 2) {
						resetAllAttackValues(cc.x, cc.y,
								mapView.map[cc.x][cc.y].unit.maxRange());
					} else if (currentUnitReadiness == 4) {
						if (mapView.map[cc.x][cc.y].unit.job.stealNode != null) {
							resetAllAttackValues(cc.x, cc.y,
									mapView.map[cc.x][cc.y].unit.maxRange());
						}
					}
					if (!mapView.map[cc.x][cc.y].unit.attacked) {
						setPossibleHealSquaresBFS(cc,
								((Healing) mapView.map[cc.x][cc.y].unit.job)
										.healRange());
						mapView.invalidate();
						currentUnitReadiness = 3;
						System.out.println("Heal Pressed");
					}
				}
			}
			break;
		// stealing
		case 2:
			if (turn == 0) {
				if (cc != null) {
					if (currentUnitReadiness == 1) {
						resetAllMoveValues(cc.x, cc.y,
								mapView.map[cc.x][cc.y].unit.movement());
					} else if (currentUnitReadiness == 2) {
						resetAllAttackValues(cc.x, cc.y,
								mapView.map[cc.x][cc.y].unit.maxRange());
					} else if (currentUnitReadiness == 3) {
						if (mapView.map[cc.x][cc.y].unit.job.canHeal()) {
							resetAllAttackValues(
									cc.x,
									cc.y,
									((Healing) mapView.map[cc.x][cc.y].unit.job)
											.healRange());
						}
					}
					if (!mapView.map[cc.x][cc.y].unit.attacked) {
						setPossibleStealSquaresBFS(cc,
								mapView.map[cc.x][cc.y].unit.maxRange());
						mapView.invalidate();
						currentUnitReadiness = 4;
						System.out.println("Steal Pressed");
					}
				}
			}
			break;
		}

	}

	private void setPossibleHealSquaresBFS(CharacterNode cc, int max) {
		LinkedList<BSFnode> queue = new LinkedList<BSFnode>();
		queue.add(new BSFnode(cc.x, cc.y, max + 1));
		BSFnode current;

		while ((current = queue.poll()) != null) {
			if (current.v > mapView.map[current.x][current.y].attackValue) {
				if (mapView.map[current.x][current.y].unit != null) {
					if ((current.x == cc.x) && (current.y == cc.y)) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					} else if (mapView.map[current.x][current.y].unit.loyalty <= 1) {

						mapView.map[current.x][current.y].attackValue = 300;
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}

					}

				} else {
					mapView.map[current.x][current.y].attackValue = current.v;
					if (!mapView.map[current.x][current.y].tile
							.BlocksRangedAttacks()) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					}
				}
			}
		}
	}

	private void setPossibleStealSquaresBFS(CharacterNode cc, int max) {
		LinkedList<BSFnode> queue = new LinkedList<BSFnode>();
		queue.add(new BSFnode(cc.x, cc.y, max + 1));
		BSFnode current;

		while ((current = queue.poll()) != null) {
			if (current.v > mapView.map[current.x][current.y].attackValue) {
				if (mapView.map[current.x][current.y].unit != null) {
					if ((current.x == cc.x) && (current.y == cc.y)) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					} else if (mapView.map[current.x][current.y].unit.loyalty >= 1) {
						// can steal from living allies
						if (mapView.map[current.x][current.y].unit.conscious) {
							mapView.map[current.x][current.y].attackValue = 400;
						} else {
							if (current.x != (mapView.xTileLength - 1)) {
								queue.add(new BSFnode(current.x + 1, current.y,
										current.v - 1));
							}
							if (current.x != 0) {
								queue.add(new BSFnode(current.x - 1, current.y,
										current.v - 1));
							}
							if (current.y != (mapView.yTileLength - 1)) {
								queue.add(new BSFnode(current.x, current.y + 1,
										current.v - 1));
							}
							if (current.y != 0) {
								queue.add(new BSFnode(current.x, current.y - 1,
										current.v - 1));
							}
						}
					}

				} else {
					mapView.map[current.x][current.y].attackValue = current.v;
					if (!mapView.map[current.x][current.y].tile
							.BlocksRangedAttacks()) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					}
				}
			}
		}
	}

	// called when someone presses bEndRound
	/**
	 * 
	 * @param view
	 */
	public void bEndRoundPressed(View view) {
		System.out.println("End Pressed");
		finish();
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @param unit
	 */
	protected void addPlayerCharacter(int i, int j, Unit unit) {

		if (unit != null) {
			mapView.map[i][j].unit = unit;
			mapView.map[i][j].unit.loyalty = 0;
			mapView.map[i][j].unit.hashLink = playerCharacterNum;
			if (playerCharacters == null) {
				playerCharacters = new ArrayList<CharacterNode>();
			}
			playerCharacters.add(new CharacterNode(i, j));
			playerCharacterNum++;
		}

	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @param unit
	 * @param aiType
	 * @param specialTargets
	 */
	protected void addAlly(int i, int j, Unit unit, int aiType,
			int[] specialTargets, CharacterNode targetLocation) {

		if (unit != null) {
			mapView.map[i][j].unit = unit;
			mapView.map[i][j].unit.loyalty = 1;
			mapView.map[i][j].unit.hashLink = alliesNum;
			if (allies == null) {
				allies = new ArrayList<NonPlayableCharacterNode>();
			}
			allies.add(new NonPlayableCharacterNode(i, j, aiType
					| AIhelper.AI_FLAG_WILL_NOT_ATTACK_ALLIES
					| AIhelper.AI_FLAG_WILL_NOT_ATTACK_PLAYERCHARACTERS,
					specialTargets, targetLocation));
			alliesNum++;
		}

	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @param unit
	 * @param aiType
	 * @param specialTargets
	 */
	protected void addEnemy(int i, int j, Unit unit, int aiType,
			int[] specialTargets, CharacterNode targetLocation) {

		if (unit != null) {
			mapView.map[i][j].unit = unit;
			mapView.map[i][j].unit.loyalty = 2;
			mapView.map[i][j].unit.hashLink = enemiesNum;
			if (enemies == null) {
				enemies = new ArrayList<NonPlayableCharacterNode>();
			}
			enemies.add(new NonPlayableCharacterNode(i, j, aiType
					| AIhelper.AI_FLAG_WILL_NOT_ATTACK_ENEMIES, specialTargets,
					targetLocation));
			enemiesNum++;
		}

	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @param unit
	 * @param aiType
	 * @param specialTargets
	 */
	protected void addThirdPartyCharacter(int i, int j, Unit unit, int aiType,
			int[] specialTargets, CharacterNode targetLocation) {

		if (unit != null) {
			mapView.map[i][j].unit = unit;
			mapView.map[i][j].unit.loyalty = 3;
			mapView.map[i][j].unit.hashLink = thirdPartyCharactersNum;
			if (thirdPartyCharacters == null) {
				thirdPartyCharacters = new ArrayList<NonPlayableCharacterNode>();
			}
			thirdPartyCharacters.add(new NonPlayableCharacterNode(i, j, aiType
					| AIhelper.AI_FLAG_WILL_NOT_ATTACK_THIRDPARTYCHARACTERS,
					specialTargets, targetLocation));
			thirdPartyCharactersNum++;
		}

	}

	private void roundEnd() {

		specificRoundEndInstructions();
		playerCharactersLeftToUse = playerCharacterNum;
		for (CharacterNode p : playerCharacters) {
			mapView.map[p.x][p.y].unit.resetMoveAndAttackIfConscious();
		}
		roundNumber++;
	}

	/**
	 * The head of the AI tree of methods and the only AI method not called by
	 * another AI method
	 * 
	 * @param tp
	 *            NonPlayableCharacterNode to implement the AI for
	 */
	private void aiMoveAndAttack(NonPlayableCharacterNode tp) {

		if (AIhelper.willNotMove(tp.aiType)) {
			aiNoMoveAllowed(tp);
		} else {
			aiMoveAllowed(tp);
		}

	}

	/**
	 * A second tier AI method called by the AI head when the current character
	 * isn't one allowed to move
	 * 
	 * @param tp
	 *            NonPlayableCharacterNode to implement the AI for
	 */
	private void aiNoMoveAllowed(NonPlayableCharacterNode tp) {
		if (!AIhelper.willNotAttack(tp.aiType)) {
			boolean[] loyaltiesToAttack = AIhelper.attacksWho(tp.aiType);
			ArrayList<CharacterNode> foes = new ArrayList<CharacterNode>();

			aiGetFoesInAttackRange(tp, loyaltiesToAttack, foes);
			if (!foes.isEmpty()) {
				aiChooseFoeAndAttack(tp, foes);
			}
		}
	}

	/**
	 * A third tier AI method called for an immovable character to decide which
	 * foes in range to attack if any
	 * 
	 * @param tp
	 *            NonPlayableCharacterNode to implement the AI for
	 * @param foes
	 *            ArrayList of CharacterNodes representing foes in range, cannot
	 *            be null or empty due to where it is called
	 */
	private void aiChooseFoeAndAttack(NonPlayableCharacterNode tp,
			ArrayList<CharacterNode> foes) {
		foes = aiNarrowFoesBySpecialTarget(tp, foes);
		foes = aiNarrowFoesByRange(tp, foes);
		foes = aiNarrowFoesByStrength(tp.aiType, foes);
		if (foes.size() > 1) {
			Random r = new Random();
			int k = r.nextInt(foes.size());
			makeScroll2DHoldSquare(foes.get(k).x, foes.get(k).y);
			fight(tp.x, tp.y, foes.get(k).x, foes.get(k).y);

		} else if (foes.size() == 1) {
			makeScroll2DHoldSquare(foes.get(0).x, foes.get(0).y);
			fight(tp.x, tp.y, foes.get(0).x, foes.get(0).y);
		}

	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param minRange
	 * @param maxRange
	 * @param loyaltiesToAttack
	 * @return
	 */
	private ArrayList<CharacterNode> aiGetFoesInAttackRange(CharacterNode tp,
			boolean[] loyaltiesToAttack, ArrayList<CharacterNode> foes) {

		LinkedList<BSFnode> queue = new LinkedList<BSFnode>();
		// + 1 because call starts with where unit is
		queue.add(new BSFnode(tp.x, tp.y, mapView.map[tp.x][tp.y].unit
				.maxRange() + 1));
		BSFnode current;

		while ((current = queue.poll()) != null) {
			if (current.v > mapView.map[current.x][current.y].attackValue) {

				if (current.x == tp.x && current.y == tp.y) {
					if (current.x != (mapView.xTileLength - 1)) {
						queue.add(new BSFnode(current.x + 1, current.y,
								current.v - 1));
					}
					if (current.x != 0) {
						queue.add(new BSFnode(current.x - 1, current.y,
								current.v - 1));
					}
					if (current.y != (mapView.yTileLength - 1)) {
						queue.add(new BSFnode(current.x, current.y + 1,
								current.v - 1));
					}
					if (current.y != 0) {
						queue.add(new BSFnode(current.x, current.y - 1,
								current.v - 1));
					}
				} else if (mapView.map[current.x][current.y].unit != null) {
					if (loyaltiesToAttack[mapView.map[current.x][current.y].unit.loyalty]) {
						if (mapView.map[current.x][current.y].unit.conscious) {
							if (attackAllowed(tp, current)) {
								// set value to one million so unit will not be
								// added to the array twice
								mapView.map[current.x][current.y].attackValue = 1000000;
								foes.add(new CharacterNode(current.x, current.y));
							}
						} else {
							if (current.x != (mapView.xTileLength - 1)) {
								queue.add(new BSFnode(current.x + 1, current.y,
										current.v - 1));
							}
							if (current.x != 0) {
								queue.add(new BSFnode(current.x - 1, current.y,
										current.v - 1));
							}
							if (current.y != (mapView.yTileLength - 1)) {
								queue.add(new BSFnode(current.x, current.y + 1,
										current.v - 1));
							}
							if (current.y != 0) {
								queue.add(new BSFnode(current.x, current.y - 1,
										current.v - 1));
							}
						}
					} else if (!mapView.map[current.x][current.y].unit.conscious) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					}

				} else {
					mapView.map[current.x][current.y].attackValue = current.v;
					if (!mapView.map[current.x][current.y].tile
							.BlocksRangedAttacks()) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					}
				}
			}
		}
		resetAllAttackValues(tp.x, tp.y,
				mapView.map[tp.x][tp.y].unit.maxRange());
		return foes;
	}

	/**
	 * Fourth tier AI method called by aiChooseFoeAndAttack to reduce the number
	 * of foes based on whether they are special foes for this character
	 * 
	 * @param tp
	 *            NonPlayableCharacterNode to implement the AI for
	 * @param foes
	 *            ArrayList of CharacterNode objects holding foes in range that
	 *            the current character might want to attack
	 */
	private ArrayList<CharacterNode> aiNarrowFoesBySpecialTarget(
			NonPlayableCharacterNode tp, ArrayList<CharacterNode> foes) {
		if (tp.specialTargets != null) {
			int k;
			if (AIhelper.onlyAttacksSpecialTargets(tp.aiType)) {
				boolean notFound;
				for (int i = foes.size() - 1; i >= 0; i--) {
					k = mapView.map[foes.get(i).x][foes.get(i).y].unit.job.type;
					notFound = true;
					for (int j = 0; j < tp.specialTargets.length; j++) {
						if (tp.specialTargets[j] == k) {
							notFound = false;
							break;
						}
					}
					if (notFound) {
						foes.remove(i);
					}
				}
			} else {
				if (foes.size() < 2) {
					return foes;
				}
				ArrayList<CharacterNode> temp = new ArrayList<CharacterNode>();
				for (CharacterNode f : foes) {
					k = mapView.map[f.x][f.y].unit.job.type;
					for (int j = 0; j < tp.specialTargets.length; j++) {
						if (tp.specialTargets[j] == k) {
							temp.add(f);
							break;
						}
					}
				}
				if (!temp.isEmpty()) {
					return temp;
				}
			}
		}
		return foes;
	}

	/**
	 * Fourth tier AI method called by aiChooseFoeAndAttack to reduce the number
	 * of foes based on whether they can fight back or not
	 * 
	 * @param tp
	 *            NonPlayableCharacterNode to implement the AI for
	 * @param foes
	 *            ArrayList of CharacterNode objects holding foes in range that
	 *            the current character might want to attack
	 */
	private ArrayList<CharacterNode> aiNarrowFoesByRange(
			NonPlayableCharacterNode tp, ArrayList<CharacterNode> foes) {

		if (AIhelper.caresAboutRange(tp.aiType)) {
			if (AIhelper.onlyAttackFoesWhoCannotDefend(tp.aiType)) {
				for (int i = foes.size() - 1; i >= 0; i--) {

					int d = Math.abs(tp.x - foes.get(i).x)
							+ Math.abs(tp.y - foes.get(i).y);
					if (mapView.map[foes.get(i).x][foes.get(i).y].unit
							.minRange() <= d
							&& mapView.map[foes.get(i).x][foes.get(i).y].unit
									.maxRange() >= d) {
						foes.remove(i);
					}
				}
			} else {
				// useless if only one foe or no foes, useless for characters
				// who
				// currently have no range, and useless for archers
				if (foes.size() < 2
						|| mapView.map[tp.x][tp.y].unit.maxRange() == 1
						|| mapView.map[tp.x][tp.y].unit.minRange() > 1) {
					return foes;
				}
				ArrayList<CharacterNode> temp = new ArrayList<CharacterNode>();
				for (CharacterNode f : foes) {

					int d = Math.abs(tp.x - f.x) + Math.abs(tp.y - f.y);
					if (mapView.map[f.x][f.y].unit.minRange() > d
							|| mapView.map[f.x][f.y].unit.maxRange() < d) {
						temp.add(f);
					}
				}
				if (!temp.isEmpty()) {
					return temp;
				}
			}
		}
		return foes;
	}

	/**
	 * Fourth tier AI method called by aiChooseFoeAndAttack to reduce the number
	 * of foes based on whether the percentage of their HP
	 * 
	 * @param type
	 * @param foes
	 *            ArrayList of CharacterNode objects holding foes in range that
	 *            the current character might want to attack
	 */
	private ArrayList<CharacterNode> aiNarrowFoesByStrength(int type,
			ArrayList<CharacterNode> foes) {

		if (AIhelper.caresAboutFoeHealth(type) && foes.size() > 1) {
			if (AIhelper.prefersAttackingStrongFoes(type)) {
				float p = -1;
				CharacterNode theOne = null;
				for (int i = 0; i < foes.size(); i++) {
					float q = mapView.map[foes.get(i).x][foes.get(i).y].unit
							.getPercentageHP();
					if (q > p) {
						p = q;
						theOne = foes.get(i);
					}
				}
				foes.clear();
				foes.add(theOne);
			} else {
				float p = 2;
				CharacterNode theOne = null;
				for (int i = 0; i < foes.size(); i++) {
					float q = mapView.map[foes.get(i).x][foes.get(i).y].unit
							.getPercentageHP();
					if (q < p) {
						p = q;
						theOne = foes.get(i);
					}
				}
				foes.clear();
				foes.add(theOne);
			}
		}
		return foes;
	}

	// ////////

	private void aiMoveAllowed(NonPlayableCharacterNode tp) {

		if (AIhelper.willNotAttack(tp.aiType)) {
			aiHealStealorHide(tp);
		} else {

			boolean[] loyaltiesToAttack = AIhelper.attacksWho(tp.aiType);
			ArrayList<CharacterNode> foes = new ArrayList<CharacterNode>();

			if (loyaltiesToAttack[0] && playerCharacters != null) {
				for (CharacterNode f : playerCharacters) {
					// might want to change
					if (mapView.map[f.x][f.y].unit.conscious) {
						foes.add(new CharacterNode(f.x, f.y));
					}
				}
			}
			if (loyaltiesToAttack[1] && allies != null) {
				for (NonPlayableCharacterNode f : allies) {
					if (f != null && mapView.map[f.x][f.y].unit.conscious) {
						foes.add(new CharacterNode(f.x, f.y));
					}
				}
			}
			if (loyaltiesToAttack[2] && enemies != null) {
				for (NonPlayableCharacterNode f : enemies) {
					if (f != null && mapView.map[f.x][f.y].unit.conscious) {
						foes.add(new CharacterNode(f.x, f.y));
					}
				}
			}
			if (loyaltiesToAttack[3] && thirdPartyCharacters != null) {
				for (NonPlayableCharacterNode f : thirdPartyCharacters) {
					if (f != null && mapView.map[f.x][f.y].unit.conscious) {
						foes.add(new CharacterNode(f.x, f.y));
					}
				}
			}

			if (foes.isEmpty()) {
				aiFindMostDefensiblePosition(tp);
			} else {
				aiMoveAndAttackChoices(tp, foes);
			}
		}
	}

	private void aiHealStealorHide(NonPlayableCharacterNode tp) {
		if (AIhelper.isAThief(tp.aiType)) {
			aiActTheThief(tp);
		} else if (AIhelper.isAHealer(tp.aiType)) {
			aiActTheHealer(tp);
		} else {
			aiFindMostDefensiblePosition(tp);
		}
	}

	private void aiActTheThief(NonPlayableCharacterNode tp) {
		ArrayList<CharacterNode> itemLocations = new ArrayList<CharacterNode>();
		if (locationsFromWhichItemsCanBeStollen != null) {
			for (CharacterNode f : locationsFromWhichItemsCanBeStollen) {
				itemLocations.add(new CharacterNode(f.x, f.y));
			}
			if (itemLocations.isEmpty()) {
				aiFindMostDefensiblePosition(tp);
			} else {
				aiMoveTowardItem(tp, itemLocations);
			}

		} else {
			aiFindMostDefensiblePosition(tp);
		}

	}

	private void aiActTheHealer(NonPlayableCharacterNode tp) {
		ArrayList<CharacterNode> teamMembers = new ArrayList<CharacterNode>();
		int loyalty = mapView.map[tp.x][tp.y].unit.loyalty;

		if (loyalty == 1) {
			for (CharacterNode f : playerCharacters) {
				if (f != null
						&& (mapView.map[f.x][f.y].unit.HP < mapView.map[f.x][f.y].unit.maxHP)) {
					teamMembers.add(new CharacterNode(f.x, f.y));
				}
			}
			for (NonPlayableCharacterNode f : allies) {
				if (f != null
						&& (mapView.map[f.x][f.y].unit.HP < mapView.map[f.x][f.y].unit.maxHP)) {
					teamMembers.add(new CharacterNode(f.x, f.y));
				}
			}
		}
		if (loyalty == 2) {
			for (NonPlayableCharacterNode f : enemies) {
				if (f != null
						&& (mapView.map[f.x][f.y].unit.HP < mapView.map[f.x][f.y].unit.maxHP)) {
					teamMembers.add(new CharacterNode(f.x, f.y));
				}
			}
		}
		if (loyalty == 3) {
			for (NonPlayableCharacterNode f : thirdPartyCharacters) {
				if (f != null
						&& (mapView.map[f.x][f.y].unit.HP < mapView.map[f.x][f.y].unit.maxHP)) {
					teamMembers.add(new CharacterNode(f.x, f.y));
				}
			}
		}

		if (teamMembers.isEmpty()) {
			aiFindMostDefensiblePosition(tp);
		} else {
			aiMoveCloseToTeamMates(tp, teamMembers);
		}
	}

	private void aiMoveCloseToTeamMates(NonPlayableCharacterNode tp,
			ArrayList<CharacterNode> teamMembers) {
		int l = teamMembers.size();
		CharacterNode[] array = new CharacterNode[l];
		for (int i = 0; i < l; i++) {
			array[i] = teamMembers.get(i);
		}

		boolean needsNextPass = true;
		CharacterNode temp;
		for (int k = 1; k < l && needsNextPass; k++) {
			needsNextPass = false;
			for (int i = 0; i < (l - k); i++) {
				if ((Math.abs(array[i].x - tp.x) + Math.abs(array[i].y - tp.y)) < (Math
						.abs(array[i + 1].x - tp.x) + Math.abs(array[i + 1].y
						- tp.y))) {
					temp = array[i];
					array[i] = array[i + 1];
					array[i + 1] = temp;
					needsNextPass = true;
				}
			}
		}

		boolean destinationFound = false;
		CharacterNode destination = new CharacterNode(-1, -1);
		aiActivateMoveSquares(tp);
		for (int i = 0; i < l; i++) {
			destinationFound = aiFindClosestMoveSquareGivenMoveSquaresActivated(
					tp, array[i], destination);
			if (destinationFound) {
				break;
			}
		}

		if (destinationFound) {
			aiMoveToDestination(tp, destination);
			aiHealClosestAllyIfAble(destination);
		} else {
			resetAllMoveValues(tp.x, tp.y,
					mapView.map[tp.x][tp.y].unit.movement());
			aiFindMostDefensiblePosition(tp);
		}

	}

	private void aiHealClosestAllyIfAble(CharacterNode d) {
		LinkedList<BSFnode> queue = new LinkedList<BSFnode>();
		queue.add(new BSFnode(d.x, d.y,
				((Healing) mapView.map[d.x][d.y].unit.job).healRange() + 1));
		BSFnode current;

		while ((current = queue.poll()) != null) {
			if (current.v > mapView.map[current.x][current.y].attackValue) {
				if (mapView.map[current.x][current.y].unit != null) {
					if ((current.x == d.x) && (current.y == d.y)) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					} else if (checkLoyaltyForHealing(
							mapView.map[current.x][current.y].unit.loyalty,
							mapView.map[d.x][d.y].unit.loyalty)) {
						((Healing) mapView.map[d.x][d.y].unit.job).heal(
								Math.abs(current.x - d.x)
										+ Math.abs(current.y - d.y),
								mapView.map[current.x][current.y].unit);
					}
				} else {
					mapView.map[current.x][current.y].attackValue = current.v;
					if (!mapView.map[current.x][current.y].tile
							.BlocksRangedAttacks()) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					}
				}
			}
		}
		resetAllAttackValues(d.x, d.y,
				((Healing) mapView.map[d.x][d.y].unit.job).healRange());
	}

	private boolean checkLoyaltyForHealing(int loyaltyOther, int loyaltySelf) {

		switch (loyaltySelf) {
		case 1:
			if (loyaltyOther <= 1) {
				return true;
			}
			break;
		case 2:
			if (loyaltyOther == 2) {
				return true;
			}
			break;
		case 3:
			if (loyaltyOther == 3) {
				return true;
			}
			break;
		}
		return false;
	}

	private void aiMoveTowardItem(NonPlayableCharacterNode tp,
			ArrayList<CharacterNode> itemLocations) {
		int l = itemLocations.size();
		CharacterNode[] array = new CharacterNode[l];
		for (int i = 0; i < l; i++) {
			array[i] = itemLocations.get(i);
		}

		boolean needsNextPass = true;
		CharacterNode temp;
		for (int k = 1; k < l && needsNextPass; k++) {
			needsNextPass = false;
			for (int i = 0; i < (l - k); i++) {
				if ((Math.abs(array[i].x - tp.x) + Math.abs(array[i].y - tp.y)) < (Math
						.abs(array[i + 1].x - tp.x) + Math.abs(array[i + 1].y
						- tp.y))) {
					temp = array[i];
					array[i] = array[i + 1];
					array[i + 1] = temp;
					needsNextPass = true;
				}
			}
		}

		boolean destinationFound = false;
		CharacterNode destination = new CharacterNode(-1, -1);
		aiActivateMoveSquares(tp);
		for (int i = 0; i < l; i++) {
			destinationFound = aiFindClosestMoveSquareGivenMoveSquaresActivated(
					tp, array[i], destination);
			if (destinationFound) {
				break;
			}
		}

		if (destinationFound) {
			aiMoveToDestination(tp, destination);
		} else {
			resetAllMoveValues(tp.x, tp.y,
					mapView.map[tp.x][tp.y].unit.movement());
			aiFindMostDefensiblePosition(tp);
		}

	}

	private void aiMoveAndAttackChoices(NonPlayableCharacterNode tp,
			ArrayList<CharacterNode> foes) {
		Unit aiUnit = mapView.map[tp.x][tp.y].unit;
		if (AIhelper.runsAwayWhenBelow50Percent(tp.aiType)
				&& (aiUnit.getPercentageHP() > .5)) {
			aiFindMostDefensiblePosition(tp);
		} else {

			ArrayList<CharacterNode> foesInRange = new ArrayList<CharacterNode>();
			ArrayList<CharacterNode> foesInRangeInTwoTurns = new ArrayList<CharacterNode>();
			ArrayList<CharacterNode> foesInRangeInThreeTurns = new ArrayList<CharacterNode>();

			for (CharacterNode f : foes) {
				int d = Math.abs(tp.x - f.x) + Math.abs(tp.y - f.y);
				if (d <= (aiUnit.movement() + aiUnit.maxRange())) {
					foesInRange.add(f);
				} else if (d <= (aiUnit.movement() * 2 + aiUnit.maxRange())) {
					foesInRangeInTwoTurns.add(f);
				} else if (d <= (aiUnit.movement() * 3 + aiUnit.maxRange())) {
					foesInRangeInThreeTurns.add(f);
				}
			}

			if (!foesInRange.isEmpty()) {
				aiTryAndAttack(tp, foesInRange, foesInRangeInTwoTurns);
			} else if (!foesInRangeInTwoTurns.isEmpty()) {
				aiMoveTowardCharacters(tp, foesInRangeInTwoTurns);
			} else {
				aiMoveTowardCharacters(tp, foesInRangeInThreeTurns);
			}
		}
	}

	private void aiTryAndAttack(NonPlayableCharacterNode tp,
			ArrayList<CharacterNode> foesInRange,
			ArrayList<CharacterNode> foesInRangeInTwoTurns) {
		foesInRange = sortFoeList(tp, foesInRange);
		aiActivateMoveSquares(tp);

		ArrayList<CharacterNode> squaresToAttackFrom = new ArrayList<CharacterNode>();
		for (CharacterNode f : foesInRange) {
			squaresToAttackFrom = aiFindAttackSquaresGivenMoveSquaresActivated(
					tp, f);

			aiNarrowByRange(tp, f, squaresToAttackFrom);

			if (!squaresToAttackFrom.isEmpty()) {
				resetAllMoveValues(tp.x, tp.y,
						mapView.map[tp.x][tp.y].unit.movement());
				aiMoveAndAttack(tp, f, squaresToAttackFrom);
				break;
			}
		}

		if (squaresToAttackFrom.isEmpty()) {
			resetAllMoveValues(tp.x, tp.y,
					mapView.map[tp.x][tp.y].unit.movement());
			aiMoveTowardCharacters(tp, foesInRangeInTwoTurns);
		}

	}

	private void aiMoveTowardCharacters(NonPlayableCharacterNode tp,
			ArrayList<CharacterNode> foesToMoveToward) {
		if (foesToMoveToward.isEmpty()) {
			aiFindMostDefensiblePosition(tp);
			return;
		}

		foesToMoveToward = sortFoeList(tp, foesToMoveToward);
		aiActivateMoveSquares(tp);

		boolean destinationFound = false;
		CharacterNode destination = new CharacterNode(-1, -1);
		for (CharacterNode f : foesToMoveToward) {
			destinationFound = aiFindClosestMoveSquareGivenMoveSquaresActivated(
					tp, f, destination);
			if (destinationFound) {
				break;
			}
		}

		resetAllMoveValues(tp.x, tp.y, mapView.map[tp.x][tp.y].unit.movement());
		if (destinationFound) {
			aiMoveToDestination(tp, destination);
		} else {
			aiFindMostDefensiblePosition(tp);
		}
	}

	private ArrayList<CharacterNode> sortFoeList(NonPlayableCharacterNode tp,
			ArrayList<CharacterNode> foeList) {

		if (AIhelper.caresAboutFoeHealth(tp.aiType)) {
			CharacterNode[] array = new CharacterNode[foeList.size()];
			int l = foeList.size();
			for (int i = 0; i < l; i++) {
				array[i] = foeList.get(i);
			}

			boolean needsNextPass = true;
			CharacterNode temp;
			if (AIhelper.prefersAttackingStrongFoes(tp.aiType)) {
				for (int k = 1; k < l && needsNextPass; k++) {
					needsNextPass = false;
					for (int i = 0; i < (l - k); i++) {
						if (mapView.map[array[i].x][array[i].y].unit
								.getPercentageHP() < mapView.map[array[i + 1].x][array[i + 1].y].unit
								.getPercentageHP()) {
							temp = array[i];
							array[i] = array[i + 1];
							array[i + 1] = temp;
							needsNextPass = true;
						}
					}
				}
			} else {
				for (int k = 1; k < l && needsNextPass; k++) {
					needsNextPass = false;
					for (int i = 0; i < (l - k); i++) {
						if (mapView.map[array[i].x][array[i].y].unit
								.getPercentageHP() > mapView.map[array[i + 1].x][array[i + 1].y].unit
								.getPercentageHP()) {
							temp = array[i];
							array[i] = array[i + 1];
							array[i + 1] = temp;
							needsNextPass = true;
						}
					}
				}
			}
			foeList = new ArrayList<CharacterNode>();
			for (int i = 0; i < l; i++) {
				foeList.add(array[i]);
			}
		}

		ArrayList<CharacterNode> a = new ArrayList<CharacterNode>();
		ArrayList<CharacterNode> b = new ArrayList<CharacterNode>();

		if (AIhelper.caresAboutRange(tp.aiType)) {
			int minRangeAI = mapView.map[tp.x][tp.y].unit.minRange();
			int maxRangeAI = mapView.map[tp.x][tp.y].unit.maxRange();

			for (CharacterNode f : foeList) {
				if (mapView.map[f.x][f.y].unit.minRange() > minRangeAI
						|| mapView.map[f.x][f.y].unit.maxRange() < maxRangeAI) {
					a.add(f);
				} else {
					b.add(f);
				}
			}
			foeList = new ArrayList<CharacterNode>();
			for (CharacterNode f : a) {
				foeList.add(f);
			}
			if (!AIhelper.onlyAttackFoesWhoCannotDefend(tp.aiType)) {
				for (CharacterNode f : b) {
					foeList.add(f);
				}
			}

		}

		if (tp.specialTargets != null) {
			a = new ArrayList<CharacterNode>();
			b = new ArrayList<CharacterNode>();
			boolean notAdded = true;
			for (CharacterNode f : foeList) {
				int type = mapView.map[f.x][f.y].unit.job.type;
				for (int i : tp.specialTargets) {
					if (i == type) {
						a.add(f);
						notAdded = false;
						break;
					}
				}
				if (notAdded) {
					b.add(f);
					notAdded = true;
				}
			}
			foeList = new ArrayList<CharacterNode>();
			for (CharacterNode f : a) {
				foeList.add(f);
			}
			if (!AIhelper.onlyAttacksSpecialTargets(tp.aiType)) {
				for (CharacterNode f : b) {
					foeList.add(f);
				}
			}
		}

		return foeList;
	}

	private void aiActivateMoveSquares(NonPlayableCharacterNode tp) {

		LinkedList<BSFnode> queue = new LinkedList<BSFnode>();
		queue.add(new BSFnode(tp.x, tp.y, mapView.map[tp.x][tp.y].unit
				.movement() + 1));
		BSFnode current;

		while ((current = queue.poll()) != null) {
			if (current.v > mapView.map[current.x][current.y].moveValue) {
				if (mapView.map[current.x][current.y].unit != null) {
					if (mapView.map[current.x][current.y].unit.loyalty == mapView.map[tp.x][tp.y].unit.loyalty
							|| !mapView.map[current.x][current.y].unit.conscious) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(
									current.x + 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(
									current.x - 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(
									current.x,
									current.y + 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(
									current.x,
									current.y - 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}

					}

				} else {
					if (!mapView.map[current.x][current.y].tile
							.isInaccessible()) {
						mapView.map[current.x][current.y].moveValue = current.v;
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(
									current.x + 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(
									current.x - 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(
									current.x,
									current.y + 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(
									current.x,
									current.y - 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
					}
				}
			}
		}
	}

	private ArrayList<CharacterNode> aiFindAttackSquaresGivenMoveSquaresActivated(
			NonPlayableCharacterNode tp, CharacterNode f) {
		boolean[] loyaltiesToAttack = AIhelper.attacksWho(tp.aiType);
		ArrayList<CharacterNode> attackSquares = new ArrayList<CharacterNode>();

		LinkedList<BSFnode> queue = new LinkedList<BSFnode>();
		// + 1 because call starts with where unit is
		queue.add(new BSFnode(f.x, f.y,
				mapView.map[tp.x][tp.y].unit.maxRange() + 1));
		BSFnode current;
		while ((current = queue.poll()) != null) {
			if (current.v > mapView.map[current.x][current.y].attackValue) {

				if (mapView.map[current.x][current.y].moveValue > 0) {
					if (attackAllowedCanMove(f, tp, current)) {
						attackSquares.add(new CharacterNode(current.x,
								current.y));
					}
				}

				if (current.x == f.x && current.y == f.y) {
					if (current.x != (mapView.xTileLength - 1)) {
						queue.add(new BSFnode(current.x + 1, current.y,
								current.v - 1));
					}
					if (current.x != 0) {
						queue.add(new BSFnode(current.x - 1, current.y,
								current.v - 1));
					}
					if (current.y != (mapView.yTileLength - 1)) {
						queue.add(new BSFnode(current.x, current.y + 1,
								current.v - 1));
					}
					if (current.y != 0) {
						queue.add(new BSFnode(current.x, current.y - 1,
								current.v - 1));
					}
				} else if (mapView.map[current.x][current.y].unit != null) {
					if (loyaltiesToAttack[mapView.map[current.x][current.y].unit.loyalty]) {
						if (mapView.map[current.x][current.y].unit.conscious) {

						} else {
							if (current.x != (mapView.xTileLength - 1)) {
								queue.add(new BSFnode(current.x + 1, current.y,
										current.v - 1));
							}
							if (current.x != 0) {
								queue.add(new BSFnode(current.x - 1, current.y,
										current.v - 1));
							}
							if (current.y != (mapView.yTileLength - 1)) {
								queue.add(new BSFnode(current.x, current.y + 1,
										current.v - 1));
							}
							if (current.y != 0) {
								queue.add(new BSFnode(current.x, current.y - 1,
										current.v - 1));
							}
						}
					} else if (!mapView.map[current.x][current.y].unit.conscious) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					}

				} else {
					mapView.map[current.x][current.y].attackValue = current.v;
					if (!mapView.map[current.x][current.y].tile
							.BlocksRangedAttacks()) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(current.x + 1, current.y,
									current.v - 1));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(current.x - 1, current.y,
									current.v - 1));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(current.x, current.y + 1,
									current.v - 1));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(current.x, current.y - 1,
									current.v - 1));
						}
					}
				}
			}
		}
		resetAllAttackValues(f.x, f.y, mapView.map[tp.x][tp.y].unit.maxRange());
		return attackSquares;
	}

	private boolean attackAllowedCanMove(CharacterNode f,
			NonPlayableCharacterNode tp, BSFnode current) {

		if (mapView.map[tp.x][tp.y].unit.minRange() == 2
				&& mapView.map[tp.x][tp.y].unit.maxRange() == 4) {
			if (current.v == 4) {
				return false;
			} else if (current.v == 1) {
				int leftRight = current.x - f.x;
				int upDown = current.y - f.y;

				switch (leftRight) {
				case -2:
					switch (upDown) {
					case -2:
						return checkIfNodeBlocksRangedAttacks(f.x - 1, f.y - 1);
					case 0:
						return checkIfNodeBlocksRangedAttacks(f.x - 1, f.y);
					case 2:
						return checkIfNodeBlocksRangedAttacks(f.x - 1, f.y + 1);
					}
					break;
				case 0:
					switch (upDown) {
					case -2:
						return checkIfNodeBlocksRangedAttacks(f.x, f.y - 1);
					case 2:
						return checkIfNodeBlocksRangedAttacks(f.x, f.y + 1);
					}
					break;
				case 2:
					switch (upDown) {
					case -2:
						return checkIfNodeBlocksRangedAttacks(f.x + 1, f.y - 1);
					case 0:
						return checkIfNodeBlocksRangedAttacks(f.x + 1, f.y);
					case 2:
						return checkIfNodeBlocksRangedAttacks(f.x + 1, f.y + 1);
					}
					break;
				}
			}
		}
		return true;
	}

	private void aiNarrowByRange(NonPlayableCharacterNode tp, CharacterNode f,
			ArrayList<CharacterNode> squaresToAttackFrom) {
		if (AIhelper.caresAboutRange(tp.aiType)) {
			int minRangeFoe = mapView.map[f.x][f.y].unit.minRange();
			int maxRangeFoe = mapView.map[f.x][f.y].unit.maxRange();
			if (AIhelper.onlyAttackFoesWhoCannotDefend(tp.aiType)) {
				for (int i = squaresToAttackFrom.size() - 1; i >= 0; i--) {

					int d = Math.abs(f.x - squaresToAttackFrom.get(i).x)
							+ Math.abs(f.y - squaresToAttackFrom.get(i).y);
					if (minRangeFoe <= d && maxRangeFoe >= d) {
						squaresToAttackFrom.remove(i);
					}
				}
			} else {
				ArrayList<CharacterNode> temp = new ArrayList<CharacterNode>();
				for (CharacterNode s : squaresToAttackFrom) {

					int d = Math.abs(f.x - s.x) + Math.abs(f.y - s.y);
					if (minRangeFoe > d || maxRangeFoe < d) {
						temp.add(s);
					}
				}
				if (!temp.isEmpty()) {
					squaresToAttackFrom.clear();
					squaresToAttackFrom.addAll(temp);
				}
			}
		}
	}

	private void aiMoveAndAttack(NonPlayableCharacterNode tp, CharacterNode f,
			ArrayList<CharacterNode> squaresToAttackFrom) {
		Random r = new Random();
		if (squaresToAttackFrom.isEmpty()) {
			return;
		} else if (squaresToAttackFrom.size() == 1) {
			aiMoveToDestination(tp, squaresToAttackFrom.get(0));
			makeScroll2DHoldSquare(f.x, f.y);

			if (mapView.map[squaresToAttackFrom.get(0).x][squaresToAttackFrom
					.get(0).y].unit.job.stealNode != null
					&& mapView.map[squaresToAttackFrom.get(0).x][squaresToAttackFrom
							.get(0).y].unit.job.stealNode
							.canStealFrom(mapView.map[f.x][f.y].unit)) {
				switch (r.nextInt(7)) {
				case 0:
				case 1:
					if (mapView.map[squaresToAttackFrom.get(0).x][squaresToAttackFrom
							.get(0).y].unit.job.stealNode
							.stealSuccessful(mapView.map[f.x][f.y].unit)) {
						mapView.map[squaresToAttackFrom.get(0).x][squaresToAttackFrom
								.get(0).y].unit.job.stealNode
								.stealRandomItem(mapView.map[f.x][f.y].unit);
					}
					break;
				case 2:
					if (mapView.map[squaresToAttackFrom.get(0).x][squaresToAttackFrom
							.get(0).y].unit.job.stealNode
							.stealSuccessful(mapView.map[f.x][f.y].unit)) {
						mapView.map[squaresToAttackFrom.get(0).x][squaresToAttackFrom
								.get(0).y].unit.job.stealNode
								.stealMostValuableItem(mapView.map[f.x][f.y].unit);
					}
					break;
				default:
					fight(squaresToAttackFrom.get(0).x,
							squaresToAttackFrom.get(0).y, f.x, f.y);
					break;
				}
			} else {
				fight(squaresToAttackFrom.get(0).x,
						squaresToAttackFrom.get(0).y, f.x, f.y);
			}
		} else {
			int k = r.nextInt(squaresToAttackFrom.size());
			aiMoveToDestination(tp, squaresToAttackFrom.get(k));
			makeScroll2DHoldSquare(f.x, f.y);

			if (mapView.map[squaresToAttackFrom.get(k).x][squaresToAttackFrom
					.get(k).y].unit.job.stealNode != null
					&& mapView.map[squaresToAttackFrom.get(k).x][squaresToAttackFrom
							.get(k).y].unit.job.stealNode
							.canStealFrom(mapView.map[f.x][f.y].unit)) {
				switch (r.nextInt(7)) {
				case 0:
				case 1:
					if (mapView.map[squaresToAttackFrom.get(0).x][squaresToAttackFrom
							.get(0).y].unit.job.stealNode
							.stealSuccessful(mapView.map[f.x][f.y].unit)) {
						mapView.map[squaresToAttackFrom.get(k).x][squaresToAttackFrom
								.get(k).y].unit.job.stealNode
								.stealRandomItem(mapView.map[f.x][f.y].unit);
					}
					break;
				case 2:
					if (mapView.map[squaresToAttackFrom.get(0).x][squaresToAttackFrom
							.get(0).y].unit.job.stealNode
							.stealSuccessful(mapView.map[f.x][f.y].unit)) {
						mapView.map[squaresToAttackFrom.get(k).x][squaresToAttackFrom
								.get(k).y].unit.job.stealNode
								.stealMostValuableItem(mapView.map[f.x][f.y].unit);
					}
					break;
				default:
					fight(squaresToAttackFrom.get(k).x,
							squaresToAttackFrom.get(k).y, f.x, f.y);
					break;
				}
			} else {

				fight(squaresToAttackFrom.get(k).x,
						squaresToAttackFrom.get(k).y, f.x, f.y);
			}
		}

	}

	private boolean aiFindClosestMoveSquareGivenMoveSquaresActivated(
			NonPlayableCharacterNode tp, CharacterNode f,
			CharacterNode destination) {
		LinkedList<BSFnode> queue = new LinkedList<BSFnode>();
		queue.add(new BSFnode(f.x, f.y, 16));
		BSFnode current;

		while ((current = queue.poll()) != null) {
			if (current.v > mapView.map[current.x][current.y].attackValue) {
				if (mapView.map[current.x][current.y].moveValue > 0) {
					destination.x = current.x;
					destination.y = current.y;
					resetAllAttackValues(f.x, f.y, 15);
					return true;
				}

				if (current.x == f.x && current.y == f.y) {
					if (current.x != (mapView.xTileLength - 1)) {
						queue.add(new BSFnode(
								current.x + 1,
								current.y,
								current.v
										- mapView.map[current.x][current.y].tile
												.getMoveImpediment()));
					}
					if (current.x != 0) {
						queue.add(new BSFnode(
								current.x - 1,
								current.y,
								current.v
										- mapView.map[current.x][current.y].tile
												.getMoveImpediment()));
					}
					if (current.y != (mapView.yTileLength - 1)) {
						queue.add(new BSFnode(
								current.x,
								current.y + 1,
								current.v
										- mapView.map[current.x][current.y].tile
												.getMoveImpediment()));
					}
					if (current.y != 0) {
						queue.add(new BSFnode(
								current.x,
								current.y - 1,
								current.v
										- mapView.map[current.x][current.y].tile
												.getMoveImpediment()));
					}

				} else if (mapView.map[current.x][current.y].unit != null) {
					if (mapView.map[current.x][current.y].unit.loyalty == mapView.map[tp.x][tp.y].unit.loyalty
							|| !mapView.map[current.x][current.y].unit.conscious) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(
									current.x + 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(
									current.x - 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(
									current.x,
									current.y + 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(
									current.x,
									current.y - 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}

					}

				} else {
					if (!mapView.map[current.x][current.y].tile
							.isInaccessible()) {
						mapView.map[current.x][current.y].attackValue = current.v;
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(
									current.x + 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(
									current.x - 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(
									current.x,
									current.y + 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(
									current.x,
									current.y - 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
					}
				}
			}
		}
		resetAllAttackValues(f.x, f.y, 15);
		return false;
	}

	private void aiFindMostDefensiblePosition(NonPlayableCharacterNode tp) {
		LinkedList<BSFnode> queue = new LinkedList<BSFnode>();
		queue.add(new BSFnode(tp.x, tp.y, mapView.map[tp.x][tp.y].unit
				.movement() + 1));
		BSFnode current;

		CharacterNode squareToMoveTo = new CharacterNode(tp.x, tp.y);

		while ((current = queue.poll()) != null) {
			if (current.v > mapView.map[current.x][current.y].moveValue) {
				if (mapView.map[current.x][current.y].unit != null) {
					if (mapView.map[current.x][current.y].unit.loyalty == mapView.map[tp.x][tp.y].unit.loyalty
							|| !mapView.map[current.x][current.y].unit.conscious) {
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(
									current.x + 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(
									current.x - 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(
									current.x,
									current.y + 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(
									current.x,
									current.y - 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}

					}

				} else {
					if (!mapView.map[current.x][current.y].tile
							.isInaccessible()) {
						mapView.map[current.x][current.y].moveValue = current.v;
						if ((mapView.map[current.x][current.y].tile
								.getCoverAvoidance() + mapView.map[current.x][current.y].tile
								.getCoverDefence()) > (mapView.map[squareToMoveTo.x][squareToMoveTo.y].tile
								.getCoverAvoidance() + mapView.map[squareToMoveTo.x][squareToMoveTo.y].tile
								.getCoverDefence())) {
							squareToMoveTo.x = current.x;
							squareToMoveTo.y = current.y;
						}
						if (current.x != (mapView.xTileLength - 1)) {
							queue.add(new BSFnode(
									current.x + 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.x != 0) {
							queue.add(new BSFnode(
									current.x - 1,
									current.y,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != (mapView.yTileLength - 1)) {
							queue.add(new BSFnode(
									current.x,
									current.y + 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
						if (current.y != 0) {
							queue.add(new BSFnode(
									current.x,
									current.y - 1,
									current.v
											- mapView.map[current.x][current.y].tile
													.getMoveImpediment()));
						}
					}
				}
			}
		}

		resetAllMoveValues(tp.x, tp.y, mapView.map[tp.x][tp.y].unit.movement());
		if (squareToMoveTo.x != tp.x || squareToMoveTo.y != tp.y) {
			aiMoveToDestination(tp, squareToMoveTo);
		}

	}

	private void aiMoveToDestination(NonPlayableCharacterNode tp,
			CharacterNode destination) {
		if (destination.x < 0) {
			return;
		}

		makeScroll2DHoldSquare(destination.x, destination.y);
		mapView.moveCharacter(tp.x, tp.y, destination.x, destination.y);
		tp.x = destination.x;
		tp.y = destination.y;
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @param range
	 */
	private void resetAllAttackValues(int i, int j, int range) {

		i = i - range;
		j = j - range;

		if (i < 0) {
			i = 0;
		}
		if (j < 0) {
			j = 0;
		}

		int k = i + range * 2 + 1;
		int l = j + range * 2 + 1;

		if (k >= mapView.xTileLength) {
			k = mapView.xTileLength;
		}
		if (l >= mapView.yTileLength) {
			l = mapView.yTileLength;
		}

		for (; i < k; i++) {
			for (int m = j; m < l; m++) {
				mapView.map[i][m].attackValue = 0;
			}
		}

	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @param speed
	 */
	private void resetAllMoveValues(int i, int j, int speed) {

		i = i - speed;
		j = j - speed;

		if (i < 0) {
			i = 0;
		}
		if (j < 0) {
			j = 0;
		}

		int k = i + speed * 2 + 1;
		int l = j + speed * 2 + 1;

		if (k >= mapView.xTileLength) {
			k = mapView.xTileLength;
		}
		if (l >= mapView.yTileLength) {
			l = mapView.yTileLength;
		}

		for (; i < k; i++) {
			for (int m = j; m < l; m++) {
				mapView.map[i][m].moveValue = 0;
			}
		}

	}

	protected void fight(int x, int y, int i, int j) {
		int d = Math.abs(x - i) + Math.abs(y - j);
		MapNode A = mapView.map[x][y];
		MapNode B = mapView.map[i][j];

		textViewControlVariable = 1;
		textView.setText(A.unit.name + " of loyalty " + A.unit.loyalty
				+ " is attacking " + B.unit.name + " of loyalty "
				+ B.unit.loyalty + "\n\n");

		int s = GameHelper.triangleAndSkillVsBonus(A.unit, B.unit, d);

		Random r = new Random();
		textView.append("Round 1: ");
		attack(A, B, d, r, s);
		if (A.unit.conscious && B.unit.conscious) {
			textView.append("Round 2: ");
			attack(B, A, d, r, s * (-1));
		}
		if (A.unit.conscious && B.unit.conscious) {
			if (A.unit.attackSpeed() - B.unit.attackSpeed() >= 4) {
				textView.append("Round 3: ");
				attack(A, B, d, r, s);
			} else if (B.unit.attackSpeed() - A.unit.attackSpeed() >= 4) {
				textView.append("Round 3: ");
				attack(B, A, d, r, s * (-1));
			}
		}

		A.unit.gainXP(B.unit.xpGainedFrom(A.unit.level),
				B.unit.getAttackType(d));
		B.unit.gainXP(A.unit.xpGainedFrom(B.unit.level),
				A.unit.getAttackType(d));

		if (!A.unit.conscious) {
			dealWithUnconsciousUnit(A);
		} else if (!B.unit.conscious) {
			dealWithUnconsciousUnit(B);
		}
	}

	protected void attack(MapNode a, MapNode b, int d, Random r, int s) {
		textView.append(a.unit.name + " attacking " + b.unit.name + "\n");
		if (d <= a.unit.maxRange() && d >= a.unit.minRange()) {
			textView.append(a.unit.name + "'s accuracy is "
					+ a.unit.accuracy(d) + "\n");
			textView.append(b.unit.name + "'s evade is "
					+ b.unit.evade(d, a.unit.getAttackType(d)) + "\n");
			int i = a.unit.accuracy(d)
					- b.unit.evade(d, a.unit.getAttackType(d));

			textView.append("Triangle and SkillVs hit bonus is " + s * 15
					+ "\n");

			i += s * 15;

			textView.append("This gives a hit chance of " + i + "\n");
			int j = r.nextInt(100);
			if (j <= i) {
				textView.append("Attack hits!\n");
				int k = a.unit.damage(d);

				textView.append("Triangle and SkillVs damage bonus is " + s
						+ "\n");

				k += s;

				switch (r.nextInt(5)) {
				case 0:
					k -= 2;
					break;
				case 1:
					k -= 1;
					break;
				case 2:
					break;
				case 3:
					k += 1;
					break;
				case 4:
					k += 2;
					break;
				}

				if (a.unit.weapon != null) {
					a.unit.weapon = a.unit.weapon.damageWeapon(k);
				}

				b.unit.job.damageArmor(k);

				i = a.unit.critChance(d) - b.unit.critDefence(d);
				textView.append(a.unit.name + "'s critChance is "
						+ a.unit.critChance(d) + "\n");
				textView.append(b.unit.name + "'s critDefence is "
						+ b.unit.critDefence(d) + "\n");
				textView.append("This gives a crit chance of " + i + "\n");
				j = r.nextInt(100);
				if (j <= i) {
					textView.append("Crit Happened!\n");
					k = k * 3;
				}
				textView.append("Final attack damage is " + k + "\n");

				textView.append("Damage reduction is "
						+ b.unit.damageReduction(a.unit.getAttackType(d))
						+ "\n");
				k -= b.unit.damageReduction(a.unit.getAttackType(d));

				if (k > 0) {
					textView.append(b.unit.name + " takes " + k + " damage\n");
					b.unit.loseHP(k);
				} else {
					textView.append(b.unit.name + " takes no damage\n");
				}
			}
		} else {
			textView.append(b.unit.name + " is out of range\n");
		}
	}

	protected void AOEPlayer(CharacterNode m) {
		Unit u = mapView.map[m.x][m.y].unit;
		if (u.job.hasAOE() && u.conscious) {
			boolean[] attacksWho = { false, false, true, true };
			activateAOE(m, (AOE) u.job, attacksWho);
		}
	}

	protected void AOEAI(NonPlayableCharacterNode m) {
		Unit u = mapView.map[m.x][m.y].unit;
		if (u.job.hasAOE() && u.conscious) {
			activateAOE(m, (AOE) u.job, AIhelper.attacksWho(m.aiType));
		}
	}

	private void activateAOE(CharacterNode m, AOE aoe, boolean[] attacksWho) {

		LinkedList<BSFnode> queue = new LinkedList<BSFnode>();
		queue.add(new BSFnode(m.x, m.y, aoe.rangeOfAOE() + 1));
		BSFnode current;

		while ((current = queue.poll()) != null) {
			if (current.v > mapView.map[current.x][current.y].attackValue) {
				mapView.map[current.x][current.y].attackValue = current.v;

				if (current.x == m.x && current.y == m.y) {
					if (current.x != (mapView.xTileLength - 1)) {
						queue.add(new BSFnode(current.x + 1, current.y,
								current.v - 1));
					}
					if (current.x != 0) {
						queue.add(new BSFnode(current.x - 1, current.y,
								current.v - 1));
					}
					if (current.y != (mapView.yTileLength - 1)) {
						queue.add(new BSFnode(current.x, current.y + 1,
								current.v - 1));
					}
					if (current.y != 0) {
						queue.add(new BSFnode(current.x, current.y - 1,
								current.v - 1));
					}
				} else if (mapView.map[current.x][current.y].unit != null) {

					if (attacksWho[mapView.map[current.x][current.y].unit.loyalty]) {
						aoe.enemyEffectingAOE(mapView.map[current.x][current.y].unit);
					} else {
						aoe.allyEffectingAOE(mapView.map[current.x][current.y].unit);
					}

					if (current.x != (mapView.xTileLength - 1)) {
						queue.add(new BSFnode(current.x + 1, current.y,
								current.v - 1));
					}
					if (current.x != 0) {
						queue.add(new BSFnode(current.x - 1, current.y,
								current.v - 1));
					}
					if (current.y != (mapView.yTileLength - 1)) {
						queue.add(new BSFnode(current.x, current.y + 1,
								current.v - 1));
					}
					if (current.y != 0) {
						queue.add(new BSFnode(current.x, current.y - 1,
								current.v - 1));
					}
				} else {
					if (current.x != (mapView.xTileLength - 1)) {
						queue.add(new BSFnode(current.x + 1, current.y,
								current.v - 1));
					}
					if (current.x != 0) {
						queue.add(new BSFnode(current.x - 1, current.y,
								current.v - 1));
					}
					if (current.y != (mapView.yTileLength - 1)) {
						queue.add(new BSFnode(current.x, current.y + 1,
								current.v - 1));
					}
					if (current.y != 0) {
						queue.add(new BSFnode(current.x, current.y - 1,
								current.v - 1));
					}
				}

			}
		}
		resetAllAttackValues(m.x, m.y, aoe.rangeOfAOE());
	}

	protected abstract float getLevel();

	protected abstract void dealWithUnconsciousUnit(MapNode node);

	protected abstract int mapID();

	protected abstract void levelStartInstructions();

	protected abstract void specificRoundEndInstructions();

	protected abstract void aiFollowLevelSpecificRules(
			NonPlayableCharacterNode tp);

	protected abstract void changeableNormalPerformance();

	protected abstract String normalChangeableText();

}
