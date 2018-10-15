package com.example.luisbb.lastfmapi.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LastFMTopArtistsHandler {
    private ArrayList<LastFMArtist> artists;
    private @SerializedName("@attr") LastFMAttr  attr;

    public ArrayList<LastFMArtist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<LastFMArtist> artists) {
        this.artists = artists;
    }

    public LastFMAttr getAttr() {
        return attr;
    }

    public void setAttr(LastFMAttr attr) {
        this.attr = attr;
    }
}
