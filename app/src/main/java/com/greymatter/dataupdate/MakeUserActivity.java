package com.greymatter.dataupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.greymatter.dataupdate.Adapters.MakeUserAdapter;
import com.greymatter.dataupdate.Models.MakeUserModel;

import java.util.ArrayList;

public class MakeUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MakeUserAdapter makeUserAdapter;
    private ArrayList<MakeUserModel> makeUserModelArrayList;
    private CardView btnAddUser;
    private Context ctx;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_user);


        recyclerView = findViewById(R.id.MakeUserRecycler);
        btnAddUser = findViewById(R.id.cvAddUser);
        imgBack = findViewById(R.id.imgBack);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        makeUserModelArrayList = new ArrayList<>();
        ctx = MakeUserActivity.this;
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.customdia);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().setDimAmount(0.3f);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        dialog.findViewById(R.id.cvMakeUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        makeUserModelArrayList.add(new MakeUserModel("Prasad","9876543210","2000"));
        makeUserModelArrayList.add(new MakeUserModel("Prasad","9876543210","2000"));
        makeUserModelArrayList.add(new MakeUserModel("Prasad","9876543210","2000"));
        makeUserModelArrayList.add(new MakeUserModel("Prasad","9876543210","2000"));
        makeUserAdapter = new MakeUserAdapter(makeUserModelArrayList,ctx);
        recyclerView.setAdapter(makeUserAdapter);
    }
}