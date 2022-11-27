package com.greymatter.dataupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.greymatter.dataupdate.adapters.TransactionAdapter;
import com.greymatter.dataupdate.helper.Constant;
import com.greymatter.dataupdate.helper.Session;
import com.greymatter.dataupdate.models.Transactions;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    Activity activity;
    TransactionAdapter transactionAdapter;
    RecyclerView recyclerView;
    CardView cvAddTransaction,cvMakeUser;
    TextView tvName,tvNumber,tvEmail,tvProfileInitial;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activity = HomeActivity.this;
        session = new Session(activity);
        cvAddTransaction = findViewById(R.id.cvAddTransaction);
        cvMakeUser = findViewById(R.id.cvMakeUser);
        tvName = findViewById(R.id.tvName);
        tvProfileInitial = findViewById(R.id.tvProfileInitial);
        tvEmail = findViewById(R.id.tvEmail);
        tvNumber = findViewById(R.id.tvNumber);
        recyclerView = findViewById(R.id.RecyclerTransactions);

        String str = session.getData(Constant.NAME);
        char firstChar = str.charAt(0);

        tvProfileInitial.setText(firstChar + "");
        tvName.setText(session.getData(Constant.NAME));
        tvNumber.setText(session.getData(Constant.MOBILE));
        tvEmail.setText(session.getData(Constant.EMAIL));
        cvMakeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MakeUserActivity.class));
            }
        });
        ArrayList<Transactions> transactions = new ArrayList<>();
        transactions.add(new Transactions("Salary","22 may 2022","+100"));
        transactions.add(new Transactions("Salary","22 may 2022","+100"));
        transactions.add(new Transactions("Salary","22 may 2022","+100"));
        transactions.add(new Transactions("Salary","22 may 2022","+100"));
        transactionAdapter = new TransactionAdapter(transactions,activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(transactionAdapter);
        cvAddTransaction.setOnClickListener( v-> {
            startActivity(new Intent(activity,AddTransAction.class));
        });

    }

}