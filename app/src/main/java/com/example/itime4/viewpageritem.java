package com.example.itime4;

public class viewpageritem {
    private int viewpager_picture;
    private String viewpager_title_name;
    private String viewpager_time;
    private String viewpager_countdown;

    public viewpageritem(int viewpager_picture, String viewpager_title_name, String viewpager_time, String viewpager_countdown) {
        this.viewpager_picture = viewpager_picture;
        this.viewpager_title_name = viewpager_title_name;
        this.viewpager_time = viewpager_time;
        this.viewpager_countdown = viewpager_countdown;
    }

    public int getViewpager_picture() {
        return viewpager_picture;
    }

    public void setViewpager_picture(int viewpager_picture) {
        this.viewpager_picture = viewpager_picture;
    }

    public String getViewpager_title_name() {
        return viewpager_title_name;
    }

    public void setViewpager_title_name(String viewpager_title_name) {
        this.viewpager_title_name = viewpager_title_name;
    }

    public String getViewpager_time() {
        return viewpager_time;
    }

    public void setViewpager_time(String viewpager_time) {
        this.viewpager_time = viewpager_time;
    }

    public String getViewpager_countdown() {
        return viewpager_countdown;
    }

    public void setViewpager_countdown(String viewpager_countdown) {
        this.viewpager_countdown = viewpager_countdown;
    }
}
