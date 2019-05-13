package demo.great.zhang.railwayvideo.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.eventbus.Message;

public class JZAutoNextStd extends JzvdStd {

    private boolean tyoe = false;
    private List<String> esp;
    private int espide = 0;

    private ImageView nextEs;

    public JZAutoNextStd(Context context) {
        super(context);
    }

    public JZAutoNextStd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        System.out.println("--------getLayoutId"+this);
        return R.layout.layout;
    }

    @Override
    public void init(Context context) {
        super.init(context);
        nextEs = findViewById(R.id.bt_next);
        nextEs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.bt_next){
            if(esp!=null){
                espide = espide+1;
                EventBus.getDefault().postSticky(new Message(espide));
                JZAutoNextStd.this.changeUrl(esp.get(espide),titleTextView.getText().toString(),0l);
                JZAutoNextStd.this.startButton.performClick();
            }
        }
    }

    @Override
    public void onAutoCompletion() {
        if(esp!=null){
            espide = espide+1;
            EventBus.getDefault().postSticky(new Message(espide));
            JZAutoNextStd.this.changeUrl(esp.get(espide),titleTextView.getText().toString(), 0l);
            startButton.performClick();
        }else{
            super.onAutoCompletion();
        }
    }


    @Override
    public void setUp(JZDataSource jzDataSource, int screen) {
        super.setUp(jzDataSource, screen);
        System.out.println("setUp");
        if(jzDataSource.objects!=null) {
            espide = (int) jzDataSource.objects[0];
            if(jzDataSource.objects.length >1) {
                esp = (List<String>) jzDataSource.objects[1];
            }
        }
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            if(jzDataSource.objects!=null) {
                nextEs.setVisibility(View.VISIBLE);
            }else{
                nextEs.setVisibility(GONE);
            }
        } else {
            nextEs.setVisibility(View.GONE);
        }
    }
}
