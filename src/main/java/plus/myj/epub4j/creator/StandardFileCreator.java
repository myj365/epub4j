package plus.myj.epub4j.creator;

import plus.myj.epub4j.constant.ConstStr;
import plus.myj.epub4j.entity.EpubFile;

import java.nio.charset.Charset;

public class StandardFileCreator {
    public static EpubFile createMimetype(Charset charset) {
        EpubFile epubFile = new EpubFile();

        epubFile.setPath("mimetype");
        epubFile.setContent("application/epub+zip".getBytes(charset));

        return epubFile;
    }

    public static EpubFile createContainer(Charset charset) {
        EpubFile epubFile = new EpubFile();

        epubFile.setPath(ConstStr.containerFilePath);

        String content = """
                <?xml version="1.0" encoding="UTF-8" ?>
                <container version="1.0" xmlns="urn:oasis:names:tc:opendocument:xmlns:container">
                   <rootfiles>
                      <rootfile full-path=\"""" +
                ConstStr.opfFilePath +
                "\"" +
                """
                   media-type="application/oebps-package+xml"/>
                   </rootfiles>
                </container>""";
        epubFile.setContent(content.getBytes(charset));
        return epubFile;
    }
}
