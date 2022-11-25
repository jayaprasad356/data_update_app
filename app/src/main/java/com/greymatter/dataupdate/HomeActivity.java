package com.greymatter.dataupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.greymatter.dataupdate.Adapters.TransactionAdapter;
import com.greymatter.dataupdate.Models.TransactionModel;
import com.greymatter.dataupdate.Services.FloatingService;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    private ArrayList<TransactionModel> transactionModelsArrayList;
    private Context ctx;
    private TransactionAdapter transactionAdapter;
    private RecyclerView recyclerView;
    private CardView cvAddTransaction;
    private TextView tvProfileName,tvName,tvNumber,tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startService(new Intent(getApplicationContext(), FloatingService.class));


        cvAddTransaction = findViewById(R.id.cvAddTransaction);
        tvProfileName = findViewById(R.id.tvProfileInitial);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvNumber = findViewById(R.id.tvNumber);
        recyclerView = findViewById(R.id.RecyclerTransactions);
        findViewById(R.id.cvMakeUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MakeUserActivity.class));
            }
        });
        ctx = HomeActivity.this;
        transactionModelsArrayList = new ArrayList<>();
        transactionModelsArrayList.add(new TransactionModel("Salary","22 may 2022","+100"));
        transactionModelsArrayList.add(new TransactionModel("Salary","22 may 2022","+100"));
        transactionModelsArrayList.add(new TransactionModel("Salary","22 may 2022","+100"));
        transactionModelsArrayList.add(new TransactionModel("Salary","22 may 2022","+100"));
        transactionAdapter = new TransactionAdapter(transactionModelsArrayList,ctx);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.setAdapter(transactionAdapter);

        cvAddTransaction.setOnClickListener( v-> {
            startActivity(new Intent(this,AddTransAction.class));
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getApplicationContext(),FloatingService.class));
    }
}