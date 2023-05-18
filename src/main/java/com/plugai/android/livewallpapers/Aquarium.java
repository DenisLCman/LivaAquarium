package com.plugai.android.livewallpapers;

import static android.content.Context.BATTERY_SERVICE;

import java.util.ArrayList;

import com.plugai.android.livewallpapers.fishes.*;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.BatteryManager;
import android.os.Build;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

public class Aquarium {

    private AquariumThread _aquariumThread;
    private SurfaceHolder _surfaceHolder;
    private ArrayList<Renderable> _fishes;
    private Bitmap _backgroundImage;

    private Bitmap _headgroundImage;
    private Context _context;

    private int batLevel;
    private BatteryManager bm;
/*

*/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void render(){
        Canvas canvas = null;
        try{

            canvas = this._surfaceHolder.lockCanvas(null);
            synchronized (this._surfaceHolder) {
                this.onDraw(canvas);
            }

        }finally{
            if(canvas != null){
                this._surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onDraw(Canvas canvas) {

        this.renderBackGround(canvas);
        for (Renderable renderable : this._fishes) {
            renderable.render(canvas);
        }
        this.renderHeadGround(canvas);
    };

    public void start(){
        this._aquariumThread.switchOn();
    }

    public void stop(){
        boolean retry = true;
        this._aquariumThread.switchOff();
        while (retry) {
            try {
                this._aquariumThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }

    public int getLeft() {
        return 0;
    }


    public int getRight() {
        return this._backgroundImage.getWidth();
    }

    public int getHeight() {
        return this._backgroundImage.getHeight();
    }
    public int getAquaLevel(){
        return getHeight()-(getHeight()*batLevel)/100;
    }

    public int getBatLevel(){
        return batLevel;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initialize(Context context, SurfaceHolder surfaceHolder) {
        bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        this._aquariumThread = new AquariumThread(this);
        this._surfaceHolder = surfaceHolder;
        this._fishes = new ArrayList<Renderable>();
        this._context = context;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        this._backgroundImage = BitmapFactory.decodeResource(context.getResources(), com.plugai.android.livewallpapers.R.drawable.aquarium2, options);
        this._headgroundImage = BitmapFactory.decodeResource(context.getResources(), com.plugai.android.livewallpapers.R.drawable.aquariumwater2, options);

        this.addFishes();
    }

    private void addFishes() {
        Point startPoint = new Point(50, getAquaLevel() + 75);
        this._fishes.add(new ClownFish(this._context, this, startPoint, 100,1));
        Point startPoint1 = new Point(250, getAquaLevel() + 75);
        this._fishes.add(new ClownFish(this._context, this, startPoint1, 100,2));
        Point startPoint2 = new Point(450, getAquaLevel() + 75);
        this._fishes.add(new ClownFish(this._context, this, startPoint2, 100,3));

    }

    private void renderBackGround(Canvas canvas)
    {
        canvas.drawBitmap(this._backgroundImage, 0, 0, null);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void renderHeadGround(Canvas canvas)
    {
        batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        canvas.drawBitmap(this._headgroundImage, 0, getHeight()-(getHeight()*batLevel)/100, null);
    }
}