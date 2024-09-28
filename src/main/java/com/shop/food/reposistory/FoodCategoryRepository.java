package com.shop.food.reposistory;

import com.shop.food.entity.food.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Integer> {
    Optional<FoodCategory> findByAlias(String alias);
}
