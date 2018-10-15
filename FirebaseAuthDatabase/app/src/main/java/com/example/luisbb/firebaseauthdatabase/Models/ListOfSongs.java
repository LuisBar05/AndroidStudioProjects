package com.example.luisbb.firebaseauthdatabase.Models;

import java.util.ArrayList;

public class ListOfSongs {
    private ArrayList<SongDetails> myPlaylist;

    public ArrayList<SongDetails> getMyPlaylist() {
        return myPlaylist;
    }

    public void setMyPlaylist(ArrayList<SongDetails> myPlaylist) {
        this.myPlaylist = myPlaylist;
    }
}
