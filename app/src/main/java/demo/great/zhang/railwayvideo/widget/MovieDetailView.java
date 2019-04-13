package demo.great.zhang.railwayvideo.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import demo.great.zhang.railwayvideo.R;
import demo.great.zhang.railwayvideo.entity.DetailMovie;

public class MovieDetailView {

    private Activity mContext;
    private Paint mPaint;
    private DetailMovie detailMovie;
    private int color [] = {R.color.pink,R.color.azure,R.color.thistle,R.color.rosybrown,R.color.dimgray};


    public MovieDetailView(Activity context,DetailMovie detailMovie) {
        this.mContext = context;
        this.detailMovie = detailMovie;
        initView();
    }

    public byte[] initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.index_hot_layout,null,false);

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        WindowManager manager = mContext.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        view.layout(0,0,width,height);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

//        view.layout(0, 0,view.getMeasuredWidth(),view.getMeasuredHeight());
        view.layout(0, 0,view.getMeasuredWidth(),view.getMeasuredHeight());
        TextView tvra = view.findViewById(R.id.tv_rating);
        tvra.setText(null == detailMovie.getRating()?"0 分":detailMovie.getRating().getAverage()+" 分");
        ImageView imageView = view.findViewById(R.id.iv_type);
        if(detailMovie.getSubtype().equals("file")){
            imageView.setImageResource(R.mipmap.file_green);
        }else{
            imageView.setImageResource(R.mipmap.video_greens);
        }
        TextView tvDetail = view.findViewById(R.id.tv_detail);
        tvDetail.setText(detailMovie.getSummary());
        Bitmap bitmap = loadBitmapFromViewBySystem(view);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        return bytes;

    }
    public static Bitmap loadBitmapFromViewBySystem(View v) {
        if (v == null) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        return bitmap;
    }

}
