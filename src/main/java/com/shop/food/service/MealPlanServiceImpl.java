package com.shop.food.service;

import com.shop.food.dto.MealPlanDto;
import com.shop.food.entity.food.Food;
import com.shop.food.entity.meal.MealPlan;
import com.shop.food.entity.meal.STATUS;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import com.shop.food.repository.FoodRepository;
import com.shop.food.repository.GroupRepository;
import com.shop.food.repository.MealPlanRepository;
import com.shop.food.repository.UserRepository;
import com.shop.food.service.iservice.MealPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MealPlanServiceImpl implements MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final FoodRepository foodRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public MealPlan createMealPlan(MealPlanDto mealPlanDto) {
        MealPlan mealPlan = new MealPlan();
        mealPlan.setName(mealPlanDto.getName());
        try {
            Date parsedDate = dateFormatter.parse(mealPlanDto.getTimeStamp());
            mealPlan.setTimeStamp(parsedDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd");
        }

        mealPlan.setStatus(STATUS.NOT_PASS_YET);

        if (foodRepository.existsById(mealPlanDto.getFoodId())) {
            Food food = foodRepository.findById(mealPlanDto.getFoodId()).orElse(null);
            mealPlan.setFood(food);
        } else {
            throw new RuntimeException("Food not found with id: " + mealPlanDto.getFoodId());
        }

        if (userRepository.existsById(mealPlanDto.getOwnerId())) {
            User owner = User.builder().id(mealPlanDto.getOwnerId()).build();
            mealPlan.setOwner(owner);
        } else {
            throw new RuntimeException("Owner not found with id: " + mealPlanDto.getOwnerId());
        }

        if (groupRepository.existsById(mealPlanDto.getGroup())) {
            Group group = Group.builder().id(mealPlanDto.getGroup()).build();
            mealPlan.setGroup(group);
        } else {
            throw new RuntimeException("Group not found with id: " + mealPlanDto.getGroup());
        }

        return mealPlanRepository.save(mealPlan);
    }

    @Override
    public MealPlan updateMealPlan(Integer id, MealPlanDto mealPlanDto) {
        MealPlan existingMealPlan = mealPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MealPlan not found with id: " + id));

        existingMealPlan.setName(mealPlanDto.getName());

        try {
            Date parsedDate = dateFormatter.parse(mealPlanDto.getTimeStamp());
            existingMealPlan.setTimeStamp(parsedDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd");
        }

        existingMealPlan.setStatus(STATUS.NOT_PASS_YET);

        if (foodRepository.existsById(mealPlanDto.getFoodId())) {
            Food food = foodRepository.findById(mealPlanDto.getFoodId()).orElse(null);
            existingMealPlan.setFood(food);
        } else {
            throw new RuntimeException("Food not found with id: " + mealPlanDto.getFoodId());
        }

        if (userRepository.existsById(mealPlanDto.getOwnerId())) {
            User owner = User.builder().id(mealPlanDto.getOwnerId()).build();
            existingMealPlan.setOwner(owner);
        } else {
            throw new RuntimeException("Owner not found with id: " + mealPlanDto.getOwnerId());
        }

        if (groupRepository.existsById(mealPlanDto.getGroup())) {
            Group group = Group.builder().id(mealPlanDto.getGroup()).build();
            existingMealPlan.setGroup(group);
        } else {
            throw new RuntimeException("Group not found with id: " + mealPlanDto.getGroup());
        }

        return mealPlanRepository.save(existingMealPlan);
    }

    @Override
    public void deleteMealPlan(Integer id) {
        mealPlanRepository.deleteById(id);
    }

    @Override
    public List<MealPlan> getMealPlansByDate(String formattedDate, Integer groupId) {
        return mealPlanRepository.findByDate(formattedDate, groupId);
    }
}
