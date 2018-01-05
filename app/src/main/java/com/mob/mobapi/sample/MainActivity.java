/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.mob.mobapi.API;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.sample.bankcard.BankCardAPIActivity;
import com.mob.mobapi.sample.boxoffice.BoxOfficeAPIActivity;
import com.mob.mobapi.sample.calendar.CalendarAPIActivity;
import com.mob.mobapi.sample.car.CarAPIActivity;
import com.mob.mobapi.sample.cook.CookAPIActivity;
import com.mob.mobapi.sample.custom.CustomAPIActivity;
import com.mob.mobapi.sample.dictionary.DictionaryAPIActivity;
import com.mob.mobapi.sample.domesticmetal.DomesticMetalAPIActivity;
import com.mob.mobapi.sample.dream.DreamAPIActivity;
import com.mob.mobapi.sample.environment.EnvironmentAPIActivity;
import com.mob.mobapi.sample.exchange.ExchangeAPIActivity;
import com.mob.mobapi.sample.flight.FlightAPIActivity;
import com.mob.mobapi.sample.footballleague.FootballLeagueAPIActivity;
import com.mob.mobapi.sample.globalstock.GlobalStockListAPIActivity;
import com.mob.mobapi.sample.gold.GoldAPIActivity;
import com.mob.mobapi.sample.health.HealthAPIActivity;
import com.mob.mobapi.sample.history.HistoryAPIActivity;
import com.mob.mobapi.sample.horoscope.HoroscopeAPIActivity;
import com.mob.mobapi.sample.idcard.IDCardAPIActivity;
import com.mob.mobapi.sample.idiom.IdiomAPIActivity;
import com.mob.mobapi.sample.iktoken.IKTokenAPIActivity;
import com.mob.mobapi.sample.ipstore.IPStoreAPIActivity;
import com.mob.mobapi.sample.laohuangli.LaoHuangLiAPIActivity;
import com.mob.mobapi.sample.lottery.LotteryAPIActivity;
import com.mob.mobapi.sample.marriage.MarriageAPIActivity;
import com.mob.mobapi.sample.mobile.MobileAPIActivity;
import com.mob.mobapi.sample.mobilelucky.MobileLuckyAPIActivity;
import com.mob.mobapi.sample.oilprice.OilPriceAPIActivity;
import com.mob.mobapi.sample.postcode.PostcodeAPIActivity;
import com.mob.mobapi.sample.sliver.SliverAPIActivity;
import com.mob.mobapi.sample.station.StationAPIActivity;
import com.mob.mobapi.sample.tiku.TiKuAPIActivity;
import com.mob.mobapi.sample.traintickets.TrainTicketsAPIActivity;
import com.mob.mobapi.sample.usercenter.UserCenterAPIActivity;
import com.mob.mobapi.sample.weather.WeatherAPIActivity;
import com.mob.mobapi.sample.wxarticle.WxArticleAPIActivity;

import nico.styTool.R;

public class MainActivity extends dump.z.BaseActivity_ implements OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob);
        findViewById(R.id.btnIntegrated).setOnClickListener(this);
        findViewById(R.id.btnCustomAPI).setOnClickListener(this);
        findViewById(R.id.btnWeatherAPI).setOnClickListener(this);
        findViewById(R.id.btnCookAPI).setOnClickListener(this);
        findViewById(R.id.btnPostcodeAPI).setOnClickListener(this);
        findViewById(R.id.btnMobileAPI).setOnClickListener(this);
        findViewById(R.id.btnStationAPI).setOnClickListener(this);
        findViewById(R.id.btnIDCardAPI).setOnClickListener(this);
        findViewById(R.id.btnEnvironmentAPI).setOnClickListener(this);
        findViewById(R.id.btnIPStoreAPI).setOnClickListener(this);
        findViewById(R.id.btnUcacheAPI).setOnClickListener(this);
        findViewById(R.id.btnMobileLuckyAPI).setOnClickListener(this);
        findViewById(R.id.btnBankCardAPI).setOnClickListener(this);
        findViewById(R.id.btnCalendarAPI).setOnClickListener(this);
        findViewById(R.id.btnLaoHuangLiAPI).setOnClickListener(this);
        findViewById(R.id.btnHealthAPI).setOnClickListener(this);
        findViewById(R.id.btnMarriageAPI).setOnClickListener(this);
        findViewById(R.id.btnHistoryAPI).setOnClickListener(this);
        findViewById(R.id.btnDreamAPI).setOnClickListener(this);
        findViewById(R.id.btnIdiomAPI).setOnClickListener(this);
        findViewById(R.id.btnDictionaryAPI).setOnClickListener(this);
        findViewById(R.id.btnHoroscopeAPI).setOnClickListener(this);
        findViewById(R.id.btnOilPriceApi).setOnClickListener(this);
        findViewById(R.id.btnLotteryApi).setOnClickListener(this);
        findViewById(R.id.btnWxArticleApi).setOnClickListener(this);
        findViewById(R.id.btnBoxOfficeApi).setOnClickListener(this);
        findViewById(R.id.btnGoldApi).setOnClickListener(this);
        findViewById(R.id.btnExchangeApi).setOnClickListener(this);
        findViewById(R.id.btnGlobalStockApi).setOnClickListener(this);
        findViewById(R.id.btnUserCenterApi).setOnClickListener(this);
        findViewById(R.id.btnSliverApi).setOnClickListener(this);
        findViewById(R.id.btnDomesticMetalApi).setOnClickListener(this);
        findViewById(R.id.btnIKTokenApi).setOnClickListener(this);
        findViewById(R.id.btnTrainTicketsApi).setOnClickListener(this);
        findViewById(R.id.btnFlightApi).setOnClickListener(this);
        findViewById(R.id.btnTiKuApi).setOnClickListener(this);
        findViewById(R.id.btnCarApi).setOnClickListener(this);
        findViewById(R.id.btnFootballLeagueApi).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIntegrated:
                //showIntegratedAPIs();
                break;
            case R.id.btnCustomAPI:
                startActivity(new Intent(this, CustomAPIActivity.class));
                break;
            case R.id.btnWeatherAPI:
                startActivity(new Intent(this, WeatherAPIActivity.class));
                break;
            case R.id.btnCookAPI:
                startActivity(new Intent(this, CookAPIActivity.class));
                break;
            case R.id.btnPostcodeAPI:
                startActivity(new Intent(this, PostcodeAPIActivity.class));
                break;
            case R.id.btnMobileAPI:
                startActivity(new Intent(this, MobileAPIActivity.class));
                break;
            case R.id.btnStationAPI:
                startActivity(new Intent(this, StationAPIActivity.class));
                break;
            case R.id.btnIDCardAPI:
                startActivity(new Intent(this, IDCardAPIActivity.class));
                break;
            case R.id.btnEnvironmentAPI:
                startActivity(new Intent(this, EnvironmentAPIActivity.class));
                break;
            case R.id.btnIPStoreAPI:
                startActivity(new Intent(this, IPStoreAPIActivity.class));
                break;
            case R.id.btnUcacheAPI:
                startActivity(new Intent(this, dump_dex.Activity.MainActivity.class));
                break;
            case R.id.btnMobileLuckyAPI:
                startActivity(new Intent(this, MobileLuckyAPIActivity.class));
                break;
            case R.id.btnBankCardAPI:
                startActivity(new Intent(this, BankCardAPIActivity.class));
                break;
            case R.id.btnCalendarAPI:
                startActivity(new Intent(this, CalendarAPIActivity.class));
                break;
            case R.id.btnLaoHuangLiAPI:
                startActivity(new Intent(this, LaoHuangLiAPIActivity.class));
                break;
            case R.id.btnHealthAPI:
                startActivity(new Intent(this, HealthAPIActivity.class));
                break;
            case R.id.btnMarriageAPI:
                startActivity(new Intent(this, MarriageAPIActivity.class));
                break;
            case R.id.btnHistoryAPI:
                startActivity(new Intent(this, HistoryAPIActivity.class));
                break;
            case R.id.btnDreamAPI:
                startActivity(new Intent(this, DreamAPIActivity.class));
                break;
            case R.id.btnIdiomAPI:
                startActivity(new Intent(this, IdiomAPIActivity.class));
                break;
            case R.id.btnDictionaryAPI:
                startActivity(new Intent(this, DictionaryAPIActivity.class));
                break;
            case R.id.btnHoroscopeAPI:
                startActivity(new Intent(this, HoroscopeAPIActivity.class));
                break;
            case R.id.btnOilPriceApi:
                startActivity(new Intent(this, OilPriceAPIActivity.class));
                break;
            case R.id.btnLotteryApi:
                startActivity(new Intent(this, LotteryAPIActivity.class));
                break;
            case R.id.btnWxArticleApi:
                startActivity(new Intent(this, WxArticleAPIActivity.class));
                break;
            case R.id.btnBoxOfficeApi:
                startActivity(new Intent(this, BoxOfficeAPIActivity.class));
                break;
            case R.id.btnGoldApi:
                startActivity(new Intent(this, GoldAPIActivity.class));
                break;
            case R.id.btnExchangeApi:
                startActivity(new Intent(this, ExchangeAPIActivity.class));
                break;
            case R.id.btnGlobalStockApi:
                startActivity(new Intent(this, GlobalStockListAPIActivity.class));
                break;
            case R.id.btnUserCenterApi:
                startActivity(new Intent(this, UserCenterAPIActivity.class));
                break;
            case R.id.btnSliverApi:
                startActivity(new Intent(this, SliverAPIActivity.class));
                break;
            case R.id.btnDomesticMetalApi:
                startActivity(new Intent(this, DomesticMetalAPIActivity.class));
                break;
            case R.id.btnIKTokenApi:
                startActivity(new Intent(this, IKTokenAPIActivity.class));
                break;
            case R.id.btnTrainTicketsApi:
                startActivity(new Intent(this, TrainTicketsAPIActivity.class));
                break;
            case R.id.btnFlightApi:
                startActivity(new Intent(this, FlightAPIActivity.class));
                break;
            case R.id.btnTiKuApi:
                startActivity(new Intent(this, TiKuAPIActivity.class));
                break;
            case R.id.btnCarApi:
                startActivity(new Intent(this, CarAPIActivity.class));
                break;
            case R.id.btnFootballLeagueApi:
                startActivity(new Intent(this, FootballLeagueAPIActivity.class));
                break;
        }
    }

    private void showIntegratedAPIs() {
        API[] apis = MobAPI.listAPI();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        StringBuilder sb = new StringBuilder();
        for (API api : apis) {
            sb.append("\n\t").append(api.getName());
        }
        String msg = getString(R.string.integrated_apis_x, sb.toString());
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.close, null);
        builder.show();
    }
}
