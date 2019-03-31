package com.dibenedetto.potito.tourapp.viewmodels;

import android.app.Application;

import com.dibenedetto.potito.tourapp.db.CategoriaSecondaria;
import com.dibenedetto.potito.tourapp.db.LocationDAO;
import com.dibenedetto.potito.tourapp.db.TourAppRepository;
import com.dibenedetto.potito.tourapp.util.Const;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ResturantsViewModel extends AndroidViewModel {

    private TourAppRepository mRepository;

    private LiveData<List<LocationDAO.LocationWithCategory>> mResturantLocations;

    private LiveData<List<CategoriaSecondaria>> mResturants;

    public ResturantsViewModel(Application application) {
        super(application);
        mRepository = new TourAppRepository(application);
        mResturants = mRepository.getCategrieSecondarieOf(Const.RESTURANTS_ID);
    }

    public LiveData<List<CategoriaSecondaria>> getResturants() {
        return mResturants;
    }

    public LiveData<List<LocationDAO.LocationWithCategory>> getLocationsOfSubCategory(int subCategory) {
        return mRepository.getLocationsOfSubCategory(subCategory);

    }

    public LiveData<List<LocationDAO.LocationWithCategory>> getLocationsOfPrimaryCategory(int category) {
        if (mResturantLocations == null) {
            return mResturantLocations = mRepository.getLocationsOfPrimaryCategory(category);
        } else {
            return this.mResturantLocations;
        }
    }






}

