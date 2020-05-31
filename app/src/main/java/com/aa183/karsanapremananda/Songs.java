package com.aa183.karsanapremananda;

import java.util.Date;

public class Songs {
    private int songID;
    private String songTitle;
    private String songArtist;
    private String songAlbum;
    private Date songDate;
    private String songImage;
    private String songLyrics;

    public Songs(int songID, String songTitle, String songArtist, String songAlbum, Date songDate, String songImage, String songLyrics) {
        this.songID = songID;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songAlbum = songAlbum;
        this.songDate = songDate;
        this.songImage = songImage;
        this.songLyrics = songLyrics;
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public Date getSongDate() {
        return songDate;
    }

    public void setSongDate(Date songDate) {
        this.songDate = songDate;
    }

    public String getSongImage() {
        return songImage;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public String getSongLyrics() {
        return songLyrics;
    }

    public void setSongLyrics(String songLyrics) {
        this.songLyrics = songLyrics;
    }
}
