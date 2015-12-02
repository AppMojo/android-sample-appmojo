package com.sample.appmojo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.appmojo.sdk.AppMojo;
import com.sample.appmojo.adapters.MainListAdapter;

public class MainActivity extends AppCompatActivity {

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
        setupListView();

    }

    private void initToolsBar(Toolbar toolbar) {
        TextView toolbarTitleTv = (TextView) findViewById(R.id.actTemplate_toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolbarTitleTv.setText(getString(R.string.banner_page_title));
    }

    private void setupListView() {
        String[] classNames = new String[]{
                BannerActivity.class.getSimpleName(),
                InterstitialActivity.class.getSimpleName()};

        MainListAdapter listAdapter = new MainListAdapter(getApplicationContext(), classNames);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(mCallBackListener);
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

    private class MainActivityCallBack implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent;
            switch (position) {
                case 0:
                    intent = new Intent(getApplicationContext(), BannerActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(getApplicationContext(), InterstitialActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
