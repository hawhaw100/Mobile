package com.example.navdrawer.ui.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SongEntity.class}, version = 2, exportSchema = false)
public abstract class SongDatabase extends RoomDatabase {

    public abstract SongDao songDao();

    private static SongDatabase INSTANCE;

    public static SongDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SongDatabase.class, "song_database")
                    .fallbackToDestructiveMigration() // Only for small apps / testing
                    .build();
        }
        return INSTANCE;
    }
}

