package com.example.babysit.Model;

public class Song {
    int resId;
    String nameSong;


    public Song(int resId, String nameSong) {
        this.resId = resId;
        this.nameSong = nameSong;
    }

    public Song() {
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }
}
