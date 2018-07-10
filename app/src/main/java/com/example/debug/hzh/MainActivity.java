package com.example.debug.hzh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ZoomImageView.OnScaleChangeListener, ZoomImageView.OnImageSetListener {
    private ImageButton clear, bichu,writetext;
    private final float[] matrixValues = new float[9];
    private final float[] matrixValues2 = new float[9];
    private LinearLayout colorchoose;
    private GraffitiView handWrite;
    private TextView save;
    private Color color;
    private IMGColorRadio cr_white, cr_black, cr_cyan, cr_red, cr_yellow, cr_blue;
    private ImageView left, right;
    private IMGColorGroup imgColorGroup;
    private TextStickerView textStickerView;
    private String mText = "";
    private FrameLayout fmlayout;
    private ZoomImageView bgimg;

    private Bitmap bitmap1, bitmap2, bitmap3;
    private Canvas mCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bgimg = findViewById(R.id.bgimg);
        bgimg.setOnSlideListener(this);
        bgimg.setOnImageSetListener(this);
        //handWrite.bgimg=bgimg;
        clear = findViewById(R.id.clear);
        colorchoose = findViewById(R.id.colorchoose);
        handWrite = findViewById(R.id.handwrite);
        bichu = findViewById(R.id.bichu);
        save = findViewById(R.id.save);
        imgColorGroup = findViewById(R.id.cg_colors);
        writetext = findViewById(R.id.writetext);
        writetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorchoose.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.putExtra("mText", mText);
                intent.setClass(MainActivity.this, EditTextView.class);
                startActivityForResult(intent, 222);
            }
        });
        fmlayout = findViewById(R.id.mix);
        cr_white = findViewById(R.id.cr_white);
        cr_black = findViewById(R.id.cr_black);
        cr_blue = findViewById(R.id.cr_blue);
        cr_cyan = findViewById(R.id.cr_cyan);
        cr_red = findViewById(R.id.cr_red);
        cr_yellow = findViewById(R.id.cr_yellow);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        cr_yellow.setOnClickListener(this);
        cr_red.setOnClickListener(this);
        cr_cyan.setOnClickListener(this);
        cr_blue.setOnClickListener(this);
        cr_black.setOnClickListener(this);
        cr_white.setOnClickListener(this);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handWrite.undo();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handWrite.reundo();
            }
        });
        textStickerView = findViewById(R.id.textstricker);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savebitmap(fmlayout);
                Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bd = (BitmapDrawable) bgimg.getDrawable();
                bitmaprec(bd.getBitmap());
                bitmaprec(bitmap1);
                bitmaprec(bitmap2);
                bitmaprec(bitmap3);
                bgimg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable
                        .timg).copy(Bitmap.Config.ARGB_8888, false));

                handWrite.clearall();
            }
        });
        bichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (colorchoose.getVisibility() == View.GONE) {
                    colorchoose.setVisibility(View.VISIBLE);
                    bichu.setImageDrawable(getResources().getDrawable(R.drawable.pencial_select));
                    //imgColorGroup.getCheckColor();
                    BitmapDrawable bd = (BitmapDrawable) bgimg.getDrawable();
                    bitmaprec(bd.getBitmap());
                    handWrite.setVisibility(View.VISIBLE);
                    bgimg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable
                            .timg).copy(Bitmap.Config.ARGB_8888, false));
                } else {
                    colorchoose.setVisibility(View.GONE);
                    bichu.setImageDrawable(getResources().getDrawable(R.drawable.pencial));
                    BitmapDrawable bd = (BitmapDrawable) bgimg.getDrawable();
                    bitmap2 = savebitmap(handWrite);
                    Matrix matrix = bgimg.mScaleMatrix;
                    matrix.getValues(matrixValues);
                    float scalex = matrixValues[Matrix.MSCALE_X];
                    float scaley = matrixValues[Matrix.MSCALE_Y];
                    float tranx = matrixValues[Matrix.MTRANS_X];
                    float trany = matrixValues[Matrix.MTRANS_Y];
                    /* Matrix matrix2 = new Matrix();
                    matrix2.postScale(1f / scalex, 1f / scaley, 0, 0);
                    matrix2.postTranslate(-1 / scalex * tranx, -1 / scaley * trany);
                    */
                    //Log.e("123fff", "" + "x" + scalex + "y" + scaley + "x" + tranx + "y" + trany);
                    bitmap1 = bd.getBitmap();
                    bitmap3 = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(),
                            bitmap1.getConfig());
                    mCanvas = new Canvas(bitmap3);
                    mCanvas.drawBitmap(bitmap1, new Matrix(), null);
                    mCanvas.drawBitmap(bitmap2, new Matrix(), null);
                    bitmaprec(bitmap1);
                    bitmaprec(bitmap2);
                    bgimg.setImageBitmap(bitmap3);
                    handWrite.setVisibility(View.GONE);
                }

            }
        });
    }

    private TextStickerView getTextStickerView() {
        return textStickerView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 222 && resultCode == 111) {
            mText = data.getStringExtra("text");
            textStickerView.setText(cutstr(mText));
            textStickerView.setTextColor(data.getIntExtra("color", R.color.colorPrimary));
        }
    }

    public void saveMyBitmap(File f, Bitmap mBitmap) throws IOException {
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        //handWrite.color=imgColorGroup.getCheckColor();
        handWrite.resetPaintColor(imgColorGroup.getCheckColor());
    }

    private String cutstr(String text) {
        if (text == null) {
            return "";
        }
        char[] strs = text.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            sb.append(strs[i]);
            if (i != 0 && (i + 1) % 17 == 0) {
                sb.append("\n");
            }
        }
        String trim = sb.toString().trim();
        return trim;
    }

    private Bitmap savebitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap((int)view.getWidth(), (int) view.getHeight(), Bitmap.Config
                .ARGB_8888);//顶部图片
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    public Bitmap mergeBitmap(Bitmap backBitmap, Bitmap frontBitmap) {

        if (backBitmap == null || backBitmap.isRecycled()
                || frontBitmap == null || frontBitmap.isRecycled()) {
            // Log.e(TAG, "backBitmap=" + backBitmap + ";frontBitmap=" + frontBitmap);
            return null;
        }
        Bitmap bitmap = backBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Rect baseRect = new Rect(0, 0, backBitmap.getWidth(), backBitmap.getHeight());
        Rect frontRect = new Rect(0, 0, frontBitmap.getWidth(), frontBitmap.getHeight());
        canvas.drawBitmap(frontBitmap, frontRect, baseRect, null);
        return bitmap;
    }

    @Override
    public void onChange(float mScale, float dx, float dy) {
        Matrix matrix = bgimg.mScaleMatrix;
        matrix.getValues(matrixValues);
        // handWrite.setVisibility(View.GONE);
        float scalex = matrixValues[Matrix.MSCALE_X];
        float scaley = matrixValues[Matrix.MSCALE_Y];
        float tranx = matrixValues[Matrix.MTRANS_X];
        float trany = matrixValues[Matrix.MTRANS_Y];
        if (textStickerView != null) {
            textStickerView.setScaleX(scalex);
            textStickerView.setScaleY(scaley);
            textStickerView.setPivotX(0);
            textStickerView.setPivotY(0);
            textStickerView.setTranslationX(tranx);
            textStickerView.setTranslationY(trany);
            textStickerView.postInvalidate();
        }
        if (handWrite != null) {
            handWrite.setScaleX(scalex);
            handWrite.setScaleY(scaley);
            handWrite.setPivotX(0);
            handWrite.setPivotY(0);
            handWrite.setTranslationX(tranx);
            handWrite.setTranslationY(trany);
            handWrite.postInvalidate();
        }
        Log.e("dff", "" + textStickerView.getWidth() * scalex + "width" + textStickerView
                .getWidth() + "scale" + scalex);
    }

    @Override
    public void onImageChange() {
        BitmapDrawable bd = (BitmapDrawable) bgimg.getDrawable();
        Bitmap mbitmap = bd.getBitmap();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(textStickerView
                .getLayoutParams());
        layoutParams.width = (int) (mbitmap.getWidth());
        layoutParams.height = (int) (mbitmap.getHeight());
        textStickerView.setLayoutParams(layoutParams);
        handWrite.setLayoutParams(layoutParams);

        Matrix matrix = bgimg.mScaleMatrix;
        matrix.getValues(matrixValues);

        float scalex = matrixValues[Matrix.MSCALE_X];
        float scaley = matrixValues[Matrix.MSCALE_Y];
        float tranx = matrixValues[Matrix.MTRANS_X];
        float trany = matrixValues[Matrix.MTRANS_Y];
        if (handWrite != null) {
            handWrite.setScaleX(scalex);
            handWrite.setScaleY(scaley);
            handWrite.setPivotX(0);
            handWrite.setPivotY(0);
            handWrite.setTranslationX(tranx);
            handWrite.setTranslationY(trany);
            handWrite.postInvalidate();
        }
    }

    public void bitmaprec(Bitmap mBitmap) {
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
        System.gc();
    }
}
