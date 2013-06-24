package com.example.search;

import com.example.ilibrary.MainActivity;
import com.example.ilibrary.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class searchBook extends Activity {

	
	private Button button = null;
	private SimpleAdapter simpleAdapter;
	private ListView listView = null;
	private ProgressDialog progressDialog;
	private Handler myHandler;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.borrow_book);
		listView = (ListView) findViewById(R.id.borrow_xinxi);
		button = (Button) findViewById(R.id.fanhui);
		
		Intent intent =getIntent();
		
		textView1 = (TextView)findViewById(R.id.text1);
		textView1.setText(intent.getStringExtra("name"));
		textView2 = (TextView)findViewById(R.id.text2);
		textView2.setText(intent.getStringExtra("author"));
		textView3 = (TextView)findViewById(R.id.text3);
		textView3.setText(intent.getStringExtra("pulisher"));
		textView4 = (TextView)findViewById(R.id.text4);
		textView4.setText(intent.getStringExtra("year"));
		progressDialog = new ProgressDialog(searchBook.this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("≈¨¡¶º”‘ÿ÷–£°");
		progressDialog.setIndeterminate(false);
		
		myHandler = new Handler() {
			@SuppressLint("ShowToast")
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					progressDialog.show();
				} else if (msg.what == 1) {

					simpleAdapter = new SimpleAdapter(searchBook.this,
							borrowBook.borrowBookList,
							R.layout.borrow_booklist, new String[] { "Tstm",
									"Ssh", "Csbm", "Ltzt" }, new int[] {
									R.id.tushutiaoma, R.id.suoshuhao,
									R.id.cangshubumen, R.id.liutongzhuangtai });
					listView.setAdapter(simpleAdapter);

					progressDialog.hide();

				} else if (msg.what == 2) {

					progressDialog.hide();

				}
			}
		};

		new Thread(new myThread()).start();
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(searchBook.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}

	class myThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			myHandler.sendEmptyMessage(0);
			Intent intent = getIntent();
			String id = intent.getStringExtra("id");
			borrowBook book = new borrowBook();
			book.startBorrowBook(id);
			myHandler.sendEmptyMessage(1);
		}

	}
}
