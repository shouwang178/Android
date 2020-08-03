package com.example.permissiondemo.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.permissiondemo.R;

/**
 *
 * 监听短信发送状态
 * */
public class SendReceiver extends BroadcastReceiver {

    private static final String SMS_ACTION = "com.android.TinySMS.RESULT";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SMS_ACTION.equals(intent.getAction())) {
            int code = getResultCode();
            if (code == Activity.RESULT_OK) {
                Toast.makeText(context, R.string.sms_send_succ, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
