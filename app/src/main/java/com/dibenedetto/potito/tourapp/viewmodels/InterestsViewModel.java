package com.dibenedetto.potito.tourapp.viewmodels;

import android.app.Application;
import android.util.SparseIntArray;

import com.dibenedetto.potito.tourapp.db.CategoriaSecondaria;
import com.dibenedetto.potito.tourapp.db.LocationDAO;
import com.dibenedetto.potito.tourapp.db.TourAppRepository;
import com.dibenedetto.potito.tourapp.util.Const;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class InterestsViewModel extends AndroidViewModel {

    private TourAppRepository mRepository;

    private LiveData<List<LocationDAO.LocationWithCategory>> mInterestLocations;

    private LiveData<List<CategoriaSecondaria>> mInterests;

    private Map<Integer, LiveData<List<LocationDAO.LocationWithCategory>>> mLocationsMap = new HashMap<>();

    public InterestsViewModel(Application application) {
        super(application);
        mRepository = new TourAppRepository(application);
        mInterests = mRepository.getCategrieSecondarieOf(Const.INTERESTS_ID);

    }

    public LiveData<List<CategoriaSecondaria>> getInterests() {
        return mInterests;
    }

    public LiveData<List<LocationDAO.LocationWithCategory>> getLocationsOfSubCategory(int subCategory) {

        LiveData<List<LocationDAO.LocationWithCategory>> data;

        if (!mLocationsMap.containsKey(subCategory)) {
            data = mRepository.getLocationsOfSubCategory(subCategory);
            mLocationsMap.put(subCategory, data);
            return data;
        } else {
        return mLocationsMap.get(subCategory);
        }
    }

    public LiveData<List<LocationDAO.LocationWithCategory>> getLocationsOfPrimaryCategory(int category) {
        if (mInterestLocations == null) {
            return mInterestLocations = mRepository.getLocationsOfPrimaryCategory(category);
        } else {
            return this.mInterestLocations;
        }
    }






}

