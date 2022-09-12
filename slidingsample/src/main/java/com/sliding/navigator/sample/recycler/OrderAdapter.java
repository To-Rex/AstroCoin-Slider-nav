package com.sliding.navigator.sample.recycler;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
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
import com.sliding.navigator.sample.constructor.TransferOrderRequest;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;
    ArrayList<TransferOrderRequest> transferOrderArrayList;

    public OrderAdapter(Context context, ArrayList<TransferOrderRequest> transferOrderArrayList) {
        this.context = context;
        this.transferOrderArrayList = transferOrderArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_order_item, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransferOrderRequest transferOrderRequest = transferOrderArrayList.get(position);


        holder.tranferdata1.setText(transferOrderRequest.getDatatransfer());
        holder.trtransfer1.setText(transferOrderRequest.getTitle());
        holder.trcoin1.setText(transferOrderRequest.getAmount());

        if (transferOrderRequest.getDatatransfer().equals("") || transferOrderRequest.getDatatransfer() == null) {
            holder.tranferdata1.setVisibility(View.GONE);
        } else {
            holder.tranferdata1.setVisibility(View.VISIBLE);
            holder.tranferdata1.setText(transferOrderRequest.getDatatransfer());
        }
        if (Objects.equals(transferOrderRequest.getStatus(), "returned")) {
            holder.trimage1.setImageResource(R.drawable.ic_component_3__1_);
        } else {
            if (Objects.equals(transferOrderRequest.getStatus(), "failed")) {
                holder.trimage1.setImageResource(R.drawable.ic_felid);
            } else {
                if (Objects.equals(transferOrderRequest.getStatus(), "success")) {
                    holder.trimage1.setImageResource(R.drawable.ic_infosouc);
                }
            }
        }
        if (Objects.equals(transferOrderRequest.getAmount(), "")) {
            holder.trcoin1.setVisibility(View.GONE);
        } else {
            holder.trcoin1.setText(transferOrderRequest.getAmount() + " ASC");
        }

        holder.itemView.setOnClickListener(v -> {
            final BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.custombottomsheet);
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
                ClipData clip = ClipData.newPlainText(transferOrderRequest.getWallet_to(), transferOrderRequest.getWallet_to());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "copy wallet id", Toast.LENGTH_SHORT).show();
            });

            if (title != null) {
                title.setText(transferOrderRequest.getTitle());
            }
            if (bottchekfio != null) {
                bottchekfio.setText(transferOrderRequest.getFio());
            }
            if (bottchekamaunt != null) {
                bottchekamaunt.setText(bottchekamaunt.getText().toString());
            }
            if (bottchekcomment != null) {
                bottchekcomment.setText(transferOrderRequest.getComment());
            }
            if (bottchektype != null) {
                bottchektype.setText(transferOrderRequest.getType());
            }
            if (bottchekstatus != null) {
                if (Objects.equals(transferOrderRequest.getStatus(), "success")) {
                    bottchekstatus.setTextColor(Color.GREEN);
                } else {
                    bottchekstatus.setTextColor(Color.RED);
                }
                bottchekstatus.setText(transferOrderRequest.getStatus());
            }
            if (bottchekdate != null) {
                String chdata = transferOrderRequest.getDate().substring(0, 10)+" "+transferOrderRequest.getDate().substring(11, 16);
                String my_new_str = chdata.replace("-", "/");
                bottchekdate.setText(my_new_str);
            }
            if (bottchekwallet_to != null) {
                bottchekwallet_to.setText(transferOrderRequest.getWallet_to());
            }
            assert imgg != null;
            if (Objects.equals(transferOrderRequest.getStatus(), "returned")) {
                imgg.setImageResource(R.drawable.ic_component_3__1_);
                assert bottchekamaunt != null;
                if (Objects.equals(transferOrderRequest.getAmount(), "")) {
                    bottchekamaunt.setVisibility(View.GONE);
                } else {
                    bottchekamaunt.setText(transferOrderRequest.getAmount() + " ASC");
                }
            } else {
                if (Objects.equals(transferOrderRequest.getStatus(), "success")) {
                    imgg.setImageResource(R.drawable.ic_infosouc);
                    assert bottchekamaunt != null;
                    if (Objects.equals(transferOrderRequest.getAmount(), "")) {
                        bottchekamaunt.setVisibility(View.GONE);
                    } else {
                        bottchekamaunt.setText(transferOrderRequest.getAmount() + " ASC");
                    }
                } else {
                    if (Objects.equals(transferOrderRequest.getStatus(), "failed")) {
                        imgg.setImageResource(R.drawable.ic_felid);
                        assert bottchekamaunt != null;
                        if (Objects.equals(transferOrderRequest.getAmount(), "")) {
                            bottchekamaunt.setVisibility(View.GONE);
                        } else {
                            bottchekamaunt.setText(transferOrderRequest.getAmount() + " ASC");
                        }
                    } else {
                        imgg.setImageResource(R.drawable.ic_felid);
                        assert bottchekamaunt != null;
                        if (Objects.equals(transferOrderRequest.getAmount(), "")) {
                            bottchekamaunt.setVisibility(View.GONE);
                        } else {
                            bottchekamaunt.setText(transferOrderRequest.getAmount() + " ASC");
                        }
                    }
                }
            }
            dialog.show();
        });
    }
    @Override
    public int getItemCount() {
        return transferOrderArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView trtransfer1, trcoin1, tranferdata1;
        ImageView trimage1;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            trtransfer1 = itemView.findViewById(R.id.trtransfer1);
            trcoin1 = itemView.findViewById(R.id.trcoin1);
            tranferdata1 = itemView.findViewById(R.id.tranferdata1);
            trimage1 = itemView.findViewById(R.id.trimage1);
        }
    }
}