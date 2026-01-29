public class LibraryFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_library, container, false);

        recyclerView = view.findViewById(R.id.libraryRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        List<PlaylistItem> playlists = new ArrayList<>();
        playlists.add(new PlaylistItem(
                "Liked Songs", R.drawable.ic_favorite));

        LibraryAdapter adapter = new LibraryAdapter(
                playlists,
                playlist -> {
                    if (playlist.getTitle().equals("Liked Songs")) {
                        NavHostFragment.findNavController(this)
                                .navigate(
                                        R.id.action_libraryFragment_to_favoritesFragment);
                    }
                });

        recyclerView.setAdapter(adapter);
        return view;
    }
}
