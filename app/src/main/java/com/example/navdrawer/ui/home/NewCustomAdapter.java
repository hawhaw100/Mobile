package com.example.navdrawer.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navdrawer.R;
//
//public class NewCustomAdapter extends RecyclerView.Adapter<NewCustomAdapter.ViewHolder> {
//
//    String names[];
//    int imgIds[];
//    Context context;
//
//    public NewCustomAdapter(Context c, String n[], int ids[]) {
//        names = n;
//        imgIds = ids;
//        context = c;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView iv;
//        public TextView tv;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            iv = itemView.findViewById(R.id.imgArtist);
//            tv = itemView.findViewById(R.id.artistName);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getBindingAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        Bundle args = new Bundle();
//                        args.putString("title", names[position]);
//
//                        Navigation.findNavController((Activity) context,
//                                        R.id.nav_host_fragment_content_main)
//                                .navigate(R.id.action_HomeFragment_to_songFragment, args);
//                    }
//                }
//            });
//        }
//    }
//
//    @NonNull
//    @Override
//    public NewCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(context).inflate(R.layout.row2, parent, false);
//        return new ViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull NewCustomAdapter.ViewHolder holder, int position) {
//        holder.tv.setText(names[position]);
//        holder.iv.setImageResource(imgIds[position]);
//    }
//
//    @Override
//    public int getItemCount() {
//        return names.length;
//    }
//}

public class NewCustomAdapter extends RecyclerView.Adapter<NewCustomAdapter.ViewHolder> {

    String names[];
    int imgIds[];
    Context context;

    public NewCustomAdapter(Context c, String n[], int ids[]) {
        names = n;
        imgIds = ids;
        context = c;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.imgArtist);
            tv = itemView.findViewById(R.id.artistName);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Bundle args = new Bundle();
                    args.putString("title", names[position]);
                    Navigation.findNavController((Activity) context,
                                    R.id.nav_host_fragment_content_main)
                            .navigate(R.id.action_HomeFragment_to_songFragment, args);
                }
            });
        }
    }

    @NonNull
    @Override
    public NewCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.row2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewCustomAdapter.ViewHolder holder, int position) {
        holder.tv.setText(names[position]);
        holder.iv.setImageResource(imgIds[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }
}
