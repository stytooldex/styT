/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.exchange;

import android.app.ListActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import nico.styTool.R;

public class QueryRealTimeActivity extends ListActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.exchange_api_title_real_time);
		getListView().setBackgroundColor(0xffffffff);
		getListView().setDivider(new ColorDrawable(0xff7f7f7f));
		getListView().setDividerHeight(1);

		// 请求和现实操作由RealTimeExchangeAdapter完成
		RealTimeExchangeAdapter adapter = new RealTimeExchangeAdapter();
		setListAdapter(adapter);
		adapter.requestData();
	}
}
