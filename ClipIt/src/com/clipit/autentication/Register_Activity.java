package com.clipit.autentication;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.clipit.menu.Inbox_Activity;
import com.example.clipit.R;
import com.example.clipit.R.id;
import com.example.clipit.R.layout;
import com.example.clipit.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter.LengthFilter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Register_Activity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);// FullScreen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // FullScreen
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_);
		
		
		Button registerButton=(Button)findViewById(R.id.register);
		final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar2);
		final EditText ur=(EditText)findViewById(R.id.user1);
		final EditText pss=(EditText)findViewById(R.id.password1);
		registerButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pb.setVisibility(View.VISIBLE);
				pb.setMax(100);
				pb.setProgress(0);
				String user=ur.getText().toString();
				String password=pss.getText().toString();
				//I create a token object and then I call to taketokenAdmin method
				TokenClass tknclass=new TokenClass(user,password,getApplicationContext());
				try {
					//We take the admin xmltoken to create a new user
					String xmlTokenAdmin=tknclass.takeTokenAdmin();
					//We take the admin token
					tknclass.toParserXML(xmlTokenAdmin);
					//We see if the user has already been registered
					if(tknclass.getIdByLogin().equalsIgnoreCase("NOTFOUND")){
					//We create a user
					tknclass.createUser();
					//Take the user xml token
					String xmlToken=tknclass.takeToken();
					//Take the user token
					tknclass.toParserXML(xmlToken);
					//We take the id of the user 
					tknclass.getIdByLogin();
					//We save the preferences to send it by Intent
					tknclass.savePreferences();
					Intent i = new Intent(Register_Activity.this,Inbox_Activity.class );
					i.putExtra("user_name", tknclass.getUser());
					i.putExtra("token_id", TokenClass.token_string);
					i.putExtra("user_id", TokenClass.id_user);
					startActivity(i);	}
					else{
						pb.setVisibility(View.INVISIBLE);
						Toast registered =Toast.makeText(getApplicationContext(),"El usuario ya esta registrado",Toast.LENGTH_LONG);
						registered.show();
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		});
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
