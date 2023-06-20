package com.release.basicbankingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

public class DBClass extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BakingAppDatabase.db";
    private static final int DATABASE_VERSION = 2;

    private static final String USER_TABLE = "UsersTable";
    private static final String ID_COL = "Id";
    private static final String NAME_COL = "Name";
    private static final String EMAIL_COL = "Email";
    private static final String CURRENT_BAL = "CurrentBalance";


    private static final String TRANSFERS_TABLE = "TransfersTable";
    private static final String TRANSACTION_ID_COL = "TransactionId";
    private static final String SENDER_NAME_COL = "Sender";
    private static final String RECEIVER_NAME_COL = "Receiver";
    private static final String AMOUNT_COL = "Amount";

    public DBClass(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Query1 = "CREATE TABLE " + USER_TABLE + " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_COL + " TEXT," +
                EMAIL_COL + " TEXT," +
                CURRENT_BAL + " INTEGER )";

        String Query2 = "CREATE TABLE " + TRANSFERS_TABLE + " (" +
                TRANSACTION_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SENDER_NAME_COL + " TEXT," +
                RECEIVER_NAME_COL + " TEXT," +
                AMOUNT_COL + " INTEGER )";

        db.execSQL(Query1);
        db.execSQL(Query2);

        CreateUsers("0","Aditya Sharma", "aditya@gmail.com", 10000, db);
        CreateUsers("1","Rohan Singh", "rohan@gmail.com", 15000, db);
        CreateUsers("2","Priyanka Jain", "priyanka@gmail.com", 12000, db);
        CreateUsers("3","Akash Kumar", "akash@gmail.com", 11000, db);
        CreateUsers("4","Shreya Mehta", "shreya@gmail.com", 18000, db);
        CreateUsers("5","Dhruv Gupta", "dhruv@gmail.com", 17000, db);
        CreateUsers("6","Riya Khanna", "riya@gmail.com", 13000, db);
        CreateUsers("7","Tanmay Kapoor", "tanmay@gmail.com", 19000, db);
        CreateUsers("8","Sahil Patel", "sahil@gmail.com", 20000, db);
        CreateUsers("9","Harsh Yadav", "harsh@gmail.com", 9900, db);

    }

    private void CreateUsers(String id, String name, String email, Integer amount, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COL, id);
        contentValues.put(NAME_COL, name);
        contentValues.put(EMAIL_COL, email);
        contentValues.put(CURRENT_BAL, amount);
        db.insert(USER_TABLE, null, contentValues);
    }

    public void Transaction(String sender, String receiver, Integer amount){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SENDER_NAME_COL, sender);
        contentValues.put(RECEIVER_NAME_COL, receiver);
        contentValues.put(AMOUNT_COL, amount);

        sqLiteDatabase.insert(TRANSFERS_TABLE, null, contentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<ArrayList<String>> transaction(String senderId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TRANSFERS_TABLE + " WHERE Sender=" + senderId, null);
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                ArrayList<String> a = new ArrayList<>();
                a.add(cursor.getString(2));
                a.add(cursor.getString(3));
                arrayList.add(a);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    public String balance(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE Id=" + id, null);
        String balance = "";
        if( cursor != null && cursor.moveToFirst() ){
            balance = Integer.toString(cursor.getInt(3));
            cursor.close();
        }
        return balance;
    }

    public ArrayList<ArrayList<String>> CustomerList() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE, null);

        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                ArrayList<String> a = new ArrayList<>();
                a.add(cursor.getString(0));
                a.add(cursor.getString(1));
                a.add(cursor.getString(2));
                a.add(Integer.toString(cursor.getInt(3)));
                arrayList.add(a);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    public void Update(String id, Integer balance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CURRENT_BAL, balance);
        db.update(USER_TABLE, contentValues, "Id=?", new String[]{id});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TRANSFERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }
}
