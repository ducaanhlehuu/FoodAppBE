package com.shop.food.repository;

import com.shop.food.entity.meal.FridgeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FridgeItemRepository extends JpaRepository<FridgeItem, Integer> {
    @Query("SELECT f FROM FridgeItem f WHERE f.group.id = :groupId")
    List<FridgeItem> findByGroupId(@Param("groupId") Integer groupId);
    List<FridgeItem> findByOwnerId(Integer ownerId);
    List<FridgeItem> findByFoodId(Integer foodId);
}
