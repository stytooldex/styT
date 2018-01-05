/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.exchange;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Exchange;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.Map;

public class QueryAllCurrency extends dump.z.BaseActivity_ implements APICallback {
	private ListView lvResult;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange_currency);
		lvResult = ResHelper.forceCast(findViewById(R.id.lvResult));
		//查询主要国家货币代码
		((Exchange) ResHelper.forceCast(MobAPI.getAPI(Exchange.NAME))).queryCurrency(this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		ArrayList<Map<String, Object>> res = ResHelper.forceCast(result.get("result"));
		SimpleAdapter adapter = new SimpleAdapter(this, res, R.layout.view_exchange_currency_item,
				new String[]{"code", "name"}, new int[]{R.id.tvCurrencyCode, R.id.tvCurrencyName});
		lvResult.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

}
