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

public class MainLayoutAdapter extends RecyclerView.Adapter {

    List<SimpleMovie> date;
    Context mContext;
    ItemClickListener linstener;

    public MainLayoutAdapter(Context context, List<SimpleMovie> value){
        this.date = value;
        this.mContext = context;
    }

    public void setLinstener(ItemClickListener linstener){
        this.linstener = linstener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_item_layout,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        System.out.println(date.get(position).getTitle());
        ((ViewHolders)holder).tvName.setText(date.get(position).getTitle());
        Object img;
        if(date.get(position).getImage().equals("custom_image.png")) {
            if (date.get(position).getSubtype().equals("file")) {
                img = R.mipmap.file_greens;
            } else {
                img = R.mipmap.video_green;
            }
        }else{
            img = URLConst.IMAGEPRE()+date.get(position).getImage();
        }

        Glide.with(mContext).load(img)
                .into(((ViewHolders) holder).mvImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linstener.ItemClick(date.get(position).getMainID(),date.get(position).getSubtype());
            }
        });
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    class ViewHolders extends RecyclerView.ViewHolder{
        ImageView mvImage;
        TextView tvName;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            mvImage = itemView.findViewById(R.id.mv_img);
            tvName = itemView.findViewById(R.id.mv_name);
        }
    }
}
