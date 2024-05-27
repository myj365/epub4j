package plus.myk.epub4j.util;

import plus.myk.epub4j.entity.EpubFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static byte[] zip(List<EpubFile> epubFiles) throws IOException {
        Objects.requireNonNull(epubFiles);

        if (!epubFiles.isEmpty()) {
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                try (ZipOutputStream zip = new ZipOutputStream(output, StandardCharsets.UTF_8)) {
                    for (EpubFile epubFile : epubFiles) {
                        if (epubFile != null) {
                            ZipEntry zipEntry = new ZipEntry(epubFile.getPath());

                            zip.putNextEntry(zipEntry);
                            zip.write(epubFile.getContent());
                            zip.closeEntry();
                        }
                    }
                }

                return output.toByteArray();
            }
        }

        return new byte[0];
    }

    public static List<EpubFile> unzip(byte[] bytes, Charset charset) throws IOException {
        Objects.requireNonNull(bytes);
        Objects.requireNonNull(charset);

        List<EpubFile> epubFiles = new ArrayList<>();

        try (ByteArrayInputStream input = new ByteArrayInputStream(bytes)) {
            try (ZipInputStream zip = new ZipInputStream(input, charset)) {
                ZipEntry zipEntry;

                while ((zipEntry = zip.getNextEntry()) != null) {
                    EpubFile epubFile = new EpubFile();

                    epubFile.setPath(zipEntry.getName());
                    if (!zipEntry.isDirectory()) {
                        epubFile.setContent(zip.readAllBytes());
                    }

                    epubFiles.add(epubFile);
                }
            }
        }

        return epubFiles;
    }
}
