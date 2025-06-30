package com.doomsdaysale.service.impl;

import com.doomsdaysale.domain.HomeCategorySection;
import com.doomsdaysale.model.Deal;
import com.doomsdaysale.model.Home;
import com.doomsdaysale.model.HomeCategory;
import com.doomsdaysale.repository.DealRepository;
import com.doomsdaysale.service.HomeCategoryService;
import com.doomsdaysale.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final DealRepository dealRepository;
    @Override
    public Home createHomePageData(List<HomeCategory> allCategories) {

        List<HomeCategory> gridCategories = allCategories.stream().filter(category->
                category.getSection() == HomeCategorySection.GRID)
                .collect(Collectors.toList());

        List<HomeCategory> shopByCategories = allCategories.stream()
                .filter(category ->
                        category.getSection() == HomeCategorySection.SHOP_BY_CATEGORY)
                .collect(Collectors.toList());
        List<HomeCategory> electricCategories = allCategories.stream()
                .filter(category ->
                        category.getSection() == HomeCategorySection.ELECTRIC_CATEGORY)
                .collect(Collectors.toList());
        List<HomeCategory> dealCategories = allCategories.stream()
                .filter(category ->
                        category.getSection() == HomeCategorySection.DEALS)
                .collect(Collectors.toList());

        List<Deal> createdDeals = new ArrayList<>();

        if(dealRepository.findAll().isEmpty()){
            List<Deal> deals = allCategories.stream()
                    .filter(category -> category.getSection() == HomeCategorySection.DEALS)
                    .map(category -> new Deal(null, 10, category))
                    .collect(Collectors.toList());
        } else{
            createdDeals = dealRepository.findAll();
        }

        Home home = new Home();
        home.setGrid(gridCategories);
        home.setShopByCategories(shopByCategories);
        home.setElectricCategories(electricCategories);
        home.setDeals(createdDeals);
        home.setDealCategories(dealCategories);

        return home;
    }
}
