package list.mquestioner;

import java.util.ArrayList;

import com.inobu.mquestionair.R;
import com.inobu.mquestionair.R.id;
import com.inobu.mquestionair.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * adapter to populate listview with data
 * 
 * @author ketan(Visit my <a
 *         href="http://androidsolution4u.blogspot.in/">blog</a>)
 */
public class DisplayAdapterLahan extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> firstName;
	private ArrayList<String> lastName;
	private ArrayList<String> addr;

	public DisplayAdapterLahan(Context c, ArrayList<String> id,
			ArrayList<String> fname, ArrayList<String> lname,
			ArrayList<String> add) {
		this.mContext = c;

		this.id = id;
		this.firstName = fname;
		this.lastName = lname;
		this.addr = add;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return id.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View child, ViewGroup parent) {
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.row_lahan, null);
			mHolder = new Holder();
			mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
			mHolder.txt_fName = (TextView) child.findViewById(R.id.txt_fName);
			mHolder.txt_lName = (TextView) child.findViewById(R.id.txt_lName);
			mHolder.txt_lAddr = (TextView) child.findViewById(R.id.txt_lAddr);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.txt_id.setText(id.get(pos));
		mHolder.txt_fName.setText(firstName.get(pos));
		mHolder.txt_lName.setText(lastName.get(pos));
		mHolder.txt_lAddr.setText(addr.get(pos));

		return child;
	}

	public class Holder {
		TextView txt_id;
		TextView txt_fName;
		TextView txt_lName;
		TextView txt_lAddr;
	}

}
