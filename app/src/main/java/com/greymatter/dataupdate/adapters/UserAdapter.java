package com.greymatter.dataupdate.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.greymatter.dataupdate.MakeUserActivity;
import com.greymatter.dataupdate.helper.ApiConfig;
import com.greymatter.dataupdate.helper.Constant;
import com.greymatter.dataupdate.helper.Session;
import com.greymatter.dataupdate.models.Users;
import com.greymatter.dataupdate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder> {


    private ArrayList<Users> usersArrayList;
    private Activity ctx;
    private Session session;
    public UserAdapter(ArrayList<Users> usersArrayList, Activity ctx) {
        this.usersArrayList = usersArrayList;
        this.ctx = ctx;
        session= new Session(ctx);
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.make_user_recycler,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
         Users model = usersArrayList.get(position);
        AlphaAnimation animation = new AlphaAnimation(0.0f,1.0f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        EditText TempEtName,TempEtMobile,TempEtExpenses;
        Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.customdia);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().setDimAmount(0.3f);
        TempEtName = dialog.findViewById(R.id.etName);
        TempEtMobile = dialog.findViewById(R.id.etMobile);
        TempEtExpenses = dialog.findViewById(R.id.etExpense);
        TempEtMobile.setText(model.getMobile());
        TempEtName.setText(model.getName());
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                dialog.findViewById(R.id.cvMakeUser).setOnClickListener(new View.OnClickListener() {
                    private EditText TempEtName,TempEtNumber,TempEtExpenses;
                    @Override
                    public void onClick(View v) {
                        TempEtName = dialog.findViewById(R.id.etName);
                        TempEtExpenses = dialog.findViewById(R.id.etExpense);
                        TempEtNumber = dialog.findViewById(R.id.etMobile);
                        UpdateDataToApi(TempEtName.getText().toString().trim(),TempEtNumber.getText().toString().trim(),model.getId(),TempEtExpenses.getText().toString().trim());
                        dialog.dismiss();

                      //  Toast.makeText(ctx, "Update done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.Name.setText(model.getName());
        holder.PhoneNumber.setText(model.getMobile());
        holder.Balance.setText("₹"+model.getBalance());

        holder.btndelete.setOnClickListener(v -> alterDialog(model.getId()));
    }

    private void alterDialog(String id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        builder.setMessage("Do you want to Delete ?");
        builder.setTitle("");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

            Deleteuser(id);
            Intent intent = new Intent(ctx, MakeUserActivity.class);
            ctx.startActivity(intent);
            Toast.makeText(ctx, "Deleted", Toast.LENGTH_SHORT).show();
            ctx.finish();



        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {

            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private ImageView editBtn,btndelete;
        private TextView Name,PhoneNumber,Balance;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            editBtn = itemView.findViewById(R.id.btnedit);
            Name = itemView.findViewById(R.id.tvName);
            PhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            Balance = itemView.findViewById(R.id.tvBalance);
            btndelete = itemView.findViewById(R.id.btndelete);
        }
    }

    private void UpdateDataToApi(String name,String number,String user_id,String Expenses) {
        Map<String,String> params = new HashMap<>();
        params.put(Constant.USER_ID,user_id);
        params.put(Constant.NAME,name);
        params.put(Constant.MOBILE,number);
        params.put(Constant.EXPENSES,Expenses);
        ApiConfig.RequestToVolley((result, response) -> {
            if(result) {
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getBoolean(Constant.SUCCESS)) {
                        Intent intent = new Intent(ctx, MakeUserActivity.class);
                        ctx.startActivity(intent);
                        Toast.makeText(ctx, "Update done", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ctx, object.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },ctx,Constant.UPDATE_USER_URL,params,true);
    }
    private void Deleteuser(String id) {
        Map<String,String> params = new HashMap<>();
        params.put(Constant.USER_ID,id);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("delete",response);
            if(result) {
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getBoolean(Constant.SUCCESS)) {

                    }else{

                        Toast.makeText(ctx, object.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },ctx,Constant.DELETE_USER,params,true);
    }
}
