/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.History;
import com.mob.tools.utils.ResHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nico.styTool.R;

public class HistoryAPIActivity extends dump.z.BaseActivity_ implements APICallback, OnItemSelectedListener {
    private ListView lvHistory;
    private Spinner spMonth;
    private Spinner spDay;
    private History api;
    private String currentDay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lvHistory = ResHelper.forceCast(findViewById(R.id.lvHistory));
        lvHistory.setSelector(new ColorDrawable(0));

        spMonth = ResHelper.forceCast(findViewById(R.id.spMonth));
        spMonth.setOnItemSelectedListener(this);
        ArrayList<String> monthList = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            monthList.add(IntegerToString(i));
        }
        spMonth.setAdapter(new ArrayAdapter<String>(this, R.layout.view_spinner, monthList));
        spDay = ResHelper.forceCast(findViewById(R.id.spDay));
        spDay.setOnItemSelectedListener(this);

        api = ResHelper.forceCast(MobAPI.getAPI(History.NAME));
        showToday();
    }

    private void showToday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] dates = dateFormat.format(new java.util.Date()).split("-");
        String currentMonth = dates[1];
        currentDay = dates[2];

        spMonth.setSelection(Integer.parseInt(currentMonth) - 1);
        spDay.setAdapter(new ArrayAdapter<String>(this, R.layout.view_spinner, getDays(Integer.parseInt(currentMonth))));
        spDay.setSelection(Integer.parseInt(currentDay) - 1);
    }

    public void onSuccess(API api, int action, Map<String, Object> result) {
        ArrayList<HashMap<String, Object>> results = ResHelper.forceCast(result.get("result"));
        HistorySimpleAdapter adapter = new HistorySimpleAdapter(this, results);
        lvHistory.setAdapter(adapter);
    }

    private String IntegerToString(int index) {
        if (index < 10) {
            return "0" + index;
        }

        return "" + index;
    }

    private ArrayList<String> getDays(int month) {
        if (month < 0 || month > 12) {
            return null;
        }

        ArrayList<String> ret = new ArrayList<String>();
        // 0 ~ 29
        for (int i = 1; i < 30; i++) {
            ret.add(IntegerToString(i));
        }

        if (month == 2) {
            return ret;
        } else if (4 == month || 6 == month || 9 == month || 11 == month) {
            ret.add(Integer.toString(30));
        } else {
            // 1 3 5 7 8 12 month
            ret.add(ResHelper.toString(30));
            ret.add(ResHelper.toString(31));
        }

        return ret;
    }

    public void onError(API api, int action, Throwable details) {
        details.printStackTrace();
        Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        if (parent.equals(spMonth)) {
            spDay.setAdapter(new ArrayAdapter<String>(this, R.layout.view_spinner, getDays(position + 1)));
            spDay.setSelection(Integer.parseInt(currentDay) - 1);
        } else if (parent.equals(spDay)) {
            currentDay = Integer.toString(position + 1);
            String date = "" + spMonth.getSelectedItem() + spDay.getSelectedItem();
            api.queryHistory(date, this);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class HistorySimpleAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<HashMap<String, Object>> results;

        HistorySimpleAdapter(Context context, ArrayList<HashMap<String, Object>> res) {
            inflater = LayoutInflater.from(context);
            results = res;
        }

        public int getCount() {
            return results.size();
        }

        public Object getItem(int position) {
            return results.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        private String getDateFormat(String date) {
            String year = date.substring(0, 4);
            String month = date.substring(4, 6);
            String day = date.substring(6, 8);
            return year + "-" + month + "-" + day;
        }

        @SuppressLint("ViewHolder")
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.view_history_item, null);
            TextView tvDate = ResHelper.forceCast(convertView.findViewById(R.id.tvDate));
            TextView tvMonth = ResHelper.forceCast(convertView.findViewById(R.id.tvMonth));
            TextView tvDay = ResHelper.forceCast(convertView.findViewById(R.id.tvDay));
            TextView tvTitle = ResHelper.forceCast(convertView.findViewById(R.id.tvTitle));
            TextView tvEvent = ResHelper.forceCast(convertView.findViewById(R.id.tvEvent));

            HashMap<String, Object> res = results.get(position);
            String date = null;
            try {
                date = ResHelper.toString(res.get("date"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // date format yyyymmdd
            try {
                tvDate.setText(getDateFormat(date));
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvMonth.setText(com.mob.tools.utils.ResHelper.toString(res.get("month")));
            tvDay.setText(com.mob.tools.utils.ResHelper.toString(res.get("day")));
            tvTitle.setText(com.mob.tools.utils.ResHelper.toString(res.get("title")));
            tvEvent.setText(com.mob.tools.utils.ResHelper.toString(res.get("event")));
            return convertView;
        }
    }
}
