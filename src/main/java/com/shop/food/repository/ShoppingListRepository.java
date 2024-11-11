package com.shop.food.repository;

import com.shop.food.entity.shopping.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Integer> {
    @Query("SELECT s FROM ShoppingList s WHERE s.belongToGroup.id = :groupId")
    List<ShoppingList> getShoppingListsByGroupId(@Param("groupId") Integer groupId);

    @Query("SELECT s FROM ShoppingList s WHERE s.assignToUser.id = :userId")
    List<ShoppingList> getShoppingListAssignedToUser(@Param("userId") Integer userId);
}

