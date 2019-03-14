package demo.great.zhang.railwayvideo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import demo.great.zhang.railwayvideo.R;

public class SubTypeAdapter extends Adapter {

    private Context mContext;
    private List<String> data;
    private SimpleItemClickListenner listenner;
    private int colorResBg [] = {Color.rgb(245, 183, 177),
            Color.rgb(210, 180, 222),
            Color.rgb(174, 214, 241),
            Color.rgb(162, 217, 206),
            Color.rgb(171, 235, 198),
            Color.rgb(250, 215, 160),
            Color.rgb(237, 187, 153),
            Color.rgb(204, 209, 209)};

    private int colorResTx [] = {Color.rgb(169, 50, 38),
            Color.rgb(155, 89, 182),
            Color.rgb(41, 128, 185),
            Color.rgb(26, 188, 156),
            Color.rgb(39, 174, 96),
            Color.rgb(241, 196, 15),
            Color.rgb(230, 126, 34),
            Color.rgb(112, 123, 124)};

    public SubTypeAdapter(Context context, List<String> stringList){
        this.mContext = context;
        this.data = stringList;
    }

    public void setItemClick(SimpleItemClickListenner listenner){
        this.listenner = listenner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sub_item_layout,parent,false);
        return new SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SubViewHolder)holder).tvSubName.setText(data.get(position));

        int resbg = colorResBg[position%colorResBg.length];
        int restx = colorResTx[position%colorResBg.length];

        ((SubViewHolder) holder).tvSubName.setTextColor(restx);
        GradientDrawable gradientDrawable = (GradientDrawable) holder.itemView.getBackground();
        gradientDrawable.setColor(resbg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.itemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class SubViewHolder extends RecyclerView.ViewHolder{

        TextView tvSubName;

        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubName = itemView.findViewById(R.id.tv_subtype_name);
        }
    }
}
