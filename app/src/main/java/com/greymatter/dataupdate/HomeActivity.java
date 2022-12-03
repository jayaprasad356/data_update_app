package com.greymatter.dataupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greymatter.dataupdate.adapters.TransactionAdapter;
import com.greymatter.dataupdate.adapters.UserAdapter;
import com.greymatter.dataupdate.helper.ApiConfig;
import com.greymatter.dataupdate.helper.Constant;
import com.greymatter.dataupdate.helper.Session;
import com.greymatter.dataupdate.models.Transactions;
import com.greymatter.dataupdate.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {


    Activity activity;
    TransactionAdapter transactionAdapter;
    RecyclerView recyclerView;
    CardView cvAddTransaction, cvMakeUser;
    TextView tvName, tvNumber, tvEmail, tvProfileInitial, totalBalance;
    Session session;
    Button btnSave;
    final Calendar myCalendar = Calendar.getInstance();
    TextView editText, tvAmount, tvTotal;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    EditText etneed;
    String s1, s2;

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
        btnSave = findViewById(R.id.btnSave);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        editText = findViewById(R.id.tvDate);
        etneed = findViewById(R.id.etneed);

        tvAmount = findViewById(R.id.tvAmount);
        tvTotal = findViewById(R.id.tvTotal);


        btnSave.setOnClickListener(view -> saveData());

        etneed.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {


            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


            }

            @Override
            public void afterTextChanged(Editable arg0) {


                s1 = etneed.getText().toString();
                s2 = tvAmount.getText().toString();

                calculate();


            }
        });


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(calendar.getTime());
        editText.setText("" + date);

        String str = session.getData(Constant.NAME);
        char firstChar = str.charAt(0);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(HomeActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tvProfileInitial.setText(firstChar + "");
        tvName.setText(session.getData(Constant.NAME));
        tvNumber.setText(session.getData(Constant.MOBILE));
        tvEmail.setText(session.getData(Constant.EMAIL));
        cvMakeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MakeUserActivity.class));
            }
        });
        transactionList();

        cvAddTransaction.setOnClickListener(v -> {
            startActivity(new Intent(activity, AddTransAction.class));
        });

    }

    private void saveData() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.MANAGER_ID, session.getData(Constant.ID));
        params.put(Constant.DATE, editText.getText().toString().trim());
        params.put(Constant.AMOUNT, tvAmount.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("SAVE_DATA", response);
            if (result) {
                Toast.makeText(activity, "Data Saved Successfylly", Toast.LENGTH_SHORT).show();
            }
        }, activity, Constant.UPDATE_NEED_URL, params, true);
    }

    private void calculate() {


        try {
            int i1 = Integer.parseInt(s1);
            int i2 = Integer.parseInt(s2);
            int subtract = i1 - i2;
            String s = String.valueOf(subtract);
            tvTotal.setText(s + "₹");
        } catch (NumberFormatException e) {
        }

    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        editText.setText(dateFormat.format(myCalendar.getTime()));
        transactionList();

    }


    private void transactionList() {
        tvAmount.setText("0");
        ArrayList<Transactions> transactions1 = new ArrayList<>();
        transactionAdapter = new TransactionAdapter(transactions1, activity);
        recyclerView.setAdapter(transactionAdapter);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.MANAGER_ID, session.getData(Constant.ID));
        params.put(Constant.DATE, editText.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("TRANS_RES", response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        //  totalBalance.setText(totalBal);
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        String needAmout = jsonObject.getString("need_amount");
                        etneed.setText(needAmout);

                        Gson g = new Gson();
                        ArrayList<Transactions> transactions = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Transactions group = g.fromJson(jsonObject1.toString(), Transactions.class);
                                transactions.add(group);
                            } else {
                                break;
                            }
                        }
                        if (jsonArray.length() >= 1) {
                            tvAmount.setText(jsonObject.getString(Constant.GRAND_TOTAL));
                            transactionAdapter = new TransactionAdapter(transactions, activity);
                            recyclerView.setAdapter(transactionAdapter);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, activity, Constant.TRANSACTIONSLIST_URL, params, true);


    }


}