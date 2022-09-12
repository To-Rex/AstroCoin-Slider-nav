package com.sliding.navigator.sample.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.sliding.navigator.sample.R;
import com.sliding.navigator.sample.SampleActivity;
import com.sliding.navigator.sample.adapter.ApiCleint;
import com.sliding.navigator.sample.constructor.TokenRequest;
import com.sliding.navigator.sample.data.SqlData;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.Executor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Password extends AppCompatActivity {
    SqlData sqlData;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private TextView txthello;
    private EditText edipasswordall;
    private View pas1, pas2, pas3, pas4;
    ImageView imgpasback, imgfingerptint;
    String text,id,token,keys,password,finger;
    Handler handler;
    int i = 0,check = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.password);

        sqlData = new SqlData(this);
        Executor executor = ContextCompat.getMainExecutor(this);

        edipasswordall = findViewById(R.id.edipasswordall);

        imgfingerptint = findViewById(R.id.imgfingerptint);
        imgpasback = findViewById(R.id.imgpasback);

        pas1 = findViewById(R.id.pas1);
        pas2 = findViewById(R.id.pas2);
        pas3 = findViewById(R.id.pas3);
        pas4 = findViewById(R.id.pas4);

        txthello = findViewById(R.id.txthello);
        TextView txtpassforgat = findViewById(R.id.txtpassforgat);
        TextView textView = findViewById(R.id.textView);
        TextView txt0 = findViewById(R.id.txt0);
        TextView txt1 = findViewById(R.id.txt1);
        TextView txt2 = findViewById(R.id.txt2);
        TextView txt3 = findViewById(R.id.txt3);
        TextView txt4 = findViewById(R.id.txt4);
        TextView txt5 = findViewById(R.id.txt5);
        TextView txt6 = findViewById(R.id.txt6);
        TextView txt7 = findViewById(R.id.txt7);
        TextView txt8 = findViewById(R.id.txt8);
        TextView txt9 = findViewById(R.id.txt9);

        readSql();

        txthello.setText("...");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        int nol = (int) textView.getY();
        int noll = (int) txthello.getY();
        int one = (int) txt1.getY();
        int two = (int) txt4.getY();
        int three = (int) txt7.getY();
        int four = (int) txt0.getY();
        int five = (int) txtpassforgat.getY();
        if (height < 1300) {
            pas1.setY(one - 40);
            pas2.setY(two - 40);
            pas3.setY(three - 40);
            pas4.setY(four - 40);
            textView.setY(nol-35);
            txthello.setY(noll-35);
            txt1.setY(one-120);
            txt2.setY(one-120);
            txt3.setY(one-120);
            txt4.setY(two-120);
            txt5.setY(two-120);
            txt6.setY(two-120);
            txt7.setY(three-120);
            txt8.setY(three-120);
            txt9.setY(three-120);
            txt0.setY(four-120);
            imgfingerptint.setY(four-120);
            imgpasback.setY(four-120);
            txtpassforgat.setY(five-155);

        }
        txtpassforgat.setOnClickListener(v -> {
            boolean s = sqlData.ozgartir(id, "0", "0", "0", "1");
            if (s) {
                Toast.makeText(this, "re-enter the login password", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Password.this, SampleActivity.class));
                finish();
            }
        });

        imgfingerptint.setOnClickListener(view -> {
            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setSubtitle("Login using fingerprint authentication")
                    .setNegativeButtonText("User App Password")
                    .build();

            handler = new Handler();
            handler.postDelayed(() -> biometricPrompt.authenticate(promptInfo), 700);
            imgfingerptint.setOnClickListener(v -> biometricPrompt.authenticate(promptInfo));
        });
        biometricPrompt = new BiometricPrompt(Password.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                pas1.setBackgroundResource(R.drawable.shadov_soucsess);
                pas2.setBackgroundResource(R.drawable.shadov_soucsess);
                pas3.setBackgroundResource(R.drawable.shadov_soucsess);
                pas4.setBackgroundResource(R.drawable.shadov_soucsess);
                handler = new Handler();
                handler.postDelayed(() -> {
                    Intent intent = new Intent(Password.this, SampleActivity.class);
                    startActivity(intent);
                    finish();
                },1000);
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                pas1.setBackgroundResource(R.drawable.shadovfaild);
                pas2.setBackgroundResource(R.drawable.shadovfaild);
                pas3.setBackgroundResource(R.drawable.shadovfaild);
                pas4.setBackgroundResource(R.drawable.shadovfaild);
                Toast.makeText(Password.this, "Authentication failed...!", Toast.LENGTH_SHORT).show();
            }
        });
        if (Objects.equals(finger, "1")) {
            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setSubtitle("Login using fingerprint authentication")
                    .setNegativeButtonText("User App Password")
                    .build();

            new Handler(Looper.getMainLooper()).postDelayed(
                    () -> biometricPrompt.authenticate(promptInfo), 500);
            imgfingerptint.setOnClickListener(v -> biometricPrompt.authenticate(promptInfo));
        } else {
            imgfingerptint.setVisibility(View.INVISIBLE);
        }
        if ("0".equals(password)) {
            check = 1;
            txthello.setText("creat password");
            txtpassforgat.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        else {
            txthello.setText("...");
            txtpassforgat.setVisibility(View.VISIBLE);
            check = 0;
            textView.setVisibility(View.VISIBLE);
            readData();
        }

        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (!Character.toString(source.charAt(i)).matches("[a-zA-Z\\d-_.]+")) {
                    return "";
                }
            }
            return null;
        };
        edipasswordall.setFilters(new InputFilter[]{filter});

        edipasswordall.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                text = edipasswordall.getText().toString();
                if (i == 1) {
                    pas1.setBackgroundResource(R.drawable.sahdov1);
                    imgpasback.setVisibility(View.VISIBLE);
                }
                if (i == 2) {
                    pas2.setBackgroundResource(R.drawable.sahdov1);
                    imgpasback.setVisibility(View.VISIBLE);
                }
                if (i == 3) {
                    pas3.setBackgroundResource(R.drawable.sahdov1);
                    imgpasback.setVisibility(View.VISIBLE);
                }
                if (i == 4) {
                    pas4.setBackgroundResource(R.drawable.sahdov1);
                    imgpasback.setVisibility(View.INVISIBLE);
                }
                if (i > 4) {
                    i = 4;
                    edipasswordall.setText(edipasswordall.getText().toString().substring(0, 4));
                }
                if (i == 4) {
                    if (check == 0) {
                        new Handler(Looper.getMainLooper()).postDelayed(
                                () -> {
                                    if (Objects.equals(password, text)) {
                                        soucsess();
                                    }else {
                                        errorpss();
                                        edipasswordall.setText(null);
                                        pas4.setBackgroundResource(R.drawable.shadovfaild);
                                    }
                                },
                                300);
                    } else {
                        pas1.setBackgroundResource(R.drawable.shadov_soucsess);
                        pas2.setBackgroundResource(R.drawable.shadov_soucsess);
                        pas3.setBackgroundResource(R.drawable.shadov_soucsess);
                        pas4.setBackgroundResource(R.drawable.shadov_soucsess);
                        readdata();

                    }
                }
            }
        });


        txt0.setOnClickListener(v -> {
            if (i <= 3) {
                i++;
                text = edipasswordall.getText().toString().trim();
                edipasswordall.setText(text + getString(R.string._0));
            }

        });
        txt1.setOnClickListener(v -> {
            if (i <= 3) {
                i++;
                text = edipasswordall.getText().toString().trim();
                edipasswordall.setText(text + getString(R.string._1));
            }


        });
        txt2.setOnClickListener(v -> {
            if (i <= 3) {
                i++;
                text = edipasswordall.getText().toString().trim();
                edipasswordall.setText(text + getString(R.string._2));
            }


        });
        txt3.setOnClickListener(v -> {
            if (i <= 3) {
                i++;
                text = edipasswordall.getText().toString().trim();
                edipasswordall.setText(text + getString(R.string._3));
            }


        });
        txt4.setOnClickListener(v -> {
            if (i <= 3) {
                i++;
                text = edipasswordall.getText().toString().trim();
                edipasswordall.setText(text + getString(R.string._4));
            }

        });
        txt5.setOnClickListener(v -> {
            if (i <= 3) {
                i++;
                text = edipasswordall.getText().toString().trim();
                edipasswordall.setText(text + getString(R.string._5));
            }

        });
        txt6.setOnClickListener(v -> {
            if (i <= 3) {
                i++;
                text = edipasswordall.getText().toString().trim();
                edipasswordall.setText(text + getString(R.string._6));
            }

        });
        txt7.setOnClickListener(v -> {
            if (i <= 3) {
                i++;
                text = edipasswordall.getText().toString().trim();
                edipasswordall.setText(text + getString(R.string._7));
            }

        });
        txt8.setOnClickListener(v -> {
            if (i <= 3) {
                i++;
                text = edipasswordall.getText().toString().trim();
                edipasswordall.setText(text + getString(R.string._8));
            }

        });
        txt9.setOnClickListener(v -> {
            if (i <= 3) {
                i++;
                text = edipasswordall.getText().toString().trim();
                edipasswordall.setText(text + getString(R.string._9));
            }

        });
        imgpasback.setOnClickListener(view -> {
            text = edipasswordall.getText().toString();
            i = text.length() - 1;
            if (i > 0) {
                String substr = text.substring(0, i);
                edipasswordall.setText(substr);
                if (i == 0) {
                    pas1.setBackgroundResource(R.drawable.shadov);
                }
                if (i == 1) {
                    pas2.setBackgroundResource(R.drawable.shadov);
                }
                if (i == 2) {
                    pas3.setBackgroundResource(R.drawable.shadov);
                }
                if (i == 3) {
                    pas4.setBackgroundResource(R.drawable.shadov);
                }
            }else {
                i = 0;
            }
        });
        imgpasback.setOnClickListener(view -> {
            text = edipasswordall.getText().toString().trim();
            i = text.length() - 1;
            if (i >= 0) {
                String substr = text.substring(0, i);
                edipasswordall.setText(substr);
                text = edipasswordall.getText().toString().trim();
                if (i == 0) {
                    pas1.setBackgroundResource(R.drawable.shadov);
                    imgpasback.setVisibility(View.VISIBLE);
                }
                if (i == 1) {
                    pas2.setBackgroundResource(R.drawable.shadov);
                    imgpasback.setVisibility(View.VISIBLE);
                }
                if (i == 2) {
                    pas3.setBackgroundResource(R.drawable.shadov);
                    imgpasback.setVisibility(View.VISIBLE);
                }
                if (i == 3) {
                    pas4.setBackgroundResource(R.drawable.shadov);
                    imgpasback.setVisibility(View.INVISIBLE);
                }
            }else {
                i = 0;
            }
        });
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
            id = stringBuffer.toString();
            token = stringBuffer1.toString();
            keys = stringBuffer2.toString();
            password = stringBuffer3.toString();
            finger = stringBuffer4.toString();
        }
    }
    @SuppressLint("SetTextI18n")
    public void readData() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("dycfvsufgjoafienvk.iso", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        txthello.setText("Hello, " + name);
    }
    public void readdata() {
        Call<TokenRequest> tokenRequestCall = ApiCleint.getUserService().userTokenRequest("Bearer " + token);
        tokenRequestCall.enqueue(new Callback<TokenRequest>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<TokenRequest> call, @NotNull Response<TokenRequest> response) {
                if (response.isSuccessful()) {
                    TokenRequest tokenRequest1 = response.body();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                if (tokenRequest1 != null) {
                                    txthello.setText("Hello, " + tokenRequest1.getName());
                                    SharedPreferences sharedPreferences = getApplication().getSharedPreferences("dycfvsufgjoafienvk.iso", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("name", tokenRequest1.getName());
                                    editor.putString("last_name", tokenRequest1.getLast_name());
                                    editor.putString("qwasar", tokenRequest1.getQwasar());
                                    editor.putString("email", tokenRequest1.getEmail());
                                    editor.putString("number", tokenRequest1.getNumber());
                                    editor.putString("stack", tokenRequest1.getStack());
                                    editor.putString("role", tokenRequest1.getRole());
                                    editor.putString("status", tokenRequest1.getStatus());
                                    editor.putString("photo", tokenRequest1.getPhoto());
                                    editor.putString("balance", tokenRequest1.getBalance());
                                    editor.putString("wallet", tokenRequest1.getWallet());
                                    editor.apply();
                                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                                startActivity(new Intent(Password.this, Password.class));
                                                finish();
                                                call.cancel();
                                            },
                                            800);
                                    String t = edipasswordall.getText().toString();
                                    boolean a = sqlData.ozgartir(id,token,"1",t,"1");
                                    if (a){Toast.makeText(Password.this, "password changed", Toast.LENGTH_SHORT).show();}
                                }
                            },600);
                }
            }
            @Override
            public void onFailure(@NotNull Call<TokenRequest> call, @NotNull Throwable t) {
                Toast.makeText(Password.this, "error", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
    private void errorpss(){
        pas4.setBackgroundResource(R.drawable.shadovfaild);
        pas1.setBackgroundResource(R.drawable.shadovfaild);
        pas2.setBackgroundResource(R.drawable.shadovfaild);
        pas3.setBackgroundResource(R.drawable.shadovfaild);
        new Handler(Looper.getMainLooper()).postDelayed(
                () -> {
                    pas1.setBackgroundResource(R.drawable.shadov);
                    pas2.setBackgroundResource(R.drawable.shadov);
                    pas3.setBackgroundResource(R.drawable.shadov);
                    pas4.setBackgroundResource(R.drawable.shadov);
                    i = 0;imgpasback.setVisibility(View.VISIBLE);
                },
                500);
    }
    private void soucsess() {
        pas1.setBackgroundResource(R.drawable.shadov_soucsess);
        pas2.setBackgroundResource(R.drawable.shadov_soucsess);
        pas3.setBackgroundResource(R.drawable.shadov_soucsess);
        pas4.setBackgroundResource(R.drawable.shadov_soucsess);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    startActivity(new Intent(Password.this, SampleActivity.class));
                    finish();
                },
                500);
    }
}
