package com.example.onclickdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        initData();
    }

    private void initData() {
        findViewById(R.id.btn1).setOnClickListener((v) -> {
            Toast.makeText(this, R.string.btn1_text, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn3).setOnClickListener(new OnClickListener());
        findViewById(R.id.btn4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, R.string.btn4_text, Toast.LENGTH_SHORT).show();
    }

    public void click(View v) {
        Toast.makeText(this, R.string.btn2_text, Toast.LENGTH_SHORT).show();
    }
}
