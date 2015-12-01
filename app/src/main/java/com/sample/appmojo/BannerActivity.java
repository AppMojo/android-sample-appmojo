package com.sample.appmojo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.appmojo.sdk.AMAdRequest;
import com.appmojo.sdk.AMBannerListener;
import com.appmojo.sdk.AMBannerView;
import com.appmojo.sdk.base.AMAdSize;
import com.appmojo.sdk.utils.AMLog;

/**
 * Created by nutron on 11/27/15 AD.
 */
public class BannerActivity extends AppCompatActivity {
    private static final String TAG = "BannerActivity";

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private AMBannerView mBannerTopView;
    private AMBannerView mBannerBtmView;
    private FrameLayout mBannerBtmLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.actTemplate_toolbar_title);
        initToolsBar();

        setupBanner();

    }

    private void initToolsBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbarTitle.setText(getString(R.string.banner_page_title));
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

    private void setupBanner() {
        String[] deviceIds = getResources().getStringArray(R.array.devices_uuid);
        AMAdRequest.Builder builder = new AMAdRequest.Builder();
        for(String uuid : deviceIds) {
            builder.addTestDevice(uuid);
        }

        AMBannerCallback bannerCallback = new AMBannerCallback();
        mBannerTopView = (AMBannerView)findViewById(R.id.banner_top_view);
        mBannerTopView.setListener(bannerCallback);
        mBannerTopView.loadAd(builder.build());

        mBannerBtmLayout = (FrameLayout) findViewById(R.id.banner_btm_layout);
        mBannerBtmView = new AMBannerView(getApplicationContext());
        mBannerBtmView.setPlacementUid(getString(R.string.banner_placement_two));
        mBannerBtmView.setAdSize(AMAdSize.BANNER);
        mBannerBtmView.setListener(bannerCallback);
        mBannerBtmView.loadAd(builder.build());
        mBannerBtmLayout.addView(mBannerBtmView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBannerTopView != null)
            mBannerTopView.destroy();

        if(mBannerBtmView != null)
            mBannerBtmView.destroy();
    }

    //   _____ __  __ __  __             ____ _
    //  |_   _|  \ | |  | | | ___  __ __/ ___| | ____ 	 ___  ___
    //    | | | \| | | \| | |/ _ \| |/ | |   | |/ _  \  / __|/ __|
    //   _| |_| |\ | | |\ | |  __/|   /| |___| | (_)  \ \__ \\__ \
    //  |_____|_| \__|_| \__|\___||__|  \____|_|\___/|_\|___/|___/

    private class AMBannerCallback extends AMBannerListener {

        @Override
        public void onAdLoaded(AMBannerView view) {
            AMLog.d(TAG, "onAdLoaded # ....");
            if (view.getPlacementUid().equals(getString(R.string.banner_placement_two))) {
                mBannerBtmLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onNotApplyConfiguration(AMBannerView view) {
            AMLog.d(TAG, "onNotApplyConfiguration # ....");
            if (view.getPlacementUid().equals(getString(R.string.banner_placement_two))) {
                mBannerBtmLayout.setVisibility(View.GONE);
            }
        }

        @Override
        public void onAdFailed(AMBannerView view, int errorCode) {
            AMLog.d(TAG, "onAdFailed # ....");
            if (view.getPlacementUid().equals(getString(R.string.banner_placement_two))) {
                mBannerBtmLayout.setVisibility(View.GONE);
            }
        }

        @Override
        public void onAdOpened(AMBannerView view) {
            AMLog.d(TAG, "onAdOpened # ....");
        }

        @Override
        public void onAdClosed(AMBannerView view) {
            AMLog.d(TAG, "onAdClosed # ....");
        }
    }
}
