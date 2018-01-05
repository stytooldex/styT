/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.domesticmetal;

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
import com.mob.mobapi.apis.DomesticMetal;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.Map;

public class DomesticMetalAPIActivity extends dump.z.BaseActivity_ implements APICallback, View.OnClickListener {
	private Button btnTianjin;
	private Button btnGuangdong;
	private Button btnNanfang;
	private TextView tvTittle;
	private ListView lvResult;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_domestic_metal);
		btnTianjin = ResHelper.forceCast(findViewById(R.id.btnTianjin));
		btnGuangdong = ResHelper.forceCast(findViewById(R.id.btnGuangdong));
		btnNanfang = ResHelper.forceCast(findViewById(R.id.btnNanfang));
		tvTittle = ResHelper.forceCast(findViewById(R.id.tvTittle));
		lvResult = ResHelper.forceCast(findViewById(R.id.lvResult));

		btnTianjin.setOnClickListener(this);
		btnGuangdong.setOnClickListener(this);
		btnNanfang.setOnClickListener(this);

		onClick(btnTianjin);
	}

	private void setBtnEnable(boolean enable) {
		btnTianjin.setEnabled(enable);
		btnGuangdong.setEnabled(enable);
		btnNanfang.setEnabled(enable);
	}

	public void onClick(View view) {
		setBtnEnable(false);
		if (view.getId() == R.id.btnTianjin) {
			tvTittle.setText(R.string.domestic_metal_api_title_tianjin);
			//交易所数据编号(1:天津贵金属交易所 2：广东贵金属 3：南方贵金属) 默认是 1
			((DomesticMetal) MobAPI.getAPI(DomesticMetal.NAME)).querySpot("1", this);
		} else if (view.getId() == R.id.btnGuangdong) {
			tvTittle.setText(R.string.domestic_metal_api_title_guangdong);
			//交易所数据编号(1:天津贵金属交易所 2：广东贵金属 3：南方贵金属) 默认是 1
			((DomesticMetal) MobAPI.getAPI(DomesticMetal.NAME)).querySpot("2", this);
		} else if (view.getId() == R.id.btnNanfang) {
			tvTittle.setText(R.string.domestic_metal_api_title_nanfang);
			//交易所数据编号(1:天津贵金属交易所 2：广东贵金属 3：南方贵金属) 默认是 1
			((DomesticMetal) MobAPI.getAPI(DomesticMetal.NAME)).querySpot("3", this);
		}
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		ArrayList<Map<String, Object>> res = ResHelper.forceCast(result.get("result"));
		if (res != null && res.size() > 0) {
			showSpotResult(res);
		}
		setBtnEnable(true);
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
		setBtnEnable(true);
	}

	private void showSpotResult(ArrayList<Map<String, Object>> result) {
		SimpleAdapter adapter = new SimpleAdapter(this, result, R.layout.view_domestic_metal_list_item,
				new String[]{"name", "highPic", "limit", "diffAmo", "lowPic", "openPri", "time", "buyPic", "sellPic", "closePri", "totalTurnover",
						"totalVol", "yesDayPic", "variety"},
				new int[]{R.id.tvName, R.id.tvHighPic, R.id.tvLimit, R.id.tvDiffAmo, R.id.tvLowPic, R.id.tvOpenPri, R.id.tvTime, R.id.tvBuyPic,
						R.id.tvSellPic, R.id.tvClosePri, R.id.tvTotalTurnover, R.id.tvTotalVol, R.id.tvYesterdayPic, R.id.tvVariety});
		lvResult.setAdapter(adapter);
	}
}
