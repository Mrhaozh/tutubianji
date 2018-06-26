package com.example.debug.hzh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button clear,bichu;
    private HandWrite handWrite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clear=findViewById(R.id.clear);
        handWrite=findViewById(R.id.handwrite);
        bichu=findViewById(R.id.bichu);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handWrite.clear();
            }
        });
        bichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handWrite.trys=!handWrite.trys;
            }
        });
    }
}
