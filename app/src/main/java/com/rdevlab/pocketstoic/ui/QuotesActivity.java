package com.rdevlab.pocketstoic.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rdevlab.pocketstoic.R;

public class QuotesActivity extends AppCompatActivity {

    private static final String TAG = QuotesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        ListView listView = findViewById(R.id.quotes_list_view);
        listView.setEmptyView(findViewById(R.id.empty_state_view));

        Intent intent = getIntent();
        String listType = intent.getStringExtra("type");

        QuotesViewModel viewModel = ViewModelProviders.of(this).get(QuotesViewModel.class);
        viewModel.getQuotesList(listType).observe(this, quotesList -> {
            // update UI
            assert quotesList != null;
            ArrayAdapter mAdapter = new QuoteAdapter(this, quotesList);
            // Assign adapter to ListView
            listView.setAdapter(mAdapter);
        });
    }
}
