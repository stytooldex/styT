package dump.x;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import nico.styTool.R;

public class Main extends dump.z.BaseActivity_ {
    static final int stytool = 5;
    static final int CM_DETAILS = 4;
    static final int CM_FETCH = 3;
    static final int CM_FORCE_CLOSE = 2;
    static final int CM_LAUNCH = 1;
    ArrayList<ItemApk> alItemApk;
    ItemApkAdapter itemApkAdapter;
    ListView lvMain;

    private class BackupTask extends AsyncTask<String, Integer, Void> {
        String destFileName;
        ProgressDialog pd;

        BackupTask(Main context) {
            this.pd = new ProgressDialog(context);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pd.setMessage("正在提取APK进展");
            this.pd.show();
        }

        protected Void doInBackground(String... urls) {
            this.destFileName = urls[Main.CM_LAUNCH];
            try {
                File f1 = new File(urls[0]);
                File f2 = new File(urls[Main.CM_LAUNCH]);
                InputStream in = new FileInputStream(f1);
                OutputStream out = new FileOutputStream(f2);
                byte[] buf = new byte[1024];
                while (true) {
                    int len = in.read(buf);
                    if (len <= 0) {
                        break;
                    }
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (Exception e) {
                // Log.d(Main.TAG, e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.pd.cancel();
            Main.this.showToast(String.valueOf("APK已被提取到") + "\n" + this.destFileName);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_);
        setTitle("免root提取APK");

        this.lvMain = (ListView) findViewById(R.id.lvMain);
        registerForContextMenu(this.lvMain);
        this.lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.showContextMenuForChild(view);
            }
        });
    }

    public void onResume() {

        this.lvMain.setAdapter(this.itemApkAdapter);
        super.onResume();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("操作");
        if (this.alItemApk.get(acmi.position).getRunning()) {
            // menu.add(0, CM_FORCE_CLOSE, 0, R.string.force_close);
        } else {
            menu.add(0, CM_LAUNCH, 0, "启动");
        }
        menu.add(0, CM_DETAILS, 0, "市场查看");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public boolean onContextItemSelected(MenuItem item) {
        final ItemApk itemApk = this.alItemApk.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        switch (item.getItemId()) {
            case CM_LAUNCH /*1*/:
                itemApk.setRunning(true);
                this.itemApkAdapter.notifyDataSetChanged();
                startActivity(getPackageManager().getLaunchIntentForPackage(itemApk.getPackageName()));
                break;
            case CM_FORCE_CLOSE /*2*/:
                if (!getApplicationInfo().packageName.equals(itemApk.getPackageName())) {
                    ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).killBackgroundProcesses(itemApk.getPackageName());
                    itemApk.setRunning(false);
                    this.itemApkAdapter.notifyDataSetChanged();
                    break;
                }
                finish();
                break;
            case CM_FETCH /*3*/:
                String dn = PreferenceManager.getDefaultSharedPreferences(this).getString("pathSelector", String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + "/APKSTYTOOL");
                new File(dn).mkdirs();
                String fn = String.valueOf(dn) + "/" + itemApk.getPackageName() + "." + itemApk.getVersionName() + ".apk";
                BackupTask backupTask = new BackupTask(this);
                String[] strArr = new String[CM_FORCE_CLOSE];
                strArr[0] = itemApk.getSourceDir();
                strArr[CM_LAUNCH] = fn;
                backupTask.execute(strArr);
                break;
            case CM_DETAILS /*4*/:
                startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse("market://details?id=" + itemApk.getPackageName())));
                break;
            case stytool /*5*/:
                LayoutInflater inflater = LayoutInflater.from(Main.this);
                View view = inflater.inflate(R.layout.back_ma, null);
                final EditText b1 = view.findViewById(R.id.back_mm);//标题
                AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                builder.setView(view).setPositiveButton("发布", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String comment = b1.getText().toString().trim();
                        if (TextUtils.isEmpty(comment)) {

                            return;
                        }

                        //publishWeibo(b2.getText().toString(), comment);
                        String dn1 = PreferenceManager.getDefaultSharedPreferences(Main.this).getString("pathSelector", String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + "/APKSTYTOOL");
                        new File(dn1).mkdirs();
                        String fn1 = String.valueOf(dn1) + "/" + comment + "" + ".apk";
                        BackupTask backupTask1 = new BackupTask(Main.this);
                        String[] strArr1 = new String[CM_FORCE_CLOSE];
                        strArr1[0] = itemApk.getSourceDir();
                        strArr1[CM_LAUNCH] = fn1;
                        backupTask1.execute(strArr1);

                        Intent share = new Intent(Intent.ACTION_SEND);
                        //ComponentName component = new ComponentName("com.tencent.mobileqq","com.tencent.mobileqq.activity.JumpActivity");
                        //share.setComponent(component);
                        File file = new File(fn1 + "");
                        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                        share.setType("*/*");
                        startActivity(Intent.createChooser(share, "发送"));

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
                break;


        }
        return super.onContextItemSelected(item);
    }



    public void showToast(final String toast) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(Main.this, toast + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
