package com.clipit.menu;

import java.io.IOException;
import java.util.ArrayList;


import com.clipit.autentication.ChoosenOption_Activity;
import com.clipit.autentication.Register_Activity;
import com.clipit.chat.ChatsMethods;
import com.example.clipit.R;
import com.example.clipit.R.id;
import com.example.clipit.R.layout;
import com.example.clipit.R.menu;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.jdom2.JDOMException;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Inbox_Activity extends Activity {

	private int closeSesion=0;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inbox_);

		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Add Drawer Item to dataList
		dataList.add(new DrawerItem(true)); // adding a spinner to the list

		dataList.add(new DrawerItem("Opciones Principales")); // adding a header to the list
		dataList.add(new DrawerItem("Mensajes Directos", R.drawable.ic_action_chat));
		dataList.add(new DrawerItem("Actividades", R.drawable.ic_action_group));
		dataList.add(new DrawerItem("Grupos", R.drawable.ic_action_email));
		//dataList.add(new DrawerItem("Lables", R.drawable.ic_action_labels));

		// dataList.add(new DrawerItem("Opciones Segundarias"));// adding a header to the list
			//dataList.add(new DrawerItem("Likes", R.drawable.ic_action_good));
			//dataList.add(new DrawerItem("Favoritos", R.drawable.ic_action_gamepad));
		 /*dataList.add(new DrawerItem("Search", R.drawable.ic_action_search));
		dataList.add(new DrawerItem("Cloud", R.drawable.ic_action_cloud));
		dataList.add(new DrawerItem("Camara", R.drawable.ic_action_camera));
		dataList.add(new DrawerItem("Video", R.drawable.ic_action_video));
		dataList.add(new DrawerItem("Groups", R.drawable.ic_action_group));
		dataList.add(new DrawerItem("Import & Export",
				R.drawable.ic_action_import_export));*/

		 dataList.add(new DrawerItem("Otras")); // adding a header to the list
		dataList.add(new DrawerItem("Acerca de", R.drawable.ic_action_about));
		dataList.add(new DrawerItem("Opciones", R.drawable.ic_action_settings));
		dataList.add(new DrawerItem("Ayuda", R.drawable.ic_action_help));
		dataList.add(new DrawerItem("Cerrar sesion",R.drawable.ic_action_back));

		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		this.getActionBar().setDisplayHomeAsUpEnabled(true);
	this.getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(Inbox_Activity.this, mDrawerLayout,
			R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {

			if (dataList.get(0).isSpinner()
					& dataList.get(1).getTitle() != null) {
				SelectItem(2);
			} else if (dataList.get(0).getTitle() != null) {
				SelectItem(1);
			} else {
				SelectItem(0);
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void SelectItem(int possition) {
		String user_name=null;
		String token=null;
		String user_id=null;
		Fragment fragment = null;
		Bundle args = new Bundle();
		SharedPreferences sp = getSharedPreferences("userpreferences", Context.MODE_PRIVATE);
		if(sp.contains("cookie_session")){
			user_name = sp.getString("user_name", null);
			token = sp.getString("token_id", null);
			user_id=sp.getString("user_id", null);}
		switch (possition) {

		case 2:
			
			fragment = new FragmentThree();
			break;
		case 3:
			fragment = new FragmentTwo();
			
			break;
		case 4:
			fragment = new FragmentTwo();
			
			break;
		/*case 5:
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(possition).getImgResID());
			break;*/
		case 6:
			fragment = new FragmentTwo();
			
			break;
		case 7:
			fragment = new FragmentThree();
			/*args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(possition).getImgResID());*/
			break;
		/*case 9:
			fragment = new FragmentOne();
			args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 10:
			fragment = new FragmentTwo();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 11:
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(possition).getImgResID());
			break;
		case 12:
			fragment = new FragmentOne();
			args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;*/
	/*	case 9:
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;	*/
		case 10:
			fragment = new FragmentOne();
			args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 11:
			fragment = new FragmentTwo();
			
			break;
		case 9:
			
			sp.edit().remove("cookie_session").commit();
			Intent i= new Intent(Inbox_Activity.this,ChoosenOption_Activity.class);
			startActivity(i);
			closeSesion=1;
			
			break;
		default:
			break;
		}

		if(closeSesion==0){
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment)
				.commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);}
		else{
			finish();
		}
		

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (dataList.get(position).getTitle() == null) {
				SelectItem(position);
			}

		}
	}
	
	@Override
	public void onBackPressed(){
		finish();
		return;
	}
}
