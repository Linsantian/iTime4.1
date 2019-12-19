package com.example.itime4;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.Date;

public class AllData implements Serializable {
    ImageView imageView;
    String titleStr;
    String tipStr;
    String timeStr;
    String picture_Str;
    Date date;

    public AllData(ImageView imageView, String titleStr, String tipStr, String timeStr, String picture_Str, Date date) {
        this.imageView = imageView;
        this.titleStr = titleStr;
        this.tipStr = tipStr;
        this.timeStr = timeStr;
        this.picture_Str = picture_Str;
        this.date = date;
    }

    public AllData() {
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getTipStr() {
        return tipStr;
    }

    public void setTipStr(String tipStr) {
        this.tipStr = tipStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getPicture_Str() {
        return picture_Str;
    }

    public void setPicture_Str(String picture_Str) {
        this.picture_Str = picture_Str;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
