package com.hoangpro.jsouppractice;

public class Post {
    private int id;
    private String jname,vname,time,thumb,length;
    private int view,lv;

    public Post(int id, String jname, String vname, String time, String thumb, String length, int view, int lv) {
        this.id = id;
        this.jname = jname;
        this.vname = vname;
        this.time = time;
        this.thumb = thumb;
        this.length = length;
        this.view = view;
        this.lv = lv;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", jname='" + jname + '\'' +
                ", vname='" + vname + '\'' +
                ", time='" + time + '\'' +
                ", thumb='" + thumb + '\'' +
                ", length='" + length + '\'' +
                ", view=" + view +
                ", lv=" + lv +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }
}
