package com.shop.food.reposistory;

import com.shop.food.entity.shopping.ShoppingList;
import com.shop.food.entity.shopping.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t WHERE t.shoppingList.id = :shoppingListId")
    List<Task> getTasksByShoppingListId(@Param("shoppingListId") Integer shoppingListId);
}

