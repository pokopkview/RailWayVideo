package demo.great.zhang.railwayvideo.entity;

import java.util.List;

public class DetailMovie {

    /**
     * mainID : 1
     * rating : {"max":10,"average":8,"stars":"45","min":0}
     * reviews_count : 3591
     * wish_count : 117230
     * douban_site :
     * year : 2008
     * images : {"small":"","large":"15490111637935988014021384242109.jpeg","medium":""}
     * alt : https://movie.douban.com/subject/2149806/
     * id : 2149806
     * mobile_url : https://movie.douban.com/subject/2149806/mobile
     * title : 入殓师
     * do_count : null
     * share_url : http://m.douban.com/movie/subject/2149806
     * seasons_count : null
     * schedule_url :
     * episodes_count : null
     * countries : ["日本"]
     * genres : ["剧情"]
     * collect_count : 500774
     * casts : [{"mainID":null,"alt":"https://movie.douban.com/celebrity/1036923/","avatars":{"small":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18584.jpg","large":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18584.jpg","medium":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18584.jpg"},"name":"本木雅弘","id":"1036923"},{"mainID":null,"alt":"https://movie.douban.com/celebrity/1098533/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p50678.jpg","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p50678.jpg","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p50678.jpg"},"name":"广末凉子","id":"1098533"},{"mainID":null,"alt":"https://movie.douban.com/celebrity/1037053/","avatars":{"small":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p50125.jpg","large":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p50125.jpg","medium":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p50125.jpg"},"name":"山崎努","id":"1037053"},{"mainID":null,"alt":"https://movie.douban.com/celebrity/1025510/","avatars":{"small":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p25652.jpg","large":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p25652.jpg","medium":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p25652.jpg"},"name":"吉行和子","id":"1025510"}]
     * current_season : null
     * original_title : おくりびと
     * summary : 买了昂贵的大提琴，想要成就一番事业的小林大悟（本木雅弘 饰），经历了4个月的管弦乐演奏，得到的却是“乐团解散”的噩耗与购买乐器的高昂债务。迫不得已，大悟与妻子美香（广末凉子 饰）搬到老屋，过着清贫的日子。某日，一则广告吸引了大悟的注意：NK代理公司，帮助旅行，高薪短工时。大悟来到NK代理公司面试，社长佐佐木生荣（山崎努 饰）二话不说就决定聘用大悟，但却对工作内容避而不谈。在大悟的再三追问下，佐佐木社长终于道明：是入殓（日语Nou Kan）工作！
     虽然心理上有所忌惮，高额的薪水还是令大悟接受了这份工作。但这样的工作对普通人谈何容易，一方面对遗体的不适，一方面又要对妻友隐瞒自己的工作，小林大悟不平凡的工作就这样开始了。©豆瓣
     * subtype : movie
     * directors : [{"mainID":null,"alt":"https://movie.douban.com/celebrity/1158861/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8539.jpg","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8539.jpg","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8539.jpg"},"name":"泷田洋二郎","id":"1158861"}]
     * comments_count : 70426
     * ratings_count : 373698
     * aka : ["礼仪师之奏鸣曲(港)","礼仪师(台)","送行者-礼仪师的乐章(台)","为逝者送行的人","纳棺师","Departures","Okuribito"]
     * resourse : ["http://192.168.1.100:8080/video/resource/入殓师.mp4"]
     * recommend : 0
     * hot : 0
     */

    private int mainID;
    private RatingBean rating;
    private int reviews_count;
    private int wish_count;
    private String douban_site;
    private String year;
    private ImagesBean images;
    private String alt;
    private String id;
    private String mobile_url;
    private String title;
    private Object do_count;
    private String share_url;
    private Object seasons_count;
    private String schedule_url;
    private Object episodes_count;
    private int collect_count;
    private Object current_season;
    private String original_title;
    private String summary;
    private String subtype;
    private int comments_count;
    private int ratings_count;
    private int recommend;
    private int hot;
    private List<String> countries;
    private List<String> genres;
    private List<CastsBean> casts;
    private List<DirectorsBean> directors;
    private List<String> aka;
    private List<String> resourse;

    public int getMainID() {
        return mainID;
    }

    public void setMainID(int mainID) {
        this.mainID = mainID;
    }

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDo_count() {
        return do_count;
    }

    public void setDo_count(Object do_count) {
        this.do_count = do_count;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public Object getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(Object seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public Object getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(Object episodes_count) {
        this.episodes_count = episodes_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public Object getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(Object current_season) {
        this.current_season = current_season;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
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

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }

    public List<DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorsBean> directors) {
        this.directors = directors;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public List<String> getResourse() {
        return resourse;
    }

    public void setResourse(List<String> resourse) {
        this.resourse = resourse;
    }

    public static class RatingBean {
        /**
         * max : 10
         * average : 8
         * stars : 45
         * min : 0
         */

        private int max;
        private int average;
        private String stars;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getAverage() {
            return average;
        }

        public void setAverage(int average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        /**
         * small :
         * large : 15490111637935988014021384242109.jpeg
         * medium :
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class CastsBean {
        /**
         * mainID : null
         * alt : https://movie.douban.com/celebrity/1036923/
         * avatars : {"small":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18584.jpg","large":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18584.jpg","medium":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18584.jpg"}
         * name : 本木雅弘
         * id : 1036923
         */

        private Object mainID;
        private String alt;
        private AvatarsBean avatars;
        private String name;
        private String id;

        public Object getMainID() {
            return mainID;
        }

        public void setMainID(Object mainID) {
            this.mainID = mainID;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBean {
            /**
             * small : http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18584.jpg
             * large : http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18584.jpg
             * medium : http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p18584.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    public static class DirectorsBean {
        /**
         * mainID : null
         * alt : https://movie.douban.com/celebrity/1158861/
         * avatars : {"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8539.jpg","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8539.jpg","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8539.jpg"}
         * name : 泷田洋二郎
         * id : 1158861
         */

        private Object mainID;
        private String alt;
        private AvatarsBeanX avatars;
        private String name;
        private String id;

        public Object getMainID() {
            return mainID;
        }

        public void setMainID(Object mainID) {
            this.mainID = mainID;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBeanX getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBeanX avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBeanX {
            /**
             * small : http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8539.jpg
             * large : http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8539.jpg
             * medium : http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p8539.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    @Override
    public String toString() {
        return "DetailMovie{" +
                "mainID=" + mainID +
                ", rating=" + rating +
                ", reviews_count=" + reviews_count +
                ", wish_count=" + wish_count +
                ", douban_site='" + douban_site + '\'' +
                ", year='" + year + '\'' +
                ", images=" + images +
                ", alt='" + alt + '\'' +
                ", id='" + id + '\'' +
                ", mobile_url='" + mobile_url + '\'' +
                ", title='" + title + '\'' +
                ", do_count=" + do_count +
                ", share_url='" + share_url + '\'' +
                ", seasons_count=" + seasons_count +
                ", schedule_url='" + schedule_url + '\'' +
                ", episodes_count=" + episodes_count +
                ", collect_count=" + collect_count +
                ", current_season=" + current_season +
                ", original_title='" + original_title + '\'' +
                ", summary='" + summary + '\'' +
                ", subtype='" + subtype + '\'' +
                ", comments_count=" + comments_count +
                ", ratings_count=" + ratings_count +
                ", recommend=" + recommend +
                ", hot=" + hot +
                ", countries=" + countries +
                ", genres=" + genres +
                ", casts=" + casts +
                ", directors=" + directors +
                ", aka=" + aka +
                ", resourse=" + resourse +
                '}';
    }
}
