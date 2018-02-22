package com.rdevlab.pocketstoic.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import com.rdevlab.pocketstoic.database.Quote;

import java.util.List;

import static com.rdevlab.pocketstoic.ui.MainActivity.mDatabase;

/**
 * Created by rms on 22/02/2018.
 */

public class QuotesViewModel extends ViewModel {

    private static final String TAG = QuotesViewModel.class.getSimpleName();

    private MutableLiveData<List<Quote>> mQuotesList;

    LiveData<List<Quote>> getQuotesList(String listType) {
        if (mQuotesList == null) {
            mQuotesList = new MutableLiveData<>();
            loadQuotes(listType);
        }
        return mQuotesList;
    }

    private void loadQuotes(String listType) {
        // async operation to fetch quotes data
        Handler mHandler = new Handler();
        mHandler.postDelayed(() -> {
            if (listType.equals("all")) {
                mQuotesList.setValue(mDatabase.quotesModel().getAllQuotes());
            } else if (listType.equals("favorites")){
                mQuotesList.setValue(mDatabase.quotesModel().getFavoriteQuotes());
            }
        }, 1000);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
