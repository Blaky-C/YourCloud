package com.yourcloud.yourcloud.Model.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.yourcloud.yourcloud.Model.Bean.docBean;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * Created by ritchie-huang on 17-1-18.
 */

public class FindDocFile {

    public Context mContext;
    public String[] fileTypes;
    public List<IFlexible> fileList;

    public FindDocFile(Context mContext, String[] fileTypes){
        this.fileTypes = fileTypes;
        this.mContext = mContext;
        queryFiles();
    }

    public List<IFlexible> getDataList(){
        return fileList;
    }

    public void queryFiles(){
        fileList = new ArrayList<>();

        docBean docBean;
        String[] projection = new String[] {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };

        String selection = "";
        for (int i = 0; i < fileTypes.length; i++) {
            if(i!=0)
            {
                selection=selection+" OR ";
            }
            selection=selection+MediaStore.Files.FileColumns.DATA+" LIKE '%"
                    +fileTypes[i]+"'";
        }
        Log.e("selection", selection);
        String sortOrder=MediaStore.Files.FileColumns.DATE_MODIFIED;

        Cursor cursor = mContext.getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                selection,
                null,
                sortOrder);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                int idindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns._ID);
                int dataindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns.DATA);
                int sizeindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns.SIZE);
                do {
                    docBean = new docBean();
                    String id = cursor.getString(idindex);
                    String path = cursor.getString(dataindex);
                    String size = cursor.getString(sizeindex);
                    int dot=path.lastIndexOf("/");
                    String name=path.substring(dot+1);
                    docBean.setId(id);
                    docBean.setName(name);
                    docBean.setPath(path);
                    docBean.setSize(size);
                    fileList.add(docBean);

                    Log.e("test", name);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }
}
