package com.example.navdrawer.ui.song;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.net.Uri;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.navdrawer.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SongOptionsBottomSheet extends BottomSheetDialogFragment {
    public Song s;
    public static SongOptionsBottomSheet newInstance(Song song){
        SongOptionsBottomSheet fragment = new SongOptionsBottomSheet();
        Bundle args = new Bundle();
        args.putSerializable("song", song);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_song_options, container, false);

        TextView tvSongTitle = view.findViewById(R.id.tvSongName);
        TextView tvArtist = view.findViewById(R.id.tvArtistN);
        TextView tvShare = view.findViewById(R.id.tvshare);
        TextView about = view.findViewById(R.id.tvAbout);
        TextView youtube = view.findViewById(R.id.tvYoutube);

        Bundle args = getArguments();
        if(args != null){
            s = (Song) args.getSerializable("song");
            tvSongTitle.setText(s.getSongName());
            tvArtist.setText(s.getArtistName());
        }

        tvShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String Sname = tvSongTitle.getText().toString();
                String Aname = tvArtist.getText().toString();

                String songLink = "https://miniSpotify.com/song/"+ Sname.replace(" ", "_");
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("Text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Listen to "+Sname+ "\n" + songLink);
                startActivity(Intent.createChooser(shareIntent, "Share song via"));
                dismiss();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // close bottom sheet first
                String Sname = tvSongTitle.getText().toString();
                String Aname = tvArtist.getText().toString();
                Song song = new Song(Sname, 0,0, "", Aname, "3:00", "2000", "" );
                SongDetails dialog = new SongDetails(s);
                dialog.show(getParentFragmentManager(), "SongDetails");

            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sname = tvSongTitle.getText().toString();
                String Aname = tvArtist.getText().toString();

                String youtubeLink = "https://www.youtube.com/results?search_query="
                        + Sname.replace(" ", "+") + "+" + Aname.replace(" ", "+");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink));
                startActivity(intent);
            }
        });
        return view;
    }
}
