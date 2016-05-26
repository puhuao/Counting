package com.wksc.framwork.baseui.activity;

import android.os.Bundle;
import android.view.Window;

import com.wksc.framwork.R;

public class BaseFragmentActivity extends CubeFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public void finish()
	{
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	public void defaultFinish()
	{
		super.finish();
	}

	@Override
	protected String getCloseWarning() {
		return null;
	}

	@Override
	protected int getFragmentContainerId() {
		return 0;
	}
}
