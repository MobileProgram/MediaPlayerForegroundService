package com.mblhcmute.mediaplayerforegroundservice;

import static com.mblhcmute.mediaplayerforegroundservice.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MBL", "MyService onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            Song song = (Song) bundle.get("objectSong");

            if(song != null) {
                startMusic(song);

                sendNotification(song);
            }
        }

        return START_NOT_STICKY;
    }

    private void startMusic(Song song) {
        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), song.getResource());
        }

        mediaPlayer.start();
    }

    private void sendNotification(Song song) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), song.getImage());

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);
        remoteViews.setTextViewText(R.id.tvTitleSong, song.getTitle());
        remoteViews.setTextViewText(R.id.tvSingleSong, song.getSingle());
        remoteViews.setImageViewBitmap(R.id.imgSong, bitmap);

        remoteViews.setImageViewResource(R.id.imgPlayOrPause,R.drawable.ic_pause);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .setSound(null)
                .build();

        startForeground(1,notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MBL", "MyService onCreate");
        if (mediaPlayer!= null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
