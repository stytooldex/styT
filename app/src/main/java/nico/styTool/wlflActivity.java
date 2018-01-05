package nico.styTool;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dump.a.RootManage;

public class wlflActivity extends dump.z.BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Override
    public void onClick(View p1) {
        // TODO: Implement this method
    }

    private ListView lvWifiList;
    private EditTextWithDel etSearch;
    private List<WifiInfo> wifiInfos = new ArrayList<>();
    private WifiAdapter adapter;
    private TextView tvNodata;
    List<WifiInfo> searchWifiInfo = null;
    android.support.v7.widget.Toolbar toolbar;
    public int mScreenWidth;
    public int mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wlfl_main);

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        initViews();
        initData();
    }

    Toast mToast;

    public void ShowToast() {
        if (!TextUtils.isEmpty("复制密码成功!")) {
            try {
                if (mToast == null) {
                    mToast = Toast.makeText(this, "复制密码成功!", Toast.LENGTH_SHORT);
                } else {
                    mToast.setText("复制密码成功!");
                }
                mToast.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initViews() {
        //btMore = (Button) findViewById(R.id.bt_more);
        lvWifiList = (ListView) findViewById(R.id.lv_wifi_list);
        etSearch = (EditTextWithDel) findViewById(R.id.et_search);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
//		初始化Toolbar控件
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });
    }

    public int dip2px(float dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (scale * dipValue + 0.5f);
    }

    //px转为dp
    public int px2dip(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void closeKeybord(android.support.v7.widget.AppCompatEditText mEditText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    public void closeKeybordACT(AutoCompleteTextView mEditText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    public void initListeners() {
        lvWifiList.setOnItemClickListener(this);
        lvWifiList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                String tempPass = "";
                if (searchWifiInfo != null) {
                    tempPass = searchWifiInfo.get(position).getPass();
                } else {
                    tempPass = wifiInfos.get(position).getPass();
                }

                ClipData clipData = ClipData.newPlainText("newPlainTextLabel", tempPass);
                clipboardManager.setPrimaryClip(clipData);

                if (TextUtils.isEmpty(tempPass)) {
                    //ShowToast("密码为空,复制失败!");
                } else {
                    ShowToast();
                }
                return true;
            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    lvWifiList.setVisibility(View.VISIBLE);
                    tvNodata.setVisibility(View.GONE);
                    adapter.clear();
                    adapter.AddAll(wifiInfos);
                    adapter.notifyDataSetChanged();
                    closeKeybord(etSearch);
                    searchWifiInfo = null;
                    return;
                }
                if (wifiInfos.size() > 0) {
                    //System.out.println("Editable:"+s.toString());
                    List<WifiInfo> info = getWifiMess(s.toString());
                    if (info != null && info.size() > 0) {
                        adapter.clear();
                        adapter.AddAll(info);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                }

                lvWifiList.setVisibility(View.GONE);
                tvNodata.setVisibility(View.VISIBLE);
                //etSearch.setVisibility(View.GONE);
            }
        });
    }


    public void initData() {
        if (!RootManage.isRoot()) {
            tvNodata.setText("Root权限获取失败，请确定手机是否已经root");
            lvWifiList.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
            etSearch.setVisibility(View.GONE);
            return;
        }

        wifiInfos = getWifiMess("");
        if (wifiInfos != null && wifiInfos.size() > 0) {
            adapter = new WifiAdapter(this);
            adapter.AddAll(wifiInfos);
            lvWifiList.setAdapter(adapter);
        } else {
            lvWifiList.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
            etSearch.setVisibility(View.GONE);
        }

        initListeners();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //生成二维码让小米链接
        Intent intent = new Intent(this, QRCodeView.class);
        if (searchWifiInfo != null) {
            intent.putExtra("str", "WIFI:T:" + searchWifiInfo.get(position).getKeymgmt() + ";P:\"" + searchWifiInfo.get(position).getPass() + "\";S:" + searchWifiInfo.get(position).getSsid() + ";");
        } else {
            intent.putExtra("str", "WIFI:T:" + wifiInfos.get(position).getKeymgmt() + ";P:\"" + wifiInfos.get(position).getPass() + "\";S:" + wifiInfos.get(position).getSsid() + ";");
        }
        startActivity(intent);

    }


    /**
     * 搜索
     *
     * @param name 要搜素的
     * @return 返回搜素的结果
     */
    private List<WifiInfo> getWifiMess(String name) {
        if (wifiInfos.size() <= 0) {
            try {
                return WifiManage.Read(wlflActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
                // ShowToastLong("读取密码失败:" + e.getMessage());
                return null;
            }
        }

        String searchName = "";
        if (!TextUtils.isEmpty(name)) {
            String[] arr = new String[name.length()];   //用一个数组存放每个字符添加正则表达式
            for (int i = 0; i < name.length(); i++) {

                String cache = name.substring(i, i + 1);

                if (Character.isLetter(cache.toCharArray()[0])) {
                    //是字母，转换为支持大小写
                    cache = "[" + cache.toLowerCase() + cache.toUpperCase() + "]";
                }

                arr[i] = ".*" + cache;
            }
            searchName = "";
            for (String anArr : arr) {
                searchName += anArr;
            }
            searchName += ".*";
        } else {
            searchName = ".*";
        }

        searchWifiInfo = new ArrayList<>();
        String oldName = "";
        for (int i = 0; i < wifiInfos.size(); i++) {
            oldName = wifiInfos.get(i).getSsid();
            Pattern ssid = Pattern.compile(searchName);
            Matcher ssidMatcher = ssid.matcher(oldName);
            if (ssidMatcher.find()) {
                searchWifiInfo.add(wifiInfos.get(i));
            }
        }
        return searchWifiInfo;

    }


}
