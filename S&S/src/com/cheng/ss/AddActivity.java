package com.cheng.ss;

import com.example.ss.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 添加信息类
 * 
 * @author Cheng
 * 
 */
public class AddActivity extends Activity {

	private String nameStr, phoneNumStr;
	private EditText name;
	private EditText phoneNum;
	private Button saveBtn;
	DBManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		name = (EditText) findViewById(R.id.add_edit_name);
		phoneNum = (EditText) findViewById(R.id.add_edit_phonenum);
		saveBtn = (Button) findViewById(R.id.add_btn_save);

		manager = new DBManager(this);
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
				if (nameStr.equals("") && phoneNumStr.equals("")) {
					Toast.makeText(getApplicationContext(), "全部空白是不允许的哟~o(￣ヘ￣o＃)",
							Toast.LENGTH_SHORT).show();
				} else {
					Information info = new Information(nameStr, phoneNumStr);
					manager.add(info);
					System.out.println(info.name + info.phoneNum);
					Toast.makeText(getApplicationContext(), "添加成功",
							Toast.LENGTH_SHORT).show(); // 显示toast
					finish(); // 关闭AddActivity
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

}
