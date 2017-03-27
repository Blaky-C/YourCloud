package com.yourcloud.yourcloud.Model.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huangrui on 2017/3/19.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "yourCloud.db";
    private static String T_FILE = "create table file_info(" +
            "file_id varchar2(50) primary key," +
            "file_type varchar2(50)," +
            "file_name varchar2(100)," +
            "file_size varchar2(50)," +
            "file_path varchar2(200)" +
            ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 2);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(T_FILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i == 1) {
            sqLiteDatabase.execSQL("drop table if exists file_info");
            onCreate(sqLiteDatabase);
        }
    }
}
