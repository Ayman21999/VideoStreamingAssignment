package com.example.ass4videostreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class MainActivity extends AppCompatActivity {
    String url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";
    PlayerView playerView;
    SimpleExoPlayer player;
    Button start ;
    Button stop ;
    private boolean whenReady = true;
    int crruntwindwo = 0;
    private long playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.button2);
        stop = findViewById(R.id.button);
        playerView = findViewById(R.id.videoexo);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniVideo();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasVideo();
            }
        });

    }

    public void iniVideo() {
        // Get the Player
        player = ExoPlayerFactory.newSimpleInstance(this);

        // Connect between the player and Player View
        playerView.setPlayer(player);
        // Media Source

        Uri uri = Uri.parse(url);
        DataSource.Factory dFactory = new DefaultDataSourceFactory(this , "exoplayer-codelab");
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dFactory).createMediaSource(uri);

        player.setPlayWhenReady(whenReady);
        player.seekTo(crruntwindwo , playbackPosition);
        player.prepare(mediaSource , false , false);
    }

    public void releasVideo(){
    if (player != null){
        whenReady = player.getPlayWhenReady();
        playbackPosition = player.getCurrentPosition();
        crruntwindwo = player.getCurrentWindowIndex();
        player.release();
        player =null;

    }
    }

    @Override
    protected void onStart() {
        super.onStart();
        iniVideo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null){
            iniVideo();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasVideo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasVideo();
    }
}