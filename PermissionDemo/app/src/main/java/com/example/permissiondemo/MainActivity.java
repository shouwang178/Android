package com.example.permissiondemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.permissiondemo.listener.OnClickListener;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        initView();
    }

    private void initView() {
        Button sendSms = findViewById(R.id.send_sms);
        sendSms.setOnClickListener(listener);

        Button openProtectdAct = findViewById(R.id.open_protected_activity);
        openProtectdAct.setOnClickListener(listener);
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onAction(View view) {
            switch (view.getId()) {
                case R.id.send_sms:
                    openSms();
                    break;
                case R.id.open_protected_activity:
                    openProtectedAct();
                    break;
            }
        }
    };

    /**
     * 打开短信页面
     *
     * */
    private void openSms() {
        Intent intent = new Intent(this, SmsActivity.class);
        startActivity(intent);
    }

    /**
     * 打开受保护的页面
     *
     * */
    private void openProtectedAct() {
        Intent intent = new Intent(this, CustomPermissionActivity.class);
        startActivity(intent);
    }
}
