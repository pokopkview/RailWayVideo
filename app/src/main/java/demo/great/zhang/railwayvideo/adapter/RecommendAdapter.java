package demo.great.zhang.railwayvideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.entity.SimpleMovie;
import demo.great.zhang.railwayvideo.net.URLConst;

public class RecommendAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<SimpleMovie> data;
    ItemClickListener itemClickListener;

    public RecommendAdapter(Context context, List<SimpleMovie> simpleMovies){
        this.mContext = context;
        this.data = simpleMovies;
    }

    public void setCliclListener(ItemClickListener listener){
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.re_layout_item,parent,false);
        return new reViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((reViewHolder)holder).tvTitle.setText(data.get(position).getTitle());
        Integer img;
        if(data.get(position).getSubtype().equals("file")){
            img = R.mipmap.file_all;
        }else{
            img = R.mipmap.video_all;
        }
        Glide.with(mContext).load(img)
                .into(((reViewHolder) holder).ivPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.ItemClick(data.get(position).getMainID(),data.get(position).getSubtype());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class reViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        ImageView ivPoster;

        public reViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivPoster = itemView.findViewById(R.id.iv_poster);

        }
    }
}
