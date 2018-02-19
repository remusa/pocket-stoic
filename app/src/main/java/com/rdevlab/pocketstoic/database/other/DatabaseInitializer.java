package com.rdevlab.pocketstoic.database.other;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.rdevlab.pocketstoic.database.AppDatabase;
import com.rdevlab.pocketstoic.database.Quote;

/**
 * Created by rms on 18/02/2018.
 */

public class DatabaseInitializer {

    public static void populateAsync(final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static Quote addQuote(final AppDatabase db, final int isFavorite,
                                  final String author, final String quoteText) {
        Quote quote = new Quote();
        quote.setFavorite(isFavorite);
        quote.setAuthor(author);
        quote.setQuoteText(quoteText);
        db.quotesModel().insertSingleQuote(quote);
        return quote;
    }

    private static void populateWithTestData(AppDatabase db) {
        db.quotesModel().deleteAllQuotes();

        Quote quote1 = addQuote(db, 1, "Seneca", "This is a test");
        Quote quote2 = addQuote(db, 1, "Marcus Aurelius", "This is also a test");
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateWithTestData(mDb);
            return null;
        }
    }

}
