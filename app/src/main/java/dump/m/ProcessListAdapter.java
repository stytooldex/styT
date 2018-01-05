package dump.m;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nico.styTool.R;

public class ProcessListAdapter extends BaseAdapter
{
    private Context context;
    private List<ProcessInfo> processList;
    private viewHolder holder;
    private processListButtonClick listener;


    public ProcessListAdapter(Context context, List<ProcessInfo> processList,
			      processListButtonClick listener)
    {
        this.context = context;
        this.processList = processList;
        this.listener = listener;
    }

    @Override
    public int getCount()
    {
        return processList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return processList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ProcessInfo processInfo = (ProcessInfo)getItem(position);

        if (convertView == null)
        {
            holder = new viewHolder();
            convertView = View.inflate(context, R.layout.process_list_item, null);
            holder.imv_avatar = convertView.findViewById(R.id.imv_avatar);
            holder.tv_name = convertView.findViewById(R.id.tv_app_name);
            holder.tv_processName = convertView.findViewById(R.id.tv_app_process_name);
            holder.btn = convertView.findViewById(R.id.btn_stop_app);
        }
        else
        {
            holder = (viewHolder)convertView.getTag();
        }

        holder.imv_avatar.setImageDrawable(processInfo.getLabelIcon());
        holder.tv_name.setText(processInfo.getLabelName());
        holder.tv_processName.setText(processInfo.getProcessName());
        holder.btn.setOnClickListener(new POnClickListener(processInfo.getProcessName()));

        convertView.setTag(holder);

        return convertView;
    }

    private class POnClickListener implements View.OnClickListener
    {
        private String processName;

        public POnClickListener(String processName)
        {
            this.processName = processName;
        }

        @Override
        public void onClick(View v)
        {
            if (listener != null)
                listener.onButtonClick(processName);
        }
    }

    private class viewHolder
    {
        ImageView imv_avatar;
        TextView tv_name;
        TextView tv_processName;
        Button btn;
    }

    public interface processListButtonClick
    {
        void onButtonClick(String processName);
    }

}
