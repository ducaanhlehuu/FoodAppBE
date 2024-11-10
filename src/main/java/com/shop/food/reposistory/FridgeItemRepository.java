package com.shop.food.reposistory;

import com.shop.food.entity.meal.FridgeItem;
import com.shop.food.entity.shopping.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FridgeItemRepository extends JpaRepository<FridgeItem, Integer> {
    @Query("SELECT f FROM FridgeItem f WHERE f.group = :groupId")
    List<FridgeItem> getFridgeItemsByGroupId(@Param("groupId") Integer groupId);
}

