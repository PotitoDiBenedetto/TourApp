package com.dibenedetto.potito.tourapp.db;


import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Diari.class, CategoriaPrimaria.class, CategoriaSecondaria.class,
        Location.class, FotoRicordo.class, Coupon.class}, version = 1)
public abstract class TourAppRoomDatabase extends RoomDatabase {

    public abstract CouponDAO getCouponDAO();

    public abstract DiaryDAO getDiaryDAO();

    public abstract LocationDAO getLocationDAO();

    private static TourAppRoomDatabase INSTANCE;

    public static TourAppRoomDatabase getDatabase(final Context context, final String DB_NAME) {
        if (INSTANCE == null) {

            final File dbFile = context.getDatabasePath(DB_NAME);

            if(/*!dbFile.exists()*/true) {
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
