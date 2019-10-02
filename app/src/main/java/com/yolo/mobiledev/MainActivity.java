package com.yolo.mobiledev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yolo.downloadmanager.DownloadManager;
import com.yolo.downloadmanager.PermissionManager;
import com.yolo.downloadmanager.ProgressListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!PermissionManager.isCheckedPermissions(this)) {
            PermissionManager.requestPermission(this);
            return;
        }
        DownloadManager downloadManager = new DownloadManager();
        downloadManager.setListener(new ProgressListener() {
            @Override
            public void onProgress(String percent) {

            }
        });

        downloadManager.execute("http://admin.newstodaypro.in//uploads//565afb3777bc7807e1dd1d38a3899b95.mp3",
                ",us1",
                ".mp3",
                "/filesssss");
        Toast.makeText(this, "downloading...", Toast.LENGTH_SHORT).show();
    }
}
