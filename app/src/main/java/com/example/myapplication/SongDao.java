package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM songs")
    public List<Song> getAllSongs();
    @Query("SELECT name FROM songs")
    public List<String> getAllSongNames();
    // TO DO: Add %name% to ensure retrieving all entries with the sent search string.
    @Query("SELECT * FROM songs where name LIKE :name")
    public List<Song> searchSongsByName(String name);
    @Query("SELECT * FROM songs where artist LIKE :artist")
    public List<Song> searchSongsByArtist(String artist);
    @Update
    public void updateSong(Song song);
    @Insert
    public void addSong(Song song);
}
