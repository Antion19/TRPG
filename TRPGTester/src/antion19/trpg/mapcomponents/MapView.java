package antion19.trpg.mapcomponents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import antion19.trpg.game_main.GameHelper;

/**
 * 
 * @author andrewleinbach
 * 
 */
public class MapView extends View {

	/**
	 * 
	 */
	public static final int imageDimension = 100;

	public Paint paintMove = null;

	public Paint paintAttack = null;
	
	public Paint paintHeal = null;

	public Paint paintSteal = null;

	/**
	 * 
	 */
	public MapNode[][] map;

	/**
	 * 
	 */
	public int xTileLength;

	/**
	 * 
	 */
	public int yTileLength;

	/**
	 * 
	 * @param context
	 */
	public MapView(Context context) {
		super(context);
		init();
	}

	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public MapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 
	 */
	public void init() {
		setFocusable(true);
		setFocusableInTouchMode(true);
		paintMove = new Paint();
		paintMove.setARGB(100, 200, 0, 0);
		paintMove.setStyle(Paint.Style.FILL);

		paintAttack = new Paint();
		paintAttack.setARGB(100, 0, 0, 200);
		paintAttack.setStyle(Paint.Style.FILL);
		
		paintHeal = new Paint();
		paintHeal.setARGB(100, 0, 200, 0);
		paintHeal.setStyle(Paint.Style.FILL);
		
		paintSteal = new Paint();
		paintSteal.setARGB(100, 150, 0, 200);
		paintSteal.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		for (int i = 0; i < yTileLength; i++) {
			for (int j = 0; j < xTileLength; j++) {
				canvas.drawBitmap(map[j][i].tile.getImage(),
						j * imageDimension, i * imageDimension, null);
				if (map[j][i].moveValue != 0) {
					canvas.drawRect(j * imageDimension, i * imageDimension,
							(j + 1) * imageDimension, (i + 1) * imageDimension,
							paintMove);
				} else if (map[j][i].attackValue == 200) {
					canvas.drawRect(j * imageDimension, i * imageDimension,
							(j + 1) * imageDimension, (i + 1) * imageDimension,
							paintAttack);
				} else if (map[j][i].attackValue == 300) {
					canvas.drawRect(j * imageDimension, i * imageDimension,
							(j + 1) * imageDimension, (i + 1) * imageDimension,
							paintHeal);
				} else if (map[j][i].attackValue == 400) {
					canvas.drawRect(j * imageDimension, i * imageDimension,
							(j + 1) * imageDimension, (i + 1) * imageDimension,
							paintSteal);
				}
				
				if (map[j][i].unit != null) {
					canvas.drawBitmap(map[j][i].unit.job.getImage(), j
							* imageDimension, i * imageDimension, null);
				}
			}
		}

	}

	/**
	 * 
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(xTileLength * imageDimension, yTileLength
				* imageDimension);
	}

	/**
	 * 
	 * @param context
	 * @param rawResource
	 */
	public void initialize(Context context, int rawResource) {
		// for use making Map2DArray
		String mapString;
		ArrayList<String> lines = new ArrayList<String>();

		InputStream is = context.getResources().openRawResource(rawResource);
		mapString = convertStreamToString(is);

		Scanner scanner = new Scanner(mapString);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			// no more lines to read
			if (line == null) {
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
			}
		}
		scanner.close();
		xTileLength = lines.get(0).length();
		yTileLength = lines.size();

		map = new MapNode[xTileLength][yTileLength];

		for (int i = 0; i < yTileLength; i++) {
			String line = lines.get(i);
			for (int j = 0; j < xTileLength; j++) {
				char ch = line.charAt(j);
				map[j][i] = new MapNode();
				map[j][i].tile = GameHelper.chooseTile(ch, context);

			}
		}

	}

	/**
	 * 
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 */
	public void moveCharacter(int startX, int startY, int endX, int endY) {
		map[endX][endY].unit = map[startX][startY].unit;
		map[startX][startY].unit = null;
		System.out.println(map[endX][endY].unit.name + " moved from x = "
				+ startX + " and y = " + startY + " to x = " + endX
				+ " and y = " + endY);

		invalidate();
	}

	/**
	 * 
	 * @param is
	 * @return
	 */
	private String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append((line + "\n"));
			}
		} catch (IOException e) {
			Log.w("LOG", e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.w("LOG", e.getMessage());
			}
		}
		return sb.toString();
	}

}
