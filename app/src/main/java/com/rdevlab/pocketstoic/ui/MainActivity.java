package com.rdevlab.pocketstoic.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.rdevlab.pocketstoic.R;
import com.rdevlab.pocketstoic.database.AppDatabase;
import com.rdevlab.pocketstoic.database.Quote;
import com.rdevlab.pocketstoic.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private int mAdCount = 0;
    private final int mNoClicks = 5;
    private InterstitialAd mInterstitialAd;

    private TextView mQuoteTextView;
    private TextView mAuthorTextView;
    private BottomNavigationItemView mFavoriteItem;

    private int mTotalQuotesCounter;
    private int mCurrentIndex = 0;
    private List<Quote> mCurrentQuotesList;
    private List<Integer> mShownQuotesIdList;
    private Quote mCurrentQuote;

    public static AppDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuoteTextView = findViewById(R.id.quote_text_view);
        mAuthorTextView = findViewById(R.id.author_text_view);
        mFavoriteItem = findViewById(R.id.action_favorites);

        mDatabase = AppDatabase.getQuotesDatabase(this);

        mTotalQuotesCounter = GeneralUtils.getAllQuotesCounter();
        mCurrentQuotesList = new ArrayList<>();
        mShownQuotesIdList = new ArrayList<>();

        mCurrentQuote = GeneralUtils.getRandomQuote();
        mCurrentQuotesList.add(mCurrentQuote);
        if (mCurrentQuote != null) {
            showQuote(mCurrentQuote);
            checkFavorite(mCurrentQuote);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationViewEx bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    adCounter();
                    buttonClicked(item.getItemId());
                    return true;
                });

        initBannerAd();
        initInterstitialAd();
    }

    private void showQuote(Quote quote) {
        mQuoteTextView.setText(quote.getQuoteText());
        mAuthorTextView.setText(quote.getAuthor());
        checkFavorite(quote);
    }

    private void copyQuote() {
        GeneralUtils.copyQuote(this, mCurrentQuote);
    }

    private void shareQuote() {
        GeneralUtils.shareQuote(this, mCurrentQuote);
    }

    private void setFavorite() {
        GeneralUtils.setFavorite(this, mCurrentQuote);
        checkFavorite(mCurrentQuote);
    }

    private void checkFavorite(Quote quote) {
        if (quote.getFavorite() == 1) {
//            mFavoriteItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp));
            mFavoriteItem.setIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorRed)));
        } else if (quote.getFavorite() == 0) {
//            mFavoriteItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_red_24dp));
//            mFavoriteItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_content_copy_black_24dp));
            mFavoriteItem.setIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorWhite)));
        }
    }

    private void previousQuote() {
        if (mCurrentQuotesList.size() > 0) {
            mCurrentIndex--;
            if (mCurrentIndex >= 0) {
                showQuote(mCurrentQuotesList.get(mCurrentIndex));
                return;
            }
            mCurrentIndex = 0;
            Toast.makeText(this, "No more previous quotes", Toast.LENGTH_SHORT).show();
        }
    }

    private void nextQuote() {
        if (mCurrentQuotesList != null) {
            mCurrentQuote = getNoneRepeatQuote();
            mCurrentQuotesList.add(mCurrentQuote);
            showQuote(mCurrentQuote);
            mCurrentIndex++;
            mShownQuotesIdList.add(mCurrentQuote.getId());
        }
    }

    private Quote getNoneRepeatQuote() {
        Quote randomQuote = GeneralUtils.getRandomQuote();
        if (mShownQuotesIdList.size() >= mTotalQuotesCounter) {
            mShownQuotesIdList.clear();
            getNoneRepeatQuote();
        } else if (mShownQuotesIdList.contains(randomQuote.getId())) {
            getNoneRepeatQuote();
        }
        return randomQuote;
    }

    private void buttonClicked(int itemId) {
        switch (itemId) {
            case R.id.action_copy:
                copyQuote();
                break;
            case R.id.action_favorites:
                setFavorite();
                break;
            case R.id.action_previous:
                previousQuote();
                break;
            case R.id.action_next:
                nextQuote();
                break;
            case R.id.action_share:
                shareQuote();
                break;
            default:
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareQuote();
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), QuotesActivity.class);
        switch (item.getItemId()) {
            case R.id.nav_camera:
                intent.putExtra("type", "all");
                startActivity(intent);
                break;
            case R.id.nav_gallery:
                intent.putExtra("type", "favorites");
                startActivity(intent);
                break;
            case R.id.nav_slideshow:

                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_share:

                break;
            case R.id.nav_send:

                break;
            default:
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initBannerAd() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest mAdRequest = new AdRequest.Builder().build();
        mAdView.loadAd(mAdRequest);
    }

    private void initInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
    }

    private void adCounter() {
        mAdCount++;
//        Log.d("Interstitial", "Click: " + getmAdCount());
        if (getmAdCount() == mNoClicks) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
//                Log.d("Interstitial", "The interstitial hasn't loaded yet, click: " + getmAdCount());
            }
            callNewInterstitialAd();
            setmAdCount();
        }
    }

    public void callNewInterstitialAd() {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private int getmAdCount() {
        return mAdCount;
    }

    private void setmAdCount() {
        this.mAdCount = 0;
    }

}
