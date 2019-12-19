package com.example.itime4;

public class mainitem {
    private int pictureSourse;
    private String picturetext;
    private String nametext;
    private String timetext;
    private String tiptext;

    public mainitem(int pictureSourse, String picturetext, String nametext, String timetext, String tiptext) {
        this.pictureSourse = pictureSourse;
        this.picturetext = picturetext;
        this.nametext = nametext;
        this.timetext = timetext;
        this.tiptext = tiptext;
    }

    public int getPictureSourse() {
        return pictureSourse;
    }

    public void setPictureSourse(int pictureSourse) {
        this.pictureSourse = pictureSourse;
    }

    public String getPicturetext() {
        return picturetext;
    }

    public void setPicturetext(String picturetext) {
        this.picturetext = picturetext;
    }

    public String getNametext() {
        return nametext;
    }

    public void setNametext(String nametext) {
        this.nametext = nametext;
    }

    public String getTimetext() {
        return timetext;
    }

    public void setTimetext(String timetext) {
        this.timetext = timetext;
    }

    public String getTiptext() {
        return tiptext;
    }

    public void setTiptext(String tiptext) {
        this.tiptext = tiptext;
    }
}
