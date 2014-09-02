package com.cheng.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ss.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	SQLiteDatabase db;
	ListView listview;
	private boolean result = false;
	
	DBManager manager;
	// 存储数据的数组列表
	ArrayList<HashMap<String, Object>> listData;
	// 适配器
	SimpleAdapter listItemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button startBtn = (Button) findViewById(R.id.main_btn_save);
		Button searchBtn = (Button) findViewById(R.id.main_btn_search);
		listview = (ListView) findViewById(R.id.listview_db);

		startBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, AddActivity.class);
				startActivity(intent);

			}
		});
		
		manager = new DBManager(this);
		//System.out.println("inflate manager!");

		/*
		 * searchBtn.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent(MainActivity.this,
		 * SearchActivity.class); startActivity(intent);
		 * 
		 * } });
		 */

		/*db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
				+ "/my.db3", null);*/
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		setList();
		/*String sql = "select count(*) as c from sqlite_master where type ='table' and name ='information';";
		Cursor cur = db.rawQuery(sql, null);
		System.out.println("count:" + cur.getCount());
		if (cur.moveToNext()) {
			int count = cur.getInt(0);
			if (count > 0) {
				result = true;
			}
		}
		
		 * if (cur.getCount() != 0) { result = true; }
		 
		if (result == true) {
			Cursor cursor = db.rawQuery("select * from information", null);
			//inflateList(cursor);
			setList();
		}*/
	}

	 public void setList() {  
	        List<Information> infos = manager.selectAll(); 
	        System.out.println(infos);
	        listData = new ArrayList<HashMap<String, Object>>(); 
	        for (Information info : infos) {  
	        	HashMap<String, Object> map = new HashMap<String, Object>();
	            map.put("name", info.name);  
	            map.put("phonenum", info.phoneNum);  
	            listData.add(map);
	        }  
	        listItemAdapter = new SimpleAdapter(MainActivity.this, listData,// 数据源
					R.layout.item_list,// ListItem的XML实现
					new String[] { "name", "phonenum" }, new int[] {
							R.id.name_list, R.id.phonenum_list });
			listview.setAdapter(listItemAdapter); 
	    }  
	private void inflateList(Cursor c) {
		int count = c.getCount();
		listData = new ArrayList<HashMap<String, Object>>();
		// 获取表的内容
		while (c.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("name", c.getString(1));
			System.out.println(c.getString(1));
			map.put("phonenum", c.getString(2));
			System.out.println(c.getString(2));

			listData.add(map);
			System.out.println(map);
		}
		listItemAdapter = new SimpleAdapter(MainActivity.this, listData,// 数据源
				R.layout.item_list,// ListItem的XML实现
				new String[] { "name", "phonenum" }, new int[] {
						R.id.name_list, R.id.phonenum_list });
		listview.setAdapter(listItemAdapter);
		// listview.setOnCreateContextMenuListener(listviewLongPress);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		manager.closeDB();
	}

}
