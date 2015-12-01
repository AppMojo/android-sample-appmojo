package com.sample.appmojo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appmojo.sdk.AMAdRequest;
import com.appmojo.sdk.AMInterstitial;
import com.appmojo.sdk.AMInterstitialListener;


public class InterstitialActivity extends AppCompatActivity {
    private static final String TAG = "InterstitialActivity";

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private AMInterstitial mInterstitialAd;
    private InterstitialCallBack mListener;
    private Button mReloadBtn;
    private Button mShowAdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.actTemplate_toolbar_title);
        initToolsBar();

        mReloadBtn = (Button) findViewById(R.id.interAd_refresh_btn);
        mShowAdBtn = (Button) findViewById(R.id.interAd_show_btn);

        setupInterstitialAd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolsBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbarTitle.setText(getString(R.string.interstitial_page_title));
    }

    private void setupInterstitialAd() {
        String interPlcId = getString(R.string.interstitial_placement_id);
        String[] deviceIds = getResources().getStringArray(R.array.devices_uuid);
        mListener = new InterstitialCallBack();

        mInterstitialAd = new AMInterstitial(getApplicationContext());
        mInterstitialAd.setPlacementUid(interPlcId);
        mInterstitialAd.setListener(mListener);

        AMAdRequest.Builder builder = new AMAdRequest.Builder();
        for(String uuid : deviceIds) {
            builder.addTestDevice(uuid);
        }
        mInterstitialAd.loadAd(builder.build());
    }

    private void setupButtonSate(boolean isLoadAd) {
        mReloadBtn.setEnabled(!isLoadAd);
        mShowAdBtn.setEnabled(isLoadAd);
    }

    public void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.interAd_refresh_btn:
                mInterstitialAd.reloadAd();
                break;

            case R.id.interAd_show_btn:
                onClickShowInterstitialAd(mInterstitialAd);
                setupButtonSate(false);
                break;
            default:
                break;
        }
    }

    private void onClickShowInterstitialAd(AMInterstitial interstitial) {
        if (interstitial.isLoaded()) {
            interstitial.show();
            Toast.makeText(getApplicationContext(),
                    interstitial.getPlacementUid(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "InterstitialAd is not ready to show!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInterstitialAd.destroy();
    }


    private class InterstitialCallBack extends AMInterstitialListener {
        @Override
        public void onAdLoaded(AMInterstitial view) {
            Log.d(TAG, "onAdLoaded...");
            setupButtonSate(true);
        }

        @Override
        public void onAdClosed(AMInterstitial view) {
            Log.d(TAG, "onAdClosed...");
        }

        @Override
        public void onAdFailed(AMInterstitial view, int errorCode) {
            Log.d(TAG, "onAdFailed...");
        }

        @Override
        public void onAdOpened(AMInterstitial view) {
            Log.d(TAG, "onAdOpened...");
        }

        @Override
        public void onNotApplyConfiguration(AMInterstitial view) {
            Log.d(TAG, "onNotApplyConfiguration...");
        }

        @Override
        public void onAdLeftApplication() {
            Log.d(TAG, "onAdLeftApplication...");
        }

        @Override
        public void onReachedFrequencyCap(AMInterstitial view, int criteria) {
            Log.d(TAG, "onReachedFrequencyCap...");
            setupButtonSate(false);
            Toast.makeText(getApplicationContext(),
                    "Reached maximum frequency capacity", Toast.LENGTH_LONG).show();
        }
    }
}
