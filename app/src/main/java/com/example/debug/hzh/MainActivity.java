package com.example.debug.hzh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private String mText="";
    private FrameLayout fmlayout;
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
                colorchoose.setVisibility(View.GONE);
                handWrite.trys=false;
              Intent intent =new Intent();
              intent.putExtra("mText",mText);
              intent.setClass(MainActivity.this,EditTextView.class);
              startActivityForResult(intent,222);
            }
        });
        fmlayout=findViewById(R.id.mix);
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
              savebitmap(fmlayout);
                Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==222&&resultCode==111){
            mText=data.getStringExtra("text");
            String text=cutstr(mText);
            textStickerView.setText(text);
            textStickerView.setTextColor(data.getIntExtra("color",R.color.colorPrimary));
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
        handWrite.color=imgColorGroup.getCheckColor();
    }
    private String cutstr(String text) {
        if (text==null){
            return "";
        }
        char[] strs=text.toCharArray();
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            sb.append(strs[i]);
            if (i!=0&&(i+1)%17==0){
                sb.append("\n");
            }
        }
        String trim = sb.toString().trim();
        return trim;
    }
    private Bitmap savebitmap(FrameLayout view){
        float width = view.getWidth();//取最大的宽度作为最终宽度
        float height = view.getHeight();//计算总高度
        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config
                .ARGB_8888);//顶部图片
        view.draw(new Canvas(bitmap));
        return bitmap;
    }
}
