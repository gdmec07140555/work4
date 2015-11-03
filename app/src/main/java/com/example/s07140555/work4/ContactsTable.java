package com.example.s07140555.work4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.apache.http.auth.NTUserPrincipal;

import java.util.Vector;

/**
 * Created by Administrator on 2015/11/1 0001.
 */
public class ContactsTable {
    private final static String TABLENAME="contatcsTable";
    private MyDB db;
    public ContactsTable(Context context){
        db=new MyDB(context);
        if (!db.isTableExists(TABLENAME)){
            String createTableSql="CREATE TABLE IF NOT EXISTS"+
                    TABLENAME+"(id_DB integer primary key AUTOINCREMENT,"+
                    User.NAME+"VARCHAR,"+
                    User.MOBILE+"VARCHAR,"+
                    User.DANWEI+"VARCHAR,"+
                    User.QQ+"VARCHAR,"+
                    User.ADRESS+"VARCHAR)";
            db.createTable(createTableSql);
        }
    }
    public boolean addDate(User user){
        ContentValues values=new ContentValues();
        values.put(User.NAME,user.getName());
        values.put(User.MOBILE,user.getMobile());
        values.put(User.DANWEI,user.getDanwei());
        values.put(User.QQ,user.getQq());
        values.put(User.ADRESS,user.getAdress());
        return db.save(TABLENAME,values);
    }
    public User[] getAlUser(){
        Vector<User> V=new Vector<User>();
        Cursor cursor=null;
        try {
            cursor=db.find("select * from"+TABLENAME,null);
            while (cursor.moveToNext()){
                User temp=new User();
                temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
                temp.setAdress(cursor.getString(cursor.getColumnIndex(User.NAME)));
                temp.setDanwei(cursor.getString(cursor.getColumnIndex(User.DANWEI)));
                temp.setAdress(cursor.getString(cursor.getColumnIndex(User.ADRESS)));
                temp.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
                temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
                temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
                V.add(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor !=null){
                cursor.close();
            }
            db.closeConnection();
        }
        if (V.size()>0){
            return V.toArray(new User[]{});
        }else {
            User[] users=new User[1];
            User user=new User();
            user.setName("无结果");
            users[0]=user;
            return users;
        }
    }
    //根据联系人到ID来获取联系人
    public User getUserByID(int id){
        Cursor cursor=null;
        try {
            cursor=db.find("select * from"+TABLENAME+"where id_DB=?",new String[]{id+""});
            User temp=new User();
            cursor.moveToNext();
            temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
            temp.setAdress(cursor.getString(cursor.getColumnIndex(User.NAME)));
            temp.setDanwei(cursor.getString(cursor.getColumnIndex(User.DANWEI)));
            temp.setAdress(cursor.getString(cursor.getColumnIndex(User.ADRESS)));
            temp.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
            temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
            temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
            return temp;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor !=null){
                cursor.close();
            }
            db.closeConnection();
        }
        return null;
    }
    //更新联系人信息
    public boolean updateUser(User user){
        ContentValues values=new ContentValues();
        values.put(User.NAME,user.getName());
        values.put(User.MOBILE,user.getMobile());
        values.put(User.DANWEI,user.getDanwei());
        values.put(User.QQ,user.getQq());
        values.put(User.ADRESS,user.getAdress());
        return db.update(TABLENAME, values, "id_db=?", new String[]{user.getId_DB() + ""});
    }
    //根据特定条件查询联系人信息
    public User[] findUserByKey(String key){
        Vector<User> V=new Vector<>();
        Cursor cursor=null;
        try {
            cursor=db.find("select * from"+TABLENAME+"where"+User.NAME+"like'%"+key+"%'"+"or"+User.MOBILE+"like'%"+key+"%'"+"or"+User.QQ+"like'%"+key+"%'",null);
            while (cursor.moveToNext()){
                User temp=new User();
                temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
                temp.setAdress(cursor.getString(cursor.getColumnIndex(User.NAME)));
                temp.setDanwei(cursor.getString(cursor.getColumnIndex(User.DANWEI)));
                temp.setAdress(cursor.getString(cursor.getColumnIndex(User.ADRESS)));
                temp.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
                temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
                temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
                V.add(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor !=null){
                cursor.close();
            }
            db.closeConnection();
        }
        if (V.size()>0){
            return V.toArray(new User[]{});
        }else {
            User[] users=new User[1];
            User user=new User();
            user.setName("无结果");
            users[0]=user;
            return users;
        }
    }
    //删除联系人
    public boolean deleteByUser(User user){
        return db.delete(TABLENAME,"id_db=?", new String[]{user.getId_DB() + ""});
    }
}
