package com.dibenedetto.potito.tourapp.ViewModels;

import android.app.Application;

import com.dibenedetto.potito.tourapp.db.Location;
import com.dibenedetto.potito.tourapp.db.LocationDAO;
import com.dibenedetto.potito.tourapp.db.LocationRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LocationsViewModel extends AndroidViewModel {

    private LocationRepository mRepository;

    private LiveData<List<LocationDAO.LocationWithCategory>> mAllLocations;

    public LocationsViewModel (Application application) {
        super(application);
        mRepository = new LocationRepository(application);
        mAllLocations = mRepository.getAllLocationsWithCategory();
    }

    public LiveData<List<LocationDAO.LocationWithCategory>> getAllLocationsWithCategories() { return mAllLocations; }

    public void insert(Location location) { mRepository.insert(location); }
}

