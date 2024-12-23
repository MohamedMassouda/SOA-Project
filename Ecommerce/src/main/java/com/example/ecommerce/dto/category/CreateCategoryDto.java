package com.example.ecommerce.dto.category;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class CreateCategoryDto {
    public String name;
    public String description;
}
