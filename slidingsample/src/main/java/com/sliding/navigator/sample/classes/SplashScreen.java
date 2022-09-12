package com.sliding.navigator.sample.classes;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sliding.navigator.sample.Login;
import com.sliding.navigator.sample.R;
import com.sliding.navigator.sample.data.SqlData;

import java.util.ArrayList;
import java.util.Collections;


@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    SqlData sqlData;
    String id = "", token = "0", keys = "0", password = "0", finger = "0", att = "0", perf = "0", prob = "0", blo = "0", qwasar = "";
    int fin = 0, in = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.spleshscreen);
        sqlData = new SqlData(this);
        readSql();
        isOnline();
        isOnliane();
        SharedPreferences sharedPreferences1 = this.getSharedPreferences("audio.iso", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("aud", "0");
        editor.apply();
    }

    private void readSql() {
        Cursor res = sqlData.oqish();
        StringBuilder stringBuffer = new StringBuilder();
        StringBuilder stringBuffer1 = new StringBuilder();
        StringBuilder stringBuffer2 = new StringBuilder();
        StringBuilder stringBuffer3 = new StringBuilder();
        StringBuilder stringBuffer4 = new StringBuilder();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append(res.getString(0));
                stringBuffer1.append(res.getString(1));
                stringBuffer2.append(res.getString(2));
                stringBuffer3.append(res.getString(3));
                stringBuffer4.append(res.getString(4));
            }
            if (stringBuffer.toString().equals("") || stringBuffer.toString().equals("0")) {
                id = "";
            } else {
                id = stringBuffer.toString();
            }
            if (stringBuffer1.toString().equals("") || stringBuffer.toString().equals("0")) {
                token = "0";
            } else {
                token = stringBuffer1.toString();
            }
            if (stringBuffer2.toString().equals("") || stringBuffer.toString().equals("0")) {
                keys = "0";
            } else {
                keys = stringBuffer2.toString();
            }
            if (stringBuffer3.toString().equals("") || stringBuffer.toString().equals("0")) {
                password = "0";
            } else {
                password = stringBuffer3.toString();
            }
            if (stringBuffer4.toString().equals("") || stringBuffer.toString().equals("0")) {
                finger = "1";
            } else {
                finger = stringBuffer4.toString();
            }
        }
    }

    public void isOnliane() {
        if (isNetworkConnected()){
            SharedPreferences sharedPreferences = this.getSharedPreferences("dycfvsufgjoafienvk.iso",
                    Context.MODE_PRIVATE);
            qwasar = sharedPreferences.getString("qwasar", "");
            ArrayList<String> list = new ArrayList<>();
            new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
                String[] item = blo.split(",");
                Collections.addAll(list, item);
                if (list.contains(qwasar)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
                    builder.setTitle("Error 303");
                    builder.setIcon(R.drawable.ic_felid);
                    builder.setCancelable(false);
                    builder.setMessage("connection not found \ndasturchi bilan bo`laning!");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        Uri uri = Uri.parse("https://t.me/yorvoration_developer");
                        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                        likeIng.setPackage("com.telegram.android");
                        try {
                            startActivity(likeIng);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://t.me/yorvoration_developer")));
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    if (perf.equals("0")) {
                        if (token.equals("0") || keys.equals("0") || token.equals("") || keys.equals("")) {
                            startActivity(new Intent(SplashScreen.this, Login.class));
                        } else {
                            startActivity(new Intent(SplashScreen.this, Password.class));
                        }
                        fin = 1;
                        finish();
                    }
                }
            }, 1500);
        }
    }

    public void isOnline() {
        if (isNetworkConnected()) {
            new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    this::isOnline, 5000);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
            builder.setTitle("No Internet Connection");
            builder.setIcon(R.drawable.ic_felid);
            builder.setCancelable(false);
            builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            });
            builder.setNegativeButton("Retry", (dialog, which) -> {
                isOnline();
                isOnliane();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    @Override
    protected void onStop() {
        in = 1;
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (in == 1) {
            isOnline();
            isOnliane();
        } else {
            in = 0;
        }
        super.onResume();
    }
}
