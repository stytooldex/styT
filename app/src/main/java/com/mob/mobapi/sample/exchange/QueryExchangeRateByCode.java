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
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Exchange;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.Map;

public class QueryExchangeRateByCode extends dump.z.BaseActivity_ implements APICallback {
	private TextView tvBuyPic;
	private TextView tvClosePri;
	private TextView tvCode;
	private TextView tvCurrency;
	private TextView tvDate;
	private TextView tvDiffAmo;
	private TextView tvDiffPer;
	private TextView tvHighPic;
	private TextView tvLowPic;
	private TextView tvOpenPri;
	private TextView tvRange;
	private TextView tvSellPic;
	private TextView tvYesterdayPic;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange_by_code);
		tvBuyPic = ResHelper.forceCast(findViewById(R.id.tvBuyPic));
		tvClosePri = ResHelper.forceCast(findViewById(R.id.tvClosePri));
		tvCode = ResHelper.forceCast(findViewById(R.id.tvCode));
		tvCurrency = ResHelper.forceCast(findViewById(R.id.tvCurrency));
		tvDate = ResHelper.forceCast(findViewById(R.id.tvDate));
		tvDiffAmo = ResHelper.forceCast(findViewById(R.id.tvDiffAmo));
		tvDiffPer = ResHelper.forceCast(findViewById(R.id.tvDiffPer));
		tvHighPic = ResHelper.forceCast(findViewById(R.id.tvHighPic));
		tvLowPic = ResHelper.forceCast(findViewById(R.id.tvLowPic));
		tvOpenPri = ResHelper.forceCast(findViewById(R.id.tvOpenPri));
		tvRange = ResHelper.forceCast(findViewById(R.id.tvRange));
		tvSellPic = ResHelper.forceCast(findViewById(R.id.tvSellPic));
		tvYesterdayPic = ResHelper.forceCast(findViewById(R.id.tvYesterdayPic));
		//查询人民币兑港元即期汇率
		((Exchange) ResHelper.forceCast(MobAPI.getAPI(Exchange.NAME))).queryByCode("CNYHKD", this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		Map<String, Object> res = ResHelper.forceCast(result.get("result"));
		tvBuyPic.setText(String.valueOf(res.get("buyPic")));
		tvClosePri.setText(String.valueOf(res.get("closePri")));
		tvCode.setText(String.valueOf(res.get("code")));
		tvCurrency.setText(String.valueOf(res.get("currency")));
		tvDate.setText(String.valueOf(res.get("date")));
		tvDiffAmo.setText(String.valueOf(res.get("diffAmo")));
		tvDiffPer.setText(String.valueOf(res.get("diffPer")));
		tvHighPic.setText(String.valueOf(res.get("highPic")));
		tvLowPic.setText(String.valueOf(res.get("lowPic")));
		tvOpenPri.setText(String.valueOf(res.get("openPri")));
		tvRange.setText(String.valueOf(res.get("range")));
		tvSellPic.setText(String.valueOf(res.get("sellPic")));
		tvYesterdayPic.setText(String.valueOf(res.get("yesDayPic")));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

}
