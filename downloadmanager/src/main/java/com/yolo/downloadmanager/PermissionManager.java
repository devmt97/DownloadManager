package com.yolo.downloadmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionManager {


    private static List<String> listPermissionsNeeded;

    public static boolean isCheckedPermissions(Context context) {
        String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        };

        listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static void requestPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context,
                listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                1);
    }
}
