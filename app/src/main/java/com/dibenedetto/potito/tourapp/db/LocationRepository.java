package com.dibenedetto.potito.tourapp.db;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class LocationRepository {

    public static final String DB_NAME = "TourApp.db";

    private LocationDAO mLocationDao;

    private LiveData<List<Location>> mLocationsLiveData;
    private List<Location> mLocations;
    private LiveData<List<LocationDAO.LocationWithCategory>> mAllLocationsLiveData;


    public LocationRepository(Application application) {
        TourAppRoomDatabase db = TourAppRoomDatabase.getDatabase(application, DB_NAME);
        mLocationDao = db.getLocationDAO();

    }

    public LiveData<List<LocationDAO.LocationWithCategory>> getAllLocationsWithCategory() {
        return mAllLocationsLiveData = mLocationDao.getLocationsWithCategory();

    }

    public void insert (Location location) {
        new insertLocationAsyncTask(mLocationDao).execute(location);
    }

    private static class insertLocationAsyncTask extends AsyncTask<Location, Void, Void> {

        private LocationDAO mAsyncTaskDao;

        insertLocationAsyncTask(LocationDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Location... params) {
            mAsyncTaskDao.insertLocation(params[0]);
            return null;
        }
    }
}
