package dump.b;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dump.filem.FileUtil;
import dump.filem.adapter.FileAdapter;
import dump.filem.adapter.FileHolder;
import dump.filem.adapter.TitleAdapter;
import dump.filem.adapter.base.RecyclerViewAdapter;
import dump.filem.bean.FileBean;
import dump.filem.bean.FileType;
import dump.filem.bean.TitlePath;
import nico.styTool.R;

public class Main5Activity extends Fragment {


    private RecyclerView title_recycler_view;
    private RecyclerView recyclerView;
    private FileAdapter fileAdapter;
    private List<FileBean> beanList = new ArrayList<>();
    private File rootFile;
    private LinearLayout empty_rel;
    private int PERMISSION_CODE_WRITE_EXTERNAL_STORAGE = 100;
    private String rootPath;
    private TitleAdapter titleAdapter;
    //  private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_5main, null);

        //RecyclerView recyclerView = view.findViewById(R.id.listview01);
        //设置Title
        title_recycler_view = (RecyclerView) view.findViewById(R.id.title_recycler_view);
        title_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        titleAdapter = new TitleAdapter(getActivity(), new ArrayList<TitlePath>());
        title_recycler_view.setAdapter(titleAdapter);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        fileAdapter = new FileAdapter(getActivity(), beanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(fileAdapter);

        empty_rel = (LinearLayout) view.findViewById(R.id.empty_rel);

        fileAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                if (viewHolder instanceof FileHolder) {
                    FileBean file = beanList.get(position);
                    FileType fileType = file.getFileType();
                    if (fileType == FileType.directory) {
                        getFile(file.getPath());

                        refreshTitleState(file.getName(), file.getPath());
                    } else if (fileType == FileType.apk) {
                        //安装app
                        FileUtil.openAppIntent(getActivity(), new File(file.getPath()));
                    } else if (fileType == FileType.image) {
                        FileUtil.openImageIntent(getActivity(), new File(file.getPath()));
                    } else if (fileType == FileType.txt) {
                        FileUtil.openTextIntent(getActivity(), new File(file.getPath()));
                    } else if (fileType == FileType.music) {
                        FileUtil.openMusicIntent(getActivity(), new File(file.getPath()));
                    } else if (fileType == FileType.video) {
                        FileUtil.openVideoIntent(getActivity(), new File(file.getPath()));
                    } else {
                        FileUtil.openApplicationIntent(getActivity(), new File(file.getPath()));
                    }
                }
            }
        });

        fileAdapter.setOnItemLongClickListener(new RecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                if (viewHolder instanceof FileHolder) {
                    FileBean fileBean = (FileBean) fileAdapter.getItem(position);
                    FileType fileType = fileBean.getFileType();
                    if (fileType != null && fileType != FileType.directory) {
                        FileUtil.sendFile(getActivity(), new File(fileBean.getPath()));
                    }
                }
                return false;
            }
        });

        titleAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                TitlePath titlePath = (TitlePath) titleAdapter.getItem(position);
                getFile(titlePath.getPath());

                int count = titleAdapter.getItemCount();
                int removeCount = count - position - 1;
                for (int i = 0; i < removeCount; i++) {
                    titleAdapter.removeLast();
                }
            }
        });

        rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        refreshTitleState("内部存储设备", rootPath);
        getFile(rootPath);

        return view;
    }

    public void getFile(String path) {
        rootFile = new File(path + File.separator);
        new MyTask(rootFile).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
    }

    class MyTask extends AsyncTask {
        File file;

        MyTask(File file) {
            this.file = file;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            List<FileBean> fileBeenList = new ArrayList<>();
            if (file.isDirectory()) {
                File[] filesArray = file.listFiles();
                if (filesArray != null) {
                    List<File> fileList = new ArrayList<>();
                    Collections.addAll(fileList, filesArray);  //把数组转化成list
                    Collections.sort(fileList, FileUtil.comparator);  //按照名字排序

                    for (File f : fileList) {
                        if (f.isHidden()) continue;

                        FileBean fileBean = new FileBean();
                        fileBean.setName(f.getName());
                        fileBean.setPath(f.getAbsolutePath());
                        fileBean.setFileType(FileUtil.getFileType(f));
                        fileBean.setChildCount(FileUtil.getFileChildCount(f));
                        fileBean.setSize(f.length());
                        fileBean.setHolderType(0);

                        fileBeenList.add(fileBean);

                        FileBean lineBean = new FileBean();
                        lineBean.setHolderType(1);
                        fileBeenList.add(lineBean);

                    }
                }
            }

            beanList = fileBeenList;
            return fileBeenList;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (beanList.size() > 0) {
                empty_rel.setVisibility(View.GONE);
            } else {
                empty_rel.setVisibility(View.VISIBLE);
            }
            fileAdapter.refresh(beanList);
        }
    }

    void refreshTitleState(String title, String path) {
        TitlePath filePath = new TitlePath();
        filePath.setNameState(title + " > ");
        filePath.setPath(path);
        titleAdapter.addItem(filePath);
        title_recycler_view.smoothScrollToPosition(titleAdapter.getItemCount());
    }

}
