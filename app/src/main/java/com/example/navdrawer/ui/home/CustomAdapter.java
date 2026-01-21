package com.example.navdrawer.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navdrawer.R;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String artists[];
    String songs[];
    int imageIds[];
    LayoutInflater inflater;

    public CustomAdapter(Context c, String a[], int imgIds[], String s[]) {
        context = c;
        artists = a;
        songs = s;
        imageIds = imgIds;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return songs.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder {
        ImageView iv;
        TextView tv;
        TextView tv1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row1, parent, false);
        }
        Holder holder = new Holder();
        holder.tv1 = convertView.findViewById(R.id.tvArtist);
        holder.iv = convertView.findViewById(R.id.imgSong);
        holder.tv = convertView.findViewById(R.id.tvSongName);
        holder.tv1.setText(artists[position]);
        holder.iv.setImageResource(imageIds[position]);
        holder.tv.setText(songs[position]);


        // convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle args = new Bundle();
//                args.putString("title", songs[position]);
//                Navigation.findNavController((Activity) context,
//                                R.id.nav_host_fragment_activity_main).
//                        navigate(R.id.action_FirstFragment_to_SecondFragment,
//                                args);
//            }
//        });
        return convertView;
    }
}

