package com.example.navdrawer.ui.song;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navdrawer.R;
import com.example.navdrawer.databinding.FragmentSongBinding;

import com.example.navdrawer.ui.database.SongDatabase;
import com.example.navdrawer.ui.database.SongEntity;
import com.example.navdrawer.ui.database.SongDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class songFragment extends Fragment {

    //SongDatabase db;
    SongAdapter adapter;
    ArrayList<Song> songs = new ArrayList<>();

    private String artistName;
    private FragmentSongBinding binding;

    private boolean isFollowing = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("");

        Bundle args = getArguments();
        if (args != null) {
            artistName = args.getString("title");
        }

        ImageView ivArtist = view.findViewById(R.id.ivArtistPic);
        TextView tvArtistName = view.findViewById(R.id.tvArtistName);
        Button btnFollow = view.findViewById(R.id.followbtn);
        RecyclerView recyclerSongs = view.findViewById(R.id.rvSong);

        tvArtistName.setText(artistName);

        if (isFollowing) {
            btnFollow.setText("Following");
        } else {
            btnFollow.setText("Follow");
        }
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollowing) {
                    isFollowing = false;
                    btnFollow.setText("Follow");
                    Toast.makeText(getActivity(), "Removed from Your Library", Toast.LENGTH_SHORT).show();
                } else {
                    isFollowing = true;
                    btnFollow.setText("Following");
                    Toast.makeText(getActivity(), "Added to Your Library", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerSongs.setLayoutManager(new LinearLayoutManager(getContext()));

        SongDatabase db = SongDatabase.getDatabase(getContext());
        insertSongsIfEmpty(db, artistName, ivArtist); // optional, inserts songs if DB empty

        // 3. Get songs for this artist from DB (background thread)
        Executors.newSingleThreadExecutor().execute(() -> {
            List<SongEntity> songEntities = db.songDao().getSongsByArtist(artistName);

            ArrayList<Song> songs = new ArrayList<>();
            for (SongEntity e : songEntities) {
                songs.add(new Song(
                        e.title,
                        e.imgResId,
                        e.audioResId,
                        e.views,
                        e.artist,
                        e.duration,
                        e.releaseYear,
                        e.genre
                ));
            }

            // Update RecyclerView on UI thread
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SongAdapter adapter = new SongAdapter(getContext(), songs);
                    recyclerSongs.setAdapter(adapter);
                }
            });
        });
        if ("Adam".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.adam);
        } else if ("Wael Jassar".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.jassar);
        } else if ("Taylor Swift".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.taylor);
        } else if ("Tamer Ashour".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.tamer);
        } else if ("Marwan Khoery".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.marwan);
        } else if ("Elissa".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.elissa);
        } else if ("Sherine".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.sherine);
        } else if ("Ariana Grande".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.ariana);
        } else if ("Ziad Burji".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.ziad);
        } else if ("Fairuz".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.fairuz);
        } else if ("Joseph Attieh".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.joseph);
        } else if ("Pitbull".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.pitbull);
        } else if ("Amr Diab".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.amr);
        } else if ("Lloyd".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.lloyd);
        } else if ("Wael Kfoury".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.wael);
        } else if ("Wegz".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.wegz);
        } else if ("Lana Del Ray".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.lana);
        } else if ("Chase Atlantic".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.chase);
        } else if ("Beach Weather".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.beach);
        } else if ("Bruno Mars".equals(artistName)) {
            ivArtist.setImageResource(R.drawable.bruno);
        }
    }

    void insertSongsIfEmpty(SongDatabase db, String artistName, ImageView ivArtist) {
        Executors.newSingleThreadExecutor().execute((new Runnable() {
            @Override
            public void run() {
                if (db.songDao().getSongsByArtist(artistName).isEmpty()) {

                    if ("Adam".equals(artistName)) {
                        db.songDao().insert(new SongEntity("على بالي", R.drawable.ala_bali, R.raw.alabali, "200",
                                "Adam", "3:00", "2021", "Lebanese Pop"));
                        db.songDao().insert(new SongEntity("يا حلو", R.drawable.ya_helou, R.raw.yahelo, "200",
                                "Adam", "3:20", "2020", "Lebanese Pop"));
                        db.songDao().insert(new SongEntity("كذا شخصية", R.drawable.ya_helou, R.raw.yahelo, "200",
                                                             "Adam", "3:15", "2019", "Lebanese Pop"));
                        db.songDao().insert(new SongEntity("في حدا", R.drawable.ala_bali, R.raw.alabali, "200",
                                "Adam", "3:10", "2018", "Lebanese Pop"));
                    }
                    else if ("Wael Jassar".equals(artistName)) {
                        // Wael Jassar songs
                        db.songDao().insert(new SongEntity("كل وعد", R.drawable.kol_waad, R.raw.klwead, "200",
                                "Wael Jassar", "4:00", "2019", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("موجوع", R.drawable.mawjou3, R.raw.mawjoua, "200",
                                "Wael Jassar", "3:50", "2020", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("نخبي ليه", R.drawable.mawjou3, R.raw.mawjoua, "200",
                                "Wael Jassar", "3:40", "2021", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("بتوحشيني", R.drawable.mawjou3, R.raw.mawjoua, "111",
                                "Wael Jassar", "3:45", "2020", "Arabic Pop"));
                    }
                    else if ("Fairuz".equals(artistName)) {
                        // Fairuz songs

                        db.songDao().insert(new SongEntity("كيفك إنت", R.drawable.kifak, R.raw.kifakinta, "123",
                                "Fairuz", "3:25", "1960", "Lebanese Classic"));
                        db.songDao().insert(new SongEntity("بعدك على بالي", R.drawable.baadak_ala_bali, R.raw.baadakalabaly, "1323",
                                "Fairuz", "3:30", "1961", "Lebanese Classic"));
                        db.songDao().insert(new SongEntity("كنا نتلاقى", R.drawable.baadak_ala_bali, R.raw.baadakalabaly, "1322",
                                "Fairuz", "3:40", "1962", "Lebanese Classic"));
                        db.songDao().insert(new SongEntity("وحدن", R.drawable.baadak_ala_bali, R.raw.baadakalabaly, "434",
                                "Fairuz", "3:50", "1963", "Lebanese Classic"));
                    }
                    else if ("Taylor Swift".equals(artistName)) {

                        //Taylor Swift songs
                        db.songDao().insert(new SongEntity("right where you left me", R.drawable.leftme, R.raw.rightwhereyouleftme, "7674",
                                "Taylor Swift", "4:05", "2020", "Pop"));
                        db.songDao().insert(new SongEntity("The Archer", R.drawable.the_archer, R.raw.thearcher, "243",
                                "Taylor Swift", "3:30", "2019", "Pop"));
                        db.songDao().insert(new SongEntity("Blank Space", R.drawable.kifak, R.raw.kifakinta, "4243",
                                "Taylor Swift", "3:25", "2014", "Pop"));
                        db.songDao().insert(new SongEntity("Cruel Summer", R.drawable.kifak, R.raw.kifakinta, "2423",
                                "Taylor Swift", "3:25", "2019", "Pop"));
                        db.songDao().insert(new SongEntity("Style", R.drawable.kifak, R.raw.kifakinta, "243",
                                "Taylor Swift", "3:25", "2015", "Pop"));
                    }
                    if ("Marwan Khoery".equals(artistName)) {
                        //Marwan Khoery songs
                        db.songDao().insert(new SongEntity("مش عم بتروحي من بالي", R.drawable.mesh_am_btruhi, R.raw.michaambitrouhiminbali,"4355",
                                "Marwan Khoery", "3:50", "2021", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("ليل مبارح", R.drawable.leil_mbereh, R.raw.leilmbrarih,"234",
                                "Marwan Khoery", "3:20", "2020", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("أنا بصراحة", R.drawable.kifak, R.raw.kifakinta,"24232",
                                "Marwan Khoery", "3:45", "2019", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("هذا أعرف", R.drawable.kifak, R.raw.kifakinta,"653",
                                "Marwan Khoery", "3:40", "2018", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("أنت ومعي", R.drawable.kifak, R.raw.kifakinta,"676",
                                "Marwan Khoery", "3:25", "2020", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("قلبك على قلبي", R.drawable.kifak, R.raw.kifakinta,"675",
                                "Marwan Khoery", "3:25", "2021", "Arabic Pop"));
                    }
                    if("Zaid Burji".equals(artistName)) {

                        //Ziad Burji songs
                        db.songDao().insert(new SongEntity("وبغار", R.drawable.wbghar, R.raw.wbghar,"7652",
                                "Ziad Burji", "3:50", "2005", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("شو حلو", R.drawable.shu_helo, R.raw.shouhelou,"9765",
                                "Ziad Burji", "3:45", "2006", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("وبتير", R.drawable.kifak, R.raw.kifakinta,"12037",
                                "Ziad Burji", "3:50", "2019", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("حيل ياني", R.drawable.kifak, R.raw.kifakinta,"7654",
                                "Ziad Burji", "3:40", "2018", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("أنا وياك", R.drawable.kifak, R.raw.kifakinta,"863",
                                "Ziad Burji", "3:50", "2020", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("هاي دي حبيبت قلبي", R.drawable.kifak, R.raw.kifakinta,"67",
                                "Ziad Burji", "3:45", "2021", "Arabic Pop"));

                    }
                    if("Tamer Ashour".equals(artistName)) {

                        //Tamer Ashour songs
                        db.songDao().insert(new SongEntity("ما كرهتش", R.drawable.makrehtush, R.raw.anamakrehtosh, "1423",
                                "Tamer Ashour", "3:50", "2017", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("هيجلة موجوع", R.drawable.hayjele_mawjou3, R.raw.haygelymawgowa, "1234",
                                "Tamer Ashour", "4:00", "2018", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("كان موضوع", R.drawable.kifak, R.raw.kifakinta, "77453",
                                "Tamer Ashour", "3:45", "2019", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("تجي نتراهن", R.drawable.kifak, R.raw.kifakinta, "7325",
                                "Tamer Ashour", "3:50", "2020", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("بقول عادي", R.drawable.kifak, R.raw.kifakinta, "48273",
                                "Tamer Ashour", "3:45", "2021", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("يوم ما تنسى", R.drawable.kifak, R.raw.kifakinta, "7436",
                                "Tamer Ashour", "3:55", "2020", "Arabic Pop"));
                    }
                    if("Elissa".equals(artistName)) {
                        //Elissa songs
                        db.songDao().insert(new SongEntity("مكتوبة ليك", R.drawable.maktuba_lek, R.raw.maktoobaleek, "76325",
                                "Elissa", "3:50", "2006", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("أجمل إحساس", R.drawable.ajmal_ehsas, R.raw.agmalihsas, "3762",
                                "Elissa", "3:55", "2008", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("من أول دقيقة", R.drawable.kifak, R.raw.kifakinta, "83621",
                                "Elissa", "4:00", "2007", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("لو", R.drawable.kifak, R.raw.kifakinta, "632",
                                "Elissa", "3:50", "2009", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("بتغيب بتروح", R.drawable.kifak, R.raw.kifakinta, "7362",
                                "Elissa", "3:55", "2010", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("متخذلنيش", R.drawable.kifak, R.raw.kifakinta, "83621",
                                "Elissa", "4:00", "2011", "Arabic Pop"));
                    }
                    if("Sherine".equals(artistName)) {
                        //Sherine songs
                        db.songDao().insert(new SongEntity("الوتر الحساس", R.drawable.watar_lhasas, R.raw.elwatarelhassas,"73512",
                                "Sherine", "4:10", "2015", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("على بالي", R.drawable.ala_bali_sheine, R.raw.alabalish,"3716",
                                "Sherine", "3:50", "2016", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("بتمنى أنساك", R.drawable.kifak, R.raw.kifakinta,"8326",
                                "Sherine", "3:55", "2017", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("كلام عينيه", R.drawable.kifak, R.raw.kifakinta,"7153",
                                "Sherine", "4:00", "2018", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("الكدابين", R.drawable.kifak, R.raw.kifakinta,"376",
                                "Sherine", "3:50", "2019", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("حبه جنة", R.drawable.kifak, R.raw.kifakinta,"3162",
                                "Sherine", "3:55", "2020", "Arabic Pop"));
                    }
                    if("Ariana Grande".equals(artistName)) {
                        //Ariana Grande songs
                        db.songDao().insert(new SongEntity("twilight zone", R.drawable.twilight_zone, R.raw.twilightzone,"82173",
                                "Ariana Grande", "4:10", "2014", "Pop"));
                        db.songDao().insert(new SongEntity("we can't be friends", R.drawable.we_cant_be_friends, R.raw.wecantbefriends,"83163",
                                "Ariana Grande", "3:50", "2014", "Pop"));
                        db.songDao().insert(new SongEntity("Santa Tell Me", R.drawable.kifak, R.raw.kifakinta,"73612",
                                "Ariana Grande", "3:55", "2014", "Pop"));
                        db.songDao().insert(new SongEntity("Save Your Tears", R.drawable.kifak, R.raw.kifakinta,"2163",
                                "Ariana Grande", "4:00", "2020", "Pop"));
                        db.songDao().insert(new SongEntity("One Last Time", R.drawable.kifak, R.raw.kifakinta,"2375",
                                "Ariana Grande", "3:50", "2015", "Pop"));
                        db.songDao().insert(new SongEntity("7 rings", R.drawable.kifak, R.raw.kifakinta,"126372", "Ariana Grande", "3:55", "2019", "Pop"));

                    }
                    if("Wael Kfoury".equals(artistName)) {
                        //Wael Kfoury songs
                        db.songDao().insert(new SongEntity("حكم القلب", R.drawable.hekem_alb, R.raw.twilightzone,"7362",
                                "Wael Kfoury", "4:00", "2006", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("كلنا مننجار", R.drawable.kelna_mnnjar, R.raw.wecantbefriends,"3321",
                                "Wael Kfoury", "3:50", "2007", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("البنت القوية", R.drawable.kifak, R.raw.kifakinta,"3286",
                                "Wael Kfoury", "3:55", "2008", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("لو حبنا غلطة", R.drawable.kifak, R.raw.kifakinta,"12376",
                                "Wael Kfoury", "3:45", "2009", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("تك تك قلبي", R.drawable.kifak, R.raw.kifakinta,"382",
                                "Wael Kfoury", "3:50", "2010", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("ست الكل", R.drawable.kifak, R.raw.kifakinta,"8263",
                                "Wael Kfoury", "4:00", "2011", "Arabic Pop"));
                    }
                    if("Lloyd".equals(artistName)) {

                        //Lloyd songs
                        db.songDao().insert(new SongEntity("You", R.drawable.you, R.raw.twilightzone,"7153",
                                "Lloyd", "4:12", "2006", "R&B / Soul"));
                        db.songDao().insert(new SongEntity("Lay It Down", R.drawable.lay_it_down, R.raw.wecantbefriends,"2736",
                                "Lloyd", "4:05", "2010", "R&B / Soul"));
                        db.songDao().insert(new SongEntity("Tru", R.drawable.kifak, R.raw.kifakinta,"326",
                                "Lloyd", "3:50", "2011", "R&B / Soul"));
                        db.songDao().insert(new SongEntity("Southside", R.drawable.kifak, R.raw.kifakinta,"3726",
                                "Lloyd", "3:55", "2004", "R&B / Soul"));
                        db.songDao().insert(new SongEntity("Player's Prayer", R.drawable.kifak, R.raw.kifakinta,"376",
                                "Lloyd", "4:00", "2010", "R&B / Soul"));
                        db.songDao().insert(new SongEntity("Caramel", R.drawable.kifak, R.raw.kifakinta,"1231",
                                "Lloyd", "3:58", "2011", "R&B / Soul"));

                    }
                    if("Pitbull".equals(artistName)) {

                        //Pitbull songs
                        db.songDao().insert(new SongEntity("Give Me Everything", R.drawable.give_me_everything, R.raw.twilightzone,"65213",
                                "Pitbull", "4:12", "2006", "Pop"));
                        db.songDao().insert(new SongEntity("Timber", R.drawable.timeber, R.raw.wecantbefriends,"8316",
                                "Pitbull", "4:05", "2010", "Pop"));
                        db.songDao().insert(new SongEntity("International Love", R.drawable.kifak, R.raw.kifakinta,"3216",
                                "Pitbull", "3:50", "2011", "Pop"));
                        db.songDao().insert(new SongEntity("International Love", R.drawable.kifak, R.raw.kifakinta,"3621",
                                "Pitbull", "3:55", "2004", "Pop"));
                        db.songDao().insert(new SongEntity("Player's International Love", R.drawable.kifak, R.raw.kifakinta,"2373",
                                "Pitbull", "4:00", "2010", "Pop"));
                        db.songDao().insert(new SongEntity("International Love", R.drawable.kifak, R.raw.kifakinta,"1732",
                                "Pitbull", "3:58", "2011", "Pop"));

                    }
                    if("Amr Diab".equals(artistName)) {
                        //Amr Diab songs
                        db.songDao().insert(new SongEntity("تملي معاك", R.drawable.tamally_maak, R.raw.twilightzone,"7352",
                                "Amr Diab", "4:10", "2000", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("خطفوني", R.drawable.khatafony, R.raw.wecantbefriends,"3271",
                                "Amr Diab", "4:05", "2000", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("مكانك في قلبي", R.drawable.kifak, R.raw.kifakinta,"7362",
                                "Amr Diab", "4:00", "2001", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("وغلاوتك", R.drawable.kifak, R.raw.kifakinta,"3761",
                                "Amr Diab", "3:55", "2001", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("خليك فاكرني", R.drawable.kifak, R.raw.kifakinta,"3183",
                                "Amr Diab", "4:15", "2002", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("أول كل حاجة", R.drawable.kifak, R.raw.kifakinta,"2837",
                                "Amr Diab", "4:20", "2002", "Arabic Pop"));
                    }
                    if("Joseph Attieh".equals(artistName)) {
                        //Joseph Attieh songs
                        db.songDao().insert(new SongEntity("تعاب الشوق", R.drawable.taeb_el_chawk, R.raw.twilightzone,"73462",
                                "Joseph Attieh", "3:50", "2015", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("ويلاك", R.drawable.weilak, R.raw.wecantbefriends,"87632",
                                "Joseph Attieh", "4:00", "2016", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("ولا غلطة", R.drawable.kifak, R.raw.kifakinta,"7236",
                                "Joseph Attieh", "3:45", "2016", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("ما بحب", R.drawable.kifak, R.raw.kifakinta,"3862",
                                "Joseph Attieh", "4:10", "2017", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("مهووم", R.drawable.kifak, R.raw.kifakinta,"8463",
                                "Joseph Attieh", "3:55", "2017", "Arabic Pop"));
                        db.songDao().insert(new SongEntity("الحق ما بموت", R.drawable.kifak, R.raw.kifakinta,"27644",
                                "Joseph Attieh", "4:05", "2018", "Arabic Pop"));
                    }
                    // Repeat for other artists...
                }
            }
        }
        ));
    }
}//
//            //Lana Del Ray songs
//        db.songDao().insert(new SongEntity("Young And Beautiful", R.drawable.young_beautiful, R.raw.twilightzone,
//                    "Lana Del Ray", "4:20", "2013", "Alternative / Pop"));
//        db.songDao().insert(new SongEntity("Summertime Sadness", R.drawable.summertime_sadness, R.raw.wecantbefriends,
//                    "Lana Del Ray", "4:25", "2012", "Alternative / Pop"));
//        db.songDao().insert(new SongEntity("Say Yes To Heaven", R.drawable.kifak, R.raw.kifakinta,
//                    "Lana Del Ray", "3:55", "2014", "Alternative / Pop"));
//        db.songDao().insert(new SongEntity("Born To Die", R.drawable.kifak, R.raw.kifakinta,
//                    "Lana Del Ray", "4:50", "2012", "Alternative / Pop"));
//        db.songDao().insert(new SongEntity("Video Games", R.drawable.kifak, R.raw.kifakinta,
//                    "Lana Del Ray", "5:00", "2011", "Alternative / Pop"));
//        db.songDao().insert(new SongEntity("Diet Mountain Dew", R.drawable.kifak, R.raw.kifakinta,
//                    "Lana Del Ray", "3:40", "2011", "Alternative / Pop"));
//
//            //Chase Atlantic songs
//        db.songDao().insert(new SongEntity("Friends", R.drawable.friends, R.raw.twilightzone,
//                    "Chase Atlantic", "3:45", "2019", "Alternative / R&B"));
//        db.songDao().insert(new SongEntity("Slow Down", R.drawable.slow_down, R.raw.wecantbefriends,
//                    "Chase Atlantic", "3:55", "2018", "Alternative / R&B"));
//        db.songDao().insert(new SongEntity("The Walls", R.drawable.kifak, R.raw.kifakinta,
//                    "Chase Atlantic", "4:05", "2017", "Alternative / R&B"));
//        db.songDao().insert(new SongEntity("Right Here", R.drawable.kifak, R.raw.kifakinta,
//                    "Chase Atlantic", "3:50", "2017", "Alternative / R&B"));
//        db.songDao().insert(new SongEntity("Church", R.drawable.kifak, R.raw.kifakinta,
//                    "Chase Atlantic", "4:10", "2017", "Alternative / R&B"));
//        db.songDao().insert(new SongEntity("Consume", R.drawable.kifak, R.raw.kifakinta,
//                    "Chase Atlantic", "3:40", "2018", "Alternative / R&B"));
//
//            //Beach Weather songs
//        db.songDao().insert(new SongEntity("High In Low Places", R.drawable.high_in_low_places, R.raw.twilightzone,
//                    "Beach Weather", "3:45", "2019", "Alternative / R&B"));
//        db.songDao().insert(new SongEntity("Fake Nice", R.drawable.fake_nice, R.raw.wecantbefriends,
//                    "Beach Weather", "3:55", "2018", "Alternative / R&B"));
//        db.songDao().insert(new SongEntity("Chit Chat", R.drawable.kifak, R.raw.kifakinta,
//                    "Beach Weather", "4:05", "2017", "Alternative / R&B"));
//        db.songDao().insert(new SongEntity("Unlovable", R.drawable.kifak, R.raw.kifakinta,
//                    "Beach Weather", "3:50", "2017", "Alternative / R&B"));
//        db.songDao().insert(new SongEntity("Church", R.drawable.kifak, R.raw.kifakinta,
//                    "Beach Weather", "4:10", "2017", "Alternative / R&B"));
//        db.songDao().insert(new SongEntity("Consume", R.drawable.kifak, R.raw.kifakinta,
//                    "Beach Weather", "3:40", "2018", "Alternative / R&B"));
//
//            //Bruno Mars songs
//        db.songDao().insert(new SongEntity("Die With A Smile", R.drawable.die_with_a_smile, R.raw.twilightzone,
//                    "Bruno Mars", "3:30", "2012", "Pop / R&B"));
//        db.songDao().insert(new SongEntity("I Just Might", R.drawable.i_just_might, R.raw.wecantbefriends,
//                    "Bruno Mars", "3:45", "2010", "Pop / R&B"));
//        db.songDao().insert(new SongEntity("It Will Rain", R.drawable.kifak, R.raw.kifakinta,
//                    "Bruno Mars", "4:05", "2011", "Pop / R&B"));
//        db.songDao().insert(new SongEntity("Talking to the Moon", R.drawable.kifak, R.raw.kifakinta,
//                    "Bruno Mars", "3:50", "2010", "Pop / R&B"));
//        db.songDao().insert(new SongEntity("Just the Way You Are", R.drawable.kifak, R.raw.kifakinta,
//                    "Bruno Mars", "3:40", "2010", "Pop / R&B"));
//        db.songDao().insert(new SongEntity("Locked out of Heaven", R.drawable.kifak, R.raw.kifakinta,
//                    "Bruno Mars", "3:53", "2012", "Pop / R&B"));
//
//            //Wegz songs
//        db.songDao().insert(new SongEntity("البخت", R.drawable.bakhet, R.raw.twilightzone,
//                    "Wegz", "2:58", "2022", "Egyptian Trap / Rap"));
//        db.songDao().insert(new SongEntity("الأيام", R.drawable.ayam, R.raw.wecantbefriends,
//                    "Wegz", "3:10", "2021", "Egyptian Trap / Rap"));
//        db.songDao().insert(new SongEntity("كلام فراس", R.drawable.kifak, R.raw.kifakinta,
//                    "Wegz", "2:45", "2021", "Egyptian Trap / Rap"));
//        db.songDao().insert(new SongEntity("Bad Days", R.drawable.kifak, R.raw.kifakinta,
//                    "Wegz", "2:50", "2020", "Egyptian Trap / Rap"));
//        db.songDao().insert(new SongEntity("واحد وعشرين", R.drawable.kifak, R.raw.kifakinta,
//                    "Wegz", "3:05", "2021", "Egyptian Trap / Rap"));
//        db.songDao().insert(new SongEntity("الوعد", R.drawable.kifak, R.raw.kifakinta,
//                    "Wegz", "3:00", "2021", "Egyptian Trap / Rap"));