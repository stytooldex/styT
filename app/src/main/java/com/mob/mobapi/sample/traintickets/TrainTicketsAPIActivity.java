/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.traintickets;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.TrainTickets;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrainTicketsAPIActivity extends dump.z.BaseActivity_ implements APICallback, AdapterView.OnItemClickListener, View.OnClickListener {
	private boolean isQuerying = false;
	private boolean currentIsTrainNoResult = false;

	private EditText etStartStation;
	private EditText etEndStation;
	private TextView etTrainNo;

	private ListView lvResult;

	private InputMethodManager inputMethodManager;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traintickets);
		etStartStation = ResHelper.forceCast(findViewById(R.id.etStartStation));
		etEndStation = ResHelper.forceCast(findViewById(R.id.etEndStation));
		etTrainNo = ResHelper.forceCast(findViewById(R.id.etTrainNo));
		lvResult = ResHelper.forceCast(findViewById(R.id.lvResult));

		findViewById(R.id.btnStation).setOnClickListener(this);
		findViewById(R.id.btnTrainNo).setOnClickListener(this);
		lvResult.setOnItemClickListener(this);
	}

	public void onClick(View v) {
		if (isQuerying) {
			return;
		}
		isQuerying = true;
		hideIme(v);
		if (v.getId() == R.id.btnStation) {
			//站站查询
			etTrainNo.setText("");
			((TrainTickets) MobAPI.getAPI(TrainTickets.NAME)).queryByStationToStation(etStartStation.getText().toString(),
					etEndStation.getText().toString(), TrainTicketsAPIActivity.this);
		} else if (v.getId() == R.id.btnTrainNo) {
			//车次查询
			etEndStation.setText("");
			etStartStation.setText("");
			((TrainTickets) MobAPI.getAPI(TrainTickets.NAME)).queryByTrainNo(etTrainNo.getText().toString(), TrainTicketsAPIActivity.this);
		}
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		if (isQuerying) {
			return;
		}
		isQuerying = true;
		HashMap<String, Object> item = ResHelper.forceCast(adapterView.getAdapter().getItem(position));
		if (currentIsTrainNoResult) {
			String start = ResHelper.forceCast(item.get("startStationName"));
			String end = ResHelper.forceCast(item.get("endStationName"));
			if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
				isQuerying = false;
				return;
			}
			etTrainNo.setText("");
			etStartStation.setText(start);
			etEndStation.setText(end);
			//站站查询
			((TrainTickets) MobAPI.getAPI(TrainTickets.NAME)).queryByStationToStation(start, end, TrainTicketsAPIActivity.this);
			return;
		}
		//车次查询
		etStartStation.setText("");
		etEndStation.setText("");
		String stationTrainCode = ResHelper.forceCast(item.get("stationTrainCode"));
		etTrainNo.setText(stationTrainCode);
		((TrainTickets) MobAPI.getAPI(TrainTickets.NAME)).queryByTrainNo(stationTrainCode, TrainTicketsAPIActivity.this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		ArrayList<Map<String, Object>> res = ResHelper.forceCast(result.get("result"));
		if (res != null && res.size() > 0) {
			switch (action) {
				case TrainTickets.ACTION_QUERY_BY_NO: showTrainNoResult(res); break;
				case TrainTickets.ACTION_QUERY_BY_STATION: showStationResult(res); break;
			}
		}
		isQuerying = false;
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
		isQuerying = false;
	}

	private void showTrainNoResult(ArrayList<Map<String, Object>> result) {
		//车次查询的结果
		currentIsTrainNoResult = true;
		SimpleAdapter adapter = new SimpleAdapter(this, result, R.layout.view_train_tickets_trainno_list_item,
				new String[]{"arriveTime", "startTime", "startStationName", "endStationName", "stationName", "stationNo", "trainClassName",
						"stationTrainCode", "stopoverTime"},
				new int[]{R.id.tvArriveTime, R.id.tvStartTime, R.id.tvStartStationName, R.id.tvEndStationName, R.id.tvStationName, R.id.tvStationNo,
						R.id.tvTrainClassName, R.id.tvStationTrainCode, R.id.tvStopoverTime});
		lvResult.setAdapter(adapter);
	}

	private void showStationResult(ArrayList<Map<String, Object>> result) {
		//站站查询的结果
		currentIsTrainNoResult = false;
		SimpleAdapter adapter = new SimpleAdapter(this, result, R.layout.view_train_tickets_station_list_item,
				new String[]{"startStationName", "endStationName", "startTime", "arriveTime", "stationTrainCode", "trainClassName", "lishi",
						"pricesw", "pricetd", "pricegrw", "pricerw", "priceyw", "priceyd", "priceed", "pricerz", "priceyz", "pricewz"},
				new int[]{R.id.tvStartStationName, R.id.tvEndStationName, R.id.tvStartTime, R.id.tvArriveTime, R.id.tvStationTrainCode,
						R.id.tvTrainClassName, R.id.tvLishi, R.id.tvPricesw, R.id.tvPricetd, R.id.tvPricegrw, R.id.tvPricerw, R.id.tvPriceyw,
						R.id.tvPriceyd, R.id.tvPriceed, R.id.tvPricerz, R.id.tvPriceyz, R.id.tvPricewz});
		lvResult.setAdapter(adapter);
	}

	private void hideIme(View view) {
		if (inputMethodManager == null) {
			inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		}
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
