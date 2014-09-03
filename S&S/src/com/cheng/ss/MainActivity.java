package com.cheng.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.ss.R;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	SQLiteDatabase db;
	ListView listview;

	DBManager manager;
	// 存储数据的数组列表
	ArrayList<HashMap<String, Object>> listData;
	// 适配器
	SimpleAdapter listItemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button startBtn = (Button) findViewById(R.id.main_btn_add);
		// Button searchBtn = (Button) findViewById(R.id.main_btn_search);
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

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		setList();//
	}

	// 初始化listview
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case android.R.id.home:
			// 在action bar点击app icon; 回到 home
			Intent homeIntent = new Intent(this, MainActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
			return true;

		case R.id.action_add:
			Intent addIntent = new Intent(this,AddActivity.class);
			startActivity(addIntent);
			return true;
		case R.id.action_search:
		default:

			return super.onOptionsItemSelected(item);

		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		manager.closeDB();
	}

}
