package antion19.trpg.units;

import java.util.Random;
import android.support.v4.app.FragmentActivity;

public class StealNode {

	Unit baseUnit;
	
	public static int q;

	public StealNode(Unit baseUnit) {
		this.baseUnit = baseUnit;
	}

	public boolean canStealFrom(Unit foeToStealFrom) {
		if (foeToStealFrom.items != null && foeToStealFrom.items.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void chooseItemToTryAndSteal(FragmentActivity activity, Unit foeToStealFrom) {
		q = 0;
		
		String[] itemStrings = new String[foeToStealFrom.items.size()];
		for (int i = 0; i < foeToStealFrom.items.size(); i++) {
			itemStrings[i] = foeToStealFrom.items.get(i).toString();
		}
		
		StealChoiceDialog stealChoiceDialog = StealChoiceDialog.newInstance(itemStrings);
		stealChoiceDialog.show(activity.getSupportFragmentManager(), "s");
	}

	public void stealMostValuableItem(Unit foeToStealFrom) {

		q = 0;

		for (int m = 0; m < foeToStealFrom.items.size(); m++) {
			if (foeToStealFrom.items.get(m).price > foeToStealFrom.items.get(q).price) {
				q = m;
			}
		}

		baseUnit.items.add(foeToStealFrom.items.get(q));
		foeToStealFrom.items.remove(q);

	}

	public void stealRandomItem(Unit foeToStealFrom) {
		Random r = new Random();
		q = r.nextInt(foeToStealFrom.items.size());
		baseUnit.items.add(foeToStealFrom.items.get(q));
		foeToStealFrom.items.remove(q);
	}

	public boolean stealSuccessful(Unit foeToStealFrom) {
		Random r = new Random();

		int i = 60 + 2 * baseUnit.skill - 2 * foeToStealFrom.skill;
		int j = r.nextInt(100);
		if (j <= i) {
			return true;
		} else {
			return false;
		}

	}

}
