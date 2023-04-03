package pl.zaitis.shop.category.controller;


import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import pl.zaitis.shop.category.model.Category;
import pl.zaitis.shop.category.model.CategoryProductsDto;
import pl.zaitis.shop.category.service.CategoryService;


import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @GetMapping("/{slug}/products")
    public CategoryProductsDto getCategoriesWithProducts(@PathVariable
                                                        @Pattern(regexp= "[a-z0-9\\-]+")
                                                        @Length(max= 255) String slug, Pageable pageable) {
        return categoryService.getCategoriesWithProducts(slug, pageable);
    }

}
