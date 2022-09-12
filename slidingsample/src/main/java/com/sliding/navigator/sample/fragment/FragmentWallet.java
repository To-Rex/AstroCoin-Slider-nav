package com.sliding.navigator.sample.fragment;
import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.sliding.navigator.sample.R;
import com.sliding.navigator.sample.adapter.ApiCleint;
import com.sliding.navigator.sample.adapter.PagerAdapter;
import com.sliding.navigator.sample.constructor.CheckWallet;
import com.sliding.navigator.sample.constructor.SendTransferRequest;
import com.sliding.navigator.sample.constructor.TokenRequest;
import com.sliding.navigator.sample.data.SqlData;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWallet extends Fragment {

    private SqlData sqlData;
    String token, wallet;
    private TextView txtusercoin, txtwalletname;
    private ClipboardManager clipboard;
    private ImageView imgsamqr;
    private ImageView imgsamqrscan;
    private ImageView imgsamsend;
    private EditText edibottomwalletid;
    private BottomSheetDialog dialog;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    SwipeRefreshLayout swipeRefreshLayout;
    int l = 0, count = 0, balans = 0, h = 0, fin = 0;
    String balance;
    /*ActivityResultLauncher<ScanOptions> resultLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            @SuppressLint("InflateParams")
            View v = getLayoutInflater().inflate(R.layout.bottomsheetsend, null);
            dialog = new BottomSheetDialog(requireContext(), R.style.custombottomsheet);
            dialog.setContentView(v);
            edibottomwalletid = v.findViewById(R.id.edibottomwalletid);
            ImageView imgpastbottom = v.findViewById(R.id.imgpastbottom);
            txtwalletname = v.findViewById(R.id.txtwalletname);
            EditText edibottomwalletamaund = v.findViewById(R.id.edibottomwalletamaund);
            EditText edibottomwallcament = v.findViewById(R.id.edibottomwallcament);
            Button btnbottomshare = v.findViewById(R.id.btnbottomshare);

            edibottomwalletid.setText(result.getContents());
            checkName();
            edibottomwallcament.setMaxLines(6);
            edibottomwalletid.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    checkName();
                }
            });
            imgpastbottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData clipData = clipboard.getPrimaryClip();
                    if (clipData != null) {
                        ClipData.Item item = clipData.getItemAt(0);
                        if (item.getText() != null) {
                            edibottomwalletid.setText(item.getText().toString());
                            Toast.makeText(requireContext(), "Pasted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            btnbottomshare.setOnClickListener(v12 -> {
                if (edibottomwalletid.getText().toString().isEmpty()) {
                    edibottomwalletid.setError("Wallet ID is empty");
                    return;
                }
                if (edibottomwalletamaund.getText().toString().isEmpty()) {
                    edibottomwalletamaund.setError("Wallet Amount is empty");
                    return;
                }
                SendTransferRequest sendTransferRequest = new SendTransferRequest();
                sendTransferRequest.setWallet_to(edibottomwalletid.getText().toString());
                double amount = Double.parseDouble(edibottomwalletamaund.getText().toString());
                sendTransferRequest.setAmount(amount);
                sendTransferRequest.setComment(edibottomwallcament.getText().toString());
                sendTransferRequest.setType("");
                sendTransferRequest.setTitle("");
                Call<Object> call = ApiCleint.getUserService().saveVotes("Bearer " + token, sendTransferRequest);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                        if (response.body() != null) {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                        call.cancel();
                    }
                    @Override
                    public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });
            });
            dialog.show();
        }
    });*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashbord, container, false);
    }
    @SuppressLint("InflateParams")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        sqlData = new SqlData(getContext());
        txtusercoin = view.findViewById(R.id.txtusercoin);

        imgsamqrscan = view.findViewById(R.id.imgsamqrscan);
        imgsamqr = view.findViewById(R.id.imgsamqr);
        imgsamsend = view.findViewById(R.id.imgsamsend);

        clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeColors(Color.GREEN,Color.RED,Color.BLACK);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        //int width = displayMetrics.widthPixels;

        if (height < 1300) {
            int joyi = (int) imgsamqrscan.getY();
            imgsamqrscan.setY(joyi-24);
            imgsamqr.setY(joyi-20);
            imgsamsend.setY(joyi-20);
            txtusercoin.setY(joyi-15);
            txtusercoin.setTextSize(18);
        }

        readsql();
        readData();
        datacheck();

        final BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.custombottomsheet);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            txtusercoin.setText("0");
            balance = "0";
            h = 0;
            readData();
            refreshtransfer();
            readdata1();
            new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 1200);

        });

        imgsamsend.setOnClickListener(v -> {
            v = getLayoutInflater().inflate(R.layout.bottomsheetsend, null);
            dialog.setContentView(v);
            txtwalletname = v.findViewById(R.id.txtwalletname);
            edibottomwalletid = v.findViewById(R.id.edibottomwalletid);
            EditText edibottomwalletamaund = v.findViewById(R.id.edibottomwalletamaund);
            EditText edibottomwallcament = v.findViewById(R.id.edibottomwallcament);
            Button btnbottomshare = v.findViewById(R.id.btnbottomshare);
            ImageView imgpastbottom = v.findViewById(R.id.imgpastbottom);
            imgpastbottom.setOnClickListener(v13 -> {
                ClipData clipData = clipboard.getPrimaryClip();
                if (clipData != null) {
                    ClipData.Item item = clipData.getItemAt(0);
                    if (item.getText() != null) {
                        edibottomwalletid.setText(item.getText().toString());
                        Toast.makeText(requireContext(), "Pasted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            checkName();
            edibottomwallcament.setMaxLines(6);
            edibottomwalletid.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    checkName();
                }
            });
            btnbottomshare.setOnClickListener(v12 -> {
                if (edibottomwalletid.getText().toString().isEmpty()) {
                    edibottomwalletid.setError("Wallet ID is empty");
                    return;
                }
                if (edibottomwalletamaund.getText().toString().isEmpty()) {
                    edibottomwalletamaund.setError("Wallet Amount is empty");
                    return;
                }

                SendTransferRequest sendTransferRequest = new SendTransferRequest();
                sendTransferRequest.setWallet_to(edibottomwalletid.getText().toString());
                double amount = Double.parseDouble(edibottomwalletamaund.getText().toString().trim());
                sendTransferRequest.setAmount(amount);
                sendTransferRequest.setComment(edibottomwallcament.getText().toString());
                sendTransferRequest.setType("");
                sendTransferRequest.setTitle("");

                Call<Object> call = ApiCleint.getUserService().saveVotes("Bearer " + token, sendTransferRequest);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {

                        if (response.body() != null) {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            call.cancel();
                        } else {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                            call.cancel();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });
            });
            dialog.show();
        });
        imgsamqr.setOnClickListener(view1 -> {
            view1 = getLayoutInflater().inflate(R.layout.bottomsheetqrcode, null);
            dialog.setContentView(view1);
            dialog.show();

            ImageView imgbottomqrcode = view1.findViewById(R.id.imgbottomqrcode);
            ImageView imgbottomcopi = view1.findViewById(R.id.imgbottomcopi);
            TextView txtbottomwallet = view1.findViewById(R.id.txtbottomwallet);
            Button btnbottomshare = view1.findViewById(R.id.btnbottomshare);
            txtbottomwallet.setText(wallet);
            txtbottomwallet.didTouchFocusSelect();
            btnbottomshare.setOnClickListener(view12 -> {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String shareBody = wallet;
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, wallet);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, wallet));
            });

            imgbottomcopi.setOnClickListener(view12 -> {
                ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(wallet, wallet);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(requireContext(), "copy wallet id", Toast.LENGTH_SHORT).show();
            });
           /* MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(wallet, BarcodeFormat.QR_CODE, 400, 400);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);
                imgbottomqrcode.setImageBitmap(bitmap);
                InputMethodManager manager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }*/
        });
        imgsamqrscan.setOnClickListener(v -> {
            v = getLayoutInflater().inflate(R.layout.bottomsheetscanner, null);
            dialog.setContentView(v);
            ImageView sheetgallarey = dialog.findViewById(R.id.sheetgallarey);
            ImageView sheetimgcam = dialog.findViewById(R.id.sheetimgcam);

            if (sheetgallarey != null) {
                sheetgallarey.setOnClickListener(v1 -> {
                    ScanCode();
                    dialog.dismiss();
                });
            }
            if (sheetimgcam != null) {
                /*sheetimgcam.setOnClickListener(v1 -> {
                    ScanOptions options = new ScanOptions();
                    options.setPrompt("Scan QR Code");
                    options.setBeepEnabled(false);
                    options.setCaptureActivity(CaptureAct.class);
                    options.setOrientationLocked(true);
                    resultLauncher.launch(options);
                    dialog.dismiss();
                });*/
            }
            dialog.show();
        });
    }
    private void ScanCode() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1000);
    }
    @SuppressLint("SetTextI18n")
    public void readData() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("dycfvsufgjoafienvk.iso", Context.MODE_PRIVATE);
        //String photo1 = sharedPreferences.getString("photo", "");
        balance = sharedPreferences.getString("balance", "");
        wallet = sharedPreferences.getString("wallet", "");
        txtusercoin.setText(balance+ " ASC");
        imgsamqr.setVisibility(View.VISIBLE);
        imgsamqrscan.setVisibility(View.VISIBLE);
        imgsamsend.setVisibility(View.VISIBLE);

        if (balance.equals("")||balance.equals("0")) {
            txtusercoin.setText("0 ASC");
            //h = 1;
        }else {
            new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
                Thread t = new Thread() {
                    @SuppressLint("UseRequireInsteadOfGet")
                    @Override
                    public void run() {
                        balans = Integer.parseInt(balance);
                        int bal = Integer.parseInt(balance);
                        if (balans < 0) {
                            if (balans < -2000) {
                                count = balans + 1000;
                                while (!isInterrupted()) {
                                    try {
                                        if (h != 1) {
                                            Thread.sleep(1);  //1000ms = 1 sec
                                            int finalBalans = balans;
                                            requireActivity().runOnUiThread(() -> {
                                                count--;
                                                if (finalBalans == count) {
                                                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                                                            () -> txtusercoin.setText(bal + " ASC"),
                                                            30);
                                                    h = 1;
                                                    count = 0;
                                                } else {
                                                    txtusercoin.setText(count + " ASC");
                                                }
                                            });
                                        } else break;
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else while (!isInterrupted()) {
                                try {
                                    if (h != 1) {
                                        Thread.sleep(1);  //1000ms = 1 sec
                                        int finalBalans = balans;
                                        requireActivity().runOnUiThread(() -> {
                                            count--;
                                            if (finalBalans == count) {
                                                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                                                        () -> txtusercoin.setText(bal + " ASC"),
                                                        30);
                                                h = 1;
                                                count = 0;
                                            } else {
                                                txtusercoin.setText(count + " ASC");
                                            }
                                        });
                                    } else break;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (balans > 2000) {
                            count = balans - 1000;
                            while (!isInterrupted()) {
                                try {
                                    if (h != 1) {
                                        Thread.sleep(1);  //1000ms = 1 sec
                                        int finalBalans = balans;
                                        requireActivity().runOnUiThread(() -> {
                                            count++;
                                            if (finalBalans == count) {
                                                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                                                        () -> txtusercoin.setText(bal + " ASC"),
                                                        10);
                                                h = 1;
                                                count = 0;
                                            } else {
                                                txtusercoin.setText(count + " ASC");
                                            }
                                        });
                                    } else break;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            while (!isInterrupted()) {
                                try {
                                    if (h != 1) {
                                        Thread.sleep(1);  //1000ms = 1 sec
                                        int finalBalans = balans;
                                        requireActivity().runOnUiThread(() -> {
                                            count++;
                                            if (finalBalans == count) {
                                                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                                                        () -> txtusercoin.setText(bal + " ASC"),
                                                        10);
                                                h = 1;
                                                count = 0;
                                            } else {
                                                txtusercoin.setText(count + " ASC");
                                            }
                                        });
                                    } else break;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                };t.start();
            },700);
        }
    }
    @SuppressLint({"ResourceAsColor", "NewApi"})
    private void refreshtransfer() {
        // PagerAdapter pagerAdapter = new PagerAdapter(requireActivity().getSupportFragmentManager());
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new TransferFragment());
        pagerAdapter.addFragment(new TransferOrderFragmant());

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#C9C9C9"), Color.parseColor("#5733D1"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#5733D1"));

        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Transfer");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("Orders");

        TabLayout.Tab selectedTab = tabLayout.getTabAt(0);

        assert selectedTab != null;
        //setTabTypeface(selectedTab, ResourcesCompat.getFont(requireContext(), R.font.fredoka_fonts));

    }
    /*private void setTabTypeface(TabLayout.Tab tab, Typeface typeface) {

        for (int i = 0; i < tab.view.getChildCount(); i++) {
            View tabViewChild = tab.view.getChildAt(i);
            if (tabViewChild instanceof TextView)
                ((TextView) tabViewChild).setTypeface(typeface);
        }
    }*/
    private void readsql() {
        Cursor res = sqlData.oqish();
        StringBuilder stringBuffer1 = new StringBuilder();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer1.append(res.getString(1));
            }
            token = stringBuffer1.toString();
            readData();
        }
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = requireContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                try {
                    String contents;
                    int[] intArray = new int[selectedImage.getWidth() * selectedImage.getHeight()];
                   /* selectedImage.getPixels(intArray, 0, selectedImage.getWidth(), 0, 0, selectedImage.getWidth(), selectedImage.getHeight());
                    LuminanceSource source = new RGBLuminanceSource(selectedImage.getWidth(), selectedImage.getHeight(), intArray);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Reader reader = new MultiFormatReader();
                    Result result = reader.decode(bitmap);
                    contents = result.getText();
*/
                    @SuppressLint("InflateParams")
                    View v = getLayoutInflater().inflate(R.layout.bottomsheetsend, null);
                    dialog = new BottomSheetDialog(requireContext(), R.style.custombottomsheet);
                    dialog.setContentView(v);

                    txtwalletname = dialog.findViewById(R.id.txtwalletname);
                    edibottomwalletid = v.findViewById(R.id.edibottomwalletid);
                    ImageView imgpastbottom = v.findViewById(R.id.imgpastbottom);
                    EditText edibottomwalletamaund = v.findViewById(R.id.edibottomwalletamaund);
                    EditText edibottomwallcament = v.findViewById(R.id.edibottomwallcament);
                    Button btnbottomshare = v.findViewById(R.id.btnbottomshare);
                    //edibottomwalletid.setText(contents);

                    imgpastbottom.setOnClickListener(v1 -> {
                        ClipData clipData = clipboard.getPrimaryClip();
                        if (clipData != null) {
                            ClipData.Item item = clipData.getItemAt(0);
                            if (item.getText() != null) {
                                edibottomwalletid.setText(item.getText().toString());
                                Toast.makeText(requireContext(), "Pasted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    checkName();
                    edibottomwallcament.setMaxLines(6);
                    edibottomwalletid.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}@Override
                        public void afterTextChanged(Editable editable) {
                            checkName();
                        }});
                    btnbottomshare.setOnClickListener(v12 -> {
                        if (edibottomwalletid.getText().toString().isEmpty()) {
                            edibottomwalletid.setError("Wallet ID is empty");
                            return;
                        }
                        if (edibottomwalletamaund.getText().toString().isEmpty()) {
                            edibottomwalletamaund.setError("Wallet Amount is empty");
                            return;
                        }
                        SendTransferRequest sendTransferRequest = new SendTransferRequest();
                        sendTransferRequest.setWallet_to(edibottomwalletid.getText().toString());
                        double amount = Double.parseDouble(edibottomwalletamaund.getText().toString());
                        sendTransferRequest.setAmount(amount);
                        sendTransferRequest.setComment(edibottomwallcament.getText().toString());
                        sendTransferRequest.setType("");
                        sendTransferRequest.setTitle("");
                        if (edibottomwalletid.getText().toString().equals("")) {
                            Toast.makeText(requireContext(), "Enter wallet id", Toast.LENGTH_SHORT).show();
                            edibottomwalletid.setError("Enter wallet id");
                            return;
                        }
                        if (edibottomwalletamaund.getText().toString().equals("")) {
                            Toast.makeText(requireContext(), "Enter amount", Toast.LENGTH_SHORT).show();
                            edibottomwalletamaund.setError("Enter amount");
                            return;
                        }
                        Call<Object> call = ApiCleint.getUserService().saveVotes("Bearer " + token, sendTransferRequest);
                        call.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                                if (response.body() != null) {
                                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    call.cancel();
                                } else {
                                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                                    call.cancel();
                                }
                            }
                            @Override
                            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                                call.cancel();
                            }
                        });
                    });
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
    public void readdata1() {
        Call<TokenRequest> tokenRequestCall = ApiCleint.getUserService().userTokenRequest("Bearer " + token);
        tokenRequestCall.enqueue(new Callback<TokenRequest>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<TokenRequest> call, @NotNull Response<TokenRequest> response) {
                if (response.isSuccessful()) {
                    TokenRequest tokenRequest1 = response.body();
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
                        if (tokenRequest1 != null) {
                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("dycfvsufgjoafienvk.iso", Context.MODE_PRIVATE);
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
                            readData();
                            call.cancel();
                        }
                    },1000);
                }
            }
            @Override
            public void onFailure(@NotNull Call<TokenRequest> call, @NotNull Throwable t) {
                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
    private void datacheck() {
        if (l == 0) {
            refreshtransfer();
            new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    () -> l = 1, 500);
        }
        new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (l == 0) {
                refreshtransfer();
                datacheck();
            }
        },500);
    }
    private void checkName() {
        if (edibottomwalletid.getText().toString().length() > 31) {
            CheckWallet checkWallet = new CheckWallet(edibottomwalletid.getText().toString());
            Call<Object> call = ApiCleint.getUserService().userWalletname("Bearer " + token, checkWallet);
            call.enqueue(new Callback<Object>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                    if (response.body() != null) {
                        String dfhj = response.body().toString();
                        new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
                            String[] s = dfhj.replace("{", "").replace("}", "").split(",");
                            txtwalletname.setText(s[0].substring(4));
                            call.cancel();
                        }, 900);
                    } else txtwalletname.setText("Not available");
                }
                @Override
                public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }
    }
}