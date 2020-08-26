package com.example.permissiondemo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * 演示受自定义权限保护的页面（权限强制执行），在应用内跳转不需要申请权限。
 * 即：权限强制执行是针对应用间的
 *
 * */
public class CustomPermissionActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_permission_layout);
    }
}
