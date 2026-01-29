import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navdrawer.R;

public class LibraryAdapter
        extends RecyclerView.Adapter<LibraryAdapter.PlaylistViewHolder> {

    private List<PlaylistItem> playlists;
    private OnPlaylistClickListener listener;

    public interface OnPlaylistClickListener {
        void onPlaylistClick(PlaylistItem playlist);
    }

    public LibraryAdapter(List<PlaylistItem> playlists,
                          OnPlaylistClickListener listener) {
        this.playlists = playlists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.libraryItems, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PlaylistViewHolder holder, int position) {

        PlaylistItem playlist = playlists.get(position);
        holder.title.setText(playlist.getTitle());
        holder.icon.setImageResource(playlist.getIconResId());

        holder.itemView.setOnClickListener(v ->
                listener.onPlaylistClick(playlist));
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        PlaylistViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.playlistIcon);
            title = itemView.findViewById(R.id.playlistTitle);
        }
    }
}
