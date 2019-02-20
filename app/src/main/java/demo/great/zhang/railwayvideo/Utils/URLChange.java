package demo.great.zhang.railwayvideo.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLChange {


    public static String decodeURL(String encode){
        try {
            encode = URLEncoder.encode(encode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        encode = encode.replace("%3D",  "=");
        encode = encode.replace("%2F", "/");
        encode = encode.replace("+", "%20");
        encode = encode.replace("%26", "&");
        encode = encode.replace("%3A",":");
        return encode;
    }
}
