package com.greymatter.dataupdate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greymatter.dataupdate.models.Transactions;
import com.greymatter.dataupdate.R;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.viewholder> {

    private ArrayList<Transactions> transactionModelsArrayList;
    private Context ctx;


    public TransactionAdapter(ArrayList<Transactions> transactionModelsArrayList, Context ctx) {
        this.transactionModelsArrayList = transactionModelsArrayList;
        this.ctx = ctx;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx)
                .inflate(R.layout.transactionrecycler,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Transactions model = transactionModelsArrayList.get(position);
        holder.tvTransactionType.setText(model.getTransactionName());
        holder.TvTransactionAmount.setText(model.getTransactionAmount());
        holder.TvTransactionDate.setText(model.getTransactionDate());
    }

    @Override
    public int getItemCount() {
        return transactionModelsArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private TextView tvTransactionType;
        private TextView TvTransactionDate;
        private TextView TvTransactionAmount;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tvTransactionType = itemView.findViewById(R.id.tvTransactionType);
            TvTransactionDate = itemView.findViewById(R.id.tvTransactionAmountDate);
            TvTransactionAmount = itemView.findViewById(R.id.tvTransactionAmount);
        }
    }
}
