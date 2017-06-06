package com.example.pedrogarrido.mydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.inputmethodservice.Keyboard;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Garrido on 06/06/2017.
 */

public class DBAdapter {

    static final String KEY_ROWID = "_id";
    static final String KEY_USERNAME = "username";
    static final String KEY_PASSWORD = "password";
    static final String KEY_CONFPASSWORD = "confPassword";
    static final String KEY_EMAIL = "email";
    static final String KEY_TELEFONE = "telefone";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "utilizadores";
    static final Integer DATABASE_VERSION = 2;

    static final String DATABASE_CREATE = "create table utilizadores (_id integer primary key autoincrement, "
            + "username text not null, password text not null, confPassword text not null, "
            + "email text not null, telefone text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS utilizadores");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a user into the database---
    public long insertUser(String username, String password, String confPassword, String email, String telefone)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, username);
        initialValues.put(KEY_PASSWORD, password);
        initialValues.put(KEY_CONFPASSWORD, confPassword);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_TELEFONE, telefone);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular user---
    public boolean deleteUser(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the users---
    public Cursor getAllUsers()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_USERNAME, KEY_PASSWORD,
                KEY_CONFPASSWORD, KEY_EMAIL, KEY_TELEFONE}, null, null, null, null, null);
    }


    //---retrieves a particular user---
    public Cursor getUser(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_USERNAME, KEY_PASSWORD, KEY_CONFPASSWORD, KEY_EMAIL, KEY_TELEFONE}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a user---
    public boolean updateUser(long rowId, String username, String password, String confPassword, String email, String telefone)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_USERNAME, username);
        args.put(KEY_PASSWORD, password);
        args.put(KEY_CONFPASSWORD, confPassword);
        args.put(KEY_EMAIL, email);
        args.put(KEY_TELEFONE, telefone);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
