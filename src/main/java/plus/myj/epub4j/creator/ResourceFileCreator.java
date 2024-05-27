package plus.myj.epub4j.creator;

import plus.myj.epub4j.entity.Book;
import plus.myj.epub4j.entity.EpubFile;
import plus.myj.epub4j.entity.Resource;
import plus.myj.epub4j.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

public class ResourceFileCreator {
    public static List<EpubFile> create(Book book) {
        List<EpubFile> epubFiles = new ArrayList<>();

        List<Resource> resources = book.getResources();
        if (resources != null) {
            for (Resource resource : resources) {
                if (resource != null) {
                    EpubFile epubFile = new EpubFile();
                    epubFile.setPath(addPrefix(resource.getPath()));
                    epubFile.setContent(resource.getContent());
                    epubFiles.add(epubFile);
                }
            }
        }

        return epubFiles;
    }

    private static String addPrefix(String path) {
        path = StrUtil.nullToEmpty(path);
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return "OEBPS/" + path;
    }
}
