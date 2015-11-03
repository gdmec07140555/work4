package com.example.s07140555.work4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.SQLException;

/**
 * Created by Administrator on 2015/11/1 0001.
 */
public class MyDB extends SQLiteOpenHelper {
    private static String DB_NAME="MyDB.db";
    private static int DB_VERSION=2;
    private SQLiteDatabase db;
    public MyDB(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        db=getWritableDatabase();
    }
    public SQLiteDatabase openConnection(){
        if (!db.isOpen()){
            db=getWritableDatabase();
        }
        return db;
    }
    public void closeConnection(){
        try {
            if (db!=null&&db.isOpen())
                db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean createTable(String createTableSql){
        try {
            openConnection();
            db.execSQL(createTableSql);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
    public boolean save(String tableName,ContentValues values){
        try {
            openConnection();
            db.insert(tableName, null, values);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;

    }
    public boolean update(String table,ContentValues values,String whereClause,String whereArgs[]){
        try {
            openConnection();
            update(table, values, whereClause, whereArgs);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
    public boolean delete (String table,String deletesql,String obj[]){
        try {
            openConnection();
            db.delete(table, deletesql, obj);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            closeConnection();
        }
        return true;
    }
    public Cursor find(String findsql,String obj[]){
        try {
            openConnection();
            Cursor cursor=db.rawQuery(findsql,obj);
            return cursor;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean isTableExists (String tableName){
        try {
            openConnection();
            String str="select count(*)xcount from "+tableName;
            db.rawQuery(str,null).close();
        }catch (SQLException e){
            return false;
        }
        return true;
    }
    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, int i, int i1) {

    }
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }
}
