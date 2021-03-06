package com.example.debug.hzh;
/*
 * @author xy
 * @emil 384813636@qq.com
 * create at 2018/6/26
 * description:
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class HandWrite extends View
{
    Paint paint = null;
    private Bitmap originalBitmap = null;
    Bitmap new1Bitmap = null;
    boolean trys=false;
    private Bitmap new2Bitmap = null;
    private float clickX = 0,clickY = 0;
    private float startX = 0,startY = 0;
    private boolean isMove = true;
    private boolean isClear = false;
    int color = Color.GREEN;
    float strokeWidth = 20f;
    private Rect mSrcRect, mDestRect;
    private int mTotalWidth, mTotalHeight,mBitWidth,mBitHeight;
    private float mScale;
    private Matrix matrix;
    public HandWrite(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image).copy(Bitmap.Config.ARGB_8888, true);
        new1Bitmap = Bitmap.createBitmap(originalBitmap);
        new2Bitmap=Bitmap.createBitmap(new1Bitmap.getWidth(),new1Bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mBitWidth=new1Bitmap.getWidth();
        mBitHeight=new1Bitmap.getHeight();
        mSrcRect = new Rect(0, 0, mBitWidth, mBitHeight);
        mDestRect = new Rect(0, 0, mBitWidth, mBitHeight);
    }

    public void setMatrix(Matrix matrix){
        this.matrix=matrix;
    }
    public void clear(){
        isClear = true;
       // new2Bitmap = Bitmap.createBitmap(originalBitmap);
        new2Bitmap = Bitmap.createBitmap(originalBitmap);
        invalidate();
    }
    public void setstyle(float strokeWidth){
        this.strokeWidth = strokeWidth;
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int left = mTotalWidth/2 - mBitWidth / 2;
        // 计算上边位置
        int top = mTotalHeight/2 - mBitHeight / 2;
        mDestRect = new Rect(left, top, left + mBitWidth, top + mBitHeight);
            //canvas.drawBitmap(HandWriting(new1Bitmap), 0, 0, null);
           canvas.drawBitmap(HandWriting(new1Bitmap), mSrcRect, mDestRect, null);
       // canvas.drawBitmap(writeText(), mSrcRect, mDestRect, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth=w;
        mTotalHeight=h;
    }

    public Bitmap HandWriting(Bitmap originalBitmap)
    {
        Canvas canvas = null;

        if(isClear){
            canvas = new Canvas(new2Bitmap);
        }
        else{
            canvas = new Canvas(originalBitmap);
        }
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        if(isMove){
            canvas.drawLine(startX, startY, clickX, clickY, paint);
        }

        startX = clickX;
        startY = clickY;

        if(isClear){
            return new2Bitmap;
        }
        return originalBitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        clickX = event.getX();
        clickY = event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN){

            isMove = false;
            invalidate();
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(trys==true) {
                isMove = true;
                invalidate();
            }
            return true;
        }

        return super.onTouchEvent(event);
    }
    /*
    public Bitmap writeText() {
          //  Bitmap bitmap = Bitmap.createScaledBitmap(originalBitmap, mTotalWidth, mTotalHeight, true);
            textbitmap=Bitmap.createBitmap(mBitWidth,mBitHeight,Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(textbitmap);
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(18 * getResources().getDisplayMetrics().density);
            textPaint.setAntiAlias(true);
            StaticLayout staticLayout = new StaticLayout("test", textPaint, mTotalWidth * 3 / 4, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            canvas.translate(20,20);
            staticLayout.draw(canvas);
            textwidth=staticLayout.getWidth();
            textheight=staticLayout.getHeight();
            return textbitmap;
    }*/

}