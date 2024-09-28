package com.shop.food.service.iservice;

import com.shop.food.entity.food.MeasureUnit;

import java.util.List;
public interface MeasureUnitService {

    MeasureUnit createMeasureUnit(MeasureUnit MeasureUnit);

    MeasureUnit getMeasureUnitById(Integer id);

    List<MeasureUnit> getAllFoodCategories();

    MeasureUnit save(Integer id, MeasureUnit MeasureUnit);

    boolean deleteMeasureUnit(Integer id);
}
