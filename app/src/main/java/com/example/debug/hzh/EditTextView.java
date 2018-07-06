package com.example.debug.hzh;
/*
 * @author xy
 * @emil 384813636@qq.com
 * create at 2018/6/28
 * description:
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextView extends Activity implements View.OnClickListener{
    private TextView sub,back;
    private EditText edittext;
    private IMGColorGroup imgColorGroup;
    private IMGColorRadio cr_white,cr_black,cr_cyan,cr_red,cr_yellow,cr_blue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_edit);
        imgColorGroup=findViewById(R.id.cg_colors);
        Intent intent=getIntent();
        sub=findViewById(R.id.sub);
        back=findViewById(R.id.back);
        edittext=findViewById(R.id.edittext);
        edittext.setText(intent.getStringExtra("mText"));
        edittext.setSelection(intent.getStringExtra("mText").length());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=edittext.getText().toString();
                if(text!=null){
                Intent intent =new Intent();
                intent.putExtra("text",text);
                intent.putExtra("color",imgColorGroup.getCheckColor());
                setResult(111,intent);
                finish();
                }else{
                    finish();
                }
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
    }

    @Override
    public void onClick(View v) {
        edittext.setTextColor(imgColorGroup.getCheckColor());
    }
}
