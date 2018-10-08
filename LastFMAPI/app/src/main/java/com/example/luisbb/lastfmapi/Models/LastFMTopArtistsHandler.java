package com.example.luisbb.lastfmapi.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LastFMTopArtistsHandler {
    @SerializedName("artist")
    private ArrayList<LastFMArtist> artists;

    public ArrayList<LastFMArtist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<LastFMArtist> artists) {
        this.artists = artists;
    }
}
