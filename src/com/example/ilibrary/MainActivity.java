package com.example.ilibrary;

import com.example.order.OrderNoticeActivity;
import com.example.overdue.OverDueNoticeActivity;
import com.example.search.SearchResource;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends Activity {
	private ImageButton yytz;
	private ImageButton chtz;
	private ImageButton tsjs;
	private ImageButton add;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (!isNetworkConnected(this)) {
			Toast toast2 = Toast.makeText(getApplicationContext(), "请检查网络!",
					Toast.LENGTH_SHORT);
			toast2.show();
		}

		

		yytz = (ImageButton) findViewById(R.id.yytz);
		yytz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						OrderNoticeActivity.class);
				startActivity(intent);

			}
		});
		
		chtz = (ImageButton) findViewById(R.id.chtz);
		chtz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						OverDueNoticeActivity.class);
				startActivity(intent);

			}
		});
		tsjs = (ImageButton) findViewById(R.id.tsjs);
		tsjs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, SearchResource.class);
					startActivity(intent);
				
			}
		});
		add = (ImageButton)findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "更多功能 尽请期待", 3000).show();
			}
		});
	}

	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
