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
    private ProgressListener listener;

    public void setListener(ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String url;
        InputStream is = null;
        URL u = null;
        int read = 0;
        int temp_progress = 0;
        int progress = 0;
        try {
            u = new URL(params[0]);
            is = u.openStream();
            URLConnection huc = u.openConnection();
            huc.setRequestProperty("Accept-Encoding", "identity"); // <--- Add this line
            int size = huc.getContentLength(); // i get negetive length

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
                int count = 0;
                if (is != null) {
                    while ((count = is.read(buffer)) != -1) {
                        read += count;
                        fos.write(buffer, 0, count);
                        publishProgress(""+(int) ((read * 100) / size));
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
        listener.onProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
