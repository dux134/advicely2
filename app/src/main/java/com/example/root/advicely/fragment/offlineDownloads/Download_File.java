package com.example.root.advicely.fragment.offlineDownloads;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.root.advicely.DatabaseHelper_and_Adds.MyActivity;
import com.example.root.advicely.R;
import com.example.root.advicely.fragment.OfflineDownload;
import com.example.root.advicely.utils.AppVariable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by root on 1/12/18.
 */

public class Download_File extends MyActivity {
    private static AtomicInteger c = new AtomicInteger(0);
    public static int NOTIFICATION_ID;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyManager;
    int id = 1;
    int counter = 0;
    private NotificationReceiver nReceiver;
    private static ArrayList<AsyncTask<String, String, Void>> arr = new ArrayList<AsyncTask<String, String, Void>>();
    Context activity;
    private String fileTitle;
    int totalSize = 0;
    int downloadedSize = 0;
    private Boolean isNotificationAlive = true;
    Thread thread;

    public Download_File(Context activity1,String downloadUrl,String fileNam) {
        activity = activity1;
        fileTitle = fileNam;
        id = getNOTIFICATION_ID();
        mNotifyManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(activity);
        mBuilder.setContentTitle(fileTitle).setContentText("Download in progress")
                .setSmallIcon(R.mipmap.ic_launcher_foreground);

        // Start a lengthy operation in a background thread
        mBuilder.setProgress(0, 0, true);
        mNotifyManager.notify(id, mBuilder.build());
        mBuilder.setAutoCancel(true);
        mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;

        ImageDownloader imageDownloader = new ImageDownloader();
        imageDownloader.execute(downloadUrl);
        arr.add(imageDownloader);


        ContentResolver contentResolver = activity.getContentResolver();
        String enabledNotificationListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = activity.getPackageName();

        if (enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName)) {
            // in this situation we know that the user has not granted the app
            // the Notification access permission
            // Check if notification is enabled for this application
//            Log.i("ACC", "Dont Have Notification access");
//            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
//            activity.startActivity(intent);
        } else {
            Log.i("ACC", "Have Notification access");
        }

        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(NLService.NOT_TAG);
        activity.registerReceiver(nReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        killTasks();
        mNotifyManager.cancelAll();
        Toast.makeText(getApplicationContext(),"cancelled",Toast.LENGTH_SHORT).show();
        unregisterReceiver(nReceiver);
    }

    class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String event = intent.getExtras().getString(NLService.NOT_EVENT_KEY);
            Log.i("NotificationReceiver", "NotificationReceiver onReceive : " + event);
            if (event.trim().contentEquals(NLService.NOT_REMOVED)) {
                killTasks();
            }

            Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
            int notificationId = intent.getIntExtra("notificationId", 0);

        }
    }

    private void killTasks() {
        if (null != arr & arr.size() > 0) {
            for (AsyncTask<String, String, Void> a : arr) {
                if (a != null) {
                    Log.i("NotificationReceiver", "Killing download thread");
                    a.cancel(true);
                }
            }
        }
    }

    private void downloadImagesToSdCard(String downloadUrl, String fileName) {
        FileOutputStream fos;
        InputStream inputStream = null;



        try {
            URL url = new URL(downloadUrl);
            /* making a directory in sdcard */
            //ff
//            String sdCard = Environment.getExternalStorageDirectory().toString();
            File myDir= new File(android.os.Environment.getExternalStorageDirectory(), AppVariable.folder_main);

            /* if specified not exist create new */
            if (!myDir.exists()) {
                myDir.mkdir();
                Log.v("", "inside mkdir");
            }

            /* checks the file and if it already exist delete */
            String fname = fileName;
            File file = new File(myDir, fname);
            Log.d("file===========path", "" + file);
            if (file.exists())
                file.delete();

            /* Open a connection */
            URLConnection ucon = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) ucon;
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            inputStream = httpConn.getInputStream();

            /*
             * if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
             * inputStream = httpConn.getInputStream(); }
             */

            fos = new FileOutputStream(file);
            totalSize = httpConn.getContentLength();

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int per;

                    while (isNotificationAlive) {
                        per = (int) (((getDownloadedSize()) / getTotalSize()) * 100f);
                        mBuilder.setContentText("Downloaded : " + (getDownloadedSize() / 1024) + "KB/" + (getTotalSize() / 1024) + "KB");
                            // Displays the progress bar for the first time.
                        mBuilder.setProgress(100,per, true);
                        mNotifyManager.notify(id, mBuilder.build());
                    }

                    Notification note = mBuilder.build();
                    note.defaults |= Notification.DEFAULT_VIBRATE;

                    mBuilder.setContentTitle(fileTitle);
                    mBuilder.setContentText("Download complete")
                            // Removes the progress bar
                            .setProgress(0, 0, false);
                    mNotifyManager.notify(id, note);
                }
            });
            thread.start();

            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;

//
            }

            inputStream.close();
            fos.close();
        } catch (IOException io) {
            inputStream = null;
            fos = null;
            io.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    class ImageDownloader extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... param) {
            downloadImagesToSdCard(param[0], fileTitle);
            return null;
        }

        protected void onProgressUpdate(String... values) {
        }

        @Override
        protected void onPreExecute() {
            Log.i("Async-Example", "onPreExecute Called");
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("Async-Example", "onPostExecute Called");

            isNotificationAlive = false;
            thread.destroy();
            File myDir= new File(android.os.Environment.getExternalStorageDirectory(), AppVariable.folder_main);
            File myFileName= new File(android.os.Environment.getExternalStorageDirectory(), AppVariable.folder_main+"/"+fileTitle);
            if (myFileName.exists()) //Checking for the file is exist or not
            {
                File from = new File(myDir,fileTitle);
                File to = new File(myDir,fileTitle+".pdf");
                from.renameTo(to);
            }

            OfflineDownload.refereshFromOutside();

            Notification note = mBuilder.build();
            note.defaults |= Notification.DEFAULT_VIBRATE;

            mBuilder.setContentTitle(fileTitle);
            mBuilder.setContentText("Download complete")
                    // Removes the progress bar
                    .setProgress(0, 0, false);
            mNotifyManager.notify(id, note);
        }

    }
    public int getNOTIFICATION_ID() {
        return NOTIFICATION_ID = c.incrementAndGet();
    }

    public int getDownloadedSize() {
        return downloadedSize;
    }

    public int getTotalSize() {
        return totalSize;
    }
}
