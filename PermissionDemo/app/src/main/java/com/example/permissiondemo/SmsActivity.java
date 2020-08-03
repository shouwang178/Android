package com.example.permissiondemo;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.permissiondemo.listener.OnClickListener;
import com.example.permissiondemo.receiver.SendReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmsActivity extends Activity {

    private final static String TAG = SmsActivity.class.getSimpleName();

    //短信发送结果Action
    private static final String SMS_ACTION = "com.android.TinySMS.RESULT";

    //权限申请对应的请求码
    private final static int REQUEST_SMS_PERMISSION_CODE = 0;

    //短信相关权限
    private List<String> permissionList = Arrays.asList(Manifest.permission.SEND_SMS);

    //短信内容
    private EditText smsContent;

    //短信号码
    private EditText smsNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_layout);
        initView();
    }

    private void initView() {
        findViewById(R.id.sendSms).setOnClickListener(listener);
        smsContent = findViewById(R.id.smsContent);
        smsNumber = findViewById(R.id.smsNumber);
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onAction(View view) {
            switch (view.getId()) {
                //发送短信
                case R.id.sendSms:
                    onSmsClick();
                    break;
            }
        }
    };

    /**
     * 发送短信，需要检查权限授予
     *
     * */
    private void onSmsClick() {
        //6.0及以上需要动态检查权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> unGrantedPermissionList = checkPermissions(permissionList);
            if (unGrantedPermissionList.size() > 0) {
                //没有授予权限，需要主动申请
                requestPermissions(unGrantedPermissionList);
                return;
            }
        }

        //6.0以下安装时已经授予权限 || 6.0及以上权限运行时已经授予
        sendSms();
    }

    /**
     * 发送短信
     *
     * */
    private void sendSms() {
        String content = smsContent.getText().toString().trim();
        String number = smsNumber.getText().toString().trim();
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(number)) {
            Log.e(TAG, "sendSms is null");
            Toast.makeText(this, R.string.sms_input_error, Toast.LENGTH_SHORT).show();
            return;
        }

        //注册广播监听短信发送状态
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(SMS_ACTION);
        registerReceiver(new SendReceiver(), intentfilter);

        //get SmsManager
        SmsManager manager = SmsManager.getDefault();
        PendingIntent pi = PendingIntent.getBroadcast(this, REQUEST_SMS_PERMISSION_CODE,
                new Intent(SMS_ACTION), PendingIntent.FLAG_ONE_SHOT);

        //将短信内容进行拆分，防止超出限制
        ArrayList<String> contentList = manager.divideMessage(content);
        for (int i = 0; i < contentList.size(); i++) {
            manager.sendTextMessage(number, null, content, pi, null);
        }
    }

    /***
     * 检查权限
     * @param permissionList 权限列表
     * @return List<String>  未授权的权限列表
     *
     */
    private List<String> checkPermissions(List<String> permissionList) {
        List<String> unGrantedPermissionList = new ArrayList<>();
        for (String permission : permissionList) {
            if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(this, permission)) {
                unGrantedPermissionList.add(permission);
            }
        }

        return unGrantedPermissionList;
    }

    /**
     *
     * 请求权限
     * @param permissionList 权限列表
     * */
    private void requestPermissions(List<String> permissionList) {
        ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (REQUEST_SMS_PERMISSION_CODE == requestCode) {
            for (int result : grantResults) {
                if (PackageManager.PERMISSION_DENIED == result) {
                    //只要有一个权限没有授予，则返回
                    return;
                }
            }

            //所有权限都授予了，可以发送短信了
            sendSms();
        }
    }
}
