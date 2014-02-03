package antion19.trpg.mapcomponents;

/**
 * 
 * @author andrewleinbach
 *
 */
public class NonPlayableCharacterNode extends CharacterNode {

	/**
	 * 
	 */
	public int aiType;
	
	public int[] specialTargets;
	
	public CharacterNode targetLocation;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param aiType
	 */
	public NonPlayableCharacterNode(int x, int y, int aiType, int[] specialTargets, CharacterNode targetLocation) {
		super(x, y);
		this.aiType = aiType;
		this.specialTargets = specialTargets;
		this.targetLocation = targetLocation;
	}

}
