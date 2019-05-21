package demo.great.zhang.railwayvideo.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.entity.LocalFileEntity;

public class LocalFileAdapter extends RecyclerView.Adapter {

    LayoutInflater layoutInflater;
    List<LocalFileEntity> localFileEntities;
    fileClickListener listener;

    public LocalFileAdapter(Context context, List<LocalFileEntity> localFileEntities){
        layoutInflater = LayoutInflater.from(context);
        this.localFileEntities = localFileEntities;
        this.localFileEntities.add(0,new LocalFileEntity("文件名","文件大小","时间",""));
    }

    public void setListener(fileClickListener itemClickListener){
        this.listener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.local_file_item_layout,parent,false);
        return new locaFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        System.out.println("onBindViewHolder");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=0) {
                    listener.onClickItem(position-1);
                }
            }
        });
        ((locaFileViewHolder)holder).fName.setText(localFileEntities.get(position).getfName());
        ((locaFileViewHolder) holder).fSize.setText(localFileEntities.get(position).getfSize());
        if(localFileEntities.get(position).getfTime().contains(":")) {
            String[] times = localFileEntities.get(position).getfTime().split(" ");
            ((locaFileViewHolder) holder).fTime.setText(times[1]);
            ((locaFileViewHolder) holder).fdata.setText(times[0]);
        }else{
            ((locaFileViewHolder) holder).fTime.setText(localFileEntities.get(position).getfTime());
            ((locaFileViewHolder) holder).fdata.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return localFileEntities.size();
    }


    class locaFileViewHolder extends RecyclerView.ViewHolder{

        TextView fName,fSize,fTime,fdata;

        public locaFileViewHolder(@NonNull View itemView) {
            super(itemView);
            fName = itemView.findViewById(R.id.tv_file_name);
            fSize = itemView.findViewById(R.id.tv_file_size);
            fTime = itemView.findViewById(R.id.tv_file_time);
            fdata = itemView.findViewById(R.id.tv_file_date);
        }
    }

    public interface fileClickListener{
        void onClickItem(int position);
    }

}
