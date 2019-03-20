package com.dibenedetto.potito.tourapp.util;

import android.content.Context;
import android.util.Log;

import com.dibenedetto.potito.tourapp.db.TourAppRoomDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.room.Room;


public class FilesUtil {

    //build the database
    public static TourAppRoomDatabase buildDatabase(Context context, final String DB_NAME ) {

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

        return Room.databaseBuilder(context.getApplicationContext(),
                TourAppRoomDatabase.class, DB_NAME)
                .build();
    }


}
