package com.example.mymvvm.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Album implements Serializable {
    String name;
    int amount;
    long thumb;

    public Album(String name, int amount, long thumb) {
        this.name = name;
        this.amount = amount;
        this.thumb = thumb;
    }

    public long getThumb() {
        return thumb;
    }

    public void setThumb(long thumb) {
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
