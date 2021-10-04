package com.example.mymvvm.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class Song implements Serializable {
    private long id;
    private String title;

    private String singer;
    private long thumbnail;
    private String songLink;
    private String album;
    private long duration;

    public Song(long id, String title, String singer, long thumbnail, String songLink, String album, long duration) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.thumbnail = thumbnail;
        this.songLink = songLink;
        this.album = album;
        this.duration = duration;
    }
    public Song(){}
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public long getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(long thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", singer='" + singer + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", songLink='" + songLink + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                '}';
    }
}
