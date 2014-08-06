package com.example.ss;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SaveActivity extends Activity {

	private String nameStr, phoneNumStr;
	private EditText name;
	private EditText phoneNum;
	private Button saveBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);

		name = (EditText) findViewById(R.id.save_edit_name);
		phoneNum = (EditText) findViewById(R.id.save_edit_phonenum);
		saveBtn = (Button) findViewById(R.id.save_btn_save);
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
			}
		});
	}

}
