package pl.zaitis.shop.category.dto;

import org.springframework.data.domain.Page;
import pl.zaitis.shop.common.model.Category;
import pl.zaitis.shop.common.dto.ProductListDto;


public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
