package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "songs")
public class Song {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String artist;
    private String location;

    public Song(int id, String name, String artist, String location) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.location = location;
    }

    @Ignore
    public Song(String name, String artist, String location) {
        this.name = name;
        this.artist = artist;
        this.location = location;
    }

    // Getters:

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getLocation() {
        return this.location;
    }

    // Setters:

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
