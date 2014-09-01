package com.cheng.ss;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		helper = new DBHelper(context);
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}

	public void add(List<Information> infos) {
		db.beginTransaction(); // 开始事务
		try {
			for (Information info : infos) {
				db.execSQL("INSERT INTO information VALUES(null, ?, ?)",
						new Object[] { info.name, info.phoneNum });
			}
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	/*
	 * public void updateName(Information info) { ContentValues cv = new
	 * ContentValues(); cv.put("age", info.name); db.update("person", cv,
	 * "name = ?", new String[] { person.name }); }
	 * 
	 * public void deleteOldPerson(Person person) { db.delete("person",
	 * "age >= ?", new String[] { String.valueOf(person.age) }); }
	 */

	public List<Information> setList() {
		ArrayList<Information> infos = new ArrayList<Information>();
		Cursor c = db.rawQuery("SELECT * FROM information", null);
		while (c.moveToNext()) {
			Information info = new Information();
			
			info.name = c.getString(c.getColumnIndex("name"));
			info.phoneNum = c.getInt(c.getColumnIndex("phoneNume"));

			infos.add(info);
		}
		c.close();
		return infos;
	}

	public void closeDB() {
		db.close();
	}
}
