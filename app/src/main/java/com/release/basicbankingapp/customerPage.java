package com.release.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class customerPage extends AppCompatActivity {

    private DBClass dbClass;
    private String id;
    private ArrayList<ArrayList<String>> arr;
    private ArrayList<User> arrayList;
    private arrayAdapter adapter;
    private ListView lastTran;
    private String b;
    private String s;
    private String email;
    private TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);

        TextView name = findViewById(R.id.customerName);
        balance = findViewById(R.id.balance);
        Button transfer = findViewById(R.id.transfer);
        TextView emailId = findViewById(R.id.email);

        lastTran = findViewById(R.id.lastTran);

        dbClass = new DBClass(this);

        Intent i = getIntent();
        s = i.getStringExtra("User");
        b = i.getStringExtra("balance");
        id  = i.getStringExtra("id");
        email = i.getStringExtra("email");

        update();

        name.setText(s);
        emailId.setText(email);

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customerPage.this, chooseCustomerPage.class);
                intent.putExtra("User", id);
                intent.putExtra("balance", b);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        update();
    }

    public String addChar(String str, char ch, int position) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return sb.toString();
    }

    public void update(){
        arrayList = new ArrayList<>();
        arr = new ArrayList<>();
        arr = dbClass.transaction(id);
        for(int k=arr.size()-1; k>=0 && k>=arr.size()-5; k--){
            arrayList.add(new User(arr.get(k).get(0), arr.get(k).get(1)));
        }
        adapter = new arrayAdapter(arrayList, this);
        lastTran.setAdapter(adapter);
        b = dbClass.balance(id);
        String x = b;

        for(int j = b.length()-4; j>=0; j-=3){
            x = addChar(x, ',', j+1);
        }

        String k = "Rs. " +  x;
        balance.setText(k);
    }

}
