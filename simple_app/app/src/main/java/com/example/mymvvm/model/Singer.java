package com.example.mymvvm.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Singer implements Serializable {
    private String name;
    private int amount;
    private long thumb;

    public Singer(String name, int amount, long thumb) {
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
