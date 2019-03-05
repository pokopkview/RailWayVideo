package demo.great.zhang.railwayvideo.fragment;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Arrays;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.activity.SubTypeActivity;
import demo.great.zhang.railwayvideo.adapter.SimpleItemClickListenner;
import demo.great.zhang.railwayvideo.adapter.SubTypeAdapter;
import demo.great.zhang.railwayvideo.base.BaseFragment;
import demo.great.zhang.railwayvideo.widget.GridDividerItemDecoration;

public class FragmentSubtype extends BaseFragment {

    @BindView(R.id.iv_subtype_icon)
    ImageView ivSubtypeIcon;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.rv_subtype)
    RecyclerView rvSubtype;
    String [] subtype = {"剧情","喜剧","动作","爱情","科幻","动画","悬疑","惊悚","恐怖","犯罪"
            ,"战争","传记","音乐","歌舞","西部","奇幻","冒险","灾难","武侠","自定义"};

    @Override
    protected Object getContentLayout() {
        return R.layout.subtype_fragment_layout;
    }

    @Override
    protected void initNet() {
        SubTypeAdapter adapter = new SubTypeAdapter(getAppActivity(), Arrays.asList(subtype));
        rvSubtype.setAdapter(adapter);
        rvSubtype.addItemDecoration(new GridDividerItemDecoration(getAppActivity(),4,2,true,true,getAppActivity().getResources().getColor(R.color.nagative_blue)));
        rvSubtype.setLayoutManager(new GridLayoutManager(getAppActivity(),4));
        adapter.setItemClick(new SimpleItemClickListenner() {
            @Override
            public void itemClick(int position) {
                Intent intent = new Intent(getAppActivity(),SubTypeActivity.class);
                intent.putExtra("sub_name",subtype[position]);
                getAppActivity().startActivity(intent);
            }
        });
    }
}
