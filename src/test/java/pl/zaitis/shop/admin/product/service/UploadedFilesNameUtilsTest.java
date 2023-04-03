package pl.zaitis.shop.admin.product.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

class UploadedFilesNameUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "test test.png, test-test.png",
            "Life is brutal a a a.png, life-is-brutal-a-a-a.png",
            "baśka44.jpg, baska44.jpg",
            "BiG DOG  HOOtEr.png, big-dog-hooter.png"
    })
    void shouldSlugifyFileName(String in, String out){
        String fileName= UploadedFilesNameUtils.slugifyFileName(in);
        assertEquals(fileName, out);
    }
}