package com.example.ecommerce.dto.product;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class CreateProductDto {
    @NonNull
    public String name;
    public int quantity;
    public double price;
    @NonNull
    public String photo;
    @NonNull
    @JsonProperty("category_name")
    public String categoryName;
}
