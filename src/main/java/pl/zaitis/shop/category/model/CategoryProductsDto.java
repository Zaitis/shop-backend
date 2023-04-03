package pl.zaitis.shop.category.model;

import org.springframework.data.domain.Page;
import pl.zaitis.shop.product.contoller.dto.ProductListDto;


public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
