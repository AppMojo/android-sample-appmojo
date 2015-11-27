package com.sample.appmojo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.appmojo.sdk.AppMojo;
import com.sample.appmojo.adapters.MainRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MainRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainActivityCallBack mCallBackListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolsBar(toolbar);

        //start AppMojo
        AppMojo.setDebugMode(true);
        AppMojo.setIsUsingDevServer(true);
        AppMojo.start(getApplicationContext(),
                getString(R.string.app_id), getString(R.string.app_secret));

        mCallBackListener = new MainActivityCallBack();
        setupRecyclerView();

    }

    private void initToolsBar(Toolbar toolbar) {
        TextView toolbarTitleTv = (TextView) findViewById(R.id.actTemplate_toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolbarTitleTv.setText(getString(R.string.banner_page_title));
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] classNames = new String[]{ BannerActivity.class.getSimpleName()};

        // specify an adapter (see also next example)
        mAdapter = new MainRecyclerAdapter(classNames);
        mAdapter.setOnItemClickListener(mCallBackListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //   _____ __  __ __  __             ____ _
    //  |_   _|  \ | |  | | | ___  __ __/ ___| | ____ 	 ___  ___
    //    | | | \| | | \| | |/ _ \| |/ | |   | |/ _  \  / __|/ __|
    //   _| |_| |\ | | |\ | |  __/|   /| |___| | (_)  \ \__ \\__ \
    //  |_____|_| \__|_| \__|\___||__|  \____|_|\___/|_\|___/|___/

    private class MainActivityCallBack implements MainRecyclerAdapter.IViewItemClickListener{

        @Override
        public void onItemClick(View view, int position) {
            if(position == 0) {
                Intent intent = new Intent(getApplicationContext(), BannerActivity.class);
                startActivity(intent);
            }
        }
    }
}
