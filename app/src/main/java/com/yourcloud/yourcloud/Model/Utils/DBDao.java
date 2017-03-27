package com.yourcloud.yourcloud.Model.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yourcloud.yourcloud.Model.Items.CommnFileItem;
import com.yourcloud.yourcloud.Model.Items.HeaderItem;
import com.yourcloud.yourcloud.Model.Items.SimpleItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * Created by huangrui on 2017/3/19.
 */

public class DBDao {
    DBHelper mDBHelper;
    private static DBDao mDBDao;

    public static DBDao getInstance(Context context) {
        if (mDBDao == null) {
            mDBDao = new DBDao(context);
        }
        return mDBDao;
    }

    public DBDao(Context mContext) {
        mDBHelper = new DBHelper(mContext);
    }

    public void addFile(String fileId, String fileType, String fileName, String fileSize, String filePath) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String INSERT_DATA = "insert into file_info values(?,?,?,?,?);";
        db.execSQL(INSERT_DATA, new String[]{fileId, fileType, fileName, fileSize, filePath});
    }

    public void deleteFile(String fileName) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String DELETE_DATA = "delete from file_info where file_name=?";
        db.execSQL(DELETE_DATA, new String[]{fileName});
    }


    public void clear() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String DELETE_DATA = "delete from file_info";
        db.execSQL(DELETE_DATA);
    }


    public void deleteFiles(List<String> fileNames) {
        for (String fileName : fileNames) {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            String DELETE_DATA = "delete from file_info where file_name=?";
            db.execSQL(DELETE_DATA, new String[]{fileName});
        }

    }

    public List<AbstractFlexibleItem> getFileList(String fileType) {
        Map<String, String> map = Constant.FILE_TYPE_MAP;
        String QUERY_DATA = "select * from file_info where file_type=?";
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_DATA, new String[]{fileType});
        if (cursor.getCount() > 0) {
            List<AbstractFlexibleItem> list = new ArrayList<>(cursor.getCount());
            HeaderItem headerItem = new HeaderItem(fileType);
            String type = map.get(fileType);
            String num = String.valueOf(cursor.getCount());
            headerItem.setTitle(type);
            headerItem.setSubtitle(num);

            while (cursor.moveToNext()) {
                CommnFileItem item = new SimpleItem(headerItem);
                String fileId = cursor.getString(cursor.getColumnIndex("file_id"));
                String fileName = cursor.getString(cursor.getColumnIndex("file_name"));
                String fileSize = cursor.getString(cursor.getColumnIndex("file_size"));
                String filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                item.setId(fileId);
                item.setName(fileName);
                item.setPath(filePath);
                item.setSize(fileSize);
                list.add(item);
            }
            cursor.close();
            db.close();
            return list;
        } else {
            List<AbstractFlexibleItem> list = new ArrayList<>(1);
            return list;
        }
    }


}
