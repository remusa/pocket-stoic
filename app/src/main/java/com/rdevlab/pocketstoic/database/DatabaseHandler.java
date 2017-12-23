package com.rdevlab.pocketstoic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rms on 22/12/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PocketStoicDB";
    private static final String TABLE_QUOTES = "Quotes";

    private static final String KEY_ID = "id";
    private static final String KEY_QUOTE = "quote";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_FAVORITE = "favorite";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUOTES_TABLE =
                "CREATE TABLE " + TABLE_QUOTES + "("
                        + KEY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_QUOTE + " TEXT,"
                        + KEY_AUTHOR + " TEXT,"
                        + KEY_FAVORITE + " INTEGER"
                        + ")";

        db.execSQL(CREATE_QUOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);

        onCreate(db);
    }

    public void addQuote(Quote quote) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QUOTE, quote.getQuote());
        values.put(KEY_AUTHOR, quote.getAuthor());
        values.put(KEY_FAVORITE, quote.getFavorite());

        db.insert(TABLE_QUOTES, null, values);
        db.close();
    }

    public Quote getQuote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_QUOTES,
                new String[]{KEY_ID, KEY_QUOTE, KEY_AUTHOR, KEY_FAVORITE},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return new Quote(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)));
    }

    public List<Quote> getAllQuotes() {
        List<Quote> QuoteList = new ArrayList<Quote>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Quote quote = new Quote();
                quote.setId(Integer.parseInt(cursor.getString(0)));
                quote.setQuote(cursor.getString(1));
                quote.setAuthor(cursor.getString(2));
                quote.setFavorite(Integer.parseInt(cursor.getString(3)));
                QuoteList.add(quote);
            } while (cursor.moveToNext());
        }

        return QuoteList;
    }

    public int getQuotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_QUOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

}
