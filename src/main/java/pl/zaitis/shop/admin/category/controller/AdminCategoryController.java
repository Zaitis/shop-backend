package pl.zaitis.shop.admin.category.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.zaitis.shop.admin.category.controller.dto.AdminCategoryDto;
import pl.zaitis.shop.admin.category.model.AdminCategory;
import pl.zaitis.shop.admin.category.service.AdminCategoryService;

import java.util.List;

import static pl.zaitis.shop.admin.common.utils.SlugfyUtils.slugifySlugName;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@CrossOrigin
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;


    @GetMapping()
    public List<AdminCategory> getCategories() {
        return adminCategoryService.getCategories();
    }

    @GetMapping("/{id}")
    public AdminCategory getCategory(@PathVariable Long id) {
        return adminCategoryService.getCategory(id);
    }

    @PostMapping()
    public AdminCategory createCategory(@RequestBody AdminCategoryDto adminCategoryDto) {
        return adminCategoryService.createCategory(mapToAdminCategory(null, adminCategoryDto));
    }


    @PutMapping("/{id}")
    public AdminCategory updateCategory(@PathVariable Long id, @RequestBody AdminCategoryDto adminCategoryDto) {
        return adminCategoryService.updateCategory(mapToAdminCategory(id, adminCategoryDto));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        adminCategoryService.deleteCategory(id);
    }

    private AdminCategory mapToAdminCategory(Long id, AdminCategoryDto adminCategoryDto) {
        return AdminCategory.builder()
                .id(id)
                .name(adminCategoryDto.getName())
                .description(adminCategoryDto.getDescription())
                .slug(slugifySlugName(adminCategoryDto.getSlug()))
                .build();
    }




}
