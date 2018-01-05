package com.mob.mobapi.sample.horoscope;

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
import com.mob.mobapi.apis.Horoscope;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

public class HoroscopeAPIActivity extends dump.z.BaseActivity_ implements APICallback, OnItemSelectedListener {
	private Spinner  spYear;
	private Spinner  spMonth;
	private Spinner  spDay;
	private Spinner  spHour;
	private TextView tvHoroscope;
	private TextView tvLunar;
	private TextView tvLunarDate;
	private TextView tvZodiac;
	private Horoscope api;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horoscope);

		spYear  = ResHelper.forceCast(findViewById(R.id.spYear));
		spMonth = ResHelper.forceCast(findViewById(R.id.spMonth));
		spDay   = ResHelper.forceCast(findViewById(R.id.spDay));
		spHour  = ResHelper.forceCast(findViewById(R.id.spHour));

		spYear.setOnItemSelectedListener(this);
		spMonth.setOnItemSelectedListener(this);
		spDay.setOnItemSelectedListener(this);
		spHour.setOnItemSelectedListener(this);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
		String[] dates = dateFormat.format(new java.util.Date()).split("-");
		int startyear = 1900;
		initSpinner(startyear, 2099, spYear); // year range(1900 - 2099)
		initSpinner(1, 12, spMonth);
		initSpinner(1, 31, spDay);
		initSpinner(0, 23, spHour);

		// set to today
		spYear.setSelection(Integer.parseInt(dates[0]) - startyear);
		spMonth.setSelection(Integer.parseInt(dates[1]) - 1);
		spDay.setSelection(Integer.parseInt(dates[2]) - 1);
		spHour.setSelection(Integer.parseInt(dates[3]));

		tvHoroscope = ResHelper.forceCast(findViewById(R.id.tvHoroscope));
		tvLunar     = ResHelper.forceCast(findViewById(R.id.tvLunar));
		tvLunarDate = ResHelper.forceCast(findViewById(R.id.tvLunarDate));
		tvZodiac    = ResHelper.forceCast(findViewById(R.id.tvZodiac));

		api = (Horoscope) MobAPI.getAPI(Horoscope.NAME);
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
		tvHoroscope.setText(com.mob.tools.utils.ResHelper.toString(res.get("horoscope")));
		tvLunar.setText(com.mob.tools.utils.ResHelper.toString(res.get("lunar")));
		tvLunarDate.setText(com.mob.tools.utils.ResHelper.toString(res.get("lunarDate")));
		tvZodiac.setText(com.mob.tools.utils.ResHelper.toString(res.get("zodiac")));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		String manDate = spYear.getSelectedItem() + "-" + spMonth.getSelectedItem() + "-" + spDay.getSelectedItem();
		String manHour = (String) spHour.getSelectedItem();
		api.queryHoroscope(manDate, manHour, this);
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}
}
