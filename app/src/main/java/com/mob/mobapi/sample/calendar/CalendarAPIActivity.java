/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Calendar;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalendarAPIActivity extends dump.z.BaseActivity_ implements APICallback, OnItemSelectedListener {
	private Spinner  spYear;
	private Spinner  spMonth;
	private Spinner  spDay;
	private TextView tvCalZodiac;
	private TextView tvCalAvoid;
	private TextView tvCalDate;
	private TextView tvCalHoliday;
	private TextView tvCalLunar;
	private TextView tvCalLunarYear;
	private TextView tvCalSuit;
	private TextView tvCalWeekday;
	private Calendar api;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		spYear  = ResHelper.forceCast(findViewById(R.id.spYear));
		spMonth = ResHelper.forceCast(findViewById(R.id.spManth));
		spDay   = ResHelper.forceCast(findViewById(R.id.spDay));

		spYear.setOnItemSelectedListener(this);
		spMonth.setOnItemSelectedListener(this);
		spDay.setOnItemSelectedListener(this);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] dates = dateFormat.format(new java.util.Date()).split("-");
		int startyear = 1900;
		initSpinner(1900, 2099, spYear);
		initSpinner(1, 12, spMonth);
		initSpinner(1, 31, spDay);

		spYear.setSelection(Integer.parseInt(dates[0]) - startyear);
		spMonth.setSelection(Integer.parseInt(dates[1]) - 1);
		spDay.setSelection(Integer.parseInt(dates[2]) - 1);

		tvCalZodiac    = ResHelper.forceCast(findViewById(R.id.tvCalZodiac));
		tvCalAvoid     = ResHelper.forceCast(findViewById(R.id.tvCalAvoid));
		tvCalDate      = ResHelper.forceCast(findViewById(R.id.tvCalDate));
		tvCalHoliday   = ResHelper.forceCast(findViewById(R.id.tvCalHoliday));
		tvCalLunar     = ResHelper.forceCast(findViewById(R.id.tvCalLunar));
		tvCalLunarYear = ResHelper.forceCast(findViewById(R.id.tvCalLunarYear));
		tvCalSuit      = ResHelper.forceCast(findViewById(R.id.tvCalSuit));
		tvCalWeekday   = ResHelper.forceCast(findViewById(R.id.tvCalWeekday));

		api = ResHelper.forceCast(MobAPI.getAPI(Calendar.NAME));
	}

	private String IntegerToString(int index) {
		if (index < 10) {
			return "0" + index;
		}

		return "" + index;
	}

	private void initSpinner(int start, int end, Spinner spinner) {
		ArrayList<String> spinnerList = new ArrayList<String>();

		for (int i = start; i < end + 1; i++) {
			spinnerList.add(IntegerToString(i));
		}

		spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.view_spinner, spinnerList));
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> res = ResHelper.forceCast(result.get("result"));
		tvCalZodiac.setText(com.mob.tools.utils.ResHelper.toString(res.get("zodiac")));
		tvCalAvoid.setText(com.mob.tools.utils.ResHelper.toString(res.get("avoid")));
		tvCalDate.setText(com.mob.tools.utils.ResHelper.toString(res.get("date")));
		tvCalHoliday.setText(com.mob.tools.utils.ResHelper.toString(res.get("holiday")));
		tvCalLunar.setText(com.mob.tools.utils.ResHelper.toString(res.get("lunar")));
		tvCalLunarYear.setText(com.mob.tools.utils.ResHelper.toString(res.get("lunarYear")));
		tvCalSuit.setText(com.mob.tools.utils.ResHelper.toString(res.get("suit")));
		tvCalWeekday.setText(com.mob.tools.utils.ResHelper.toString(res.get("weekday")));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		String date = "" + spYear.getSelectedItem() + "-" + spMonth.getSelectedItem() + "-" + spDay.getSelectedItem();
		api.queryCalendar(date, this);
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}
}
