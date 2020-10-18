package com.example.babysit.Model;

public class VideoClip {
    String nameVideo;
    String pathVideo;



    public VideoClip(String nameVideo, String pathVideo) {
        this.nameVideo = nameVideo;
        this.pathVideo = pathVideo;
    }

    public VideoClip() {
    }

    public String getNameVideo() {
        return nameVideo;
    }

    public void setNameVideo(String nameVideo) {
        this.nameVideo = nameVideo;
    }

    public String getPathVideo() {
        return pathVideo;
    }

    public void setPathVideo(String pathVideo) {
        this.pathVideo = pathVideo;
    }


}
