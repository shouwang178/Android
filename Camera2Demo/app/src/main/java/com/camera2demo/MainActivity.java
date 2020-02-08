package com.camera2demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint({"ValidFragment", "NewApi"})
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private CameraDevice cameraDevice;

    List<String> requiredPermissions = Arrays.asList(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        checkPermissions();

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String backCameraId = getBackCameraId(cameraManager);
        openCamera(cameraManager, backCameraId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraDevice != null) {
            cameraDevice.close();
        }
    }

    private void checkPermissions() {
        List<String> unGrantedPermissions = new ArrayList<>();
        for (String permission : requiredPermissions) {
            if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(permission)) {
                unGrantedPermissions.add(permission);
            }
        }

        if (unGrantedPermissions.size() != 0) {
            requestPermissions(unGrantedPermissions.toArray(new String[unGrantedPermissions.size()]), 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private String getBackCameraId(CameraManager cameraManager) {
        try {
            String[] cameraIdList = cameraManager.getCameraIdList();
            CameraCharacteristics characteristics;
            for (String cameraId : cameraIdList) {
                characteristics = cameraManager.getCameraCharacteristics(cameraId);
                int facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (CameraCharacteristics.LENS_FACING_BACK == facing) {
                    return cameraId;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void openCamera(CameraManager cameraManager, String cameraId) {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            cameraManager.openCamera(cameraId, callback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private CameraDevice.StateCallback callback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            Toast.makeText(MainActivity.this, "相机已开启", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {

        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
        }

        @Override
        public void onClosed(@NonNull CameraDevice camera) {
            super.onClosed(camera);
            Toast.makeText(MainActivity.this, "相机已关闭", Toast.LENGTH_LONG).show();
        }
    };
}
