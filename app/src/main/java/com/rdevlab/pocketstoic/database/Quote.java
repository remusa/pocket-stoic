package com.rdevlab.pocketstoic.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by rms on 22/12/2017.
 */

//class QuoteLanguage {
//    @ColumnInfo(name = "quote_english")
//    public String quoteEnglish;
//}

@Entity
public class Quote {

    @ColumnInfo(name = "_id")
    private int uid;

    @NonNull
    @PrimaryKey//(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "QUOTES")
    private String quoteText;

    @ColumnInfo(name = "AUTHOR")
    private String author;

    @ColumnInfo(name = "FAVORITE")
    private int favorite;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }
}
