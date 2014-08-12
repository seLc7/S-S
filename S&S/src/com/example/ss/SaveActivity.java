package com.example.ss;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 负责添加信息
 * 
 * @author Cheng
 * 
 */
public class SaveActivity extends Activity {

	private String nameStr, phoneNumStr;
	private EditText name;
	private EditText phoneNum;
	private Button saveBtn;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);

		name = (EditText) findViewById(R.id.save_edit_name);
		phoneNum = (EditText) findViewById(R.id.save_edit_phonenum);
		saveBtn = (Button) findViewById(R.id.save_btn_save);

		db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
				+ "/my.db3", null);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nameStr = name.getText().toString();
				phoneNumStr = phoneNum.getText().toString();

				try {
					insertData(db, nameStr, phoneNumStr);
				} catch (SQLiteException se) {
					db.execSQL("create table if not exists information(_id integer primary key autoincrement,"
							+ "name varchar(50),phonenum varchar(50))");
					insertData(db, nameStr, phoneNumStr);
					System.out.println("insert error!");
				}
			}
		});
	}

	private void insertData(SQLiteDatabase db, String name, String phonenum) {
		db.execSQL("insert into information values(null, ?,?)", new String[] {
				name, phonenum });
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (db != null && db.isOpen()) {
			db.close();
		}
	}

}
