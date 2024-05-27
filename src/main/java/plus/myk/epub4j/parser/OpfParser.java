package plus.myk.epub4j.parser;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import plus.myk.epub4j.constant.ConstStr;
import plus.myk.epub4j.entity.*;
import plus.myk.epub4j.util.StrUtil;

import java.util.*;

public class OpfParser {
    public static Resource parseOpf(EpubFile opfEpubFile, Map<String, EpubFile> epubFileMap, Book book) throws Exception {
        Objects.requireNonNull(opfEpubFile);
        Objects.requireNonNull(epubFileMap);
        Objects.requireNonNull(book.getCharset());
        Objects.requireNonNull(book);


        String opfXml = new String(opfEpubFile.getContent(), book.getCharset());
        Element root = DocumentHelper.parseText(opfXml).getRootElement();

        parseMetadata(root, book);
        parseManifest(root, book, epubFileMap);
        Resource ncxResource = parseSpine(root, book);
        parseGuide(root, book);

        return ncxResource;
    }

    private static void parseMetadata(Element root, Book book) {
        Element metadata = root.element("metadata");

        if (metadata != null) {
            if (StrUtil.notEmpty(getText(metadata, "title"))) book.setTitle(getText(metadata, "title"));
            if (StrUtil.notEmpty(getText(metadata, "author"))) book.setAuthor(getText(metadata, "author"));
            if (StrUtil.notEmpty(getText(metadata, "publisher"))) book.setPublisher(getText(metadata, "publisher"));
            if (StrUtil.notEmpty(getText(metadata, "language"))) book.setLanguage(getText(metadata, "language"));
            if (StrUtil.notEmpty(getText(metadata, "date"))) book.setDate(getText(metadata, "date"));
            if (StrUtil.notEmpty(getText(metadata, "subject"))) book.setSubject(getText(metadata, "subject"));
            if (StrUtil.notEmpty(getText(metadata, "description"))) book.setDescription(getText(metadata, "description"));
            if (StrUtil.notEmpty(getText(metadata, "source"))) book.setSource(getText(metadata, "source"));
            if (StrUtil.notEmpty(getText(metadata, "rights"))) book.setRights(getText(metadata, "rights"));
        }
    }

    private static String getText(Element element, String name) {
        element = element.element(name);

        if (element != null) {
            return element.getText();
        } else {
            return null;
        }
    }

    private static void parseManifest(Element root, Book book, Map<String, EpubFile> epubFileMap) {
        Element manifest = root.element("manifest");

        List<Resource> resources = new ArrayList<>();
        if (manifest != null) {
            List<Element> items = manifest.elements("item");
            if (items != null && !items.isEmpty()) {
                for (Element item : items) {
                    Resource resource = new Resource();
                    resource.setId(item.attributeValue("id"));
                    resource.setPath(item.attributeValue("href"));
                    resource.setMediaType(item.attributeValue("media-type"));

                    EpubFile epubFile = epubFileMap.get(ConstStr.folderName + ConstStr.fileSeparator + resource.getPath());
                    if (epubFile != null) {
                        resource.setContent(epubFile.getContent());
                    }

                    resources.add(resource);
                }
            }
        }

        book.setResources(resources);
    }

    private static Resource parseSpine(Element root, Book book) {
        Resource ncxResource = null;
        List<Spine> spines = new ArrayList<>();

        Element spineElement = root.element("spine");
        if (spineElement != null) {
            Map<String, Resource> resourceMap = createResourceIdMap(book.getResources());

            final String ncxId = spineElement.attributeValue("toc");
            if (StrUtil.isEmpty(ncxId)) {
                throw new RuntimeException("xxx.opf spine must have a toc attribute");
            }

            ncxResource = resourceMap.get(ncxId);
            if (ncxResource == null) {
                throw new RuntimeException("xxx.opf->manifest must have a xxx.ncx");
            } else {
                book.getResources().removeIf(resource -> ncxId.equals(resource.getId()));
            }


            List<Element> elements = spineElement.elements("itemref");
            if (elements != null && !elements.isEmpty()) {
                for (Element element : elements) {
                    Spine spine = new Spine();

                    String resourceId = element.attributeValue("idref");
                    String linear = element.attributeValue("linear");

                    Resource resource = resourceMap.get(resourceId);
                    if (resource == null) {
                        throw new RuntimeException("xxx.opf->spine->itemref->idref is not found: " + resourceId);
                    }
                    spine.setResource(resource);

                    if (StrUtil.notEmpty(linear)) {
                        if ("yes".equals(linear)) {
                            spine.setLinear(Spine.Linear.yes);
                        } else if ("no".equals(linear)) {
                            spine.setLinear(Spine.Linear.no);
                        } else {
                            throw new RuntimeException("xxx.opf->spine->itemref->linear is error: " + linear);
                        }
                    }

                    spines.add(spine);
                }
            }
        }

        book.setSpines(spines);
        if (ncxResource == null) {
            throw new RuntimeException("xxx.opf->manifest->xxx.ncx is not found");
        }
        return ncxResource;
    }

    private static void parseGuide(Element root, Book book) {
        List<Guide> guides = new ArrayList<>();

        Element guideElement = root.element("guide");
        if (guideElement != null) {
            Map<String, Resource> resourcePathMap = createResourcePathMap(book.getResources());

            List<Element> elements = guideElement.elements("reference");
            if (elements != null && !elements.isEmpty()) {
                for (Element element : elements) {
                    Guide guide = new Guide();
                    guide.setType(element.attributeValue("type"));
                    guide.setTitle(element.attributeValue("title"));

                    String href = element.attributeValue("href");
                    if (StrUtil.notEmpty(href)) {
                        guide.setResource(resourcePathMap.get(href));
                    }

                    guides.add(guide);
                }
            }
        }

        book.setGuides(guides);
    }

    private static Map<String, Resource> createResourceIdMap(List<Resource> resources) {
        Map<String, Resource> map = new HashMap<>();
        if (resources != null && !resources.isEmpty()) {
            for (Resource resource : resources) {
                map.put(resource.getId(), resource);
            }
        }
        return map;
    }
    private static Map<String, Resource> createResourcePathMap(List<Resource> resources) {
        Map<String, Resource> map = new HashMap<>();
        if (resources != null && !resources.isEmpty()) {
            for (Resource resource : resources) {
                map.put(resource.getPath(), resource);
            }
        }
        return map;
    }
}
