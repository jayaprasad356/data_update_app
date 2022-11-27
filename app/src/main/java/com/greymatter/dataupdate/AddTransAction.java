package com.greymatter.dataupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greymatter.dataupdate.adapters.UserSpinAdapter;
import com.greymatter.dataupdate.helper.ApiConfig;
import com.greymatter.dataupdate.helper.Constant;
import com.greymatter.dataupdate.helper.Session;
import com.greymatter.dataupdate.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddTransAction extends AppCompatActivity {


    private ImageView imback;
    Activity activity;
    Session session;
    Users[] users;
    UserSpinAdapter adapter;
    String UserId = "";
    Spinner spinUser;
    CardView cvTransfer;
    EditText etAmount,etRemark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trans_action);
        activity = AddTransAction.this;
        session = new Session(activity);
        imback = findViewById(R.id.imgBack);
        spinUser = findViewById(R.id.spinUser);
        cvTransfer = findViewById(R.id.cvTransfer);
        etAmount = findViewById(R.id.etAmount);
        etRemark = findViewById(R.id.etRemark);
        imback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTransAction.this,HomeActivity.class));
                finish();
            }
        });
        cvTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTransaction();
            }
        });
        userList();
    }

    private void addTransaction() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.MANAGER_ID, session.getData(Constant.ID));
        params.put(Constant.USER_ID, UserId);
        params.put(Constant.AMOUNT,etAmount.getText().toString().trim());
        params.put(Constant.REMARKS, etRemark.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity,HomeActivity.class);
                        startActivity(intent);
                       finish();

                    }else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();



                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, activity, Constant.ADD_TRANSACTION_URL, params, true);
    }

    private void userList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.MANAGER_ID, session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        users = new Users[jsonArray.length()];

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                users[i] = new Users();
                                users[i].setId(jsonObject1.getString(Constant.ID));
                                users[i].setName(jsonObject1.getString(Constant.NAME));
                            } else {
                                break;
                            }
                        }
                        adapter = new UserSpinAdapter(activity, android.R.layout.simple_spinner_item, users);
                        spinUser.setAdapter(adapter); // Set the custom adapter to the spinner
                        spinUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view,
                                                       int position, long id) {
                                // Here you get the current item (a User object) that is selected by its position
                                Users users = adapter.getItem(position);
                                UserId = users.getId();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapter) {
                            }
                        });


                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, activity, Constant.USER_LIST_URL, params, true);
    }

}