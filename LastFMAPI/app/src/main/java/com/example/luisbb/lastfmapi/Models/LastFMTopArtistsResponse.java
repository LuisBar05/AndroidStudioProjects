package com.example.luisbb.lastfmapi.Models;

import com.google.gson.annotations.SerializedName;

public class LastFMTopArtistsResponse {
    @SerializedName("artists")
    private LastFMTopArtistsHandler topArtists;

    public LastFMTopArtistsHandler getTopArtists() {
        return topArtists;
    }

    public void setTopArtists(LastFMTopArtistsHandler topArtists) {
        this.topArtists = topArtists;
    }
}
