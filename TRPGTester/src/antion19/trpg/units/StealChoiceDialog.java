package antion19.trpg.units;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import antion19.trpg.R;

public class StealChoiceDialog extends DialogFragment {

	ArrayAdapter<String> adapter = null;

	public static StealChoiceDialog newInstance(String[] i) {
		StealChoiceDialog dialog = new StealChoiceDialog();

		Bundle args = new Bundle();
		args.putStringArray("i", i);
		dialog.setArguments(args);
		return dialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String[] tempString = getArguments().getStringArray("i");

		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, tempString);

		setStyle(DialogFragment.STYLE_NO_FRAME, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.dialog_stealing, container, false);

		ListView lv = (ListView) v.findViewById(R.id.stealList);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				StealNode.q = position;
				dismiss();
			}
		});

		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
