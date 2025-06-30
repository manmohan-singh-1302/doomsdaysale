package com.doomsdaysale.service;

import com.doomsdaysale.model.Home;
import com.doomsdaysale.model.HomeCategory;

import java.util.List;

public interface HomeService {

    public Home createHomePageData(List<HomeCategory> allCategories);
}
