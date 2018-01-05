/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.gold;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Gold;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.Map;

public class GoldAPIActivity extends dump.z.BaseActivity_ implements APICallback, View.OnClickListener {
	private Button btnFuture;
	private Button btnSpot;
	private TextView tvTittle;
	private ListView lvResult;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gold);
		btnFuture = ResHelper.forceCast(findViewById(R.id.btnFuture));
		btnSpot = ResHelper.forceCast(findViewById(R.id.btnSpot));
		tvTittle = ResHelper.forceCast(findViewById(R.id.tvTittle));
		lvResult = ResHelper.forceCast(findViewById(R.id.lvResult));

		btnFuture.setOnClickListener(this);
		btnSpot.setOnClickListener(this);
	}

	private void setBtnEnable(boolean enable) {
		btnFuture.setEnabled(enable);
		btnSpot.setEnabled(enable);
	}

	public void onClick(View view) {
		setBtnEnable(false);
		if (view.getId() == R.id.btnFuture) {
			tvTittle.setText(R.string.gold_api_title_future);
			//查询期货黄金
			((Gold) ResHelper.forceCast(MobAPI.getAPI(Gold.NAME))).queryFuture(this);
		} else {
			tvTittle.setText(R.string.gold_api_title_spot);
			//查询现货黄金
			((Gold) ResHelper.forceCast(MobAPI.getAPI(Gold.NAME))).querySpot(this);
		}
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		ArrayList<Map<String, Object>> res = ResHelper.forceCast(result.get("result"));
		if (res != null && res.size() > 0) {
			switch (action) {
				case Gold.ACTION_FUTURE: showFutureResult(res); break;
				case Gold.ACTION_SPOT: showSpotResult(res); break;
			}
		}
		setBtnEnable(true);
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
		setBtnEnable(true);
	}

	private void showFutureResult(ArrayList<Map<String, Object>> result) {
		//期货黄金
		SimpleAdapter adapter = new SimpleAdapter(this, result, R.layout.view_gold_future_list_item,
				new String[]{ "name", "buyNumber", "buyPic", "closePri", "code", "date", "dynamicSettle", "highPic", "limit", "lowPic", "openPri",
						"positionNum", "sellNumber", "sellPic", "totalVol", "yesDayPic", "yesDaySettle"},
				new int[]{ R.id.tvName, R.id.tvBuyNumber, R.id.tvBuyPic, R.id.tvClosePri, R.id.tvCode, R.id.tvDate, R.id.tvDynamicSettle,
						R.id.tvHighPic, R.id.tvLimit, R.id.tvLowPic, R.id.tvOpenPri, R.id.tvPositionNum, R.id.tvSellNumber, R.id.tvSellPic,
						R.id.tvTotalVol, R.id.tvYesterdayPic, R.id.tvYesterdaySettle });
		lvResult.setAdapter(adapter);
	}

	private void showSpotResult(ArrayList<Map<String, Object>> result) {
		//现货黄金
		SimpleAdapter adapter = new SimpleAdapter(this, result, R.layout.view_gold_spot_list_item,
				new String[]{ "name", "closePri", "highPic", "limit", "lowPic", "openPri", "time", "totalTurnover", "totalVol", "yesDayPic",
						"variety"},
				new int[]{ R.id.tvName, R.id.tvClosePri, R.id.tvHighPic, R.id.tvLimit, R.id.tvLowPic, R.id.tvOpenPri, R.id.tvTime,
						R.id.tvTotalTurnover, R.id.tvTotalVol, R.id.tvYesterdayPic, R.id.tvVariety });
		lvResult.setAdapter(adapter);
}
}
