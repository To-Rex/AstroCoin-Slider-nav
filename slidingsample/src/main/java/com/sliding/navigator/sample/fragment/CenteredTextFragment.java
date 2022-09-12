package com.sliding.navigator.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sliding.navigator.sample.R;

public class CenteredTextFragment extends Fragment {

    private static final String EXTRA_TEXT = "text";

    public static CenteredTextFragment createFor(String text) {
        CenteredTextFragment fragment = new CenteredTextFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        assert args != null;
        String text = args.getString(EXTRA_TEXT);
        Toast.makeText(getActivity(), args.getString("text"), Toast.LENGTH_SHORT).show();
        if (text.equals(getString(R.string.one))) {
            replaceFragment(new FragmentWallet());
            return inflater.inflate(R.layout.dashbord, container, false);
        }
        if (text.equals(getString(R.string.two))) {
            replaceFragment(new FragmentSearchUser());
            return inflater.inflate(R.layout.myaccaunt, container, false);
        }
        if (text.equals(getString(R.string.tree))) {
            replaceFragment(new FragmentSettings());
            return inflater.inflate(R.layout.messages, container, false);
        }
        if (text.equals(getString(R.string.four))) {
            replaceFragment(new FragmentWallet());
            return inflater.inflate(R.layout.chart, container, false);
        } else {
            replaceFragment(new FragmentWallet());
            return inflater.inflate(R.layout.dashbord, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        final String text = args != null ? args.getString(EXTRA_TEXT) : "";
    }
    private void replaceFragment(FragmentWallet fragmentWallet) {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragmentWallet);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void replaceFragment(FragmentSettings fragmentSettings) {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragmentSettings);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void replaceFragment(FragmentSearchUser fragmentSearchUser) {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragmentSearchUser);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
