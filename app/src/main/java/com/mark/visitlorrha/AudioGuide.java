package com.mark.visitlorrha;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class AudioGuide extends AppCompatActivity {

    MediaPlayer mPlayer;

    ImageView playPauseImage;
    ImageView imageViewOfAttraction;

    String filename = "";
    String filenameForImage;
    int durationOfTrack;



//    TextView textViewDurationOfTrack;
//    TextView textViewProgressOfTrack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_guide);

        playPauseImage = (ImageView) findViewById(R.id.imageViewPlayPauseAudioGuide);
        // textViewDurationOfTrack = (TextView) findViewById(R.id.textViewDurationOfTrack);
        // textViewProgressOfTrack = (TextView) findViewById(R.id.textViewProgressOfTrack);
        imageViewOfAttraction = (ImageView) findViewById(R.id.imageViewAudioGuide);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // shows the up/back button in the action bar



        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if(b != null){
            filename = (String) b.get("filename"); // e.g. domincanabbey
        }

        filenameForImage = filename;
        filenameForImage = filenameForImage + "pic1";
        int resourceIdImage = getResources().getIdentifier(filenameForImage, "drawable", getPackageName());
        imageViewOfAttraction.setImageResource(resourceIdImage);

        filename = "audio" + filename;  // audiodomincanabbey, now to match it up with the mp3 file

        // looks in the resource folder, in the subfolder raw for the 'filename', which is 'audioATTRACTIONNAME' in this case
        int resourceId = getResources().getIdentifier(filename, "raw", getPackageName());

        mPlayer = MediaPlayer.create(this, resourceId); // loads the mp3 file to play into the media player
        durationOfTrack = mPlayer.getDuration();
        mPlayer.start(); // automatically plays the mp3 when activity is loaded
        playPauseImage.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp); // default is the pause button as it plays automatically



        final SeekBar scrubber = (SeekBar) findViewById(R.id.seekBar);
        scrubber.setMax(mPlayer.getDuration()); // the max size of the srubber is set to the length of the audio track

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scrubber.setProgress(mPlayer.getCurrentPosition());
            }
        }, 0, 100 ); // starts playing with no delay, updates the location of the scrubber every tenth of a second.


        scrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mPlayer != null && fromUser) { // only updates if the user has moved it i.e. not programmatically moved
                    mPlayer.seekTo(progress);
                    playPauseImage.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayer.start();
            }
        });

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // triggered when the tracks ends
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playPauseImage.setImageResource(R.drawable.ic_play_circle_outline_black_24dp); // when track is finished, play button is displayed to play the track again
            }
        });

        playPauseImage.setOnClickListener(new View.OnClickListener() { // refereing to the play/pause button/image
            @Override
            public void onClick(View view) {
                if(mPlayer.isPlaying()){
                    mPlayer.pause();
                    playPauseImage.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                }else{
                    mPlayer.start();
                    playPauseImage.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                }

            }
        });

    }


    @Override
    protected void onPause() { // if user presses the back button, the track is paused
        super.onPause();
        mPlayer.pause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: // the up/back button in the action bar
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
