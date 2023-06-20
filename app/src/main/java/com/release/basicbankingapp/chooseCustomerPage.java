package com.release.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class chooseCustomerPage extends AppCompatActivity {

    private DBClass db;
    ArrayList<String> arr;
    ArrayList<ArrayList<String>> arrayList;
    paymentPage page;
    ImageView animate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_customer_page);

        Intent intent = getIntent();
        final String sender = intent.getStringExtra("User");
        final String balance = intent.getStringExtra("balance");

        animate  = findViewById(R.id.animate);
        animate.setVisibility(View.GONE);

        Glide.with(chooseCustomerPage.this).load(R.drawable.animate).into(animate);
        final ListView listView = findViewById(R.id.listView);

        arrayList = new ArrayList<>();

        db = new DBClass(this);

        arr = new ArrayList<>();
        arrayList = db.CustomerList();

        for(int i=0; i<arrayList.size(); i++){
            if(arrayList.get(i).get(0).equals(sender)) continue;
            arr.add(arrayList.get(i).get(1));
        }



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arr);

        listView.setAdapter(arrayAdapter);
        final int cur_bal = Integer.parseInt(balance);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                page = new paymentPage(chooseCustomerPage.this, sender, balance, listView.getItemAtPosition(position).toString(), animate);
                page.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int displayWidth = displayMetrics.widthPixels;
                int displayHeight = displayMetrics.heightPixels;
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(page.getWindow().getAttributes());
                int dialogWindowWidth = (int) (displayWidth * 0.9f);
                int dialogWindowHeight = (int) (displayHeight * 0.4f);
                layoutParams.width = dialogWindowWidth;
                layoutParams.height = dialogWindowHeight;
                page.getWindow().setAttributes(layoutParams);
            }
        });
    }

}