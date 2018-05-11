package com.example.note.seoulddok.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.example.note.seoulddok.Contact;
import com.example.note.seoulddok.Model.RecvData;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private Context context;

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void createTableMobile (){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS  MOBILE (id INTEGER PRIMARY KEY AUTOINCREMENT , TIME TEXT NOT NULL , MESSAGE TEXT NOT NULL)");
        db.close();

    }
    public void createTableCar (){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS  CAR (id INTEGER PRIMARY KEY AUTOINCREMENT , TIME TEXT NOT NULL , MESSAGE TEXT NOT NULL)");
        db.close();
    }

    public void insertMobileData(String time  , String msg){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO MOBILE (TIME,MESSAGE) VALUES ('"+time+"','"+msg+"')");
    }

    public ArrayList<RecvData> getRecvData(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecvData> recvData = new ArrayList<RecvData>();

        Cursor cursor = db.rawQuery("select * from MOBILE", null);
        while (cursor.moveToNext()){
            recvData.add(new RecvData("mobile",cursor.getString(0),cursor.getString(1),cursor.getString(2)));
        }
        return recvData;
    }

    public void insert(String _query){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();

    }
}
