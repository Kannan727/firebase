package com.example.myapplication;

public class Track {
    private  String RegisterID;
    private String TrackName;
    private int Rating;
    public Track() {
    }

    public Track(String registerID, String trackName, int rating) {
        RegisterID = registerID;
        TrackName = trackName;
        Rating = rating;
    }

    public String getRegisterID() {
        return RegisterID;
    }

    public String getTrackName() {
        return TrackName;
    }

    public int getRating() {
        return Rating;
    }
}

