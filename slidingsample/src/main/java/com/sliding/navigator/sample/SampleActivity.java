package com.sliding.navigator.sample;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sliding.navigator.SlidingRootNav;
import com.sliding.navigator.SlidingRootNavBuilder;
import com.sliding.navigator.sample.fragment.FragmentWallet;
import com.sliding.navigator.sample.menu.DrawerAdapter;
import com.sliding.navigator.sample.menu.DrawerItem;
import com.sliding.navigator.sample.menu.SimpleItem;
import com.sliding.navigator.sample.menu.SpaceItem;
import com.sliding.navigator.sample.fragment.CenteredTextFragment;

import java.io.File;
import java.util.Arrays;

public class SampleActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_MESSAGES = 2;
    private static final int POS_CART = 3;
    private static final int POS_LOGOUT = 5;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ACCOUNT),
                createItemFor(POS_MESSAGES),
                createItemFor(POS_CART),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            finish();
        }
        slidingRootNav.closeMenu();
        Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
        showFragment(selectedScreen);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("dycfvsufgjoafienvk.iso", Context.MODE_PRIVATE);
        String photo = sharedPreferences.getString("photo", ""),
                name = sharedPreferences.getString("name", ""),
                lastname = sharedPreferences.getString("last_name", ""),
                email = sharedPreferences.getString("email", "");
        TextView navname = findViewById(R.id.navname);
        navname.setText(name+" "+lastname);
        ImageView nawuserimg = findViewById(R.id.nawuserimg);
        if (photo.equals("")) {
            nawuserimg.setImageResource(R.drawable.ic_profile_circle);
        } else {
            Glide.with(this).load("https://api.astrocoin.uz" + photo).into(nawuserimg);
        }
        nawuserimg.setOnLongClickListener(view1 -> {
            downloadImageNew("temp", "https://api.astrocoin.uz" + photo);
            return true;
        });
        if(position == 5){
            return new SimpleItem(screenIcons[position], screenTitles[position])
                    .withIconTint(color(android.R.color.holo_red_dark))
                    .withTextTint(color(android.R.color.holo_red_dark))
                    .withSelectedIconTint(color(android.R.color.holo_purple))
                    .withSelectedTextTint(color(android.R.color.holo_red_dark));
        }else {
            return new SimpleItem(screenIcons[position], screenTitles[position])
                    .withTextTint(color(R.color.textColorPrimary))
                    .withSelectedIconTint(color(android.R.color.holo_purple))
                    .withSelectedTextTint(color(android.R.color.holo_purple));
        }
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    private void downloadImageNew(String filename, String downloadUrlOfImage) {
        try {
            DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(downloadUrlOfImage);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + filename + ".jpg");
            dm.enqueue(request);
            Toast.makeText(this, "Image download started.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Image download failed.", Toast.LENGTH_SHORT).show();
        }
    }
}