package com.clipit.menu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clipit.chat.ListInbox;
import com.example.clipit.R;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

	Context context;
	List<DrawerItem> drawerItemList;
	int layoutResID;

	public CustomDrawerAdapter(Context context, int layoutResourceID,
			List<DrawerItem> listItems) {
		super(context, layoutResourceID, listItems);
		this.context = context;
		this.drawerItemList = listItems;
		this.layoutResID = layoutResourceID;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		DrawerItemHolder drawerHolder;
		View view = convertView;
		String user_name=null;
		String token=null;
		String user_id=null;
		SharedPreferences sp =getContext().getSharedPreferences("userpreferences", Context.MODE_PRIVATE);
		if(sp.contains("cookie_session")){
			user_name = sp.getString("user_name", null);
			token = sp.getString("token_id", null);
			user_id=sp.getString("user_id", null);}
		if (view == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			drawerHolder = new DrawerItemHolder();

			view = inflater.inflate(layoutResID, parent, false);
			drawerHolder.ItemName = (TextView) view
					.findViewById(R.id.drawer_itemName);
			drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

			drawerHolder.spinner = (Spinner) view
					.findViewById(R.id.drawerSpinner);

			drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);

			drawerHolder.headerLayout = (LinearLayout) view
					.findViewById(R.id.headerLayout);
			drawerHolder.itemLayout = (LinearLayout) view
					.findViewById(R.id.itemLayout);
			drawerHolder.spinnerLayout = (LinearLayout) view
					.findViewById(R.id.spinnerLayout);

			view.setTag(drawerHolder);

		} else {
			drawerHolder = (DrawerItemHolder) view.getTag();

		}
		
		DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

		if (dItem.isSpinner()) {
			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.spinnerLayout.setVisibility(LinearLayout.VISIBLE);

			List<SpinnerItem> userList = new ArrayList<SpinnerItem>();

			userList.add(new SpinnerItem(R.drawable.user1, user_name));
			/*userList.add(new SpinnerItem(R.drawable.user2, "Brain Jekob",
					"brain.j@gmail.com"));*/

			CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context,
					R.layout.custom_spinner_item, userList);

			drawerHolder.spinner.setAdapter(adapter);

			drawerHolder.spinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							Toast.makeText(context, "User Changed",
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});

		} else if (dItem.getTitle() != null) {
			drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.title.setText(dItem.getTitle());
			Log.d("Getview","Passed4");
		} else {
			
			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);

			drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
					dItem.getImgResID()));
			drawerHolder.ItemName.setText(dItem.getItemName());
			Log.d("Getview","Passed5");
		}
		return view;
	}

	private static class DrawerItemHolder {
		TextView ItemName, title;
		ImageView icon;
		LinearLayout headerLayout, itemLayout, spinnerLayout;
		Spinner spinner;
	}
}