package demo.great.zhang.railwayvideo.entity;

public class LocalFileEntity {
    private String fName;
    private String fSize;
    private String fTime;
    private String url;


    public LocalFileEntity(String fName, String fSize, String fTime, String url) {
        this.fName = fName;
        this.fSize = fSize;
        this.fTime = fTime;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfSize() {
        return fSize;
    }

    public void setfSize(String fSize) {
        this.fSize = fSize;
    }

    public String getfTime() {
        return fTime;
    }

    public void setfTime(String fTime) {
        this.fTime = fTime;
    }
}
