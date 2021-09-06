package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {

    // Declaring the variables:
    ImageView playButton, pauseButton, fastForwardButton, fastRewindButton, coverArt;
    SeekBar seekBar;
    TextView playerDuration, playerCurrentTime;
    TextView songName;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // Defining the variables:
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        fastForwardButton = findViewById(R.id.fastForwardButton);
        fastRewindButton = findViewById(R.id.fastRewindButton);
        coverArt = findViewById(R.id.coverArt);
        seekBar = findViewById(R.id.seekBar);
        playerDuration = findViewById(R.id.playerDuration);
        playerCurrentTime = findViewById(R.id.playerCurrentTime);
        songName = findViewById(R.id.songName);

        Intent intent = getIntent();
        String fullSongName = intent.getStringExtra("full_song_name");
        String resourceName = intent.getStringExtra("resource_name");

        // Setting initial values:
        pauseButton.setVisibility(View.GONE);
        playerCurrentTime.setText(convertToTimeFormat(0));
        songName.setText(fullSongName);

        // Creating the Media Player:
        mediaPlayer = MediaPlayer.create(this, getResources().getIdentifier(resourceName, "raw", getApplicationContext().getPackageName()));

        // Setting up runnable:
        runnable = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        };

        // Getting the song duration in milliseconds:
        int duration = mediaPlayer.getDuration();

        // Formatting the duration:
        String formattedDuration = convertToTimeFormat(duration);
        playerDuration.setText(formattedDuration);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To prevent leaking of other played audio from previous sessions:
                // Hiding the play button:
                playButton.setVisibility(View.GONE);
                // Show the pause button:
                pauseButton.setVisibility(View.VISIBLE);
                // Start the media player:
                mediaPlayer.start();
                // Set max on the seekBar:
                seekBar.setMax(mediaPlayer.getDuration());
                // Now we start the handler:
                handler.postDelayed(runnable, 0);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiding the pause button:
                pauseButton.setVisibility(View.GONE);
                // Showing the play button:
                playButton.setVisibility(View.VISIBLE);
                // Pausing the media player:
                mediaPlayer.pause();
                // Now we stop the handler:
                handler.removeCallbacks(runnable);
            }
        });

        fastForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current position of media player:
                int currentPosition = mediaPlayer.getCurrentPosition();
                // Check if the song is playing and if the song is at the end:
                if(mediaPlayer.isPlaying() && currentPosition != mediaPlayer.getDuration()) {
                    currentPosition += 5000;
                }
                // Set the seekBar to the current position:
                mediaPlayer.seekTo(currentPosition);
            }
        });

        fastRewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current position of media player:
                int currentPosition = mediaPlayer.getCurrentPosition();
                // Check if the song is playing and if it is at the beginning:
                if (mediaPlayer.isPlaying() && currentPosition > 5000) {
                    currentPosition -= 5000;
                }
                // Set the seekBar to the current position:
                mediaPlayer.seekTo(currentPosition);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Check if the change on the seekBar is from the user:
                if (fromUser) {
                    // Setting the media player to the progress user chose by dragging the seekBar:
                    mediaPlayer.seekTo(progress);
                }

                playerCurrentTime.setText(convertToTimeFormat(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Hide the pause button:
                pauseButton.setVisibility(View.GONE);
                // Show the play button:
                playButton.setVisibility(View.VISIBLE);
                // Set the seekBar to the beginning:
                mediaPlayer.seekTo(0);
            }
        });
    }

    // Checking to see if the user left the Player Activity:
    @Override
    public void onBackPressed()
    {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        // Finishes the activity. Done so the player can stop when we leave the application:
        finish();
    }

    private String convertToTimeFormat(int duration) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }


}