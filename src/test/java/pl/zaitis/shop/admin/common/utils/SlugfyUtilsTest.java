//package pl.zaitis.shop.admin.common.utils;
//
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class SlugfyUtilsTest {
//
//    @ParameterizedTest
//    @CsvSource({
//            "test test.png, test-test.png",
//            "Life is brutal a a a.png, life-is-brutal-a-a-a.png",
//            "baśka44.jpg, baska44.jpg",
//            "BiG DOG  HOOtEr.png, big-dog-hooter.png"
//    })
//    void shouldSlugifyFileName(String in, String out){
//        String fileName= SlugfyUtils.slugifyFileName(in);
//        assertEquals(fileName, out);
//    }
//}