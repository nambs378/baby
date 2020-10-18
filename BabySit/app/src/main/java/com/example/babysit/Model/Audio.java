package com.example.babysit.Model;

public class Audio {
    String nameAudio;
    String pathAudio;


    public Audio(String nameAudio, String pathAudio) {
        this.nameAudio = nameAudio;
        this.pathAudio = pathAudio;
    }

    public Audio() {
    }

    public String getNameAudio() {
        return nameAudio;
    }

    public void setNameAudio(String nameAudio) {
        this.nameAudio = nameAudio;
    }

    public String getPathAudio() {
        return pathAudio;
    }

    public void setPathAudio(String pathAudio) {
        this.pathAudio = pathAudio;
    }
}
