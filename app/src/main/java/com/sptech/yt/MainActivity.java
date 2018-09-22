package com.sptech.yt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    WebView yt;
    ProgressBar pb, hb;
    String url = "https://www.youtube.com/channel/UC-mcsGW0Vv839GkRP_g5TQw";
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.facebook:
                        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/profile.php?id=100010414819561"));
                        startActivity(intent);
                        break;
                    case R.id.twitter:
                        Intent in =new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/anujrsby44"));
                        startActivity(in);
                        break;
                    case R.id.gmail:
                        Intent intt=new Intent(Intent.ACTION_VIEW,Uri.parse("https://plus.google.com/u/0/111953892108345581546"));
                        startActivity(intt);
                        break;
                    case R.id.about:
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent inte=new Intent(MainActivity.this,Main3Activity.class);
                                startActivity(inte);
                            }
                        },15);


                }
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        yt = (WebView) findViewById(R.id.YTWeb);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        hb = (ProgressBar) findViewById(R.id.horizontalPb);
        if (isNetworkAvailable()) {
            yt.loadUrl(url);
        } else {
            pb.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar
                    .make(toolbar, "No Internet Connection..!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(MainActivity.this, MainActivity.class);
                            finish();
                            startActivity(i);
                        }
                    });
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.show();
        }
        WebSettings webSettings = yt.getSettings();
        webSettings.setJavaScriptEnabled(true);
        yt.setWebViewClient(new MyWebViewClient());

        yt.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                hb.setProgress(newProgress);
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                    hb.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && yt.canGoBack()) {
            yt.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    private boolean isInstalled(String uri) {
        PackageManager pm = getPackageManager();
        boolean isIn;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            isIn = true;
        } catch (PackageManager.NameNotFoundException e) {
            isIn = false;
        }
        return isIn;
    }

    @Override
    public void onBackPressed() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to Exit")

                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // showInterstitial();
                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  showInterstitial();
                        dialog.cancel();
                    }

                });

        android.support.v7.app.AlertDialog alert = builder.create();
        alert.setTitle("Exit ?");
        alert.show();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void setupToolbar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        }
}
