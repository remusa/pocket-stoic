package com.rdevlab.pocketstoic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.rdevlab.pocketstoic.database.DataBaseHelper;
import com.rdevlab.pocketstoic.database.Quote;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private int mAdCount = 0;
    private final int mNoClicks = 5;
    private InterstitialAd mInterstitialAd;

    private int allQuoteCounter;
    private ArrayList<String> authors;
    private String mActivityTitle;
    private DataBaseHelper mDB;
    private int quoteCounter = 0;
    private Quote shareQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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

                        switch (item.getItemId()) {
                            case R.id.action_copy:
                                Toast.makeText(MainActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_favorites:
                                Toast.makeText(MainActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_previous:
                                break;
                            case R.id.action_next:
                                break;
                            case R.id.action_share:
                                Toast.makeText(MainActivity.this, "action_share", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                        }
                        return true;
                    }
                });

        importDatabase();
        this.allQuoteCounter = 0;
        Bundle extras = getIntent().getExtras();

        initBannerAd();
        initInterstitialAd();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initBannerAd() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest mAdRequest = new AdRequest.Builder().build();
        mAdView.loadAd(mAdRequest);
    }

    private void initInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
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

    private void importDatabase() {
        this.mDB = new DataBaseHelper(getApplicationContext());
        try {
            this.mDB.createDataBase();
        } catch (IOException e) {

        }
    }

}
