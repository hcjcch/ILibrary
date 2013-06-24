package com.example.search;

import com.example.ilibrary.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SearchResource extends Activity{

	private Spinner jiansuocileixingSpinner = null;
	private EditText jiansuociEditText = null;
	private Spinner pipeifangshiSpinner = null;
	private Spinner ziliaoleixingSpinner = null;
	private Spinner fenguanmingchenSpinner = null;
	private String jiansuocistring = null;
	private String jiansuocileixingstring = null;
	private String pipeifangshiString = null;
	private String ziliaoleixingstring = null;
	private String fenguanmingchenString = null;
	private Button kaishijiansuoButton = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_resource);
		
		jiansuocileixingSpinner = (Spinner)findViewById(R.id.jiansuocileixing2);
		jiansuociEditText = (EditText)findViewById(R.id.jiansuoci2);
		pipeifangshiSpinner = (Spinner)findViewById(R.id.pipeifangshi2);
		ziliaoleixingSpinner = (Spinner)findViewById(R.id.ziliaoleixing2);
	    fenguanmingchenSpinner = (Spinner)findViewById(R.id.fenguanmingchen2);
	    kaishijiansuoButton = (Button)findViewById(R.id.startjiansuo);
	    
	    
	    jiansuocileixingSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				jiansuocileixingstring = arg0.getItemAtPosition(arg2).toString();
				if(jiansuocileixingstring.equals("所有题名"))
					jiansuocileixingstring = "title";
				else if(jiansuocileixingstring.equals("著/作者"))
					jiansuocileixingstring = "author";
				else if(jiansuocileixingstring.equals("标准号(ISBN或ISSN)"))
					jiansuocileixingstring = "number";
				else if(jiansuocileixingstring.equals("主题词"))
					jiansuocileixingstring = "subject_term";
				else if(jiansuocileixingstring.equals("出版年"))
					jiansuocileixingstring = "publish_year";
				else if(jiansuocileixingstring.equals("出版社"))
					jiansuocileixingstring = "publisher";
				else if(jiansuocileixingstring.equals("分类号"))
					jiansuocileixingstring = "call_no";
				else if(jiansuocileixingstring.equals("题名缩拼"))
					jiansuocileixingstring = "title_pinyi";
				else
					jiansuocileixingstring = "book_barcode";
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    pipeifangshiSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				pipeifangshiString = arg0.getItemAtPosition(arg2).toString();
				if(pipeifangshiString.equals("前向匹配"))
					pipeifangshiString ="qx";
				else if(pipeifangshiString.equals("模糊匹配"))
					pipeifangshiString ="mh";
				else pipeifangshiString = "jq";
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    ziliaoleixingSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	    	
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				ziliaoleixingstring = arg0.getItemAtPosition(arg2).toString();
				if(ziliaoleixingstring.equals("全部"))
					ziliaoleixingstring = "all";
				else 
					ziliaoleixingstring = "0"+String.valueOf(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    fenguanmingchenSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String a ="所有分馆";
				fenguanmingchenString = arg0.getItemAtPosition(arg2).toString();
				if(fenguanmingchenString.equals(a))
					fenguanmingchenString="all";
				else 
					fenguanmingchenString = fenguanmingchenString.substring(0,1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    kaishisearch();
	}
	
	public void kaishisearch(){
	    kaishijiansuoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jiansuocistring = jiansuociEditText.getText().toString();
				if(jiansuocistring.length()>1){
					Intent intent = new Intent();
					intent.putExtra("a",jiansuocistring);
					intent.putExtra("b",jiansuocileixingstring);
					intent.putExtra("c",pipeifangshiString);
					intent.putExtra("d",ziliaoleixingstring);
					intent.putExtra("f",fenguanmingchenString);
					intent.setClass(SearchResource.this,SearchResult.class);
					startActivity(intent);
				}
				else {
					Toast toast = Toast.makeText(getApplicationContext(), "检索词过短,请重新输入!", Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});
	}
}
