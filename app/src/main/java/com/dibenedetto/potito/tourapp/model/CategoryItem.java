package com.dibenedetto.potito.tourapp.model;

import java.util.ArrayList;
import java.util.List;

public class CategoryItem {

    public int mText;
    public int mImage;

    public CategoryItem() {

    }

    CategoryItem(int name, int idR) {
        this.mImage = idR;
        this.mText = name;

    }

    public static List<CategoryItem> getCategoryList() {
        List<CategoryItem> list = new ArrayList<>();


        return list;
    }



}

