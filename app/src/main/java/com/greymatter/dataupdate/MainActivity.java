package com.greymatter.dataupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class MainActivity extends AppCompatActivity {

    private OtpTextView pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pin = findViewById(R.id.pin);

        pin.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                if(pin.getOTP().equals("0000")) {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Wrong pin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}