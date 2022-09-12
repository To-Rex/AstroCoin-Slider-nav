package com.sliding.navigator.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sliding.navigator.sample.adapter.ApiCleint;
import com.sliding.navigator.sample.classes.Password;
import com.sliding.navigator.sample.constructor.LoginRequest;
import com.sliding.navigator.sample.constructor.LoginResponse;
import com.sliding.navigator.sample.data.SqlData;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private SqlData sqlData;
    private EditText edilogemail, edilogpassword;
    String id, token, keys, finger, password, usertoken;
    private ProgressBar logprogressBar;
    @SuppressLint({"InflateParams", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_login);

        sqlData = new SqlData(this);
        logprogressBar = findViewById(R.id.logprogressBar);
        edilogemail = findViewById(R.id.edilogemail);
        edilogpassword = findViewById(R.id.edilogpassword);
        Button btnloginsain_in = findViewById(R.id.btnlogsubmit);
        TextView txtmalumot = findViewById(R.id.txtmalumot);
        Button getBtnloginsain_up = findViewById(R.id.getBtnloginsain_up);
        readsql();
        final BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.custombottomsheet);
        txtmalumot.setOnClickListener(v -> {
            v = getLayoutInflater().inflate(R.layout.borromsheetweb, null);
            dialog.setContentView(v);
            //dialog.setCancelable(false);
            View onclossed1 = v.findViewById(R.id.onclossed1);
            View onclossed = v.findViewById(R.id.onclossed);
            onclossed.setOnClickListener(view1 -> dialog.dismiss());
            onclossed1.setOnClickListener(view1 -> dialog.dismiss());
            WebView mWebView = v.findViewById(R.id.webView);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            mWebView.loadUrl("https://astrocoin.uz/recover");
            dialog.show();
        });
        getBtnloginsain_up.setOnClickListener(v -> {
            v = getLayoutInflater().inflate(R.layout.borromsheetweb, null);
            dialog.setContentView(v);
            // dialog.setCancelable(false);
            View onclossed1 = v.findViewById(R.id.onclossed1);
            View onclossed = v.findViewById(R.id.onclossed);
            onclossed.setOnClickListener(view1 -> dialog.dismiss());
            onclossed1.setOnClickListener(view1 -> dialog.dismiss());
            WebView mWebView = v.findViewById(R.id.webView);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            mWebView.loadUrl("https://astrocoin.uz/qwasar-check");
            dialog.show();
        });
        btnloginsain_in.setOnClickListener(view -> {

            logprogressBar.setVisibility(View.VISIBLE);
            String b = edilogemail.getText().toString();
            String a = edilogpassword.getText().toString();

            if (b.isEmpty() && a.isEmpty()) {
                edilogemail.setError("Email is required");
                edilogpassword.setError("Password is required");
                logprogressBar.setVisibility(View.GONE);
            } else {
                login();
            }
        });
    }
    public void login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(edilogemail.getText().toString());
        loginRequest.setPassword(edilogpassword.getText().toString());
        Call<LoginResponse> loginResponseCall = ApiCleint.getUserService().uerLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        usertoken = loginResponse.getStatus();
                        boolean result = sqlData.kiritish(usertoken, "1", "0", "0");
                        if (result) {
                            logprogressBar.setVisibility(View.GONE);
                            startActivity(new Intent(Login.this, Password.class));
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Not Saved", Toast.LENGTH_SHORT).show();
                        }
                        call.cancel();
                        readsql();
                    }
                    if (loginResponse != null) {
                        logprogressBar.setVisibility(View.GONE);
                        call.cancel();
                    }
                } else {
                    edilogemail.setError("Email is not valid");
                    edilogpassword.setError("Password is not valid");
                    Toast.makeText(Login.this, "error", Toast.LENGTH_SHORT).show();
                    logprogressBar.setVisibility(View.GONE);
                    call.cancel();
                }
            }
            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                Toast.makeText(Login.this, "error", Toast.LENGTH_SHORT).show();
                logprogressBar.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }
    private void readsql() {
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
            id = stringBuffer.toString();
            token = stringBuffer1.toString();
            keys = stringBuffer2.toString();
            password = stringBuffer3.toString();
            finger = stringBuffer4.toString();
        }
    }
}