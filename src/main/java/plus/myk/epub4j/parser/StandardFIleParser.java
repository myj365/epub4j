package plus.myk.epub4j.parser;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import plus.myk.epub4j.constant.ConstStr;
import plus.myk.epub4j.entity.EpubFile;
import plus.myk.epub4j.util.StrUtil;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StandardFIleParser {
    public static Map<String, EpubFile> convert(List<EpubFile> epubFiles) {
        Map<String, EpubFile> map = new HashMap<>();

        for (EpubFile epubFile : epubFiles) {
            if (epubFile != null) {
                map.put(epubFile.getPath(), epubFile);
            }
        }

        return map;
    }

    public static void parseMimetype(Map<String, EpubFile> epubFileMap, Charset charset) {
        EpubFile epubFile = epubFileMap.get("mimetype");
        if (epubFile != null) {
            String s = new String(epubFile.getContent(), charset);

            if (s.strip().equals("application/epub+zip")) {
                return;
            }
        }
        throw new RuntimeException("epub file does not contain MIME type");
    }

    public static EpubFile parseContainer(Map<String, EpubFile> epubFileMap, Charset charset) throws Exception {
        EpubFile epubFile = epubFileMap.get(ConstStr.containerFilePath);
        if (epubFile != null) {
            String xml = new String(epubFile.getContent(), charset);

            Document doc = DocumentHelper.parseText(xml);

            Optional<Element> rootfile = Optional.ofNullable(doc.getRootElement())
                    .map(element -> element.element("rootfiles"))
                    .map(element -> element.elements("rootfile"))
                    .map(elements -> {
                        if (!elements.isEmpty()) {
                            return elements.get(0);
                        } else {
                            return null;
                        }
                    });

            String opfFilePath = rootfile.map(element -> element.attributeValue("full-path")).orElse("");
            String opfFileMediaType = rootfile.map(element -> element.attributeValue("media-type")).orElse("");

            if (!ConstStr.MediaType.opf.equals(opfFileMediaType)) {
                throw new RuntimeException(ConstStr.containerFilePath + " file does not contain right opf file media type: " + opfFileMediaType);
            }

            EpubFile opfEpubFile = epubFileMap.get(opfFilePath);
            if (StrUtil.isEmpty(opfFilePath) || opfEpubFile == null) {
                throw new RuntimeException(ConstStr.containerFilePath + " file does not contain right opf file path: " + opfFilePath);
            }

            return opfEpubFile;
        }

        throw new RuntimeException("epub file does not contain " + ConstStr.containerFilePath);
    }
}
