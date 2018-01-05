/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.marriage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import com.mob.mobapi.apis.Marriage;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

public class MarriageAPIActivity extends dump.z.BaseActivity_ implements APICallback, OnItemSelectedListener {
	private Spinner  spManYear;
	private Spinner  spManMonth;
	private Spinner  spManDay;
	private Spinner  spManHour;
	private Spinner  spWomenYear;
	private Spinner  spWomenMonth;
	private Spinner  spWomenDay;
	private Spinner  spWomenHour;
	private TextView tvMarriageType;
	private TextView tvMenLunar;
	private TextView tvMenLunarTime;
	private TextView tvMenMarriage;
	private TextView tvMenZodiac;
	private TextView tvWomanLunar;
	private TextView tvWonmanLuarTime;
	private TextView tvWonmanMarriage;
	private TextView tvWonmanZodiac;
	private Marriage api;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marriage);

		spManYear    = ResHelper.forceCast(findViewById(R.id.spManYear));
		spManMonth   = ResHelper.forceCast(findViewById(R.id.spManMonth));
		spManDay     = ResHelper.forceCast(findViewById(R.id.spManDay));
		spManHour    = ResHelper.forceCast(findViewById(R.id.spManHour));
		spWomenYear  = ResHelper.forceCast(findViewById(R.id.spWomenYear));
		spWomenMonth = ResHelper.forceCast(findViewById(R.id.spWomenMonth));
		spWomenDay   = ResHelper.forceCast(findViewById(R.id.spWomenDay));
		spWomenHour  = ResHelper.forceCast(findViewById(R.id.spWomenHour));

		spManYear.setOnItemSelectedListener(this);
		spManMonth.setOnItemSelectedListener(this);
		spManDay.setOnItemSelectedListener(this);
		spManHour.setOnItemSelectedListener(this);
		spWomenYear.setOnItemSelectedListener(this);
		spWomenMonth.setOnItemSelectedListener(this);
		spWomenDay.setOnItemSelectedListener(this);
		spWomenHour.setOnItemSelectedListener(this);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] dates = dateFormat.format(new java.util.Date()).split("-");
		int thisyear  = Integer.parseInt(dates[0]);
		int startyear = 1920;
		initSpinner(startyear, thisyear, spManYear);  // year range(1900 - 2099)
		initSpinner(1, 12, spManMonth);
		initSpinner(1, 31, spManDay);
		initSpinner(0, 23, spManHour);
		initSpinner(startyear, thisyear, spWomenYear); // year range(1900 - 2099)
		initSpinner(1, 12, spWomenMonth);
		initSpinner(1, 31, spWomenDay);
		initSpinner(0, 23, spWomenHour);

		spManYear.setSelection((thisyear - 22) - startyear);
		spWomenYear.setSelection((thisyear - 20) - startyear);

		tvMarriageType   = ResHelper.forceCast(findViewById(R.id.tvMarriageType));
		tvMenLunar       = ResHelper.forceCast(findViewById(R.id.tvMenLunar));
		tvMenLunarTime   = ResHelper.forceCast(findViewById(R.id.tvMenLunarTime));
		tvMenMarriage    = ResHelper.forceCast(findViewById(R.id.tvMenMarriage));
		tvMenZodiac      = ResHelper.forceCast(findViewById(R.id.tvMenZodiac));
		tvWomanLunar     = ResHelper.forceCast(findViewById(R.id.tvWomanLunar));
		tvWonmanLuarTime = ResHelper.forceCast(findViewById(R.id.tvWonmanLuarTime));
		tvWonmanMarriage = ResHelper.forceCast(findViewById(R.id.tvWonmanMarriage));
		tvWonmanZodiac   = ResHelper.forceCast(findViewById(R.id.tvWonmanZodiac));

		api = (Marriage) MobAPI.getAPI(Marriage.NAME);
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
		tvMarriageType.setText(com.mob.tools.utils.ResHelper.toString(res.get("marriageType")));
		tvMenLunar.setText(com.mob.tools.utils.ResHelper.toString(res.get("menLunar")));
		tvMenLunarTime.setText(com.mob.tools.utils.ResHelper.toString(res.get("menLunarTime")));
		tvMenMarriage.setText(com.mob.tools.utils.ResHelper.toString(res.get("menMarriage")));
		tvMenZodiac.setText(com.mob.tools.utils.ResHelper.toString(res.get("menZodiac")));
		tvWomanLunar.setText(com.mob.tools.utils.ResHelper.toString(res.get("womanLunar")));
		tvWonmanLuarTime.setText(com.mob.tools.utils.ResHelper.toString(res.get("wonmanLuarTime")));
		tvWonmanMarriage.setText(com.mob.tools.utils.ResHelper.toString(res.get("wonmanMarriage")));
		tvWonmanZodiac.setText(com.mob.tools.utils.ResHelper.toString(res.get("wonmanZodiac")));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		String manDate = spManYear.getSelectedItem() + "-" + spManMonth.getSelectedItem() + "-" + spManDay.getSelectedItem();
		String manHour = (String) spManHour.getSelectedItem();
		String womenDate = spWomenYear.getSelectedItem() + "-" + spWomenMonth.getSelectedItem() + "-" + spWomenDay.getSelectedItem();
		String womenHour = (String) spWomenHour.getSelectedItem();
		api.queryMarriage(manDate, manHour, womenDate, womenHour, this);
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}
}
