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
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FootballLeagueRoundActivity extends ListActivity implements APICallback, AdapterView.OnItemClickListener {
	private int type;	// 0查询某个轮次的比赛信息; 1查询队伍的比赛信息

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getListView().setBackgroundColor(0xffffffff);
		getListView().setDivider(new ColorDrawable(0xff7f7f7f));
		getListView().setDividerHeight(1);
		getListView().setOnItemClickListener(this);

		String leagueTypeCn = getIntent().getExtras().getString("leagueTypeCn");
		String season = getIntent().getExtras().getString("season");
		int maxRound = getIntent().getExtras().getInt("maxRound");
		String teamA = getIntent().getExtras().getString("teamA");
		String teamB = getIntent().getExtras().getString("teamB");
		type = getIntent().getExtras().getInt("type");

		FootballLeague api = ResHelper.forceCast(MobAPI.getAPI(FootballLeague.NAME));

		if (type == 0) {
			//随机获取round
			String round = String.valueOf(new Random().nextInt(maxRound));
			setTitle(getString(R.string.footballleague_api_title_round_info, leagueTypeCn, round));
			//查询某个轮次的比赛信息
			api.queryMatchInfoByRound(leagueTypeCn, season, round, this);
		} else if (type == 1) {
			int round = getIntent().getExtras().getInt("round");
			//模拟teamA和teamB只有一个的情况
			if (Math.random() < 0.3) {
				teamA = null;
				setTitle(getString(R.string.footballleague_api_title_team_info_a, teamB));
			} else if (Math.random() < 0.7) {
				teamB = null;
				setTitle(getString(R.string.footballleague_api_title_team_info_a, teamA));
			} else {
				setTitle(getString(R.string.footballleague_api_title_team_info_ab, teamA, teamB));
			}
			//查询队伍的比赛信息
			api.queryMatchInfoByTeam(leagueTypeCn,
					teamA, teamB, season, String.valueOf(round), this);
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (type == 1) {
			return;
		}
		try {
			Map<String, Object> item = (Map<String, Object>) parent.getAdapter().getItem(position);
			Intent intent = new Intent(this, FootballLeagueRoundActivity.class);
			intent.putExtra("leagueTypeCn", String.valueOf(item.get("leagueTypeCn")));
			intent.putExtra("season", String.valueOf(item.get("season")));
			intent.putExtra("round", Integer.parseInt(String.valueOf(item.get("round"))));
			intent.putExtra("teamA", String.valueOf(item.get("homeTeam")));
			intent.putExtra("teamB", String.valueOf(item.get("visitors")));
			intent.putExtra("type", 1);
			startActivity(intent);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private String getInfo(List<Map<String, Object>> info) {
		StringBuffer sb = new StringBuffer();
		if (info != null && info.size() > 0) {
			for (Map<String, Object> infoItem : info) {
				sb.append(getString(R.string.footballleague_api_item_goal_info, String.valueOf(infoItem.get("goalPlayerNameCn"))
						, String.valueOf(infoItem.get("goalTime"))));
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		try {
			List<Map<String, Object>> res = (List<Map<String, Object>>) result.get("result");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (res != null && !res.isEmpty()) {
				Map<String, Object> tmp;
				for (Map<String, Object> item : res) {
					tmp = new HashMap<String, Object>();
					tmp.putAll(item);
					tmp.put("homeInfo", getInfo((List<Map<String, Object>>) item.get("homeTeamMatchInfo")));
					tmp.put("visitorsInfo", getInfo((List<Map<String, Object>>) item.get("visitorsMatchInfo")));
					list.add(tmp);
				}
			}
			getListView().setAdapter(new MyAdapter(this, list));
			if (type == 0) {
				TextView headView = new TextView(getBaseContext());
				headView.setTextSize(14);
				headView.setTextColor(0xFF999999);
				headView.setGravity(Gravity.CENTER);
				headView.setPadding(0, 10, 0, 10);
				headView.setText(R.string.footballleague_api_round_hint);
				getListView().addHeaderView(headView);
			}
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
				convertView = LayoutInflater.from(context).inflate(R.layout.view_footballleague_round_list_item, parent, false);
			}
			Map<String, Object> item = getItem(position);
			TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
			TextView tvHomeTeam = (TextView) convertView.findViewById(R.id.tvHomeTeam);
			TextView tvHomeTeamMatchInfo = (TextView) convertView.findViewById(R.id.tvHomeTeamMatchInfo);
			TextView tvVisitorsMatchInfo = (TextView) convertView.findViewById(R.id.tvVisitorsMatchInfo);
			TextView tvHomeTeamScore = (TextView) convertView.findViewById(R.id.tvHomeTeamScore);
			TextView tvLeagueTypeCn = (TextView) convertView.findViewById(R.id.tvLeagueTypeCn);
			TextView tvMatchCity = (TextView) convertView.findViewById(R.id.tvMatchCity);
			TextView tvRound = (TextView) convertView.findViewById(R.id.tvRound);
			TextView tvSeason = (TextView) convertView.findViewById(R.id.tvSeason);
			TextView tvSituation = (TextView) convertView.findViewById(R.id.tvSituation);
			TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
			TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			TextView tvVisitors = (TextView) convertView.findViewById(R.id.tvVisitors);
			TextView tvVisitorsScore = (TextView) convertView.findViewById(R.id.tvVisitorsScore);

			tvDate.setText(String.valueOf(item.get("date")));
			tvHomeTeam.setText(String.valueOf(item.get("homeTeam")));
			tvHomeTeamMatchInfo.setText(String.valueOf(item.get("homeInfo")));
			tvVisitorsMatchInfo.setText(String.valueOf(item.get("visitorsInfo")));
			tvHomeTeamScore.setText(String.valueOf(item.get("homeTeamScore")));
			tvLeagueTypeCn.setText(String.valueOf(item.get("leagueTypeCn")));
			tvMatchCity.setText(String.valueOf(item.get("matchCity")));
			tvRound.setText(String.valueOf(item.get("round")));
			tvSeason.setText(String.valueOf(item.get("season")));
			tvSituation.setText(String.valueOf(item.get("situation")));
			tvStatus.setText(String.valueOf(item.get("status")));
			tvTime.setText(String.valueOf(item.get("time")));
			tvVisitors.setText(String.valueOf(item.get("visitors")));
			tvVisitorsScore.setText(String.valueOf(item.get("visitorsScore")));
			return convertView;
		}
	}
}
