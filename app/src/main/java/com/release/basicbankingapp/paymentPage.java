package com.release.basicbankingapp;

import androidx.annotation.NonNull;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class paymentPage extends Dialog {

    private Context a;
    private String sender;
    private String balance;
    private String receiver;
    private ImageView animate;
    private DBClass db;
    public paymentPage(@NonNull Context context, String sender, String balance, String receiver, ImageView animate) {
        super(context);
        this.a = context;
        this.sender = sender;
        this.balance = balance;
        this.receiver = receiver;
        this.animate = animate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_payment_page);
        Button transfer = findViewById(R.id.transfer);
        final EditText amount = findViewById(R.id.edit_text);

        db = new DBClass(a);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int transfer = Integer.parseInt(amount.getText().toString());
                int curBal = Integer.parseInt(balance);
                if(transfer>curBal){
                    amount.setError("Not enough balance");
                    amount.requestFocus();
                }
                else {
                    showAnimation();
                    Toast.makeText(a, "Money transferred successfully!! ", Toast.LENGTH_SHORT).show();
                    db.Transaction(sender, receiver, transfer);
                    balance = Integer.toString(curBal-transfer);
                    db.Update(sender, curBal-transfer);
                    dismiss();
                }
            }
        });

    }

    private void showAnimation(){
        animate.setVisibility(View.VISIBLE);
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                animate.setVisibility(View.GONE);
            }
        }.start();
    }

}