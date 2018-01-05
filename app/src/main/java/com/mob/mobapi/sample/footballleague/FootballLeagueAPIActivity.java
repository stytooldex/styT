/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.footballleague;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.FootballLeague;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nico.styTool.R;

public class FootballLeagueAPIActivity extends ListActivity implements AdapterView.OnItemClickListener, APICallback {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.footballleague_api_title_param);
		getListView().setBackgroundColor(0xffffffff);
		getListView().setDivider(new ColorDrawable(0xff7f7f7f));
		getListView().setDividerHeight(1);
		getListView().setOnItemClickListener(this);

		//参数词典查询
		FootballLeague api = ResHelper.forceCast(MobAPI.getAPI(FootballLeague.NAME));
		api.queryParam(this);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		try {
			Map<String, Object> item = (Map<String, Object>) parent.getAdapter().getItem(position);
			Intent intent = new Intent(this, FootballLeagueRoundActivity.class);
			intent.putExtra("leagueTypeCn", String.valueOf(item.get("leagueTypeCn")));
			intent.putExtra("season", String.valueOf(item.get("season")));
			intent.putExtra("maxRound", Integer.parseInt(String.valueOf(item.get("maxRound"))));
			intent.putExtra("type", 0);
			startActivity(intent);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		try {
			Map<String, Object> res = (Map<String, Object>) result.get("result");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (res != null && !res.isEmpty()) {
				Map<String, Object> map;
				for (Map.Entry<String, Object> tmp : res.entrySet()) {
					List<HashMap<String, Object>> value = (List<HashMap<String, Object>>) tmp.getValue();
					if (value != null && value.size() > 0) {
						for (Map<String, Object> info : value) {
							List<HashMap<String, Object>> teamInfoSet = (List<HashMap<String, Object>>) info.get("teamInfoSet");
							if (teamInfoSet != null && teamInfoSet.size() > 0) {
								for (Map<String, Object> item : teamInfoSet) {
									map = new HashMap<String, Object>();
									map.put("name", tmp.getKey());
									map.put("leagueTypeCn", info.get("leagueTypeCn"));
									map.put("maxRound", info.get("maxRound"));
									map.put("season", info.get("season"));
									map.putAll(item);
									list.add(map);
								}
							}
						}
					}
				}
			}
			getListView().setAdapter(new MyAdapter(this, list));
			TextView headView = new TextView(getBaseContext());
			headView.setTextSize(14);
			headView.setTextColor(0xFF999999);
			headView.setGravity(Gravity.CENTER);
			headView.setPadding(0, 10, 0, 10);
			headView.setText(R.string.footballleague_api_param_hint);
			getListView().addHeaderView(headView);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	private static class MyAdapter extends BaseAdapter {
		private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		private Context context;

		public MyAdapter(Context context, List<Map<String, Object>> list) {
			this.context = context;
			if (list != null) {
				this.list.clear();
				this.list.addAll(list);
			}
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Map<String, Object> getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.view_footballleague_list_item, parent, false);
			}
			TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
			View llInfo = convertView.findViewById(R.id.llInfo);
			TextView tvLeagueTypeCn = (TextView) convertView.findViewById(R.id.tvLeagueTypeCn);
			TextView tvMaxRound = (TextView) convertView.findViewById(R.id.tvMaxRound);
			TextView tvSeason = (TextView) convertView.findViewById(R.id.tvSeason);
			Map<String, Object> item = getItem(position);
			if (showName(position)) {
				tvName.setVisibility(View.VISIBLE);
				llInfo.setVisibility(View.VISIBLE);
				tvName.setText(String.valueOf(item.get("name")));
				tvLeagueTypeCn.setText(String.valueOf(item.get("leagueTypeCn")));
				tvMaxRound.setText(String.valueOf(item.get("maxRound")));
				tvSeason.setText(String.valueOf(item.get("season")));
			} else {
				tvName.setVisibility(View.GONE);
				llInfo.setVisibility(View.GONE);
			}
			TextView tvTeamNameCn = (TextView) convertView.findViewById(R.id.tvTeamNameCn);
			TextView tvTeamLogoUrl = (TextView) convertView.findViewById(R.id.tvTeamLogoUrl);
			tvTeamNameCn.setText(String.valueOf(item.get("teamNameCn")));
			tvTeamLogoUrl.setText(String.valueOf(item.get("teamLogoUrl")));
			return convertView;
		}

		private boolean showName(int position) {
			if (position > 0 && String.valueOf(getItem(position).get("name")).equals(
					String.valueOf(getItem(position - 1).get("name")))) {
				return false;
			}
			return true;
		}
	}
}
