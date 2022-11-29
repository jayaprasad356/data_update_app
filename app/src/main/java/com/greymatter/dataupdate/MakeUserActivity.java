package com.greymatter.dataupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greymatter.dataupdate.adapters.UserAdapter;
import com.greymatter.dataupdate.helper.ApiConfig;
import com.greymatter.dataupdate.helper.Constant;
import com.greymatter.dataupdate.helper.Session;
import com.greymatter.dataupdate.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MakeUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private com.greymatter.dataupdate.adapters.UserAdapter UserAdapter;
    private CardView btnAddUser;
    private Activity activity;
    ImageView imgBack;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_user);


        recyclerView = findViewById(R.id.MakeUserRecycler);
        btnAddUser = findViewById(R.id.cvAddUser);
        imgBack = findViewById(R.id.imgBack);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activity = MakeUserActivity.this;
        session = new Session(activity);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.customdia);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().setDimAmount(0.3f);
        EditText etName = dialog.findViewById(R.id.etName);
        EditText etMobile = dialog.findViewById(R.id.etMobile);
        CardView cvMakeUser = dialog.findViewById(R.id.cvMakeUser);

        cvMakeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(etName.getText().toString().trim(),etMobile.getText().toString().trim(),dialog);
            }
        });
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

        userList();


    }

    public void userList()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.MANAGER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Users> users = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Users group = g.fromJson(jsonObject1.toString(), Users.class);
                                users.add(group);
                            } else {
                                break;
                            }
                        }
                        UserAdapter = new UserAdapter(users,activity);
                        recyclerView.setAdapter(UserAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, activity, Constant.USER_LIST_URL, params, true);

    }

    private void registerUser(String name, String mobile, Dialog dialog)
    {
        Map<String,String> params = new HashMap<>();
        params.put(Constant.MANAGER_ID,session.getData(Constant.ID));
        params.put(Constant.NAME,name.trim());
        params.put(Constant.MOBILE,mobile.trim());
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("ADD_USER",response);
            if(result) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean(Constant.SUCCESS)) {
                        userList();
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();


                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },activity, Constant.ADD_USER_URL,params,true);
    }
}