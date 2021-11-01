package org.onecell.spring.template.db.utils;

import com.google.common.io.Files;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public static boolean isChild(Path parent,Path child)
    {
        Path parentNormalize = parent.normalize();
        Path childNormalize = child.normalize();

        if (childNormalize.startsWith(parentNormalize) == false) {
            return false;
        }
        return true;

    }

    public static String mimeType(String fileExtension)
    {
        MimeMappings mappings =MimeMappings.DEFAULT;
        String mimeType = mappings.get(fileExtension);

        if(mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return mimeType;

    }


    public static ResponseEntity toResponseEntity(String file_path) throws MalformedURLException {

        String fileExtension = Files.getFileExtension(file_path);

        String mimeType = FileUtil.mimeType(fileExtension);

        Resource resource = new UrlResource(Paths.get(file_path).toFile().toURI());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
