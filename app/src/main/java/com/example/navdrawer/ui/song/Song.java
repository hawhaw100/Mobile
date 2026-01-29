package com.example.navdrawer.ui.song;

import java.io.Serializable;

public class Song implements Serializable {
    private int imgIds, audio;
    private String SongName, artistName, views, duration, relDate, genre;

    public Song(String Sname, int imgid, int a, String v, String Aname, String dur, String rel, String g){
        SongName = Sname;
        imgIds = imgid;
        audio = a;
        views = v;
        artistName = Aname;
        duration = dur;
        relDate = rel;
        genre = g;
    }
    public String getSongName(){
        return SongName;
    }
    public String getViews(){
        return views;
    }
    public int getImgIds(){
        return imgIds;
    }
    public int getAudio(){
        return audio;
    }

    public String getArtistName(){
        return artistName;
    }
    public String getDuration(){
        return duration;
    }
    public String getRelDate(){
        return relDate;
    }
    public String getGenre(){
        return genre;
    }
}
