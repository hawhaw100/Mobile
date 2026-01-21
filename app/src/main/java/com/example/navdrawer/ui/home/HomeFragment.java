package com.example.navdrawer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.navdrawer.R;
import com.example.navdrawer.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String songs[] = {"El Hob Gany", "BIRDS OF A FEATHER", "Sweater Weather", "COME N GO", "W Btir"};
        String artists[] = {"TUL8TE", "Billie Eilish", "The Neighbourhood", "Yeat", "Ziad Bourji"};
        int imgIds[] = {R.drawable.hob, R.drawable.birds,
                R.drawable.weather, R.drawable.come, R.drawable.wbtir};

        CustomAdapter adapter = new CustomAdapter(getContext(),
                artists, imgIds, songs);
        binding.lv.setAdapter(adapter);

        int imgs[] = {R.drawable.wael, R.drawable.lloyd, R.drawable.pitbull,
                R.drawable.amr, R.drawable.joseph, R.drawable.lana, R.drawable.chase,
                R.drawable.beach, R.drawable.bruno, R.drawable.wegz};
        String titles[] = {"Wael Kfoury", "Lloyd", "Pitbull", "Amr Diab", "Joseph Attieh",
                "Lana Del Ray", "Chase Atlantic", "Beach Weather", "Bruno Mars", "Wegz"};

        NewCustomAdapter adapter2 = new NewCustomAdapter(getContext(),
                titles, imgs);
        binding.rv.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.rv.setAdapter(adapter2);

        int imgs2[] = {R.drawable.adam, R.drawable.jassar, R.drawable.fairuz,
                R.drawable.marwan, R.drawable.taylor, R.drawable.tamer, R.drawable.elissa,
                R.drawable.sherine, R.drawable.ariana, R.drawable.ziad};
        String names[] = {"Adam", "Wael Jassar", "Fairuz", "Marwan Khoery", "Taylor Swift",
                "Tamer Ashour", "Elissa", "Sherine", "Ariana Grande", "Ziad Burji"};


        NewCustomAdapter2 adapter3 = new NewCustomAdapter2(getContext(),
                names, imgs2);
        binding.rv2.setLayoutManager(
                new LinearLayoutManager(getContext()));
        binding.rv2.setAdapter(adapter3);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}