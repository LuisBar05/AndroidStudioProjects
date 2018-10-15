package com.example.luisbb.lastfmapi.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LastFMArtist {
    private String name;
    private String playcount;
    private String listeners;
    private String mbid;
    private String url;
    private ArrayList<ImageCoverArt> images;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaycount() {
        return playcount;
    }

    public void setPlaycount(String playcount) {
        this.playcount = playcount;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<ImageCoverArt> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageCoverArt> images) {
        this.images = images;
    }
}
