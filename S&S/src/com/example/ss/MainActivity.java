package com.example.ss;

import java.util.ArrayList;
import java.util.HashMap;

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

		startBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						SaveActivity.class);
				startActivity(intent);

			}
		});

		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						SearchActivity.class);
				startActivity(intent);

			}
		});

		db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
				+ "/my.db3", null);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		String sql = "select count(*) as c from sqlite_master where type ='table' and name ='information';";
		Cursor cur = db.rawQuery(sql, null);
		
		if (cur.moveToNext()) {
			int count = cur.getInt(0);
			if (count > 0) {
				result = true;
			}
		}
		if (result == true) {
			Cursor cursor = db.rawQuery("select * from information", null);
			inflateList(cursor);
		}
	}

	private void inflateList(Cursor c) {
		int column = c.getColumnCount();
		listData = new ArrayList<HashMap<String, Object>>();
		// 获取表的内容
		while (c.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < column; i++) {
				map.put("username", c.getString(1));
				map.put("phonenum", c.getString(2));
			}
			listData.add(map);
		}
		listItemAdapter = new SimpleAdapter(this, listData,// 数据源
				R.layout.item_list,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "name", "phonenum" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.name_list, R.id.phonenum_list });
		listview.setAdapter(listItemAdapter);
		//listview.setOnCreateContextMenuListener(listviewLongPress);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
