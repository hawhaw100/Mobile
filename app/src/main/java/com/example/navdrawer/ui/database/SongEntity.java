package com.example.navdrawer.ui.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "songs")
public class SongEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public int imgResId;      // R.drawable.xxx
    public int audioResId;    // R.raw.xxx
    public String views;
    public String artist;
    public String duration;
    public String releaseYear;
    public String genre;
    @ColumnInfo(name = "is_liked")
    public boolean isLiked;


    public SongEntity(String title, int imgResId, int audioResId, String views,
                      String artist, String duration, String releaseYear,
                      String genre) {
        this.title = title;
        this.imgResId = imgResId;
        this.audioResId = audioResId;
        this.views = views;
        this.artist = artist;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.isLiked = false;
    }
}
