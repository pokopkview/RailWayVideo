package demo.great.zhang.railwayvideo.net;

public class URLConst {
    public static final String netURL = "http://2b3b707881.iok.la";
    public static String staticURL = "http://192.168.1.100:8080";
    public static String baseurl(){return staticURL;}
    public static String GETRECOMMEND(){return staticURL+"/recommend";}
    public static String GETRECENTLY(){return staticURL+"/recently";}
    public static String GETHOT(){return staticURL + "/movie/hot";}
    public static String GETGEN(){ return staticURL + "/movie/type";}
    public static String GETDETAIL(){return staticURL + "/getdetailmovie";}
    public static String GETMOVIEBYTITLE(){return staticURL + "/movie/search";}
    public static String SETWATCHED(){return staticURL + "/movie/watched";}
    public static String IMAGEPRE(){return staticURL+"/image/";}


}
