package com.example.debug.hzh;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button clear,bichu,writetext;
    private LinearLayout colorchoose;
    private HandWrite handWrite;
    private TextView save;
    private Color color;
    private IMGColorRadio cr_white,cr_black,cr_cyan,cr_red,cr_yellow,cr_blue;
    private IMGColorGroup imgColorGroup;
    private TextStickerView textStickerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clear=findViewById(R.id.clear);
        colorchoose=findViewById(R.id.colorchoose);
        handWrite=findViewById(R.id.handwrite);
        bichu=findViewById(R.id.bichu);
        save=findViewById(R.id.save);
        imgColorGroup=findViewById(R.id.cg_colors);
        writetext=findViewById(R.id.writetext);
        writetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               textStickerView.setText("文字");
               textStickerView.setTextColor(R.color.colorPrimary);
            }
        });
        cr_white=findViewById(R.id.cr_white);
        cr_black=findViewById(R.id.cr_black);
        cr_blue=findViewById(R.id.cr_blue);
        cr_cyan=findViewById(R.id.cr_cyan);
        cr_red=findViewById(R.id.cr_red);
        cr_yellow=findViewById(R.id.cr_yellow);
        cr_yellow.setOnClickListener(this);
        cr_red.setOnClickListener(this);
        cr_cyan.setOnClickListener(this);
        cr_blue.setOnClickListener(this);
        cr_black.setOnClickListener(this);
        cr_white.setOnClickListener(this);
        textStickerView=findViewById(R.id.textstricker);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File f = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/aaa.jpg");
                try {

                    saveMyBitmap(f, handWrite.new1Bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handWrite.clear();
            }
        });
        bichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(colorchoose.getVisibility()==View.GONE){
                    colorchoose.setVisibility(View.VISIBLE);
                    //imgColorGroup.getCheckColor();

                    handWrite.trys=!handWrite.trys;
                }else{
                    colorchoose.setVisibility(View.GONE);
                    //imgColorGroup.getCheckColor();
                    handWrite.color=imgColorGroup.getCheckColor();
                    handWrite.trys=!handWrite.trys;
                }

            }
        });
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
        handWrite.color=imgColorGroup.getCheckColor();
    }
}
