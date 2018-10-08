package com.example.luisbb.lastfmapi.Utils;

import com.example.luisbb.lastfmapi.Models.LastFMTopArtistsHandler;
import com.example.luisbb.lastfmapi.Models.LastFMTopArtistsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFMService {

    @GET("?method=chart.gettopartists")
    Call<LastFMTopArtistsResponse> getTopArtists(@Query("limit") int limit, @Query("api_key") String apiKey, @Query("format") String format);

}
