package com.example.navdrawer.ui.song;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.navdrawer.R;

public class SongDetails extends DialogFragment {
    Song song;

    public SongDetails(Song song) {
        this.song = song;
    }

    public Dialog onCreateDialog(@NonNull Bundle savedInstance) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.song_details, null);

        TextView tvSong = dialogView.findViewById(R.id.tvSong);
        TextView tvArtist = dialogView.findViewById(R.id.tvArtistName);
        TextView tvDuration = dialogView.findViewById(R.id.tvDuration);
        TextView tvReleaseYear = dialogView.findViewById(R.id.tvReleaseYear);
        TextView tvGenre = dialogView.findViewById(R.id.tvGenre);
        Button btnClose = dialogView.findViewById(R.id.btnClose);

        if (song != null) {
            tvSong.setText(song.getSongName());
            tvArtist.setText(song.getArtistName());
            tvDuration.setText(song.getDuration());
            tvReleaseYear.setText(song.getRelDate());
            tvGenre.setText(song.getGenre());
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setView(dialogView);
        return b.create();
    }
}

