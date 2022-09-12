package com.sliding.navigator.sample.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sliding.navigator.sample.R;
import com.sliding.navigator.sample.constructor.CheckWallet;
import com.sliding.navigator.sample.constructor.SendTransferRequest;
import com.sliding.navigator.sample.constructor.UserRequestdata;
import com.sliding.navigator.sample.data.SqlData;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListAdapter extends BaseAdapter implements Filterable {
    private final List<UserRequestdata> itemsModelsl;
    private List<UserRequestdata> itemsModelListFiltered;
    private final Context context;
    String token = "";
    private ClipboardManager clipboard;
    private SqlData sqlData;
    ImageView photoview2;
    float[] lastEvent = null;
    float d = 0f;
    float newRot = 0f;
    private boolean isZoomAndRotate;
    private boolean isOutSide;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private final PointF start = new PointF();
    private final PointF mid = new PointF();
    float oldDist = 1f;
    private float xCoOrdinate, yCoOrdinate;

    public UserListAdapter(List<UserRequestdata> itemsModelsl, Context context) {
        this.itemsModelsl = itemsModelsl;
        this.itemsModelListFiltered = itemsModelsl;
        this.context = context;
    }
    @Override
    public int getCount() {
        return itemsModelListFiltered.size();
    }
    @Override
    public Object getItem(int position) {
        return itemsModelListFiltered.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @SuppressLint({"SetTextI18n", "NewApi", "ClickableViewAccessibility"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(context).inflate(R.layout.usersearch_item, parent, false);
        }
        UserRequestdata dataModal = (UserRequestdata) getItem(position);

        TextView nameTV = listitemView.findViewById(R.id.ustransfer);
        TextView idtxt = listitemView.findViewById(R.id.uscoin);
        ImageView courseIV = listitemView.findViewById(R.id.usimage);

        if (dataModal.getName().length() > 12 || dataModal.getLast_name().length() > 12) {
            //String nae = dataModal.getName() + " " + dataModal.getLast_name();
            assert nameTV != null;
           // nameTV.setText(nae.substring(0, 22) + "...");
            nameTV.setText(dataModal.getName());
            //Toast.makeText(context, ""+nae, Toast.LENGTH_SHORT).show();
        } else {
            assert nameTV != null;
            //nameTV.setText(dataModal.getName() + " " + dataModal.getLast_name());
            nameTV.setText(dataModal.getName());
        }
        //nameTV.setText(dataModal.getName() + " " + dataModal.getLast_name());
        String ccoin = dataModal.getBalance().split("\\.0")[0];

        if (ccoin.length() > 0) {
            int coiint = Integer.parseInt(ccoin);
            if (coiint < 0) {
                idtxt.setText("-" + dataModal.getBalance().split("\\.0")[0] + " ASC");
            } else {
                if (coiint == 0) {
                    idtxt.setText("0" + " ASC");
                } else {
                    idtxt.setText("+" + dataModal.getBalance().split("\\.0")[0] + " ASC");
                }
            }
        } else {
            idtxt.setText("0" + " ASC");
        }


        String image = dataModal.getPhoto();

        if (Objects.equals(dataModal.getPhoto(), "")
                || Objects.equals(dataModal.getPhoto(), null)) {
            courseIV.setImageResource(R.drawable.ic_profile_circle);
        } else {
            Glide.with(context).load("https://api.astrocoin.uz" + image).into(courseIV);
        }
        listitemView.setOnClickListener(v -> {
            final BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.custombottomsheet);
            dialog.setContentView(R.layout.bottomsheetsearchuser);
            UserRequestdata dataaa = (UserRequestdata) getItem(position);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            TextView txtsearchusersend = dialog.findViewById(R.id.txtsearchusersend);
            TextView name = dialog.findViewById(R.id.txtsearchbottomname);
            TextView coin = dialog.findViewById(R.id.txtsearchusercoin);
            TextView qwasar = dialog.findViewById(R.id.txtbottomsearchqwasar);
            TextView stak = dialog.findViewById(R.id.textView12);
            TextView wolletid = dialog.findViewById(R.id.txtbottvalletiiid);
            ImageView imageshape = dialog.findViewById(R.id.setshapimguser090d);


            String namee = dataaa.getName() + " " + dataaa.getLast_name();
            if (namee.length() > 24) {
                assert name != null;
                name.setText(namee.substring(0, 24) + "...");
            } else {
                assert name != null;
                name.setText(namee);
            }
            assert imageshape != null;
            imageshape.setOnClickListener(v1 -> {
                Dialog dialog01 = new Dialog(context);
                dialog01.setContentView(R.layout.popupsearch);
                dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog01.setCancelable(true);

                ImageView setshapimguser = dialog01.findViewById(R.id.setshapimguser01);
                TextView qwtext = dialog01.findViewById(R.id.textView901);
                qwtext.setOnLongClickListener(v23 -> {
                    downloadImageNew("temp", "https://api.astrocoin.uz" + dataaa.getPhoto());
                    return false;
                });
                setshapimguser.setOnTouchListener((v22, event) -> {
                    ImageView view = (ImageView) v22;
                    view.bringToFront();
                    viewTransformation(view, event);
                    return true;
                });
                if (Objects.equals(dataModal.getPhoto(), "")
                        || Objects.equals(dataModal.getPhoto(), null)) {
                    setshapimguser.setImageResource(R.drawable.ic_profile_circle);
                } else {
                    Glide.with(context).load("https://api.astrocoin.uz" + image).into(setshapimguser);
                }
                qwtext.setText(dataaa.getQwasar());
                dialog01.show();                                
            });

            assert wolletid != null;
            wolletid.setOnLongClickListener(v13 -> {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(dataaa.getWallet(), dataaa.getWallet());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "copy wallet id", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                return false;
            });
            wolletid.setOnClickListener(view -> {
                dialog.setContentView(R.layout.bottomsheetqrcode);
                ImageView imgbottomqrcode = dialog.findViewById(R.id.imgbottomqrcode);
                ImageView imgbottomcopi = dialog.findViewById(R.id.imgbottomcopi);
                TextView txtbottomwallet = dialog.findViewById(R.id.txtbottomwallet);
                Button btnbottomshare = dialog.findViewById(R.id.btnbottomshare);
                assert txtbottomwallet != null;
                txtbottomwallet.setText(dataaa.getWallet());
                txtbottomwallet.didTouchFocusSelect();
                assert btnbottomshare != null;
                btnbottomshare.setOnClickListener(view12 -> {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String shareBody = dataaa.getWallet();
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, dataaa.getWallet());
                    intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    context.startActivity(Intent.createChooser(intent, dataaa.getWallet()));
                });

                assert imgbottomcopi != null;
                imgbottomcopi.setOnClickListener(view12 -> {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(dataaa.getWallet(), dataaa.getWallet());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, "copy wallet id", Toast.LENGTH_SHORT).show();
                });
                dialog.show();
            });
            if (ccoin.length() > 0) {
                int coiint = Integer.parseInt(ccoin);
                assert coin != null;
                if (coiint < 0) {
                    coin.setText("-" + dataModal.getBalance().split("\\.0")[0] + " ASC");
                } else {
                    if (coiint == 0) {
                        coin.setText("0" + " ASC");
                    } else {
                        coin.setText("+" + dataModal.getBalance().split("\\.0")[0] + " ASC");
                    }
                }
            } else {
                assert coin != null;
                coin.setText("0" + " ASC");
            }
            assert qwasar != null;
            qwasar.setText(dataaa.getQwasar());
            assert stak != null;
            assert wolletid != null;
            wolletid.setText(dataaa.getWallet());
            stak.setText(dataaa.getStack());
            if (dataaa.getPhoto() == null ||
                    Objects.equals(dataaa.getPhoto(), "") || Objects
                    .equals(dataaa.getPhoto(), null)) {
                courseIV.setImageResource(R.drawable.ic_profile_circle);
            } else {
                assert imageshape != null;
                Glide.with(context).load("https://api.astrocoin.uz" + dataaa.getPhoto()).into(imageshape);
            }
            assert txtsearchusersend != null;
            txtsearchusersend.setOnClickListener(v1 -> {
                dialog.dismiss();
                new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
                    clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    sqlData = new SqlData(context);
                    dialog.setContentView(R.layout.bottomsheetsend);
                    EditText edibottomwalletid = dialog.findViewById(R.id.edibottomwalletid);
                    ImageView imgpastbottom = dialog.findViewById(R.id.imgpastbottom);
                    TextView txtwalletname = dialog.findViewById(R.id.txtwalletname);
                    EditText edibottomwalletamaund = dialog.findViewById(R.id.edibottomwalletamaund);
                    EditText edibottomwallcament = dialog.findViewById(R.id.edibottomwallcament);
                    Button btnbottomshare = dialog.findViewById(R.id.btnbottomshare);
                    assert edibottomwalletid != null;
                    edibottomwalletid.setText(dataaa.getWallet());
                    Cursor res = sqlData.oqish();
                    StringBuilder stringBuffer1 = new StringBuilder();
                    if (res != null && res.getCount() > 0) {
                        while (res.moveToNext()) {
                            stringBuffer1.append(res.getString(1));
                        }
                        token = stringBuffer1.toString();
                    }
                    edibottomwallcament.setMaxLines(6);
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
                                        assert txtwalletname != null;
                                        txtwalletname.setText(s[0].substring(4));
                                        call.cancel();
                                    }, 900);
                                } else {
                                    assert txtwalletname != null;
                                    txtwalletname.setText("Not available");
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                call.cancel();
                            }
                        });
                    }
                    edibottomwalletid.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
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
                                                assert txtwalletname != null;
                                                txtwalletname.setText(s[0].substring(4));
                                                call.cancel();
                                            }, 900);
                                        } else {
                                            assert txtwalletname != null;
                                            txtwalletname.setText("Not available");
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                        call.cancel();
                                    }
                                });
                            }
                        }
                    });
                    assert imgpastbottom != null;
                    imgpastbottom.setOnClickListener(v2 -> {
                        ClipData clipData = clipboard.getPrimaryClip();
                        if (clipData != null) {
                            ClipData.Item item = clipData.getItemAt(0);
                            if (item.getText() != null) {
                                edibottomwalletid.setText(item.getText().toString());
                                Toast.makeText(context, "Pasted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    edibottomwallcament.setMaxLines(6);
                    assert btnbottomshare != null;
                    btnbottomshare.setOnClickListener(v12 -> {
                        if (edibottomwalletid.getText().toString().isEmpty()) {
                            edibottomwalletid.setError("Wallet ID is empty");
                            return;
                        }
                        assert edibottomwalletamaund != null;
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
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                    dialog.show();
                }, 800);
            });
            dialog.show();

        });
        return listitemView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = itemsModelsl.size();
                    filterResults.values = itemsModelsl;
                } else {
                    List<UserRequestdata> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();
                    for (UserRequestdata userRequestdata : itemsModelsl) {
                        if (userRequestdata.getName().contains(searchStr)
                                || userRequestdata.getStack().contains(searchStr)
                                || userRequestdata.getLast_name().contains(searchStr)
                                || userRequestdata.getStatus().contains(searchStr)
                                || userRequestdata.getBalance().contains(searchStr)
                                || userRequestdata.getQwasar().contains(searchStr)) {
                            resultsModel.add(userRequestdata);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemsModelListFiltered = (List<UserRequestdata>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    private void downloadImageNew(String filename, String downloadUrlOfImage) {
        try {
            DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(downloadUrlOfImage);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + filename + ".jpg");
            dm.enqueue(request);
            Toast.makeText(context, "Image download started.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Image download failed.", Toast.LENGTH_SHORT).show();
        }
    }
    private void viewTransformation(View view, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                xCoOrdinate = view.getX() - event.getRawX();
                yCoOrdinate = view.getY() - event.getRawY();

                start.set(event.getX(), event.getY());
                isOutSide = false;
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    midPoint(mid, event);
                    mode = ZOOM;
                }

                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
                isZoomAndRotate = false;
                if (mode == DRAG) {
                    float x = event.getX();
                    float y = event.getY();
                }
            case MotionEvent.ACTION_OUTSIDE:
                isOutSide = true;
                mode = NONE;
                lastEvent = null;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isOutSide) {
                    if (mode == DRAG) {
                        isZoomAndRotate = false;
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                    }
                    if (mode == ZOOM && event.getPointerCount() == 2) {
                        float newDist1 = spacing(event);
                        if (newDist1 > 10f) {
                            float scale = newDist1 / oldDist * view.getScaleX();
                            view.setScaleX(scale);
                            view.setScaleY(scale);
                        }
                        if (lastEvent != null) {
                            newRot = rotation(event);
                            view.setRotation((float) (view.getRotation() + (newRot - d)));
                        }
                    }
                }
                break;
        }
    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (int) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
