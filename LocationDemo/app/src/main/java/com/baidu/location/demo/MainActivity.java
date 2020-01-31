package com.baidu.location.demo;

import java.util.ArrayList;
import java.util.List;

import com.baidu.baidulocationdemo.R;
import com.baidu.location.service.Utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/***
 * 本类代码同定位业务本身无关，负责显示列表
 *
 * @author baidu
 *
 */
public class MainActivity extends Activity {
    private final int SDK_PERMISSION_REQUEST = 127;
    private ListView FunctionList;
    private String permissionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_list);
        FunctionList = (ListView) findViewById(R.id.functionList);
        FunctionList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));
        // after andrioid m,must request Permiision on runtime
        getPersimmions();
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        FunctionList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Class<?> targetClass = null;
                switch (arg2) {
                    case 0:
                        targetClass = LocationActivity.class;
                        break;
                    case 1:
                        targetClass = LocationOption.class;
                        break;
                    case 2:
                        targetClass = LocationAutoNotify.class;
                        break;
                    case 3:
                        targetClass = LocationFilter.class;
                        break;
                    case 4:
                        targetClass = NotifyActivity.class;
                        break;
                    case 5:
                        targetClass = IndoorLocationActivity.class;
                        break;
                    case 6:
                        targetClass = GeoFenceMultipleActivity.class;
                        break;
                    case 7:
                        targetClass = SceneLocationActivity.class;
                        break;
                    case 8:
                        targetClass = ForegroundActivity.class;
                        break;
                    case 9:
                        targetClass = AssistLocationActivity.class;
                        break;
                    case 10:
                        targetClass = LocationNotifyActivity.class;
                        break;
                    case 11:
                        targetClass = IsHotWifiActivity.class;
                        break;
                    case 12:
                        targetClass = QuestActivity.class;
                        break;
                    default:
                        return;
                }

                if (targetClass != null) {
                    Intent intent = new Intent(MainActivity.this, targetClass);
                    intent.putExtra("from", 0);
                    startActivity(intent);
                }
            }
        });
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        data.add(getResources().getString(R.string.baseLocation));
        data.add(getResources().getString(R.string.configLocationParam));
        data.add(getResources().getString(R.string.customCbInst));
        data.add(getResources().getString(R.string.consistantLocation));
        data.add(getResources().getString(R.string.locationMsgNotify));
        data.add(getResources().getString(R.string.indoorLocation));
        data.add(getResources().getString(R.string.geographicalFence));
        data.add(getResources().getString(R.string.sceneLocation));
        data.add(getResources().getString(R.string.backgroundLocation));
        data.add(getResources().getString(R.string.h5Location));
        data.add(getResources().getString(R.string.locationNotify));
        data.add(getResources().getString(R.string.mobileHotspot));
        data.add(getResources().getString(R.string.problemDesc));
        return data;
    }
}
