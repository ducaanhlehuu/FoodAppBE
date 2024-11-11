package com.shop.food.service;

import com.shop.food.entity.food.MeasureUnit;
import com.shop.food.repository.MeasureUnitRepository;
import com.shop.food.service.iservice.MeasureUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasureUnitServiceImpl implements MeasureUnitService {

    private final MeasureUnitRepository measureUnitRepository;

    @Override
    public MeasureUnit createMeasureUnit(MeasureUnit measureUnit) {
        return measureUnitRepository.save(measureUnit);
    }

    @Override
    public MeasureUnit getMeasureUnitById(Integer id) {
        return measureUnitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MeasureUnit not found with id: " + id));
    }

    @Override
    public List<MeasureUnit> getAllFoodCategories() {
        return measureUnitRepository.findAll();
    }

    @Override
    public MeasureUnit save(Integer id, MeasureUnit measureUnit) {
        if (!measureUnitRepository.existsById(id)) {
            throw new IllegalArgumentException("MeasureUnit not found with id: " + id);
        }
        measureUnit.setId(id);
        return measureUnitRepository.save(measureUnit);
    }

    @Override
    public boolean deleteMeasureUnit(Integer id) {
        if (!measureUnitRepository.existsById(id)) {
            throw new IllegalArgumentException("MeasureUnit not found with id: " + id);
        }
        measureUnitRepository.deleteById(id);
        return true;
    }
}
