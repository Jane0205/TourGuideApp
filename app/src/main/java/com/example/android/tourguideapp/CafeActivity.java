package com.example.android.tourguideapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by 재은 on 2017-02-23.
 */

public class CafeActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    private MediaPlayer.OnCompletionListener mCompletionListener =
            new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer mediaPlayer){
                    releaseMediaPlayer();
                }
            };
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                        // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                        // our app is allowed to continue playing sound but at a lower volume. We'll treat
                        // both cases the same way because our app is playing short sound files.

                        // Pause playback and reset player to the start of the file. That way, we can
                        // play the word from the beginning when we resume playback.
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                        // Stop playback and clean up resources
                        releaseMediaPlayer();
                    }
                }
            };

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Detail> details = new ArrayList<Detail>();
        details.add(new Detail("Café Carlyle", "The Carlyle, A Rosewood Hotel, 35 E 76th St, New York", R.drawable.cafe, 2127441600,R.raw.bgm));
        details.add(new Detail("Mamajuana Cafe", "247 Dyckman St, New York", R.drawable.cafe,2123040140,R.raw.bgm));
        details.add(new Detail("Piccolo Cafe", "313 Amsterdam Ave, New York", R.drawable.cafe, 2128730962,R.raw.bgm));
        details.add(new Detail("Macaron Cafe", "44 E 59th St, New York, NY 10022", R.drawable.cafe, 2124862470,R.raw.bgm));
        details.add(new Detail("Caffe Bene", "9 Edward M Morgan Pl, New York, NY 10032", R.drawable.cafe,467915518,R.raw.bgm));
        details.add(new Detail("Europa Cafe", "205 W 57th St # 2, New York, NY 10019", R.drawable.cafe, 2129774030,R.raw.bgm));
        details.add(new Detail("Café Habana", "17 Prince St, New York, NY 10012", R.drawable.cafe, 2126252001,R.raw.bgm));
        details.add(new Detail("Joe Pro Shop & Headquarters", " 131 W 21st St, New York, NY 10011", R.drawable.cafe,2129247400,R.raw.bgm));

        DetailAdapter adapter = new DetailAdapter(this,details,R.color.cafe);

        ListView listView = (ListView)findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                releaseMediaPlayer();

                Detail detail = details.get(position);

                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                      mediaPlayer = MediaPlayer.create(CafeActivity.this,detail.getAudioResourceId());


                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

    }

}
