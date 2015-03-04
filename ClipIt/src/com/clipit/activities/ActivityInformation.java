package com.clipit.activities;

import com.example.clipit.R;
import com.example.clipit.R.id;
import com.example.clipit.R.layout;
import com.example.clipit.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityInformation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_information);
		Activities actividad=(Activities)this.getIntent().getSerializableExtra("ConversationWithUser");
		setTitle("Actividad-"+actividad.getNameActivities());
		//getActionBar().setIcon(R.drawable.logo_jxl);
		TextView tituloActividad=(TextView)findViewById(R.id.nombreActividad);
		TextView descripcionActividad=(TextView)findViewById(R.id.descripcionActividad);
		tituloActividad.setText(actividad.getNameActivities().toUpperCase());
		descripcionActividad.setText(actividad.getDescription());
		 getActionBar().setHomeButtonEnabled(true);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_information, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		switch (item.getItemId()) {
        case android.R.id.home:
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
	}
}
