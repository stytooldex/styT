/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.boxoffice;

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
import com.mob.mobapi.apis.BoxOffice;
import nico.styTool.R;

import java.util.ArrayList;
import java.util.Map;

import com.mob.tools.utils.ResHelper;

public class BoxOfficeAPIActivity extends dump.z.BaseActivity_ implements APICallback, View.OnClickListener {
	private Button btnDayCN;
	private Button btnWeekCN;
	private Button btnWeekHK;
	private Button btnWeekendCN;
	private Button btnWeekendUS;
	private TextView tvTittle;
	private ListView lvResult;

	//当前查询的票房类别对应的view id
	private int currentViewId;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boxoffice);
		btnDayCN = ResHelper.forceCast(findViewById(R.id.btnDayCN));
		btnWeekCN = ResHelper.forceCast(findViewById(R.id.btnWeekCN));
		btnWeekHK = ResHelper.forceCast(findViewById(R.id.btnWeekHK));
		btnWeekendCN = ResHelper.forceCast(findViewById(R.id.btnWeekendCN));
		btnWeekendUS = ResHelper.forceCast(findViewById(R.id.btnWeekendUS));
		tvTittle = ResHelper.forceCast(findViewById(R.id.tvTittle));
		lvResult = ResHelper.forceCast(findViewById(R.id.lvResult));

		btnDayCN.setOnClickListener(this);
		btnWeekCN.setOnClickListener(this);
		btnWeekHK.setOnClickListener(this);
		btnWeekendCN.setOnClickListener(this);
		btnWeekendUS.setOnClickListener(this);
	}

	private void setBtnEnable(boolean enable) {
		btnDayCN.setEnabled(enable);
		btnWeekCN.setEnabled(enable);
		btnWeekHK.setEnabled(enable);
		btnWeekendCN.setEnabled(enable);
		btnWeekendUS.setEnabled(enable);
	}

	public void onClick(View view) {
		setBtnEnable(false);
		//查询相关票房
		currentViewId = view.getId();
		switch (currentViewId) {
			case R.id.btnDayCN: {
				tvTittle.setText(R.string.boxoffice_api_btn_day_cn);
				((BoxOffice) ResHelper.forceCast(MobAPI.getAPI(BoxOffice.NAME))).queryDay("CN", BoxOfficeAPIActivity.this);
			} break;
			case R.id.btnWeekCN: {
				tvTittle.setText(R.string.boxoffice_api_btn_week_cn);
				((BoxOffice) ResHelper.forceCast(MobAPI.getAPI(BoxOffice.NAME))).queryWeek("CN", BoxOfficeAPIActivity.this);
			} break;
			case R.id.btnWeekHK: {
				tvTittle.setText(R.string.boxoffice_api_btn_week_hk);
				((BoxOffice) ResHelper.forceCast(MobAPI.getAPI(BoxOffice.NAME))).queryWeek("HK", BoxOfficeAPIActivity.this);
			} break;
			case R.id.btnWeekendCN: {
				tvTittle.setText(R.string.boxoffice_api_btn_weekend_cn);
				((BoxOffice) ResHelper.forceCast(MobAPI.getAPI(BoxOffice.NAME))).queryWeekend("CN", BoxOfficeAPIActivity.this);
			} break;
			case R.id.btnWeekendUS: {
				tvTittle.setText(R.string.boxoffice_api_btn_weekend_us);
				((BoxOffice) ResHelper.forceCast(MobAPI.getAPI(BoxOffice.NAME))).queryWeekend("US", BoxOfficeAPIActivity.this);
			} break;
		}
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		ArrayList<Map<String, Object>> res = ResHelper.forceCast(result.get("result"));
		if (res != null && res.size() > 0) {
			switch (action) {
				case BoxOffice.ACTION_DAY: showDayResult(res); break;
				case BoxOffice.ACTION_WEEK: showWeekResult(res); break;
				case BoxOffice.ACTION_WEEKEND: showWeekendResult(res); break;
			}
		}
		setBtnEnable(true);
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
		setBtnEnable(true);
	}

	private void showDayResult(ArrayList<Map<String, Object>> result) {
		//内地实时票房
		SimpleAdapter adapter = new SimpleAdapter(this, result, R.layout.view_boxoffice_day_cn_list_item,
				new String[]{"name", "cur", "days", "sum"}, new int[]{R.id.tvName, R.id.tvCur, R.id.tvDays, R.id.tvSum});
		lvResult.setAdapter(adapter);
	}

	private void showWeekResult(ArrayList<Map<String, Object>> result) {
		SimpleAdapter adapter = null;
		if (currentViewId == R.id.btnWeekCN) {
			//内地周票房
			int[] ids = new int[]{R.id.tvName, R.id.tvWeekSum, R.id.tvWeekPeriod, R.id.tvSum, R.id.tvDays};
			adapter = new SimpleAdapter(this, result, R.layout.view_boxoffice_week_cn_list_item,
					new String[]{"name", "weekSum", "weekPeriod", "sum", "days"}, ids);
		} else if (currentViewId == R.id.btnWeekHK) {
			//香港周票房
			adapter = new SimpleAdapter(this, result, R.layout.view_boxoffice_week_hk_list_item,
					new String[]{"name", "sumOfWeekHK", "weekPeriodOfHK", "sumOfHK", "daysOfHK"},
					new int[]{R.id.tvName, R.id.tvWeekSum, R.id.tvWeekPeriod, R.id.tvSum, R.id.tvDays});
		}
		if (adapter != null) {
			lvResult.setAdapter(adapter);
		}
	}

	private void showWeekendResult(ArrayList<Map<String, Object>> result) {
		SimpleAdapter adapter = null;
		if (currentViewId == R.id.btnWeekendCN) {
			//内地周末票房
			adapter = new SimpleAdapter(this, result, R.layout.view_boxoffice_weekend_cn_list_item,
					new String[]{"name", "weekendSum", "weekendPeriod", "sum", "days"},
					new int[]{R.id.tvName, R.id.tvWeekendSum, R.id.tvWeekendPeriod, R.id.tvSum, R.id.tvDays});
		} else if (currentViewId == R.id.btnWeekendUS) {
			//北美周末票房
			adapter = new SimpleAdapter(this, result, R.layout.view_boxoffice_weekend_us_list_item,
					new String[]{"name", "sumOfWeekendUS", "weekendPeriodOfUS", "sumOfUS", "weeksOfUS"},
					new int[]{R.id.tvName, R.id.tvWeekendSum, R.id.tvWeekendPeriod, R.id.tvSum, R.id.tvDays});
		}
		if (adapter != null) {
			lvResult.setAdapter(adapter);
		}
	}
}
