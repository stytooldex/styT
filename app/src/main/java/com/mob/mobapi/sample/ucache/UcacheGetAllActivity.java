/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.ucache;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

public class UcacheGetAllActivity extends dump.z.BaseActivity_ implements OnClickListener {
	private EditText etTable;
	private ListView lvResult;
	private GetAllAdapter adapter;
	private View btnSearch;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.ucache_api_getall);
		setContentView(R.layout.activity_ucache_getall);
		etTable = ResHelper.forceCast(findViewById(R.id.etTable));
		lvResult = ResHelper.forceCast(findViewById(R.id.lvResult));

		etTable.setText("mobile");
		btnSearch = findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);
		btnSearch.performClick();
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btnSearch) {
			btnSearch.setEnabled(false);
			if (adapter == null) {
				adapter = new GetAllAdapter() {
					public void requestComplete() {
						super.requestComplete();
						btnSearch.setEnabled(true);
					}
				};
				lvResult.setAdapter(adapter);
			}
			adapter.request(etTable.getText().toString().trim());
		}
	}
}
