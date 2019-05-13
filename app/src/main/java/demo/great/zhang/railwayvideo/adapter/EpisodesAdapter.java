package demo.great.zhang.railwayvideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import demo.great.zhang.railwayvideo.R;

public class EpisodesAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> data;
    private int selectItem = 0;
    private EpisodeListener listener;

    public EpisodesAdapter(Context context, List<String> data){
        this.mContext = context;
        this.data = data;
    }

    public void setSelectListener(EpisodeListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.episode_item_layout,parent,false);
        return new EpisodeViewHolder(view);
    }

    public void setSelectItem(int itemcount){
        selectItem = itemcount;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((EpisodeViewHolder)holder).tvEpisode.setText((position+1)+"");
        if(position==selectItem){
            ((EpisodeViewHolder) holder).tvEpisode.setTextColor(mContext.getResources().getColor(R.color.nagative_blue));
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.episodes_shape));
        }else{
            ((EpisodeViewHolder) holder).tvEpisode.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.episodes_simple_shape));
        }
        holder.setIsRecyclable(false);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem = position;
                listener.ItemSelect(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class EpisodeViewHolder extends RecyclerView.ViewHolder{

        TextView tvEpisode;
        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEpisode = itemView.findViewById(R.id.tv_episodes);
        }
    }
    public interface EpisodeListener{
        void ItemSelect(int position);
    }

}
