package com.plugai.android.livewallpapers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

public class AquariumWallpaperService extends WallpaperService {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Engine onCreateEngine() {
        return new AquariumWallpaperEngine();
    }


    class AquariumWallpaperEngine extends Engine{

        private Aquarium _aquarium;


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public AquariumWallpaperEngine() {
            this._aquarium = new Aquarium();
            this._aquarium.initialize(getBaseContext(), getSurfaceHolder());
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onVisibilityChanged(boolean visible) {
            if(visible){
                this._aquarium.render();
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            this._aquarium.start();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this._aquarium.stop();
        }
    }
}