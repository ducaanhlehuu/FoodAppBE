package com.shop.food.controller;

import com.shop.food.entity.food.FoodCategory;
import com.shop.food.entity.user.Role;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.service.iservice.FoodCategoryService;
import com.shop.food.entity.response.ResponseBody;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
public class FoodCategoryController {

    private final FoodCategoryService foodCategoryService;

    @GetMapping
    public ResponseEntity<ResponseBody> getAllCategories() {
        List<FoodCategory> categories = foodCategoryService.getAll();
        return ResponseEntity.ok(new ResponseBody("Lấy danh sách thành công", ResponseBody.SUCCESS, categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> getCategoryById(@PathVariable Integer id) {
        FoodCategory category = foodCategoryService.getById(id);
        if (category != null) {
            return ResponseEntity.ok(new ResponseBody("Lấy danh mục thành công", ResponseBody.SUCCESS, category));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBody("Không tìm thấy danh mục", ResponseBody.NOT_FOUND, null));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseBody> createCategory(@Param("name") String name, @Param("alias") String alias, @Param("description") @Nullable String description) throws UnauthorizedException {
        FoodCategory category = FoodCategory.builder().name(name).alias(alias).description(description).build();
        foodCategoryService.create(category);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseBody("Tạo danh mục thành công", ResponseBody.SUCCESS, category));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseBody> updateCategory(@PathVariable Integer id, @RequestBody FoodCategory category) throws UnauthorizedException {
        FoodCategory updatedCategory = foodCategoryService.save(id, category);
        return ResponseEntity.ok(new ResponseBody("Cập nhật danh mục thành công", ResponseBody.SUCCESS, updatedCategory));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseBody> deleteCategory(@PathVariable Integer id) throws UnauthorizedException {
        foodCategoryService.delete(id);
        return ResponseEntity.ok(new ResponseBody("Xóa danh mục thành công", ResponseBody.DELETED, ""));
    }

}
