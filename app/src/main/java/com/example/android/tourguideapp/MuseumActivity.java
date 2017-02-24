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

public class MuseumActivity extends AppCompatActivity {

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
        details.add(new Detail("The Museum of Modern Art", "11 W 53rd St, New York, NY 10019", R.drawable.museum, 2127089400));
        details.add(new Detail("The Metropolitan Museum of Art", "1000 5th Ave, New York, NY 10028", R.drawable.museum, 2125357710));
        details.add(new Detail("American Museum of Natural History", " Central Park West & 79th St, New York, NY 10024", R.drawable.museum, 2127695100));
        details.add(new Detail("Solomon R. Guggenheim Museum", "1071 5th Ave, New York, NY 10128", R.drawable.museum, 2124233500));
        details.add(new Detail("Whitney Museum of American Art", "99 Gansevoort St, New York, NY 10014", R.drawable.museum, 2125703600));
        details.add(new Detail("Museum of Sex", "233 5th Ave, New York, NY 10016", R.drawable.museum, 2126896337));
        details.add(new Detail("The Morgan Library & Museum", " 225 Madison Ave, New York, NY 10016", R.drawable.museum, 2126850008));


        DetailAdapter adapter = new DetailAdapter(this,details,R.color.museum);

        ListView listView = (ListView)findViewById(R.id.list);

        listView.setAdapter(adapter);


    }

}
