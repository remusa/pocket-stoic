package com.rdevlab.pocketstoic.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rdevlab.pocketstoic.R;
import com.rdevlab.pocketstoic.database.Quote;

import java.util.List;

/**
 * Created by rms on 22/02/2018.
 */

public class QuoteAdapter extends ArrayAdapter<Quote> {

    public QuoteAdapter(@NonNull Context context, @NonNull List<Quote> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }

        final Quote currentQuote = getItem(position);

        TextView quoteTextView = listViewItem.findViewById(R.id.quote_text_view);
        TextView authorTextView = listViewItem.findViewById(R.id.author_text_view);

        quoteTextView.setText(currentQuote.getQuoteText());
        authorTextView.setText(currentQuote.getAuthor());

        return listViewItem;
    }
}
