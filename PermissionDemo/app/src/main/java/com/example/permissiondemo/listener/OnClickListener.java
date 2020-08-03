package com.example.permissiondemo.listener;

import android.view.View;

public abstract class OnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        onAction(v);
    }

    public abstract void onAction(View view);
}
