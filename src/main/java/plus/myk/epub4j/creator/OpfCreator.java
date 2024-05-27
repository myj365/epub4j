package plus.myk.epub4j.creator;

import org.dom4j.*;
import plus.myk.epub4j.constant.ConstStr;
import plus.myk.epub4j.entity.*;
import plus.myk.epub4j.util.StrUtil;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class OpfCreator {
    public static EpubFile create(Book book) {
        Document doc = DocumentHelper.createDocument();
        Element root = createRoot(doc);

        createMetadata(root, book);
        final String ncxId = createManifest(root, book.getResources());
        createSpine(root, ncxId, book.getSpines());
        createGuide(root, book.getGuides());


        String xml = StrUtil.xmlPretty(doc);

        EpubFile epubFile = new EpubFile();
        epubFile.setPath(ConstStr.opfFilePath);
        epubFile.setContent(xml.getBytes(book.getCharset()));
        return epubFile;
    }

    private static Element createRoot(Document doc) {
        Element root = doc.addElement("package");

        root.addAttribute("version", "2.0");
        root.addAttribute("unique-identifier", "PrimaryID");

        root.addNamespace("", "http://www.idpf.org/2007/opf");

        return root;
    }

    private static void createMetadata(Element root, Book book) {
        Element metadata = root.addElement("metadata");

        Namespace dc = new Namespace("dc", "http://purl.org/dc/elements/1.1");
        Namespace opf = new Namespace("opf", "http://www.idpf.org/2007/opf");

        metadata.add(dc);
        metadata.add(opf);

        metadata.addElement(new QName("identifier", dc)).addAttribute("opf:scheme", "ISBN");

        if (StrUtil.notEmpty(book.getTitle())) metadata.addElement(new QName("title", dc)).setText(book.getTitle());
        if (StrUtil.notEmpty(book.getAuthor())) metadata.addElement(new QName("author", dc)).setText(book.getAuthor());
        if (StrUtil.notEmpty(book.getPublisher())) metadata.addElement(new QName("publisher", dc)).setText(book.getPublisher());
        if (StrUtil.notEmpty(book.getLanguage())) metadata.addElement(new QName("language", dc)).setText(book.getLanguage());
        if (StrUtil.notEmpty(book.getDate())) metadata.addElement(new QName("date", dc)).setText(book.getDate());
        if (StrUtil.notEmpty(book.getSubject())) metadata.addElement(new QName("subject", dc)).setText(book.getSubject());
        if (StrUtil.notEmpty(book.getDescription())) metadata.addElement(new QName("description", dc)).setText(book.getDescription());
        if (StrUtil.notEmpty(book.getSource())) metadata.addElement(new QName("source", dc)).setText(book.getSource());
        if (StrUtil.notEmpty(book.getRights())) metadata.addElement(new QName("rights", dc)).setText(book.getRights());
    }

    private static String createManifest(Element root, List<Resource> resources) {
        resources = resources == null ? Collections.emptyList() : resources;

        Element manifest = root.addElement("manifest");

        final String ncxId = UUID.randomUUID().toString();
        Element ncxElement = manifest.addElement("item");
        ncxElement.addAttribute("id", ncxId);
        ncxElement.addAttribute("href", ConstStr.ncxFileName);
        ncxElement.addAttribute("media-type", ConstStr.MediaType.ncx);

        for (Resource resource : resources) {
            if (resource != null) {
                resource.setId(UUID.randomUUID().toString());

                Element item = manifest.addElement("item");
                item.addAttribute("id", resource.getId());
                item.addAttribute("href", resource.getPath());
                item.addAttribute("media-type", resource.getMediaType());
            }
        }

        return ncxId;
    }

    private static void createSpine(Element root, final String ncxId, List<Spine> spines) {
        spines = spines == null ? Collections.emptyList() : spines;

        Element spineElement = root.addElement("spine");
        spineElement.addAttribute("toc", ncxId);

        for (Spine spine : spines) {
            if (spine != null && spine.getResource() != null) {
                Element item = spineElement.addElement("itemref");

                item.addAttribute("idref", spine.getResource().getId());
                if (spine.getLinear() != null) {
                    item.addAttribute("linear", spine.getLinear().name());
                }
            }
        }
    }

    private static void createGuide(Element root, List<Guide> guides) {
        if (guides != null && !guides.isEmpty()) {
            Element element = root.addElement("guide");

            for (Guide guide : guides) {
                if (guide != null && guide.getResource() != null) {
                    Element reference = element.addElement("reference");

                    if (guide.getType() != null) reference.addAttribute("type", guide.getType());
                    if (guide.getTitle() != null) reference.addAttribute("title", guide.getTitle());
                    if (guide.getResource().getPath() != null) reference.addAttribute("href", guide.getResource().getPath());
                }
            }
        }
    }
}
