package com.example.itime4;

public class Item {
    private int pictureSource;
    private String bigtext;
    private String smalltext;

    public Item(int pictureSource, String bigtext, String smalltext) {
        this.pictureSource = pictureSource;
        this.bigtext = bigtext;
        this.smalltext = smalltext;
    }

    public int getPictureSource() {
        return pictureSource;
    }

    public String getBigtext() {
        return bigtext;
    }

    public String getSmalltext() {
        return smalltext;
    }

    public void setPictureSource(int pictureSource) {
        this.pictureSource = pictureSource;
    }

    public void setBigtext(String bigtext) {
        this.bigtext = bigtext;
    }

    public void setSmalltext(String smalltext) {
        this.smalltext = smalltext;
    }

    public void appendSmalltext(String smalltext){
        this.smalltext = this.smalltext +smalltext;
    }
}
