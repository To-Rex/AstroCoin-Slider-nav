package com.sliding.navigator.sample.recycler;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sliding.navigator.sample.R;
import com.sliding.navigator.sample.constructor.TransferRequest;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

    Context context;
    ArrayList<TransferRequest> transferRequestArrayList;

    public ModelAdapter(Context context, ArrayList<TransferRequest> transferRequestArrayList) {
        this.context = context;
        this.transferRequestArrayList = transferRequestArrayList;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_item, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TransferRequest transferRequest = transferRequestArrayList.get(position);
        holder.tranferdata.setText(transferRequest.getDatatransfer());
        holder.trtransfer.setText(transferRequest.getTitle());
        holder.trcoin.setText(transferRequest.getAmount());

        if (transferRequest.getDatatransfer().equals("") || transferRequest.getDatatransfer() == null) {
            holder.tranferdata.setVisibility(View.GONE);
        } else {
            holder.tranferdata.setVisibility(View.VISIBLE);
            holder.tranferdata.setText(transferRequest.getDatatransfer());
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("dycfvsufgjoafienvk.iso", Context.MODE_PRIVATE);
        String wallet = sharedPreferences.getString("wallet", "");

        if (Objects.equals(transferRequest.getStatus(), "success") && !Objects.equals(transferRequest.getWallet_to(), wallet)||
                Objects.equals(transferRequest.getStatus(), "failed") && Objects.equals(transferRequest.getWallet_to(), wallet)) {
            holder.trimage.setImageResource(R.drawable.ic_component_3__1_);
            if (Objects.equals(transferRequest.getAmount(), "")) {
                holder.trcoin.setVisibility(View.GONE);
            } else {
                holder.trcoin.setText("-" + transferRequest.getAmount() + " ASC");
            }
        } else {
            if (Objects.equals(transferRequest.getStatus(), "failed") && !Objects.equals(transferRequest.getWallet_to(), wallet)) {
                holder.trimage.setImageResource(R.drawable.ic_felid);
                if (Objects.equals(transferRequest.getAmount(), "")) {
                    holder.trcoin.setVisibility(View.GONE);
                } else {
                    holder.trcoin.setText(transferRequest.getAmount() + " ASC");
                }
            } else {
                if (Objects.equals(transferRequest.getStatus(), "success") && Objects.equals(transferRequest.getWallet_to(), wallet)) {
                    holder.trimage.setImageResource(R.drawable.ic_soucsess);
                    if (Objects.equals(transferRequest.getAmount(), "")) {
                        holder.trcoin.setVisibility(View.GONE);
                    } else {
                        holder.trcoin.setText("+" + transferRequest.getAmount() + " ASC");
                    }
                } else {
                    holder.trimage.setImageResource(R.drawable.ic_felid);
                    if (Objects.equals(transferRequest.getAmount(), "")) {
                        holder.trcoin.setVisibility(View.GONE);
                    } else {
                        holder.trcoin.setText(transferRequest.getAmount() + " ASC");
                    }
                }
            }

        }


        final BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.custombottomsheet);
        holder.itemView.setOnClickListener(v -> {
            v = LayoutInflater.from(context).inflate(R.layout.bottomsheetschek, null);
            dialog.setContentView(v);
            ImageView imgg = dialog.findViewById(R.id.imageView23);
            TextView bottchekfio = dialog.findViewById(R.id.bottchekfio);
            TextView bottchektype = dialog.findViewById(R.id.bottchektype);
            TextView bottchekdate = dialog.findViewById(R.id.bottchekdate);
            TextView bottchekamaunt = dialog.findViewById(R.id.bottchekamaunt);
            TextView bottchekwallet_to = dialog.findViewById(R.id.bottchekwallet_to);
            TextView bottchekcomment = dialog.findViewById(R.id.bottchekcomment);
            TextView bottchekstatus = dialog.findViewById(R.id.bottchekstatus);
            TextView title = dialog.findViewById(R.id.bottomchektitle);

            assert bottchekwallet_to != null;
            bottchekwallet_to.setOnClickListener(v1 -> {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(transferRequest.getWallet_to(), transferRequest.getWallet_to());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "copy wallet id", Toast.LENGTH_SHORT).show();
            });

            if (title != null) {
                title.setText(transferRequest.getTitle());
            }
            if (bottchekfio != null) {
                bottchekfio.setText(transferRequest.getFio());
            }
            if (bottchekamaunt != null) {
                bottchekamaunt.setText(bottchekamaunt.getText().toString());
            }
            if (bottchekcomment != null) {
                bottchekcomment.setText(transferRequest.getComment());
            }
            if (bottchektype != null) {
                bottchektype.setText(transferRequest.getType());
            }
            if (bottchekstatus != null) {
                if (Objects.equals(transferRequest.getStatus(), "success")) {
                    bottchekstatus.setTextColor(Color.GREEN);
                } else {
                    bottchekstatus.setTextColor(Color.RED);
                }
                bottchekstatus.setText(transferRequest.getStatus());
            }
            if (bottchekdate != null) {
                String chdata = transferRequest.getDate().substring(0, 10)+" "+transferRequest.getDate().substring(11, 16);
                String my_new_str = chdata.replace("-", "/");
                bottchekdate.setText(my_new_str);
            }
            if (bottchekwallet_to != null) {
                bottchekwallet_to.setText(transferRequest.getWallet_to());

            }
            if (Objects.equals(transferRequest.getStatus(), "success") && !Objects.equals(transferRequest.getWallet_to(), wallet) ||
                    Objects.equals(transferRequest.getStatus(), "failed") && Objects.equals(transferRequest.getWallet_to(), wallet)) {
                assert imgg != null;
                imgg.setImageResource(R.drawable.ic_component_3__1_);
                assert bottchekamaunt != null;
                if (Objects.equals(transferRequest.getAmount(), "")) {
                    bottchekamaunt.setVisibility(View.GONE);
                } else {
                    bottchekamaunt.setText("-" + transferRequest.getAmount() + " ASC");
                }
            } else {
                if (Objects.equals(transferRequest.getStatus(), "success") && Objects.equals(transferRequest.getWallet_to(), wallet)) {
                    assert imgg != null;
                    imgg.setImageResource(R.drawable.ic_soucsess);
                    assert bottchekamaunt != null;
                    if (Objects.equals(transferRequest.getAmount(), "")) {
                        bottchekamaunt.setVisibility(View.GONE);
                    } else {
                        bottchekamaunt.setText("+" + transferRequest.getAmount() + " ASC");
                    }
                } else {
                    if (Objects.equals(transferRequest.getStatus(), "failed") && !Objects.equals(transferRequest.getWallet_to(), wallet)) {
                        assert imgg != null;
                        imgg.setImageResource(R.drawable.ic_felid);
                        assert bottchekamaunt != null;
                        if (Objects.equals(transferRequest.getAmount(), "")) {
                            bottchekamaunt.setVisibility(View.GONE);
                        } else {
                            bottchekamaunt.setText(transferRequest.getAmount() + " ASC");
                        }
                    } else {
                        if (Objects.equals(transferRequest.getStatus(), "returned")) {
                            assert imgg != null;
                            imgg.setImageResource(R.drawable.ic_felid);
                            assert bottchekamaunt != null;
                            if (Objects.equals(transferRequest.getAmount(), "")) {
                                bottchekamaunt.setVisibility(View.GONE);
                            } else {
                                bottchekamaunt.setText(transferRequest.getAmount() + " ASC");
                            }
                        }
                    }
                }
            }
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return transferRequestArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView trtransfer, trcoin, tranferdata;
        ImageView trimage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            trtransfer = itemView.findViewById(R.id.trtransfer);
            trcoin = itemView.findViewById(R.id.trcoin);
            tranferdata = itemView.findViewById(R.id.tranferdata);
            trimage = itemView.findViewById(R.id.trimage);

        }
    }
}
