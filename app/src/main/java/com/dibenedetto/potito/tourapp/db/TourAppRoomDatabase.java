package com.dibenedetto.potito.tourapp.db;


import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.dibenedetto.potito.tourapp.util.Const;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static android.content.Context.DOWNLOAD_SERVICE;
import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

@Database(entities = {Diari.class, CategoriaPrimaria.class, CategoriaSecondaria.class,
        Location.class, FotoRicordo.class, Coupon.class}, version = 1)
public abstract class TourAppRoomDatabase extends RoomDatabase {

    public abstract CouponDAO getCouponDAO();

    public abstract DiaryDAO getDiaryDAO();

    public abstract LocationDAO getLocationDAO();

    private static TourAppRoomDatabase INSTANCE;

    private static File downloadedFile;

    //check if DB have to be updated;
    public static boolean haveToUpdate(Context context) {
        return (getDefaultSharedPreferences(context)
                .getBoolean(Const.DB_UPDATE_FLAG_KEY, false) && INSTANCE == null);
    }

    //download the updated DB and return the download-ID
    public static long updateDatabase(final Context context, final String DB_NAME) {

            downloadedFile = new File(context.getExternalFilesDir(null), "TempDB");
            downloadedFile.getParentFile().mkdirs();

            DownloadManager.Request request = new DownloadManager.Request(
                    Uri.parse(Const.REMOTE_DB_PATH + Const.REMOTE_DB_ID))
                    .setDestinationUri(Uri.fromFile(downloadedFile));

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            return downloadManager.enqueue(request);
    }

    // replace DB with the downloaded one
    public static void replaceDownloadedDatabase(Context context, String DB_NAME) {
            //spostalo nella cartella di room

        if (downloadedFile.getParentFile().mkdirs()) {

        final File dbFile = context.getDatabasePath(DB_NAME);
           dbFile.getParentFile().mkdirs();

           try {
                final InputStream inputStream = new FileInputStream(downloadedFile);
                final OutputStream output = new FileOutputStream(dbFile);

                byte[] buffer = new byte[8192];
                int length;

                while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                    output.write(buffer, 0, length);
                }

                output.flush();
                output.close();
                inputStream.close();
            }
            catch (IOException e) {
                Log.d("Activity", "Failed to open file", e);
                e.printStackTrace();
            }

            // instanceate

        synchronized (TourAppRoomDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TourAppRoomDatabase.class, DB_NAME)
                        .build();
                }
            }
        }
    }



    public static TourAppRoomDatabase getDatabase(final Context context, final String DB_NAME) {

        if (INSTANCE == null) {

            final File dbFile = context.getDatabasePath(DB_NAME);

            if(!dbFile.exists()) {
                // Make sure we have a path to the file
                dbFile.getParentFile().mkdirs();

                // Try to copy database file
                try {
                    final InputStream inputStream = context.getAssets().open(DB_NAME);
                    final OutputStream output = new FileOutputStream(dbFile);

                    byte[] buffer = new byte[8192];
                    int length;

                    while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                        output.write(buffer, 0, length);
                    }

                    output.flush();
                    output.close();
                    inputStream.close();
                }
                catch (IOException e) {
                    Log.d("Activity", "Failed to open file", e);
                    e.printStackTrace();
                }

            }

            synchronized (TourAppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TourAppRoomDatabase.class, DB_NAME)
                            .build();
                }
            }

        }

        return INSTANCE;
    }

}
