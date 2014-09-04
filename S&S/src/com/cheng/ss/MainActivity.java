package com.cheng.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.ss.R;

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
		searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				searchAlertDialog();//弹出搜索对话框
			}
		});
		manager = new DBManager(this);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		setList(manager.selectAll());//全部查询，初始化listview
	}

	// 初始化listview
	protected void setList(List<Information> infos) {
		listData = new ArrayList<HashMap<String, Object>>();
		for (Information info : infos) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", info.name); // 姓名
			map.put("phonenum", "Tel:"+info.phoneNum); // 电话
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

	//ActionBar按钮事件
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
			//添加
			Intent addIntent = new Intent(this,AddActivity.class);
			startActivity(addIntent);
			return true;
		
		case R.id.action_search:
			//搜索
			searchAlertDialog();
		
		default:

			return super.onOptionsItemSelected(item);

		}
	}
	
	// 搜索对话框
	private void searchAlertDialog(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				MainActivity.this); // AlertDialog
		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		final View view = inflater.inflate(R.layout.dialog_search, null);
		
		alertDialog.setTitle(R.string.search);
		alertDialog.setView(view);
		alertDialog.setPositiveButton("搜索",
				new DialogInterface.OnClickListener() { // 搜索按键事件
					public void onClick(DialogInterface dialog, int whichButton) {

						EditText keyWordText = (EditText) view
								.findViewById(R.id.search_edittext);
						String keyWord = keyWordText.getText().toString(); // 获取输入的关键字
						setList(manager.selectKey(keyWord)); //listview 显示搜索结果
					}
				});

		alertDialog.setNegativeButton("取消",
				new DialogInterface.OnClickListener() { // 取消按钮
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});
		alertDialog.create().show();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		manager.closeDB();
	}

}
