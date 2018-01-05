package nico.styTool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.InetAddress;


public class ServerControlActivity extends Fragment {
    private TextView ipText;

    // protected MyLog myLog = new MyLog(this.getClass().getName());

    //private AdvertConfig advertConfig;


    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: // We are being told to do a UI update
                    // If more than one UI update is queued up, we only need to
                    // do one.
                    removeMessages(0);
                    updateUi();
                    break;
                case 1: // We are being told to display an error message
                    removeMessages(1);
            }
        }
    };

    private TextView instructionText;

    private TextView instructionTextPre;

    private View startStopButton;

    private Activity mActivity;

    private View mRootView;

    public ServerControlActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        mRootView = inflater.inflate(R.layout.server_control_activity, container, false);

        // Set the application-wide context global, if not already set
        Context myContext = Globals.getContext();
        if (myContext == null) {
            myContext = mActivity.getApplicationContext();
            if (myContext == null) {
                throw new NullPointerException("Null context!?!?!?");
            }
            Globals.setContext(myContext);
        }

        ipText = mRootView.findViewById(R.id.ip_address);
        instructionText = mRootView.findViewById(R.id.instruction);
        instructionTextPre = mRootView.findViewById(R.id.instruction_pre);
        startStopButton = mRootView.findViewById(R.id.start_stop_button);
        startStopButton.setOnClickListener(startStopListener);

        updateUi();
        UiUpdater.registerClient(handler);

        // quickly turn on or off wifi.
        mRootView.findViewById(R.id.wifi_state_image).setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(
                                android.provider.Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    }
                });
        return mRootView;
    }

    /*
     @Override
     public boolean onBack() 
     {
     loadInterAd();
     return true;// 设置成false让back失效 ，true表示 不失效
     }


     */

    /**
     * Whenever we regain focus, we should update the button text depending on
     * the state of the server service.
     */
    public void onStart() {
        super.onStart();
        UiUpdater.registerClient(handler);
        updateUi();
    }

    public void onResume() {
        super.onResume();

        UiUpdater.registerClient(handler);
        updateUi();
        // Register to receive wifi status broadcasts
        //myLog.l(Log.DEBUG, "Registered for wifi updates");
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mActivity.registerReceiver(wifiReceiver, filter);
    }

    /*
     * Whenever we lose focus, we must unregister from UI update messages from
     * the FTPServerService, because we may be deallocated.
     */
    public void onPause() {
        super.onPause();
        UiUpdater.unregisterClient(handler);
        //myLog.l(Log.DEBUG, "Unregistered for wifi updates");
        mActivity.unregisterReceiver(wifiReceiver);
    }

    public void onStop() {
        super.onStop();
        UiUpdater.unregisterClient(handler);
    }

    public void onDestroy() {
        super.onDestroy();
        UiUpdater.unregisterClient(handler);
    }

    /**
     * This will be called by the static UiUpdater whenever the service has
     * changed state in a way that requires us to update our UI. We can't use
     * any myLog.l() calls in this function, because that will trigger an
     * endless loop of UI updates.
     */
    @SuppressLint("SetTextI18n")
    public void updateUi() {
        //myLog.l(Log.DEBUG, "Updating UI", true);
        //Error:Error: The WIFI_SERVICE must be looked up on the Application context or memory will leak on devices < Android N. Try changing mActivity to mActivity.getApplicationContext()  [WifiManagerLeak]

        WifiManager wifiMgr = (WifiManager) mActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //int wifiState = wifiMgr.getWifiState();
        WifiInfo info = wifiMgr.getConnectionInfo();
        String wifiId = info != null ? info.getSSID() : null;
        boolean isWifiReady = S_MVP.isWifiEnabled();

        setText(R.id.wifi_state, isWifiReady ? wifiId : getString(R.string.no_wifi_hint));
        ImageView wifiImg = mRootView.findViewById(R.id.wifi_state_image);
        wifiImg.setImageResource(isWifiReady ? R.drawable.wifi_file : R.drawable.wifi_automatic);

        boolean running = S_MVP.isRunning();
        if (running) {
            // myLog.l(Log.DEBUG, "updateUi: server is running", true);
            // Put correct text in start/stop button
            // Fill in wifi status and address
            InetAddress address = S_MVP.getWifiIp();
            if (address != null) {
                String port = ":" + S_MVP.getPort();
                ipText.setText("ftp://" + address.getHostAddress() + (S_MVP.getPort() == 21 ? "" : port));

            } else {
                // could not get IP address, stop the service
                Context context = mActivity.getApplicationContext();
                Intent intent = new Intent(context, S_MVP.class);
                context.stopService(intent);
                ipText.setText("");
            }
        }

        startStopButton.setEnabled(isWifiReady);
        TextView startStopButtonText = mRootView.findViewById(R.id.start_stop_button_text);
        if (isWifiReady) {
            startStopButtonText.setText(running ? R.string.stop_server : R.string.start_server);
            //startStopButtonText.setCompoundDrawablesWithIntrinsicBounds(running ? R.drawable.disconnect : R.drawable.connect, 0, 0, 0);
            //startStopButtonText.setTextColor(running ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorPrimary));
        } else {
            if (S_MVP.isRunning()) {
                Context context = mActivity.getApplicationContext();
                Intent intent = new Intent(context, S_MVP.class);
                context.stopService(intent);
            }

            startStopButtonText.setText(R.string.no_wifi);
            startStopButtonText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            startStopButtonText.setTextColor(Color.GRAY);
        }

        ipText.setVisibility(running ? View.VISIBLE : View.INVISIBLE);
        instructionText.setVisibility(running ? View.VISIBLE : View.GONE);
        instructionTextPre.setVisibility(running ? View.GONE : View.VISIBLE);
    }

    private void setText(int id, String text) {
        TextView tv = mRootView.findViewById(id);
        tv.setText(text);
    }

    OnClickListener startStopListener = new OnClickListener() {
        public void onClick(View v) {
            Globals.setLastError(null);
            File chrootDir = new File(Defaults.chrootDir);
            if (!chrootDir.isDirectory())
                return;

            Context context = mActivity.getApplicationContext();
            Intent intent = new Intent(context, S_MVP.class);

            Globals.setChrootDir(chrootDir);
            if (!S_MVP.isRunning()) {
                warnIfNoExternalStorage();
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    context.startService(intent);
                }
            } else {
                context.stopService(intent);
            }
        }
    };

    private void warnIfNoExternalStorage() {
        String storageState = Environment.getExternalStorageState();
        if (!storageState.equals(Environment.MEDIA_MOUNTED)) {
            //  myLog.i("Warning due to storage state " + storageState);
            Toast toast = Toast.makeText(mActivity, R.string.storage_warning, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        public void onReceive(Context ctx, Intent intent) {
            //  myLog.l(Log.DEBUG, "Wifi status broadcast received");
            updateUi();
        }
    };

    boolean requiredSettingsDefined() {
        SharedPreferences settings = mActivity.getSharedPreferences(Defaults.getSettingsName(), Defaults.getSettingsMode());
        String username = settings.getString("username", null);
        String password = settings.getString("password", null);
        return !(username == null || password == null);
    }

    /**
     * Get the settings from the FTPServerService if it's running, otherwise
     * load the settings directly from persistent storage.
     */
    SharedPreferences getSettings() {
        SharedPreferences settings = S_MVP.getSettings();
        if (settings != null) {
            return settings;
        } else {
            return mActivity.getPreferences(Activity.MODE_PRIVATE);
        }
    }


}

