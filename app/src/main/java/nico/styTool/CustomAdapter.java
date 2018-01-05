package nico.styTool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<File> fileList;
    private Context context;
    private OnItemClickLitener myOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View v, int pos, File file);
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.myOnItemClickLitener = onItemClickLitener;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public CustomAdapter(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fileName;
        private ImageView iv_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_fileName = (TextView) itemView.findViewById(R.id.tv_fileName);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_item_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        final File file = fileList.get(i);
        if (file.isFile()) {
            viewHolder.iv_icon.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.file));
        } else if (file.isDirectory()) {
            viewHolder.iv_icon.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.folder));
        }
        String fileName = file.getName();
        viewHolder.tv_fileName.setText(fileName);

        if (myOnItemClickLitener != null && file.isDirectory()) {
            viewHolder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnItemClickLitener.onItemClick(viewHolder.itemView, i, file);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }
}
