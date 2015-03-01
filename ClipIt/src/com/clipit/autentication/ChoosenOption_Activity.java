package com.clipit.autentication;

import java.util.Timer;
import java.util.TimerTask;


import com.clipit.menu.Inbox_Activity;
import com.example.clipit.R;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class ChoosenOption_Activity extends Activity {
	/*
	 * Here we have the variables for the image and text switcher. we use 2
	 * arrays, one for the text and other for pictures
	 */
	private static final String[] TEXTS = {
			"Aplicacion de mensajeria del proyecto europeo Yuxtalearn",
			"Chatea con tus compañeros para poneros de acuerdo en las tareas a realizar",
			"Comparte ideas entre tus compañeros y sobre todo aprende" };
	private static final int[] IMAGES = { R.drawable.logo_jxl,
			R.drawable.chatea, R.drawable.comparte };
	private int mPosition = 0;
	private TextSwitcher mTextSwitcher;
	private ImageSwitcher mImageSwitcher;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences sp = getSharedPreferences("userpreferences", Context.MODE_PRIVATE);
		//we see if we have a cookie in our sharepreferences.If true we take the items of the cookie and we start the session automatically
		if(sp.contains("cookie_session")){
			String user_name = sp.getString("user_name", null);
			String token = sp.getString("token_id", null);
			String user_id=sp.getString("user_id", null);
		
			Intent i = new Intent(this,Inbox_Activity.class );
	//We send the intent with the extras of the items taken.
			i.putExtra("user_name", user_name);
			i.putExtra("token_id", token);
			i.putExtra("user_id", user_id);
			startActivity(i);
			finish();
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);// FullScreen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // FullScreen
		setContentView(R.layout.activity_choosen_option_);
		mTextSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
		/*
		 * We create the view for the text switcher
		 */
		mTextSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				TextView textView = new TextView(ChoosenOption_Activity.this);
				textView.setGravity(Gravity.CENTER);
				return textView;
			}
		});
		/* we set the animation for the view */
		mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
		mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);

		mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
		/*
		 * We create the view for the image switcher
		 */
		mImageSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				ImageView imageView = new ImageView(ChoosenOption_Activity.this);
				return imageView;
			}
		});
		/* we set the animation for the view */
		mImageSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
		mImageSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);
		mTextSwitcher.setText(TEXTS[mPosition]);
		mImageSwitcher.setBackgroundResource(IMAGES[mPosition]);
		/*We create 2 threads for using the differents views, adding a position
		 * in the arrays created.
		 */
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(6000);

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mPosition++;
								mTextSwitcher.setText(TEXTS[mPosition]);
								mImageSwitcher
										.setBackgroundResource(IMAGES[mPosition]);

							}
						});

					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			};
		};
		thread.start();

		Thread thread1 = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(12000);

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mPosition++;
								mTextSwitcher.setText(TEXTS[mPosition]);
								mImageSwitcher
										.setBackgroundResource(IMAGES[mPosition]);

							}
						});

					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			};
		};
		thread1.start();
		/*
		 * We assign to the button the power of going to the next activity or screen
		 */
		Button login=(Button)findViewById(R.id.login);
		Button register=(Button)findViewById(R.id.register);
		login.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(ChoosenOption_Activity.this,Login_Activity.class);
				startActivity(i);
				//finish();
			}
		} );
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(ChoosenOption_Activity.this,Register_Activity.class);
				startActivity(i);
				//finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choosen_option_, menu);
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
