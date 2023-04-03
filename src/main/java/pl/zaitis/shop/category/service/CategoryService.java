package pl.zaitis.shop.category.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaitis.shop.category.model.Category;
import pl.zaitis.shop.category.model.CategoryProductsDto;
import pl.zaitis.shop.category.repository.CategoryRepository;
import pl.zaitis.shop.product.contoller.dto.ProductListDto;
import pl.zaitis.shop.product.model.Product;
import pl.zaitis.shop.product.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public CategoryProductsDto getCategoriesWithProducts(String slug, Pageable pageable) {
        Category categoryBySlug = categoryRepository.findBySlug(slug);
        Page<Product> products = productRepository.findByCategoryId(categoryBySlug.getId(), pageable);
        List<ProductListDto> productListDtoList =
                products.getContent().stream()
                        .map(product -> ProductListDto.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .currency(product.getCurrency())
                                .image(product.getImage())
                                .slug(product.getSlug())
                                .build())
                        .toList();
        return new CategoryProductsDto(categoryBySlug, new PageImpl<>(productListDtoList, pageable, products.getTotalElements()));
    }
}
