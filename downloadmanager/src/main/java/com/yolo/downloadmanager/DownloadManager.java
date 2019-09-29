package com.yolo.downloadmanager;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class DownloadManager extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String url;
        InputStream is = null;
        URL u = null;
        int len1 = 0;
        int temp_progress = 0;
        int progress = 0;
        try {
            u = new URL(params[0]);
            is = u.openStream();
            URLConnection huc = u.openConnection();
            huc.connect();
            int size = huc.getContentLength();

            if (huc != null) {
                String file_name = params[1] + params[2];
//	                    String storagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/YoutubeVideos";
                String storagePath = Environment.getExternalStorageDirectory() + params[3];
                File f = new File(storagePath);
                if (!f.exists()) {
                    f.mkdir();
                }

                FileOutputStream fos = new FileOutputStream(f.getAbsolutePath() + "/" + file_name);
                byte[] buffer = new byte[1024];
                int total = 0;
                if (is != null) {
                    while ((len1 = is.read(buffer)) != -1) {
                        total += len1;
                        progress = (int) ((total * 100) / size);
                        if (progress >= 0) {
                            temp_progress = progress;
                            publishProgress("" + progress);
                        } else {
                            publishProgress("" + temp_progress + 1);
                        }

                        fos.write(buffer, 0, len1);
                    }
                }

                if (fos != null) {
                    publishProgress("" + 100);
                    fos.close();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.d("abcdef", values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
