package demo.great.zhang.railwayvideo.entity;

public class SimpleMovie {


    /**
     * mainID : 1
     * title : 入殓师
     * image : 15490111637935988014021384242109.jpeg
     * genres : [剧情]
     * subtype : movie
     * recommend : 0
     * hot : 0
     */

    private int mainID;
    private String title;
    private String image;
    private String genres;
    private String subtype;
    private int recommend;
    private int hot;

    public int getMainID() {
        return mainID;
    }

    public void setMainID(int mainID) {
        this.mainID = mainID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }
}
