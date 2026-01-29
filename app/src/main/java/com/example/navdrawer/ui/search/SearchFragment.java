package com.example.navdrawer.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navdrawer.MainActivity;
import com.example.navdrawer.R;

import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchInput;
    private LinearLayout discoverContainer;

//    private SongDao songDao;

    private final int[] discoverImages = {
            R.drawable.adam,
            R.drawable.sherine,
            R.drawable.joseph,
            R.drawable.amr,
            R.drawable.ariana
    };

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchInput = view.findViewById(R.id.searchInput);
        discoverContainer = view.findViewById(R.id.discoverContainer);
//
//        SongDatabase database = SongDatabase.getDatabase(requireContext());
//        songDao = database.songDao();
//        // Get all songs
//        List<SongEntity> songs = songDao.getAllSongs();
//
//        // Search songs
//        List<SongEntity> results = songDao.searchSongs("adam");

        return view;
    }

    private void setupSearchActions() {

        // SEARCH FROM KEYBOARD
        searchInput.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            String keyword = searchInput.getText().toString().trim();
            if (!keyword.isEmpty()) {
                //searchSongs(keyword);
            } else {
                Toast.makeText(requireContext(), "Please enter a search term", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        // SEARCH FROM ICON CLICK (RIGHT SIDE)
        searchInput.setOnTouchListener((View v, MotionEvent event) -> {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Check if the drawable on the right exists
                if (searchInput.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                    // Calculate the position of the right drawable
                    int drawableWidth = searchInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();

                    // Check if touch is within the drawable area (right side)
                    if (event.getX() >= (searchInput.getWidth() - drawableWidth - searchInput.getPaddingEnd())) {
                        String keyword = searchInput.getText().toString().trim();
                        if (!keyword.isEmpty()) {
                            //searchSongs(keyword);
                        } else {
                            Toast.makeText(requireContext(), "Please enter a search term", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                }
            }
            return false;
        });
    }

    private void loadDiscoverImages() {
        // Clear any existing views first
        discoverContainer.removeAllViews();

        for (int img : discoverImages) {
            // Inflate the CORRECT layout for individual discover items
            View itemView = getLayoutInflater().inflate(R.layout.item, discoverContainer, false);
            ImageView imageView = itemView.findViewById(R.id.discoverImage);
            imageView.setImageResource(img);

            // Add the item to the container
            discoverContainer.addView(itemView);
        }
    }

//    // INSERT SONGS ONLY ONCE
//    private void insertSongsIfNeeded() {
//        if (songDao.getAllSongs().isEmpty()) {
//
//            songDao.insertSong(new SongEntity(
//                    "Ya Helo",
//                    R.drawable.adam,
//                    R.raw.yahelo,
//                    "Adam",
//                    "3:20",
//                    "2021",
//                    "Pop",
//                    "Lyrics preview"
//            ));
//
//            songDao.insertSong(new SongEntity(
//                    "Baadak Ala Bali",
//                    R.drawable.sherine,
//                    R.raw.baadakalabaly,
//                    "Sherine",
//                    "4:10",
//                    "1980",
//                    "Classic",
//                    "Lyrics preview"
//            ));
//
//            songDao.insertSong(new SongEntity(
//                    "El Watar El Hassas",
//                    R.drawable.sherine,
//                    R.raw.elwatarelhassas,
//                    "Ragheb Alama",
//                    "3:50",
//                    "1995",
//                    "Arabic Pop",
//                    "Lyrics preview"
//            ));
//        }
//    }
//
//    private void searchSongs(String keyword) {
//        List<SongEntity> songs = songDao.getAllSongs();
//
//        for (SongEntity song : songs) {
//            if (song.getSongName().toLowerCase().contains(keyword.toLowerCase())
//                    || song.getArtistName().toLowerCase().contains(keyword.toLowerCase())) {
//
//                Intent intent = new Intent(requireContext(), MainActivity.class);
//                intent.putExtra("songName", song.getSongName());
//                intent.putExtra("artistName", song.getArtistName());
//                intent.putExtra("audioRes", song.getAudioRes());
//                intent.putExtra("imgRes", song.getImgRes());
//                startActivity(intent);
//                return;
//            }
//        }
//
//        Toast.makeText(requireContext(), "No songs found", Toast.LENGTH_SHORT).show();
    }