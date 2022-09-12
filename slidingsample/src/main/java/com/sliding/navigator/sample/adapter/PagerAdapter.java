package com.sliding.navigator.sample.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sliding.navigator.sample.fragment.TransferFragment;
import com.sliding.navigator.sample.fragment.TransferOrderFragmant;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {
    private static final List<Fragment> fragments = new ArrayList<>(2);
    public PagerAdapter(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TransferFragment();
            case 1:
                return new TransferOrderFragmant();
        }
        return new TransferFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }
}
