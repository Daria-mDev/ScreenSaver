package com.example.screensaver;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.service.dreams.DreamService;
import android.util.Log;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class ScreenSaver extends DreamService {

    private static final String TAG = "DreamServiceMovie";
    private VideoView videoView;

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        setInteractive( false );
        setFullscreen( true );
        setContentView( R.layout.dream_service );
        Log.d( TAG, "__onAttachedToWindow()" );
    }

    @Override
    public void onDreamingStarted(){
        super.onDreamingStarted();
        Log.d( TAG, "__onDreamingStarted()" );


        videoView = findViewById( R.id.videoView );
        videoView.setOnPreparedListener( new MyVideoPreparedListener() );
        videoView.setOnCompletionListener( new MyVideoOnCompletionListener() );
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() +"/"+R.raw.video));
        videoView.start();
    }

    @Override
    public void onDreamingStopped(){
        super.onDreamingStopped();
        Log.d( TAG, "__onDreamingStopped()" );
        videoView.stopPlayback();
    }

    @Override
    public void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        Log.d( TAG, "__onDetachedFromWindow()" );
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d( TAG, "__onConfigurationChanged()" );
        wakeUp();
        finish();
    }

    class MyVideoPreparedListener implements MediaPlayer.OnPreparedListener{
        @Override
        public void onPrepared(MediaPlayer mediaPlayer ){
            Log.d( TAG, "__MyVideoPreparedListener.onPrepared()" );
            videoView.start();
        }
    }

    class MyVideoOnCompletionListener implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mediaPlayer ){
            Log.d( TAG, "__MyVideoOnCompletionListener.onCompletion()" );
            videoView.seekTo( 0 );
            videoView.start();
        }
    }

}