//package pl.zaitis.shop.admin.category.service;
//
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Disabled;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import pl.zaitis.shop.admin.category.model.AdminCategory;
//import pl.zaitis.shop.admin.category.repository.AdminCategoryRepository;
//
//@ContextConfiguration(classes = {AdminCategoryService.class})
//@ExtendWith(SpringExtension.class)
//class AdminCategoryServiceTest {
//    @MockBean
//    private AdminCategoryRepository adminCategoryRepository;
//
//    @Autowired
//    private AdminCategoryService adminCategoryService;
//
//    /**
//     * Method under test: {@link AdminCategoryService#getCategories()}
//     */
//    @Test
//    void testGetCategories() {
//        ArrayList<AdminCategory> adminCategoryList = new ArrayList<>();
//        when(adminCategoryRepository.findAll()).thenReturn(adminCategoryList);
//        List<AdminCategory> actualCategories = adminCategoryService.getCategories();
//        assertSame(adminCategoryList, actualCategories);
//        assertTrue(actualCategories.isEmpty());
//        verify(adminCategoryRepository).findAll();
//    }
//
//    /**
//     * Method under test: {@link AdminCategoryService#getCategory(Long)}
//     */
//    @Test
//    void testGetCategory() {
//        AdminCategory adminCategory = new AdminCategory();
//        when(adminCategoryRepository.findById((Long) any())).thenReturn(Optional.of(adminCategory));
//        assertSame(adminCategory, adminCategoryService.getCategory(1L));
//        verify(adminCategoryRepository).findById((Long) any());
//    }
//
//    /**
//     * Method under test: {@link AdminCategoryService#getCategory(Long)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testGetCategory2() {
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.util.NoSuchElementException: No value present
//        //       at java.util.Optional.orElseThrow(Optional.java:377)
//        //       at pl.zaitis.shop.admin.category.service.AdminCategoryService.getCategory(AdminCategoryService.java:21)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        when(adminCategoryRepository.findById((Long) any())).thenReturn(Optional.empty());
//        adminCategoryService.getCategory(1L);
//    }
//
//    /**
//     * Method under test: {@link AdminCategoryService#createCategory(AdminCategory)}
//     */
//    @Test
//    void testCreateCategory() {
//        AdminCategory adminCategory = new AdminCategory();
//        when(adminCategoryRepository.save((AdminCategory) any())).thenReturn(adminCategory);
//        assertSame(adminCategory, adminCategoryService.createCategory(new AdminCategory()));
//        verify(adminCategoryRepository).save((AdminCategory) any());
//    }
//
//    /**
//     * Method under test: {@link AdminCategoryService#updateCategory(AdminCategory)}
//     */
//    @Test
//    void testUpdateCategory() {
//        AdminCategory adminCategory = new AdminCategory();
//        when(adminCategoryRepository.save((AdminCategory) any())).thenReturn(adminCategory);
//        assertSame(adminCategory, adminCategoryService.updateCategory(new AdminCategory()));
//        verify(adminCategoryRepository).save((AdminCategory) any());
//    }
//
//    /**
//     * Method under test: {@link AdminCategoryService#deleteCategory(Long)}
//     */
//    @Test
//    void testDeleteCategory() {
//        doNothing().when(adminCategoryRepository).deleteById((Long) any());
//        adminCategoryService.deleteCategory(1L);
//        verify(adminCategoryRepository).deleteById((Long) any());
//    }
//}
//
