package demo.great.zhang.railwayvideo.Utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import androidx.core.content.FileProvider;
import demo.great.zhang.railwayvideo.BuildConfig;
import demo.great.zhang.railwayvideo.MainActivity;

public class FileUtils {

    public static void openAssignFolder(Context context,String path){
        File file = new File(path);
        if(null==file || !file.exists()){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri photoURI = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                file);
        System.out.println(file);
        System.out.println(photoURI.toString());
        intent.setDataAndType(photoURI, "file/*");
        try {
            context.startActivity(intent);
//            startActivity(Intent.createChooser(intent,"选择浏览工具"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
