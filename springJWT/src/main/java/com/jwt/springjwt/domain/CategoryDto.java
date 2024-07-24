package com.jwt.springjwt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    private Integer categoryId;
    private Integer userId;
    private String title;
    private String description;
    private Double totalExpanse;
}
