package com.example.navdrawer.ui.song;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.IconCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navdrawer.R;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Song> songsList;
    public Song song;
    public SongAdapter(Context c, ArrayList<Song> s){
        context = c;
        songsList = s;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView SongNametv, viewtv;
        private ImageView Songiv, iconiv;
        Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SongNametv = (TextView) itemView.findViewById(R.id.tvSongName);
            Songiv = (ImageView) itemView.findViewById(R.id.imgSong);
            viewtv = (TextView) itemView.findViewById(R.id.tvViews);
            iconiv = (ImageView) itemView.findViewById(R.id.imgMore);
        }
    }
    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        Song song = songsList.get(position);
        holder.SongNametv.setText(song.getSongName());
        holder.viewtv.setText(song.getViews());
        holder.Songiv.setImageResource(song.getImgIds());
        int pos = holder.getAdapterPosition();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("songs", songsList);
                args.putInt("currentIndex", pos);

                Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                        .navigate(R.id.action_SongFragment_to_playerFragment, args);
            }
        });
        holder.iconiv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SongOptionsBottomSheet sheet = SongOptionsBottomSheet.newInstance(song);
                if(context instanceof FragmentActivity) {
                    sheet.show(((FragmentActivity) context).getSupportFragmentManager(), "SongOption");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }
}
