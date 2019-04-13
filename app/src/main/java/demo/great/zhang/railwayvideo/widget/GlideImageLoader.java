package demo.great.zhang.railwayvideo.widget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

import demo.great.zhang.railwayvideo.net.URLConst;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
//                .apply(new RequestOptions().transforms(new CenterCrop()))
                .into(imageView);
    }
}
