package demo.great.zhang.railwayvideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import demo.great.zhang.railwayvideo.R;

public class SubTypeAdapter extends Adapter {

    private Context mContext;
    private List<String> data;
    private SimpleItemClickListenner listenner;

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
