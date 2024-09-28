package com.shop.food.reposistory;

import com.shop.food.entity.user.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query("SELECT g FROM Group g JOIN g.members u WHERE u.id = :userId")
    List<Group> getGroupsByUser(@Param("userId") Integer userId);

    @Query("SELECT g FROM Group g JOIN g.members u WHERE u.email = :email")
    List<Group> getGroupsByUserEmail(@Param("email") String email);
}
