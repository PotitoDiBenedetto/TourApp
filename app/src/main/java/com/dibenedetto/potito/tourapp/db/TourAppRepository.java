package com.dibenedetto.potito.tourapp.db;

import android.app.Application;
import android.os.AsyncTask;

import com.dibenedetto.potito.tourapp.util.Const;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TourAppRepository {

    public static final String DB_NAME = Const.DB_NAME;

    private LocationDAO mLocationDao;
    private CouponDAO mCouponDao;
    private DiaryDAO mDiaryDao;

    /*
    private LiveData<List<Location>> mLocationsLiveData;
    private LiveData<List<LocationDAO.LocationWithCategory>> mAllLocationsLiveData;
    private LiveData<List<CategoriaPrimaria>> mPrimaryCategories;
    private LiveData<List<CategoriaSecondaria>> mSecondaryCategories;
    private LiveData<List<Diari>> mDiaries;
    private LiveData<List<FotoRicordo>> mPhotos;
    private LiveData<List<Coupon>> mCoupons;
    */

    /**
     * Constructor
     * @param application
     */
    public TourAppRepository(Application application) {
        TourAppRoomDatabase db = TourAppRoomDatabase.getDatabase(application, DB_NAME);
        mLocationDao = db.getLocationDAO();

    }

    /**
     * Returns all locations and the associated categories information
     * @return
     */
    public LiveData<List<LocationDAO.LocationWithCategory>> getAllLocationsWithCategory() {
        return mLocationDao.getLocationsWithCategory();
    }

    public LiveData<List<CategoriaSecondaria>> getCategrieSecondarieOf(int categoriaPrimaria) {
        return mLocationDao.getCategrieSecondarieOf(categoriaPrimaria);
    }

    public LiveData<List<LocationDAO.LocationWithCategory>> getLocationsOfSubCategory(int subCategory) {
        return mLocationDao.getLocationsOfSubCategory(subCategory);
    }

    public LiveData<List<LocationDAO.LocationWithCategory>> getLocationsOfPrimaryCategory(int categoriaPrimaria) {
        return mLocationDao.getLocationsOfPrimaryCategory(categoriaPrimaria);
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
