package com.example.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Account.class, Song.class}, version = 1, exportSchema = false)
public abstract class SoundbankDatabase extends RoomDatabase {

    // All used DAOs:
    public abstract AccountDao accountDao();
    public abstract SongDao songDao();

    private static SoundbankDatabase INSTANCE=null;

    public static SoundbankDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = buildDatabaseInstance(context);
        }
        return INSTANCE;
    }

    private static SoundbankDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                SoundbankDatabase.class,
                "soundbank_database")
                .allowMainThreadQueries().build();
    }

}