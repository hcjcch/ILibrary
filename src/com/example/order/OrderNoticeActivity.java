package com.example.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ilibrary.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class OrderNoticeActivity extends Activity {
	private Button searchButton;
	private ProgressDialog progressDialog;
	private Handler myHandler;
	private ListView listView;
	private List<Map<String,String>> list;
	private boolean isException = false;
	
	@SuppressLint("HandlerLeak")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_ordernotice);
		
		listView = (ListView)findViewById(R.id.listView);
		
		searchButton = (Button)findViewById(R.id.search);
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSearchRequested();	
			}
		});
			
		
		progressDialog = new ProgressDialog(OrderNoticeActivity.this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("努力加载中！");
		progressDialog.setIndeterminate(false);
		
		
		myHandler = new Handler() {
			@SuppressLint("ShowToast")
			public void handleMessage(Message msg) {
				if(msg.what==0){
					progressDialog.show();
				}else if(msg.what==1){
					SimpleAdapter adapter = new SimpleAdapter(
							OrderNoticeActivity.this, list, R.layout.order,
							new String[] { "dztm", "dzxm", "tstitle", "tstm",
									"qsdd", "jzrq" }, new int[] { R.id.dztm,
									R.id.dzxm, R.id.tstitle, R.id.tstm,
									R.id.qsdd, R.id.jzrq });
					listView.setAdapter(adapter);					
					progressDialog.hide();
				
				}else if(msg.what==2){
					String[] strs = {""};
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrderNoticeActivity.this, R.layout.simple_selectable_list_item, strs);
					listView.setAdapter(adapter);
					progressDialog.hide();
					if(isException){
						Toast.makeText(OrderNoticeActivity.this, "网络异常 或 无连接", 3000).show();
						isException = false;
					}else{
						Toast.makeText(OrderNoticeActivity.this, "无结果", 3000).show();
					}
					
				}
			}
		};
		
	}
	
	private void handleSearchQuery(Intent queryIntent) throws Exception  {
		final String queryAction = queryIntent.getAction();
		if (Intent.ACTION_SEARCH.equals(queryAction)) {
			onSearch(queryIntent);
		}
	}
	
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		try {
			handleSearchQuery(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void onSearch(final Intent intent) throws Exception {
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				list = null;
				myHandler.sendEmptyMessage(0);
				final String queryString = intent.getStringExtra(SearchManager.QUERY);
				Map<String, String> params = new HashMap<String, String>();
				params.put("barcode", queryString);
				params.put("fangshi", "1");
				params.put("title", "");
				String result = null;
				try {
					result = OrderInfo.sendPostMessage(params, "GB2312");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					isException = true;
					myHandler.sendEmptyMessage(2);
					
				}
				
				if(result!=null){
					int a;
					int b;
					if((a=result.indexOf("<br><table"))==-1){
						myHandler.sendEmptyMessage(2);
					}else{
						b = result.indexOf("</table>", a);
						list = OrderInfo.getNotice(result.substring(a, b));
						myHandler.sendEmptyMessage(1);
					}
				}
				
			}

		}).start();
		
	}

}
	

	

