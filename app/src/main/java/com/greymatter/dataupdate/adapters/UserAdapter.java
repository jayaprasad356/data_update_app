package com.greymatter.dataupdate.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greymatter.dataupdate.models.Users;
import com.greymatter.dataupdate.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder> {


    private ArrayList<Users> usersArrayList;
    private Context ctx;
    public UserAdapter(ArrayList<Users> usersArrayList, Context ctx) {
        this.usersArrayList = usersArrayList;
        this.ctx = ctx;
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
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ctx);
                dialog.setContentView(R.layout.customdia);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                dialog.show();
                dialog.getWindow().setDimAmount(0.3f);
                dialog.findViewById(R.id.cvMakeUser).setOnClickListener(new View.OnClickListener() {
                    private EditText TempEtName,TempEtNumber,TempEtAmount;
                    @Override
                    public void onClick(View v) {
                        TempEtName = dialog.findViewById(R.id.etName);
                        TempEtAmount = dialog.findViewById(R.id.etAmount);
                        TempEtNumber = dialog.findViewById(R.id.etMobile);
                        dialog.dismiss();
                        Toast.makeText(ctx, "Update done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.Name.setText(model.getName());
        holder.PhoneNumber.setText(model.getMobile());
        holder.Balance.setText(model.getBalance());
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private ImageView editBtn;
        private TextView Name,PhoneNumber,Balance;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            editBtn = itemView.findViewById(R.id.btnedit);
            Name = itemView.findViewById(R.id.tvName);
            PhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            Balance = itemView.findViewById(R.id.tvBalance);
        }
    }
}
