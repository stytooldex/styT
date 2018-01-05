/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.weather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Weather;
import nico.styTool.R;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.Hashon;

public class QueryByIpActivity extends dump.z.BaseActivity_ implements APICallback, OnClickListener {
	private EditText etIP;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_qbyip);
		etIP = (EditText) findViewById(R.id.etIP);
		findViewById(R.id.btnSearch).setOnClickListener(this);

		// 获取API实例，请求支持预报的城市列表
		Weather api = (Weather) MobAPI.getAPI(Weather.NAME);
		api.getSupportedCities(this);

		new Thread(){
			public void run() {
				String ip = null;
				try {
					NetworkHelper network = new NetworkHelper();
					ArrayList<KVPair<String>> values = new ArrayList<KVPair<String>>();
					values.add(new KVPair<String>("ie", "utf-8"));
					String resp = network.httpGet("http://pv.sohu.com/cityjson", values, null, null);
					resp = resp.replace("var returnCitySN = {", "{").replace("};", "}");
					ip = (String) (new Hashon().fromJson(resp).get("cip"));
				} catch (Throwable t) {
					t.printStackTrace();
				}

				if (ip != null) {
					final String finalIp = ip;
					runOnUiThread(new Runnable() {
						public void run() {
							etIP.setHint(finalIp);
							if (etIP.getText().length() <= 0) {
								etIP.setText(finalIp);
							}
						}
					});
				}
			}
		}.start();
	}

	public void onClick(View v) {
		Weather api = (Weather) MobAPI.getAPI(Weather.NAME);
		api.queryByIPAddress(etIP.getText().toString(), this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		switch (action) {
			case Weather.ACTION_IP: onWeatherDetailsGot(result); break;
		}
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
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
