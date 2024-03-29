package com.rdevlab.pocketstoic.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by rms on 18/02/2018.
 */

@Dao
public interface QuotesDao {

    @Insert
    void insertSingleQuote(Quote quote);

    @Insert
    void insertMultipleQuotes(Quote... quotes);

    @Update
    void updateSingleQuote(Quote quote);

    @Delete
    void deleteSingleQuote(Quote quote);

    @Delete
    void deleteAllQuotes(Quote... quote);

    @Query("SELECT * FROM Quote")
    List<Quote> getAllQuotes();

    @Query("SELECT * FROM Quote WHERE ID = :id")
    Quote getSingleQuote(int id);

    @Query("SELECT * FROM Quote WHERE FAVORITE == 1")
    List<Quote> getFavoriteQuotes();

}
