package com.example.navdrawer.ui.player;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navdrawer.R;
import com.example.navdrawer.ui.database.SongDatabase;
import com.example.navdrawer.ui.database.SongEntity;
import com.example.navdrawer.ui.song.Song;

import java.util.ArrayList;
import java.util.concurrent.Executors;


public class playerFragment extends Fragment {

    private SongEntity currentSongEntity;
    private SongDatabase db;

    private ImageView btnPlay, btnPrev, btnNext, ivSong, btnFavorite;
    private Runnable runnable;
    private SeekBar seekBar;
    private TextView txtSong, txtArtist, tvCurrent, tvDuration;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    private int currentIndex = 0;
    private ArrayList<Song> songs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("");

        // Bind views
        btnPlay = view.findViewById(R.id.btnPlay);
        btnPrev = view.findViewById(R.id.btnPrev);
        btnNext = view.findViewById(R.id.btnNext);
        btnFavorite = view.findViewById(R.id.btnfavorite);
        seekBar = view.findViewById(R.id.seekBar);
        txtSong = view.findViewById(R.id.txtSong);
        txtArtist = view.findViewById(R.id.txtArtist);
        tvCurrent = view.findViewById(R.id.tv1);
        tvDuration = view.findViewById(R.id.tv2);
        ivSong = view.findViewById(R.id.imgCover);

        db = SongDatabase.getDatabase(requireContext());

        // Get songs from arguments
        Bundle args = getArguments();
        if (args != null) {
            ArrayList<Song> passedSongs = (ArrayList<Song>) args.getSerializable("songs");
            int index = args.getInt("currentIndex", 0);

            if (passedSongs != null && !passedSongs.isEmpty() && index >= 0 && index < passedSongs.size()) {
                songs = passedSongs;
                currentIndex = index;
                loadSong(currentIndex);
            } else {
                Toast.makeText(getContext(), "No songs to play", Toast.LENGTH_SHORT).show();
                btnFavorite.setEnabled(false);
            }
        } else {
            Toast.makeText(getContext(), "No arguments passed", Toast.LENGTH_SHORT).show();
            btnFavorite.setEnabled(false);
        }

        // Play / Pause
        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                    updateSeekBar();
                }
            }
        });

        // Next song
        btnNext.setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % songs.size();
            loadSong(currentIndex);
        });

        // Previous song
        btnPrev.setOnClickListener(v -> {
            currentIndex--;
            if (currentIndex < 0) currentIndex = songs.size() - 1;
            loadSong(currentIndex);
        });

        // Favorite song
        btnFavorite.setOnClickListener(v -> {
            if (currentSongEntity == null) return;

            currentSongEntity.isLiked = !currentSongEntity.isLiked;

            // Update DB in background
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    db.songDao().insert(currentSongEntity); // insert or replace
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Update UI
            btnFavorite.setSelected(currentSongEntity.isLiked);
            Toast.makeText(getContext(),
                    currentSongEntity.isLiked ? "Added to Liked Songs" : "Removed from Liked Songs",
                    Toast.LENGTH_SHORT).show();

//            // Notify Library fragment (optional)
//            if (getParentFragmentManager().findFragmentByTag("libraryFragment") instanceof LibraryFragment) {
//                LibraryFragment libraryFragment = (LibraryFragment) getParentFragmentManager().findFragmentByTag("libraryFragment");
//                libraryFragment.refreshLikedSongs(); // create this method in LibraryFragment
//            }
        });

        // SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int pos, boolean fromUser) {
                if (fromUser && mediaPlayer != null) mediaPlayer.seekTo(pos);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        runnable = this::updateSeekBar;
    }

    private void loadSong(int index) {
        Song song = songs.get(index);

        txtSong.setText(song.getSongName());
        txtArtist.setText(song.getArtistName());
        ivSong.setImageResource(song.getImgIds());

        // Create SongEntity for DB
        currentSongEntity = new SongEntity(
                song.getSongName(),
                song.getImgIds(),
                song.getAudio(),
                "0",
                song.getArtistName(),
                "3:00",
                "2021",
                "Pop"
        );

        // Check if already liked
        Executors.newSingleThreadExecutor().execute(() -> {
            SongEntity dbSong = db.songDao().getSongByName(song.getSongName());
            if (dbSong != null) {
                currentSongEntity.isLiked = dbSong.isLiked;
                requireActivity().runOnUiThread(() -> btnFavorite.setSelected(currentSongEntity.isLiked));
            }
        });

        playSong(song.getAudio());
    }

    private void playSong(int audio) {
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(getActivity(), audio);
        mediaPlayer.start();

        seekBar.setMax(mediaPlayer.getDuration());
        tvDuration.setText(formatTime(mediaPlayer.getDuration()));
        updateSeekBar();
    }

    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    private void updateSeekBar() {
        if (mediaPlayer == null) return;
        int currentPos = mediaPlayer.getCurrentPosition();
        seekBar.setProgress(currentPos);
        tvCurrent.setText(formatTime(currentPos));

        if (mediaPlayer.isPlaying()) handler.postDelayed(runnable, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(runnable);
    }
}
