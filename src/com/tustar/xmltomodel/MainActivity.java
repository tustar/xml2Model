package com.tustar.xmltomodel;

import com.tustar.xmltomodel.NS.NSActivityMain;
import com.tustar.xmltomodel.NS.NSViewstubLayout;

import android.os.Bundle;
import android.R.id;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private final static String TAG = MainActivity.class.getSimpleName();
	private NSActivityMain self;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		self = new NSActivityMain(this);

		self.mIncludeText.setText("Include, no id");
		self.mInclude1.mIncludeText.setText("Include, id");
		
		View convertView = self.mViewstub1.inflate();
		self.mViewstub1Layout = new NSViewstubLayout(convertView);
		self.mViewstub1Layout.mViewstubSplash.setText("ViewStub, id");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
