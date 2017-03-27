package com.yourcloud.yourcloud.Model.Utils;

import android.content.Context;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.kii.cloud.storage.KiiBucket;
import com.kii.cloud.storage.KiiObject;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiObjectCallBack;
import com.kii.cloud.storage.callback.KiiQueryCallBack;
import com.kii.cloud.storage.query.KiiQuery;
import com.kii.cloud.storage.query.KiiQueryResult;
import com.kii.cloud.storage.resumabletransfer.KiiRTransfer;
import com.kii.cloud.storage.resumabletransfer.KiiRTransferCallback;
import com.kii.cloud.storage.resumabletransfer.KiiUploader;
import com.yourcloud.yourcloud.Model.Items.CommnFileItem;
import com.yourcloud.yourcloud.Model.Items.SimpleItem;
import com.yourcloud.yourcloud.View.Fragment.UploadFileFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * Created by huangrui on 2017/2/8.
 */

public class KiiUtils {

    private KiiObject mKiiObject;
    private KiiBucket mKiiBucket;

    public KiiUtils() {

    }

    public KiiUtils(KiiBucket mKiiBucket) {
        this.mKiiBucket = mKiiBucket;
    }


    public void queryObject() {

        KiiUser user = KiiUser.getCurrentUser();

        KiiBucket myBucket = user.bucket(user.getID() + "_bucket");
        KiiQuery all_query = new KiiQuery();

        myBucket.query(new KiiQueryCallBack<KiiObject>() {
            @Override
            public void onQueryCompleted(int token, KiiQueryResult<KiiObject> result, Exception exception) {
                if (exception != null) {
                    // Error handling
                    return;
                }

                List<AbstractFlexibleItem> formatList = new ArrayList<>();
                List<KiiObject> objLists = result.getResult();
                for (KiiObject obj : objLists) {
                    CommnFileItem item = new SimpleItem();
                    item.setId(obj.getString("_id"));
                    item.setName(obj.getString("Name"));
                    item.setSize(obj.getString("Size"));
                    Uri uri = obj.toUri();
                    String path = "kiicloud://users" + uri.getPath();
                    item.setPath(path);
                    formatList.add(item);
                }

                Constant.cloudFileList = formatList;
//                        Message message = Message.obtain();
//                        message.what = Constant.CLOUD_OBJECT;
//                        message.obj = formatList;

            }
        }, all_query);

    }


    public void UploadSingleItem(Context mContext, CommnFileItem item) {
        KiiUser user = KiiUser.getCurrentUser();

        if (user != null) {
            KiiBucket mKiiBucket = user.bucket(user.getID() + "_bucket");

            String fileName = item.getName();
            String fileSize = item.getSize();
            String filePath = item.getPath();
            String fileId = item.getId();

            mKiiObject = mKiiBucket.object(fileId);
            mKiiObject.set("Name", fileName);
            mKiiObject.set("Size", fileSize);
            mKiiObject.saveAllFields(new KiiObjectCallBack() {
                @Override
                public void onSaveCompleted(int token, @NonNull KiiObject object, @Nullable Exception exception) {
                    super.onSaveCompleted(token, object, exception);
                    File file = new File(filePath);
                    KiiUploader uploader = mKiiObject.uploader(mContext, file);
                    uploader.transferAsync(new KiiRTransferCallback() {
                        @Override
                        public void onStart(@NonNull KiiRTransfer operator) {
                            super.onStart(operator);
                        }

                        @Override
                        public void onTransferCompleted(@NonNull KiiRTransfer operator, @NonNull Exception e) {
                            super.onTransferCompleted(operator, e);
                            queryObject();
                            Toast.makeText(mContext, fileName + "上传完成", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onProgress(@NonNull KiiRTransfer operator, long completedInBytes, long totalSizeinBytes) {
                            super.onProgress(operator, completedInBytes, totalSizeinBytes);

                        }
                    });

                }
            }, true);
        } else {
            Toast.makeText(mContext, "没有该用户", Toast.LENGTH_SHORT).show();

        }
    }


    public void Upload(Context mContext, List<AbstractFlexibleItem> list) {
        KiiUser user = KiiUser.getCurrentUser();


        if (user != null) {
            KiiBucket mKiiBucket = user.bucket(user.getID() + "_bucket");
            for (AbstractFlexibleItem item : list) {
                CommnFileItem itemTemp = (CommnFileItem) item;
                String fileName = itemTemp.getName();
                String fileSize = itemTemp.getSize();
                String filePath = itemTemp.getPath();
                String fileId = itemTemp.getId();

                mKiiObject = mKiiBucket.object(fileId);
                mKiiObject.set("Name", fileName);
                mKiiObject.set("Size", fileSize);
                mKiiObject.saveAllFields(new KiiObjectCallBack() {
                    @Override
                    public void onSaveCompleted(int token, @NonNull KiiObject object, @Nullable Exception exception) {
                        super.onSaveCompleted(token, object, exception);
                        File file = new File(filePath);
                        KiiUploader uploader = mKiiObject.uploader(mContext, file);
                        uploader.transferAsync(new KiiRTransferCallback() {
                            @Override
                            public void onStart(@NonNull KiiRTransfer operator) {
                                super.onStart(operator);
                            }

                            @Override
                            public void onTransferCompleted(@NonNull KiiRTransfer operator, @NonNull Exception e) {
                                super.onTransferCompleted(operator, e);
                                queryObject();
                                Toast.makeText(mContext, fileName + "上传完成", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onProgress(@NonNull KiiRTransfer operator, long completedInBytes, long totalSizeinBytes) {
                                super.onProgress(operator, completedInBytes, totalSizeinBytes);

                                Message message = Message.obtain();
                                message.what = Constant.UPLOAD_PROCESS;
                                message.obj = (completedInBytes / totalSizeinBytes) * 100;
                                UploadFileFragment.newInstance().mUploadHandler.sendMessage(message);
                            }
                        });

                    }
                }, true);
            }
        } else {
            Toast.makeText(mContext, "没有该用户", Toast.LENGTH_SHORT).show();

        }

    }
}
