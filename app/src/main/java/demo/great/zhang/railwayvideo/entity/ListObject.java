package demo.great.zhang.railwayvideo.entity;

import java.util.List;

public class ListObject<T> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
