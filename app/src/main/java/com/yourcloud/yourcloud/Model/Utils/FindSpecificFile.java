package com.yourcloud.yourcloud.Model.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.yourcloud.yourcloud.Model.Items.AbstractItem;
import com.yourcloud.yourcloud.Model.Items.HeaderItem;
import com.yourcloud.yourcloud.Model.Items.SimpleItem;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * Created by ritchie-huang on 17-1-18.
 */

public class FindSpecificFile {


    public static FindSpecificFile mInstance;
    public Context mContext;
    public String[] fileTypes;
    public List<AbstractFlexibleItem> fileList;
    public HeaderItem headerItem;


    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public FindSpecificFile() {
    }

    public FindSpecificFile(String[] fileTypes) {
        this.fileTypes = fileTypes;
        queryFiles();
    }

    public FindSpecificFile(Context mContext, String[] fileTypes, HeaderItem headerItem) {
        this.headerItem = headerItem;
        this.mContext = mContext;
        this.fileTypes = fileTypes;
        queryFiles();
    }

    public List<AbstractFlexibleItem> getDataList() {
        return fileList;
    }


    public void queryFiles() {
        fileList = new ArrayList<>();

        AbstractItem AbstractItem;
        int index = 0;
        String[] projection = new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };

        String selection = "";
        for (int i = 0; i < fileTypes.length; i++) {
            if (i != 0) {
                selection = selection + " OR ";
            }
            selection = selection + MediaStore.Files.FileColumns.DATA + " LIKE '%"
                    + fileTypes[i] + "'";
        }
        Log.e("selection", selection);
        String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;

        Cursor cursor = mContext.getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                selection,
                null,
                sortOrder);
        String docFileNumbers = String.valueOf(cursor.getCount());
        headerItem.setSubtitle(docFileNumbers);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                int idindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns._ID);
                int dataindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns.DATA);
                int sizeindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns.SIZE);
                do {
                    AbstractItem = new SimpleItem(headerItem);

                    String id = cursor.getString(idindex);
                    String path = cursor.getString(dataindex);
                    String size = Utils.FormatFileSize(Long.parseLong(cursor.getString(sizeindex)));
                    int dot = path.lastIndexOf("/");
                    String name = path.substring(dot + 1);
                    AbstractItem.setId(id);
                    AbstractItem.setName(name);
                    AbstractItem.setPath(path);
                    AbstractItem.setSize(size);
                    fileList.add(AbstractItem);

                    Log.e("test", name);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }
}
