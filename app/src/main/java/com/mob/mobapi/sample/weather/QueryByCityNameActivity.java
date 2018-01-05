/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.weather;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Weather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nico.styTool.R;

public class QueryByCityNameActivity extends dump.z.BaseActivity_ implements APICallback, OnItemSelectedListener {
	private Spinner spProvince;
	private Spinner spCity;
	private Spinner spDistrict;

	private ArrayList<HashMap<String, Object>> resultList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_qbyid);
		spProvince = (Spinner) findViewById(R.id.spProvince);
		spProvince.setOnItemSelectedListener(this);
		spCity = (Spinner) findViewById(R.id.spCity);
		spCity.setOnItemSelectedListener(this);
		spDistrict = (Spinner) findViewById(R.id.spDistrict);
		spDistrict.setOnItemSelectedListener(this);

		// 获取API实例，请求支持预报的城市列表
		Weather api = (Weather) MobAPI.getAPI(Weather.NAME);
		api.getSupportedCities(this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		switch (action) {
			case Weather.ACTION_CITYS: onDistrictListGot(result); break;
			case Weather.ACTION_QUERY: onWeatherDetailsGot(result); break;
		}
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	@SuppressWarnings("unchecked")
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.equals(spProvince)) {
			ArrayList<HashMap<String, Object>> cityList
					= (ArrayList<HashMap<String, Object>>) resultList.get(position).get("city");
			ArrayList<String> cities = new ArrayList<String>();
			for (HashMap<String, Object> c : cityList) {
				cities.add(com.mob.tools.utils.ResHelper.toString(c.get("city")));
			}
			spCity.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, cities));
		} else if (parent.equals(spCity)) {
			ArrayList<HashMap<String, Object>> cityList
					= (ArrayList<HashMap<String, Object>>) resultList.get(spProvince.getSelectedItemPosition()).get("city");
			ArrayList<HashMap<String, Object>> districtList
					= (ArrayList<HashMap<String, Object>>) cityList.get(position).get("district");
			ArrayList<String> districts = new ArrayList<String>();
			if (districtList == null || districtList.size() == 0) {
				// 如果城市没有区县,则区县栏显示城市名
				districts.add((String) parent.getItemAtPosition(position));
			} else {
				for (HashMap<String, Object> d : districtList) {
					districts.add(com.mob.tools.utils.ResHelper.toString(d.get("district")));
				}
			}
			spDistrict.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, districts));
		} else if (parent.equals(spDistrict)) {
			// 根据选中的地址，查询天气情况

			String district = (String) spDistrict.getSelectedItem();
			Weather api = (Weather) MobAPI.getAPI(Weather.NAME);
			api.queryByCityName(district, this);
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}

	// 显示城市数据
	@SuppressWarnings("unchecked")
	private void onDistrictListGot(Map<String, Object> result) {
		resultList = (ArrayList<HashMap<String, Object>>) result.get("result");
		ArrayList<String> provinces = new ArrayList<String>();
		for (HashMap<String, Object> province : resultList) {
			provinces.add(com.mob.tools.utils.ResHelper.toString(province.get("province")));
		}
		spProvince.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, provinces));
	}

	// 显示天气数据
	private void onWeatherDetailsGot(Map<String, Object> result) {
		TextView tvWeather = (TextView) findViewById(R.id.tvWeather);
		TextView tvTemperature = (TextView) findViewById(R.id.tvTemperature);
		TextView tvHumidity = (TextView) findViewById(R.id.tvHumidity);
		TextView tvWind = (TextView) findViewById(R.id.tvWind);
		TextView tvSunrise = (TextView) findViewById(R.id.tvSunrise);
		TextView tvSunset = (TextView) findViewById(R.id.tvSunset);
		TextView tvAirCondition = (TextView) findViewById(R.id.tvAirCondition);
		TextView tvPollution = (TextView) findViewById(R.id.tvPollution);
		TextView tvCold = (TextView) findViewById(R.id.tvCold);
		TextView tvDressing = (TextView) findViewById(R.id.tvDressing);
		TextView tvExercise = (TextView) findViewById(R.id.tvExercise);
		TextView tvWash = (TextView) findViewById(R.id.tvWash);
		TextView tvCrawlerTime = (TextView) findViewById(R.id.tvCrawlerTime);

		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, Object>> results = (ArrayList<HashMap<String, Object>>) result.get("result");
		HashMap<String, Object> weather = results.get(0);
		tvWeather.setText(com.mob.tools.utils.ResHelper.toString(weather.get("weather")));
		tvTemperature.setText(com.mob.tools.utils.ResHelper.toString(weather.get("temperature")));
		tvHumidity.setText(com.mob.tools.utils.ResHelper.toString(weather.get("humidity")));
		tvWind.setText(com.mob.tools.utils.ResHelper.toString(weather.get("wind")));
		tvSunrise.setText(com.mob.tools.utils.ResHelper.toString(weather.get("sunrise")));
		tvSunset.setText(com.mob.tools.utils.ResHelper.toString(weather.get("sunset")));
		tvAirCondition.setText(com.mob.tools.utils.ResHelper.toString(weather.get("airCondition")));
		tvPollution.setText(com.mob.tools.utils.ResHelper.toString(weather.get("pollutionIndex")));
		tvCold.setText(com.mob.tools.utils.ResHelper.toString(weather.get("coldIndex")));
		tvDressing.setText(com.mob.tools.utils.ResHelper.toString(weather.get("dressingIndex")));
		tvExercise.setText(com.mob.tools.utils.ResHelper.toString(weather.get("exerciseIndex")));
		tvWash.setText(com.mob.tools.utils.ResHelper.toString(weather.get("washIndex")));
		String time = com.mob.tools.utils.ResHelper.toString(weather.get("updateTime"));
		String date = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
		time = time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12);
		tvCrawlerTime.setText(date + " " + time);

		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, Object>> weeks = (ArrayList<HashMap<String,Object>>) weather.get("future");
		if (weeks != null) {
			LinearLayout llWeekList = (LinearLayout) findViewById(R.id.llWeekList);
			llWeekList.removeAllViews();
			for (HashMap<String, Object> week : weeks) {
				View llWeek = View.inflate(this, R.layout.view_weather_week, null);
				llWeekList.addView(llWeek);

				TextView tvWeek = (TextView) llWeek.findViewById(R.id.tvWeek);
				TextView tvWeekTemperature = (TextView) llWeek.findViewById(R.id.tvWeekTemperature);
				TextView tvWeekDayTime = (TextView) llWeek.findViewById(R.id.tvWeekDayTime);
				TextView tvWeekNight = (TextView) llWeek.findViewById(R.id.tvWeekNight);
				TextView tvWeekWind = (TextView) llWeek.findViewById(R.id.tvWeekWind);

				tvWeek.setText(com.mob.tools.utils.ResHelper.toString(week.get("week")));
				tvWeekTemperature.setText(com.mob.tools.utils.ResHelper.toString(week.get("temperature")));
				tvWeekDayTime.setText(com.mob.tools.utils.ResHelper.toString(week.get("dayTime")));
				tvWeekNight.setText(com.mob.tools.utils.ResHelper.toString(week.get("night")));
				tvWeekWind.setText(com.mob.tools.utils.ResHelper.toString(week.get("wind")));
			}
		}
	}

}
