package com.plugai.android.livewallpapers;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public abstract class AquaticAnimal implements Renderable {

    private static int MAX_SPEED = 100;
    private Context _context;
    private Aquarium _aquarium;
    private FishSprite _leftSprite;
    private FishSprite _rightSprite;
    private FishSprite _deathSprite;
    private int _type;
    private int _direction = -2;
    private int _speedFraction;
    private long _previousTime;


    public AquaticAnimal(Context context, Aquarium aquarium){
        this._context = context;
        this._aquarium = aquarium;
    }

    protected void initialize(Bitmap leftBitmap, Bitmap rightBitmap, Bitmap deathBitmap,int fps, int totalFrames, Point startPoint, int speed, int type){
        this._leftSprite = new FishSprite(leftBitmap, fps, totalFrames, startPoint);
        this._rightSprite = new FishSprite(rightBitmap, fps, totalFrames, startPoint);
        this._deathSprite = new FishSprite(deathBitmap, fps, totalFrames, startPoint);
        this._speedFraction = (MAX_SPEED / speed) * 10;
        this._type = type;
    }

    private FishSprite getSprite(){
        if(this._direction < 0){
            return this._leftSprite;
        }
        if(this._direction > 0){
            return this._rightSprite;
        }
        return this._deathSprite;
    }

    public int getDirection(){
        FishSprite sprite = this.getSprite();
        int xPos = sprite.getXPos();
        int deepLevel = (int) (Math.random()%3);
        if(_aquarium.getBatLevel()<=70-10*_type){
            this._direction = 0;
        }
        if(this._direction < 0){
            xPos += sprite.getWidth();
        }
        if(this._direction == 0){

        }
        if(xPos < this._aquarium.getLeft()){
            this._direction = 2;
            sprite.setYPos((int) (_aquarium.getAquaLevel()+75+100*(deepLevel)));
        }else if(xPos > this._aquarium.getRight()){
            this._direction = -2;
            sprite.setYPos((int) (_aquarium.getAquaLevel()+75+100*(deepLevel)));
        }else{
            //
        }

        return this._direction;
    }

    public Context getContext(){
        return this._context;
    }

    public Aquarium getAquarium(){
        return this._aquarium;
    }

    @Override
    public void render(Canvas canvas){

            long currentTime = System.currentTimeMillis();
            this.getSprite().render(canvas, currentTime);
            this.swim(currentTime);

    }

    public void swim(long currentTime){
        long diff = currentTime - this._previousTime;
        if(diff > this._speedFraction){
            int currentX = this.getSprite().getXPos();
            int currentY = this.getSprite().getYPos();
            if(this.getDirection() != 0) {
                this.getSprite().setXPos(currentX + this.getDirection());
            }
            else{
                this.getSprite().setRotate();
                if(currentY >= _aquarium.getAquaLevel()-75){
                    this.getSprite().setYPos(currentY - 2);
                }
                else{
                    this.getSprite().setYPos(_aquarium.getAquaLevel()-75);
                }
            }
            this._previousTime = currentTime;
        }
    }


}