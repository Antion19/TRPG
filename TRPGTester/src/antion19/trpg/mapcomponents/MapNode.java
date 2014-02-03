package antion19.trpg.mapcomponents;

import java.util.ArrayList;

import antion19.trpg.item.Item;
import antion19.trpg.tiles.Tile;
import antion19.trpg.units.Unit;

/**
 * 
 * @author andrewleinbach
 * 
 */
public class MapNode {

	/**
	 * 
	 */
	public int attackValue = 0;

	/**
	 * 
	 */
	public int moveValue = 0;

	/**
	 * 
	 */
	public Tile tile = null;

	/**
	 * 
	 */
	public Unit unit = null;

	/**
	 * 
	 */
	public ArrayList<Item> items = null;

}
