package com.example.debug.hzh;
/*
 * @author xy
 * @emil 384813636@qq.com
 * create at 2018/6/28
 * description:
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextView extends AppCompatActivity{
    private TextView sub,back;
    private EditText edittext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_edit);
        sub=findViewById(R.id.sub);
        back=findViewById(R.id.back);
        edittext=findViewById(R.id.edittext);
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
                setResult(111,intent);
                finish();
                }else{
                    finish();
                }
            }
        });

    }
}
