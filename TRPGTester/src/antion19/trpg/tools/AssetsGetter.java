package antion19.trpg.tools;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;

/**
 * 
 * @author andrewleinbach
 *
 */
public class AssetsGetter {

	/**
	 * 
	 */
	public AssetsGetter() {
	}

	/**
	 * 
	 * @param context
	 * @param fileName
	 * @param value
	 * @return
	 */
	public static Bitmap getImageAsset(Context context,
			String fileName, int value) {
		
		Bitmap image;
		
		Options options = new Options();
		
		switch (value) {

		case 1:
			options.inPreferredConfig = Config.RGB_565;
			break;
		case 2:
			options.inPreferredConfig = Config.ARGB_8888;
			break;
		case 3:
			options.inPreferredConfig = Config.ARGB_4444;
			break;
		default:
			System.out.println("Error: Invalid value");
			System.exit(1);
		}
			
		InputStream in = null;
		try {

			in = context.getAssets().open(fileName);
			image = BitmapFactory.decodeStream(in, null, options);
			if (image == null)
				throw new RuntimeException("Couldn't load bitmap from asset '"
						+ fileName + "'");
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load bitmap from asset '"
					+ fileName + "'");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return image;
	}
}
