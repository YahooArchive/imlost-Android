package com.yahoo.android.client.imlost;

import android.support.v4.app.Fragment;

import com.yahoo.android.client.imlost.fragment.ImLostFragment;

public class ImLostActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ImLostFragment();
	}

}
