package com.example.navdrawer.ui.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface SongDao {

    // Insert a song. If it already exists, replace it
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SongEntity song);

    // Update an existing song
    @Update
    void update(SongEntity song);

    // Get all songs by a specific artist
    @Query("SELECT * FROM songs WHERE artist = :artistName")
    List<SongEntity> getSongsByArtist(String artistName);

    // Get all liked songs (as LiveData for observing in UI)
    @Query("SELECT * FROM songs WHERE is_liked = 1")
    LiveData<List<SongEntity>> getLikedSongs();

    // Get a song by name
    @Query("SELECT * FROM songs WHERE title = :songName LIMIT 1")
    SongEntity getSongByName(String songName);

    // Delete all songs
    @Query("DELETE FROM songs")
    void deleteAll();
}
