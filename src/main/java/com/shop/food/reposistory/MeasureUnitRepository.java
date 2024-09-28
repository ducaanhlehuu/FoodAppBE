package com.shop.food.reposistory;

import com.shop.food.entity.food.MeasureUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeasureUnitRepository extends JpaRepository<MeasureUnit, Integer> {
    Optional<MeasureUnit> findByUnitName(String unitName);
}
