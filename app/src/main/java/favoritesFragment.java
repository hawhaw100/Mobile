import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navdrawer.R;

public class FavoritesFragment extends Fragment {

    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    public View onCreateView() {
        return onCreateView((LayoutInflater) null, (ViewGroup) null, (Bundle) null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;

        return inflater.inflate(
                R.layout.favorites_fragment, container, false);
    }
    RecyclerView recyclerView;
    SongAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.favorites_fragment, container, false);

        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        adapter = new SongAdapter();
        recyclerView.setAdapter(adapter);

        // Observe liked songs
        MainActivity.db.songDao()
                .getLikedSongs()
                .observe(getViewLifecycleOwner(), songs -> {
                    adapter.submitList(songs);
                });

        return view;
    }

    private Object getViewLifecycleOwner() {
    }
}

