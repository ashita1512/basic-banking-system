package com.release.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class allCustomersPage extends AppCompatActivity {

    private DBClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_page);

        final ListView listView = findViewById(R.id.listView);

        ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>();

        db = new DBClass(this);

        ArrayList<String> name = new ArrayList<>();
        final ArrayList<String> balance = new ArrayList<>();
        final ArrayList<String> idx = new ArrayList<>();
        final ArrayList<String> email = new ArrayList<>();

        arrayList = db.CustomerList();
        //Toast.makeText(this, arrayList.get(1).get(0), Toast.LENGTH_SHORT).show();
        for(int i=0; i<arrayList.size(); i++){
            idx.add(arrayList.get(i).get(0));
            name.add(arrayList.get(i).get(1));
            balance.add(arrayList.get(i).get(3));
            email.add(arrayList.get(i).get(2));
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, name);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(allCustomersPage.this, customerPage.class);
                i.putExtra("User", listView.getItemAtPosition(position).toString());
                i.putExtra("id", idx.get(position));
                i.putExtra("balance", balance.get(position));
                i.putExtra("email", email.get(position));
                startActivity(i);
            }
        });
    }
}
