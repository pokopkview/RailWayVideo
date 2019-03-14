package demo.great.zhang.railwayvideo.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.activity.PlayVideoActivity;
import demo.great.zhang.railwayvideo.adapter.ItemClickListener;
import demo.great.zhang.railwayvideo.adapter.RecommendAdapter;
import demo.great.zhang.railwayvideo.base.BaseFragment;
import demo.great.zhang.railwayvideo.entity.ListObject;
import demo.great.zhang.railwayvideo.entity.SimpleMovie;
import demo.great.zhang.railwayvideo.viewmodel.SearchByTitleViewMode;

public class FragmentSearch extends BaseFragment {

    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.bt_search_confrim)
    Button btSearchConfrim;
    @BindView(R.id.rl_search)
    RecyclerView rlSearch;

    @Override
    protected Object getContentLayout() {
        return R.layout.search_fragment_layout;
    }

    private SearchByTitleViewMode viewMode;

    @Override
    protected void initNet() {
        viewMode = ViewModelProviders.of(getAppActivity()).get(SearchByTitleViewMode.class);
        viewMode.getSearchResult().observe(getAppActivity(),observer);
    }

    @Override
    protected void initView(View contentView) {

        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if((actionId == 0 || actionId ==3)&& event !=null ){
                    if(!etInput.getText().toString().isEmpty()) {
                        viewMode.search(etInput.getText().toString());
                    }else{
                        getAppActivity().showMsg("请出入内容！");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.bt_search_confrim)
    public void searchMovie(View view){
        if(!etInput.getText().toString().isEmpty()) {
            viewMode.search(etInput.getText().toString());
        }else{
            getAppActivity().showMsg("请出入内容！");
        }
    }



    private Observer<ListObject<SimpleMovie>> observer = new Observer<ListObject<SimpleMovie>>() {
        @Override
        public void onChanged(ListObject<SimpleMovie> simpleMovies) {
            InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
            if (manager != null)
                manager.hideSoftInputFromWindow(btSearchConfrim.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            RecommendAdapter adapter = new RecommendAdapter(getAppActivity(), simpleMovies.getList());
            rlSearch.setLayoutManager(new GridLayoutManager(getAppActivity(), 3));
            rlSearch.setAdapter(adapter);
            adapter.setCliclListener(itemClickListener);
        }
    };

    private ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void ItemClick(int mainID, String subtype) {
            Intent intent = new Intent(getAppActivity(), PlayVideoActivity.class);
            intent.putExtra("mainID", mainID);
            intent.putExtra("subtype", subtype);
            startActivity(intent);
        }
    };
}
