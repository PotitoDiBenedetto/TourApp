package com.dibenedetto.potito.tourapp.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CouponDAO {

    @Insert
    void insertCoupon(Coupon coupon);

    @Query("SELECT * FROM Coupon ORDER BY _id")
    List<Coupon> getCoupons();

    @Query("SELECT * FROM Coupon WHERE location = :location ORDER BY _id")
    List<Coupon> getCouponByLocation(Location location);
}
