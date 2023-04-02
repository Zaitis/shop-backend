package pl.zaitis.shop.admin.service;

import com.github.slugify.Slugify;
import org.apache.commons.io.FilenameUtils;

class UploadedFilesNameUtils {
    public static String slugifyFileName(String filename) {

        String name = FilenameUtils.getBaseName(filename);
        final Slugify slg = Slugify.builder().build();
        String changeName =slg.slugify(name);

        return changeName+"."+FilenameUtils.getExtension(filename);
    }
}
