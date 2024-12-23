package com.example.ecommerce.dto.product;

import com.example.ecommerce.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private int quantity;
    private double price;
    private String photo;
    @JsonProperty("category_name")
    private String categoryName;
    @JsonProperty("out_of_stock")
    private boolean isOutOfStock;

    public static ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setQuantity(product.getQuantity());
        productDto.setPrice(product.getPrice());
        productDto.setPhoto(product.getPhoto());
        productDto.setCategoryName(product.getCategory().getName());
        productDto.setOutOfStock(product.getQuantity() == 0);
        return productDto;
    }

    public static List<ProductDto> from(List<Product> products) {
        return products.stream().map(ProductDto::from).toList();
    }
}
