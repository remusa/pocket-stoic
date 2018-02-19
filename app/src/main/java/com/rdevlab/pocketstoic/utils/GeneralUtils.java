package com.rdevlab.pocketstoic.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.rdevlab.pocketstoic.database.Quote;

import java.util.Random;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.rdevlab.pocketstoic.ui.MainActivity.database;

/**
 * Created by rms on 19/02/2018.
 */

public class GeneralUtils {

    private static final String TAG = GeneralUtils.class.getSimpleName();

    public static Quote getRandomQuote() {
        int total = database.quotesModel().getAllQuotes().size();
        Random random = new Random();
        try {
            Quote randomQuote = database.quotesModel().getSingleQuote(
                    random.nextInt((total) + 1));
            if (randomQuote != null) {
                return randomQuote;
            }
        } catch (Exception e) {
            Log.e(TAG, "getRandomQuote: ", e);
        }
        return database.quotesModel().getSingleQuote(100);
    }

    public static int getAllQuotesCounter() {
        return database.quotesModel().getAllQuotes().size();
    }

    public static void copyQuote(Context context, Quote quote) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        assert clipboard != null;
        clipboard.setPrimaryClip(ClipData.newPlainText("Pocket Stoic Quote",
                quote.getQuoteText() + " - " + quote.getAuthor()));
        Toast.makeText(context, "Quote copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    public static void shareQuote(Context context, Quote quote) {
        String currentQuote = quote.getQuoteText() + " by " + quote.getAuthor();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra("android.intent.extra.TEXT", currentQuote);
        context.startActivity(shareIntent);
    }

}
