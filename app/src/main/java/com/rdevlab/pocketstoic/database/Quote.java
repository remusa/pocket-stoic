package com.rdevlab.pocketstoic.database;

/**
 * Created by rms on 22/12/2017.
 */

public class Quote extends SugarRecord {

    private int id;
    private String quote;
    private String author;
    private int favorite;

    public Quote() {
    }

    public Quote(int id, String quote, String author, int favorite) {
        this.id = id;
        this.quote = quote;
        this.author = author;
        this.favorite = favorite;
    }

    public Quote(String quote, String author, int favorite) {
        this.quote = quote;
        this.author = author;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
