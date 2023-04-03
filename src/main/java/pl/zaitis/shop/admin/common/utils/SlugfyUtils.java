package pl.zaitis.shop.admin.common.utils;

import com.github.slugify.Slugify;
import org.apache.commons.io.FilenameUtils;

public class SlugfyUtils {
    public static String slugifyFileName(String filename) {

        String name = FilenameUtils.getBaseName(filename);
        final Slugify slg = Slugify.builder().build();
        String changeName =slg.slugify(name);

        return changeName+"."+FilenameUtils.getExtension(filename);
    }

    public static String slugifySlugName(String slug) {
        Slugify slg = Slugify.builder()
                .customReplacement("_", "-")
                .build();
        return  slg.slugify(slug);
    }
}
