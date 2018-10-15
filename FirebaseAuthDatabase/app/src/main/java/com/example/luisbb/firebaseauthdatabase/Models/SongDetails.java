package com.example.luisbb.firebaseauthdatabase.Models;

public class SongDetails {
    private String songId;
    private String album;
    private String author;
    private String company;
    private String composer;
    private String cover;
    private String title;
    private int year;

    public SongDetails(){
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    public String getSongId(){
        return songId;
    }

    public void setSongId(String songId){
        this.songId=songId;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
