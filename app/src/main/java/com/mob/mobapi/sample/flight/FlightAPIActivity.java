/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.flight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.mob.mobapi.apis.Flight;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightAPIActivity extends dump.z.BaseActivity_ implements APICallback, AdapterView.OnItemClickListener, View.OnClickListener {
	private boolean isQuerying = false;
	private boolean currentIsQueryByNo = false;

	private EditText etStart;
	private EditText etEnd;
	private TextView etNo;

	private ListView lvResult;
	private SimpleAdapter adapter;
	private List<Map<String, Object>> listResult;

	private InputMethodManager inputMethodManager;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flight);
		etStart = ResHelper.forceCast(findViewById(R.id.etStart));
		etEnd = ResHelper.forceCast(findViewById(R.id.etEnd));
		etNo = ResHelper.forceCast(findViewById(R.id.etNo));
		lvResult = ResHelper.forceCast(findViewById(R.id.lvResult));

		findViewById(R.id.btnThreeCode).setOnClickListener(this);
		findViewById(R.id.btnLine).setOnClickListener(this);
		findViewById(R.id.btnNo).setOnClickListener(this);
		lvResult.setOnItemClickListener(this);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btnThreeCode) {
			//查询所有城市机场三字码信息
			startActivity(new Intent(this, FlightCityThreeCodesAPIActivity.class));
			return;
		}
		if (isQuerying) {
			return;
		}
		isQuerying = true;
		hideIme(v);
		if (v.getId() == R.id.btnLine) {
			//根据航线查询
			etNo.setText("");
			((Flight) MobAPI.getAPI(Flight.NAME)).queryByLine(etStart.getText().toString(), etEnd.getText().toString(), FlightAPIActivity.this);
		} else if (v.getId() == R.id.btnNo) {
			//根据航班号查询
			etEnd.setText("");
			etStart.setText("");
			((Flight) MobAPI.getAPI(Flight.NAME)).queryByNo(etNo.getText().toString(), FlightAPIActivity.this);
		}
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		if (isQuerying) {
			return;
		}
		isQuerying = true;
		HashMap<String, Object> item = ResHelper.forceCast(adapterView.getAdapter().getItem(position));
		if (currentIsQueryByNo) {
			String start, end;
			if (Math.random() < 0.5) {
				//使用机场三字码
				start = ResHelper.forceCast(item.get("fromAirportCode"));
				end = ResHelper.forceCast(item.get("toAirportCode"));
			} else {
				//使用城市名称
				start = ResHelper.forceCast(item.get("fromCityName"));
				end = ResHelper.forceCast(item.get("toCityName"));
			}
			if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
				isQuerying = false;
				return;
			}
			etNo.setText("");
			etStart.setText(start);
			etEnd.setText(end);
			//根据航线查询
			((Flight) MobAPI.getAPI(Flight.NAME)).queryByLine(start, end, FlightAPIActivity.this);
			return;
		}
		//根据航班号查询
		etStart.setText("");
		etEnd.setText("");
		String flightNo = ResHelper.forceCast(item.get("flightNo"));
		etNo.setText(flightNo);
		((Flight) MobAPI.getAPI(Flight.NAME)).queryByNo(flightNo, FlightAPIActivity.this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		ArrayList<Map<String, Object>> res = ResHelper.forceCast(result.get("result"));
		if (res != null && res.size() > 0) {
			switch (action) {
				case Flight.ACTION_QUERY_BY_NO: {
					currentIsQueryByNo = true;
					showResult(res);
				} break;
				case Flight.ACTION_QUERY_BY_LINE: {
					currentIsQueryByNo = false;
					showResult(res);
				} break;
			}
		}
		isQuerying = false;
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
		isQuerying = false;
	}

	private void showResult(ArrayList<Map<String, Object>> result) {
		if (listResult == null) {
			listResult = new ArrayList<Map<String, Object>>();
		}
		listResult.clear();
		if (result != null) {
			listResult.addAll(result);
		}
		if (adapter == null) {
			adapter = new SimpleAdapter(this, listResult, R.layout.view_flight_info_list_item,
					new String[]{"airLines", "flightNo", "flightRate", "flightTime", "from", "fromAirportCode", "fromCityCode", "fromCityName",
							"fromTerminal", "planTime", "planArriveTime", "to", "toAirportCode", "toCityCode", "toCityName", "toTerminal", "week"},
					new int[]{R.id.tvAirLines, R.id.tvFlightNo, R.id.tvFlightRate, R.id.tvFlightTime, R.id.tvFrom, R.id.tvFromAirportCode,
							R.id.tvFromCityCode, R.id.tvFromCityName, R.id.tvFromTerminal, R.id.tvPlanTime, R.id.tvPlanArriveTime, R.id.tvTo,
							R.id.tvToAirportCode, R.id.tvToCityCode, R.id.tvToCityName, R.id.tvToTerminal, R.id.tvWeek});
			lvResult.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
	}

	private void hideIme(View view) {
		if (inputMethodManager == null) {
			inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		}
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
