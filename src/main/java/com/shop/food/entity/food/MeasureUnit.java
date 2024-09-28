package com.shop.food.entity.food;

import com.shop.food.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@EqualsAndHashCode(callSuper = false)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasureUnit extends BaseEntity {
    @Column(nullable = false)
    private String unitName;
    private String toGram;
    private String description;
}
