package com.sliding.navigator.sample.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlData extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String  DATABASE_NAME = "Astrum.db";
    private static final String  TABLE_NAME = "AstroCoin_Table";
    public static final String Col_1 = "lOGIN";
    public static final String Col_2 = "KEYS";
    public static final String Col_3 = "PASSWORD";
    public static final String Col_4 = "FINGERPRINT";

    public SqlData(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, lOGIN TEXT,KEYS TEXT,PASSWORD TEXT,FINGERPRINT TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }
    public Cursor oqish(){
        SQLiteDatabase db1 = this.getWritableDatabase();
        return db1.rawQuery("Select * from " + TABLE_NAME + " ORDER BY ID DESC LIMIT 1",null);
    }
    public Boolean ozgartir(String id,String login,String keys,String password,String finger) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_1,login);
        contentValues.put(Col_2,keys);
        contentValues.put(Col_3,password);
        contentValues.put(Col_4,finger);
        int result = sqLiteDatabase.update(TABLE_NAME,contentValues,"ID =?",new String[]{id});
        return result > 0;
    }
    public Boolean kiritish(String login,String keys,String password,String finger) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col_1,login);
            contentValues.put(Col_2,keys);
            contentValues.put(Col_3,password);
            contentValues.put(Col_4,finger);
            long result = db.insert(TABLE_NAME,null,contentValues);
            db.close();
        return result != -1;
    }
}
