package com.plugai.android.livewallpapers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.WallpaperManager;

public class MainActivity extends Activity {
    final String LOG_TAG = "myLogs";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    public void onClickStart(View v) {
        try{
            Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    new ComponentName(this, AquariumWallpaperService.class));
            startActivity(intent);
            this.finish();
        }
        catch(Exception e){
            //super.onSetWallpaperClick(v);
        }
    }

    public void onClickStop(View v) {
        stopService(new Intent(this, AquariumWallpaperService.class));
    }
}
