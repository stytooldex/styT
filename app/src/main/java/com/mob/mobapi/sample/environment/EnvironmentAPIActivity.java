package com.mob.mobapi.sample.environment;

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
import com.mob.mobapi.apis.Environment;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

public class EnvironmentAPIActivity extends dump.z.BaseActivity_ implements OnClickListener, APICallback {
	private EditText etCity;
	private TextView tvProvince;
	private TextView tvCity;
	private TextView tvDistrict;
	private TextView tvAqi;
	private TextView tvNo2;
	private TextView tvPm10;
	private TextView tvPm25;
	private TextView tvQuality;
	private TextView tvSo2;
	private LinearLayout llHourData;
	private LinearLayout llFetureData;
	private TextView tvUpdateTime;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_environment);
		etCity     = ResHelper.forceCast(findViewById(R.id.etCity));
		tvProvince = ResHelper.forceCast(findViewById(R.id.tvProvince));
		tvCity     = ResHelper.forceCast(findViewById(R.id.tvCity));
		tvDistrict = ResHelper.forceCast(findViewById(R.id.tvDistrict));
		tvAqi  = ResHelper.forceCast(findViewById(R.id.tvAqi));
		tvNo2  = ResHelper.forceCast(findViewById(R.id.tvNo2));
		tvPm10 = ResHelper.forceCast(findViewById(R.id.tvPm10));
		tvPm25 = ResHelper.forceCast(findViewById(R.id.tvPm25));
		tvQuality = ResHelper.forceCast(findViewById(R.id.tvQuality));
		tvSo2     = ResHelper.forceCast(findViewById(R.id.tvSo2));
		llHourData   = ResHelper.forceCast(findViewById(R.id.llHourData));
		llFetureData = ResHelper.forceCast(findViewById(R.id.llFetureData));
		tvUpdateTime = ResHelper.forceCast(findViewById(R.id.tvUpdateTime));
		findViewById(R.id.btnSearch).setOnClickListener(this);
	}

	public void onClick(View v) {
		// 获取API实例，请求空气质量信息
		Environment api = ResHelper.forceCast(MobAPI.getAPI(Environment.NAME));
		api.query(etCity.getText().toString().trim(), null, this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) result.get("result");
		HashMap<String, Object> data = list.get(0);
		llHourData.setVisibility(View.GONE);
		((LinearLayout) llHourData.getChildAt(2)).removeAllViews();
		llFetureData.setVisibility(View.GONE);
		((LinearLayout) llFetureData.getChildAt(2)).removeAllViews();
		updateUI(data);
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	private void updateUI(HashMap<String, Object> result) {
		tvProvince.setText(ResHelper.toString(result.get("province")));
		tvCity.setText(ResHelper.toString(result.get("city")));
		tvDistrict.setText(ResHelper.toString(result.get("district")));
		tvAqi.setText(ResHelper.toString(result.get("aqi")));
		tvNo2.setText(ResHelper.toString(result.get("no2")));
		tvPm10.setText(ResHelper.toString(result.get("pm10")));
		tvPm25.setText(ResHelper.toString(result.get("pm25")));
		tvQuality.setText(ResHelper.toString(result.get("quality")));
		tvSo2.setText(ResHelper.toString(result.get("so2")));
		tvUpdateTime.setText(ResHelper.toString(result.get("updateTime")));

		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, Object>> hourData = (ArrayList<HashMap<String,Object>>) result.get("hourData");
		if (hourData != null && hourData.size() > 0) {
			llHourData.setVisibility(View.VISIBLE);
			LinearLayout llList = (LinearLayout) llHourData.getChildAt(2);
			for (HashMap<String, Object> hour : hourData) {
				View llHour = View.inflate(this, R.layout.view_environment_hour, null);
				TextView tvTime = (TextView) llHour.findViewById(R.id.tvTime);
				String dateTime = ResHelper.toString(hour.get("dateTime"));
				tvTime.setText(dateTime.split(" ")[1]);
				TextView tvAqi = (TextView) llHour.findViewById(R.id.tvAqi);
				tvAqi.setText(ResHelper.toString(hour.get("aqi")));
				llList.addView(llHour);
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, Object>> fetureData = (ArrayList<HashMap<String,Object>>) result.get("fetureData");
		if (fetureData != null && fetureData.size() > 0) {
			llFetureData.setVisibility(View.VISIBLE);
			LinearLayout llList = (LinearLayout) llFetureData.getChildAt(2);
			for (HashMap<String, Object> data : fetureData) {
				View llDate = View.inflate(this, R.layout.view_environment_hour, null);
				TextView tvTime = (TextView) llDate.findViewById(R.id.tvTime);
				tvTime.setText(ResHelper.toString(data.get("date")));
				TextView tvAqi = (TextView) llDate.findViewById(R.id.tvAqi);
				String aqi = ResHelper.toString(data.get("aqi"));
				String quality = ResHelper.toString(data.get("quality"));
				tvAqi.setText(aqi + " (" + quality + ")");
				llList.addView(llDate);
			}
		}
	}

}
