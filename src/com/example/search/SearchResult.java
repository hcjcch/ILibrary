package com.example.search;

import java.util.Map;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResult extends Activity {
	private Button formeButton = null;
	private Button nextButton = null;
	private TextView pageView = null;
	private ListView bookView = null;
	private ProgressDialog progressDialog;
	private Handler myHandler;

	public String jiansuocistring1;
	public String jiansuocileixingstring1;
	public String fenguanmingchenString1;
	public String pipeifangshiString1;
	public String ziliaoleixingstring1;
	public String pagenumber = "1";
	public String pageNumber;
	private SimpleAdapter simpleAdapter = null;

	public SearchResult() {

	}

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);
		bookView = (ListView) findViewById(R.id.booklist);
		formeButton = (Button) findViewById(R.id.forme);
		nextButton = (Button) findViewById(R.id.next);
		pageView = (TextView) findViewById(R.id.page);

		Intent intent = getIntent();
		jiansuocistring1 = intent.getStringExtra("a");
		jiansuocileixingstring1 = intent.getStringExtra("b");
		pipeifangshiString1 = intent.getStringExtra("c");
		ziliaoleixingstring1 = intent.getStringExtra("d");
		fenguanmingchenString1 = intent.getStringExtra("f");

		progressDialog = new ProgressDialog(SearchResult.this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("努力加载中！");
		progressDialog.setIndeterminate(false);

		myHandler = new Handler() {
			@SuppressLint("ShowToast")
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					progressDialog.show();
				} else if (msg.what == 1) {

					simpleAdapter = new SimpleAdapter(
							SearchResult.this,
							Search.bookinformation,
							R.layout.resultlist,
							new String[] { "name", "author", "pulisher", "year" },
							new int[] { R.id.name, R.id.author, R.id.location,
									R.id.year });
					bookView.setAdapter(simpleAdapter);
					pageView.setText(pagenumber+"/"+Search.pagenumber);

					progressDialog.hide();
					if (Search.pagesum == 0) {
						Toast toast = Toast.makeText(
								SearchResult.this.getApplicationContext(),
								"对不起，查询无果!", Toast.LENGTH_LONG);
						toast.show();
					}

				} else if (msg.what == 2) {

					progressDialog.hide();

				}
			}
		};

		new Thread(new myThread()).start();

		formeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Integer.parseInt(pagenumber) == 1) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"当前已经是第一页", Toast.LENGTH_SHORT);
					toast.show();
				} else {
					formepage();
					pageView.setText(pagenumber+"/"+Search.pagenumber);
				}
			}
		});

		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Integer.parseInt(pagenumber) == Search.pagenumber) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"当前已经最后页", Toast.LENGTH_SHORT);
					toast.show();
				} else {
					nextpage();
					pageView.setText(pagenumber+"/"+Search.pagenumber);
				}
			}
		});
		bookView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) simpleAdapter
						.getItem(arg2);
				String id = map.get("id");
				Intent intent = new Intent();
				intent.putExtra("id", id);
				intent.putExtra("name", map.get("name"));
				intent.putExtra("author", map.get("author"));
				intent.putExtra("pulisher", map.get("pulisher"));
				intent.putExtra("year", map.get("year"));
				intent.setClass(SearchResult.this, searchBook.class);
				startActivity(intent);
			}
		});

	}

	public void nextpage() {
		pagenumber = String.valueOf(Integer.parseInt(pagenumber) + 1);
		new Thread(new myThread()).start();
	}

	public void formepage() {
		pagenumber = String.valueOf(Integer.parseInt(pagenumber) - 1);
		new Thread(new myThread()).start();
	}

	class myThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			myHandler.sendEmptyMessage(0);
			Search searchbook = new Search(jiansuocistring1,
					jiansuocileixingstring1, pipeifangshiString1,
					ziliaoleixingstring1, fenguanmingchenString1, pagenumber);
			searchbook.startsearch();
			myHandler.sendEmptyMessage(1);
		}

	}

}
