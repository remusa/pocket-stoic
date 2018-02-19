package com.rdevlab.pocketstoic.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private int mAdCount = 0;
    private final int mNoClicks = 5;
    private InterstitialAd mInterstitialAd;

    private TextView quoteTextView;
    private TextView authorTextView;

    private int allQuoteCounter;
    private Quote currentQuote;

    public static AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteTextView = findViewById(R.id.quote_text_view);
        authorTextView = findViewById(R.id.author_text_view);

        database = AppDatabase.getQuotesDatabase(this);

        allQuoteCounter = GeneralUtils.getAllQuotesCounter();

        currentQuote = GeneralUtils.getRandomQuote();
        if (currentQuote != null) {
            setCurrentQuote();
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

    private void setCurrentQuote() {
        quoteTextView.setText(currentQuote.getQuoteText());
        authorTextView.setText(currentQuote.getAuthor());
    }

    private void copyQuote() {
        GeneralUtils.copyQuote(this, currentQuote);
    }

    private void shareQuote() {
        GeneralUtils.shareQuote(this, currentQuote);
    }

    private void buttonClicked(int itemId) {
        switch (itemId) {
            case R.id.action_copy:
                copyQuote();
                break;
            case R.id.action_favorites:
                Toast.makeText(MainActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_previous:
                Toast.makeText(MainActivity.this, "Previous", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_next:
                currentQuote = GeneralUtils.getRandomQuote();
                setCurrentQuote();
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
        switch (item.getItemId()) {
            case R.id.nav_camera:
                // Handle the camera action
                break;
            case R.id.nav_gallery:

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
        Log.d("Interstitial", "Click: " + getmAdCount());
        if (getmAdCount() == mNoClicks) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("Interstitial", "The interstitial hasn't loaded yet, click: " + getmAdCount());
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
