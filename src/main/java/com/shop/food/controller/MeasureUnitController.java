package com.shop.food.controller;

import com.shop.food.entity.food.MeasureUnit;
import com.shop.food.entity.response.ResponseBody;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.service.iservice.MeasureUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/unit")
@RequiredArgsConstructor
public class MeasureUnitController {

    private final MeasureUnitService measureUnitService;

    @GetMapping
    public ResponseEntity<ResponseBody> getAllMeasureUnits() {
        List<MeasureUnit> measureUnits = measureUnitService.getAllFoodCategories();
        return ResponseEntity.ok(new ResponseBody("Lấy danh sách đơn vị đo thành công", "", measureUnits));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> getMeasureUnitById(@PathVariable Integer id) {
        MeasureUnit measureUnit = measureUnitService.getMeasureUnitById(id);
        return ResponseEntity.ok(new ResponseBody("Lấy đơn vị đo thành công", "", measureUnit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseBody> createMeasureUnit(@Param("unitName") String unitName, @Param("toGram") String toGram, @Param("description") String description){
        MeasureUnit createdMeasureUnit = MeasureUnit.builder().unitName(unitName).toGram(toGram).description(description).build();
        measureUnitService.createMeasureUnit(createdMeasureUnit);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseBody("Tạo đơn vị đo thành công", "", createdMeasureUnit));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseBody> updateMeasureUnit(@PathVariable Integer id, @RequestBody MeasureUnit measureUnit) throws UnauthorizedException {
        MeasureUnit updatedMeasureUnit = measureUnitService.save(id, measureUnit);
        return ResponseEntity.ok(new ResponseBody("Cập nhật đơn vị đo thành công", "", updatedMeasureUnit));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseBody> deleteMeasureUnit(@PathVariable Integer id) throws UnauthorizedException {
        measureUnitService.deleteMeasureUnit(id);
        return ResponseEntity.ok(new ResponseBody("Xóa thành công", "", null));
    }
}
