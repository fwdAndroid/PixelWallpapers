package com.fawad.pixelwallpapers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.leo.simplearcloader.SimpleArcLoader;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class FullScreenWallpaper extends AppCompatActivity {

    String originalUrl="";
    ImageView photoView;
    SimpleArcLoader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_wallpaper);

        getSupportActionBar().setTitle("Wallapers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#ff6f00"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        loader = new SimpleArcLoader(getApplicationContext());
        loader.start();
        Intent intent =  getIntent();

        originalUrl = intent.getStringExtra("originalUrl");

        photoView = findViewById(R.id.photoView);
        Glide.with(this).load(originalUrl).into(photoView);
        loader.stop();
        loader.setVisibility(View.GONE);
        photoView.setVisibility(View.VISIBLE);





    }

    public void SetWallpaperEvent() {

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Bitmap bitmap  = ((BitmapDrawable)photoView.getDrawable()).getBitmap();
        try {
            wallpaperManager.setBitmap(bitmap);
            FancyToast.makeText(getApplicationContext(),"Wallpaper Set",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void DownloadWallpaperEvent() {

        DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(originalUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
        FancyToast.makeText(this,"Download Complete",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.full_wallpaper,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.nav_set) {
            SetWallpaperEvent();
        }
        if(item.getItemId()==R.id.nav_download) {
            DownloadWallpaperEvent();
        }
        return super.onOptionsItemSelected(item);
    }
  }
