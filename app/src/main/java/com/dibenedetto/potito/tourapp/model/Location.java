package com.dibenedetto.potito.tourapp.model;

public class Location {

    public double longitude;
    public double latitude;
    public String name;
    public String address;
    public InterestCategory category;
    public int priceCategory;
    public String orariApertura;
    public boolean isPreferred;

    public static class InterestCategory {

        public static final String RESTURANT = "Resturant";
        public static final int RESTURANT_CODE = 1;
        public static final String HOTEL = "Hotel";
        public static final int HOTEL_CODE = 2;
        public static final String INTEREST = "Interest";
        public static final int INTEREST_CODE = 3;
        public static final String INFOPOINT = "Infopoint";
        public static final int INFOPOINT_CODE = 4;

        public String catPrimaria;
        public String catSecondaria;
        public int codCatPrimaria;
        public int codCatSecondaria;

    }
}
