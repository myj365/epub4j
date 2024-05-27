package plus.myj.epub4j.creator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import plus.myj.epub4j.constant.ConstStr;
import plus.myj.epub4j.entity.Book;
import plus.myj.epub4j.entity.Catalog;
import plus.myj.epub4j.entity.EpubFile;
import plus.myj.epub4j.util.StrUtil;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class NcxCreator {
    public static EpubFile create(Book book) {
        Document doc = DocumentHelper.createDocument();

        Element root = createRoot(doc);
        if (book.getCatalog() != null) {
            createHead(root, book.getCatalog());
            createInfo(root, book);
            createNavMap(root, book.getCatalog());
        }

        String xml = StrUtil.xmlPretty(doc);

        EpubFile epubFile = new EpubFile();
        epubFile.setPath(ConstStr.ncxFilePath);
        epubFile.setContent(xml.getBytes(book.getCharset()));
        return epubFile;
    }

    private static Element createRoot(Document doc) {
        doc.addDocType("ncx", "-//NISO//DTD ncx 2005-1//EN\" \"http://www.daisy.org/z3986/2005/ncx-2005-1.dtd", null);
        Element root = doc.addElement("ncx");

        root.addAttribute("version", "2005-1");
        root.addAttribute("xml:lang", "zh-CN");

        root.addNamespace("", "http://www.daisy.org/z3986/2005/ncx/");

        return root;
    }

    private static void createHead(Element root, Catalog catalog) {
        Element head = root.addElement("head");

        head.addElement("meta").addAttribute("name", "dtb:uid").addAttribute("content", UUID.randomUUID().toString());
        head.addElement("meta").addAttribute("name", "dtb:depth").addAttribute("content", String.valueOf(catalog.getDepth()));
        head.addElement("meta").addAttribute("name", "dtb:totalPageCount").addAttribute("content", "0");
        head.addElement("meta").addAttribute("name", "dtb:maxPageNumber").addAttribute("content", "0");
    }

    private static void createInfo(Element root, Book book) {
        if (StrUtil.notEmpty(book.getTitle())) root.addElement("docTitle").addElement("text").setText(book.getTitle());
        if (StrUtil.notEmpty(book.getAuthor())) root.addElement("docAuthor").addElement("text").setText(book.getTitle());
    }
    private static void createNavMap(Element root, Catalog catalog) {
        Element navMap = root.addElement("navMap");

        List<Catalog.NavPoint> navPoints = catalog.getNavPoints();
        if (navPoints != null && !navPoints.isEmpty()) {
            AtomicInteger atomicInteger = new AtomicInteger(0);

            for (Catalog.NavPoint navPoint : navPoints) {
                addNavPoint(navPoint, navMap, atomicInteger);
            }
        }
    }

    private static void addNavPoint(Catalog.NavPoint navPoint, Element element, AtomicInteger atomicInteger) {
        if (navPoint != null && StrUtil.notEmpty(navPoint.getTitle()) && navPoint.getResource() != null) {
            final int i = atomicInteger.incrementAndGet();

            Element navPointElement = element.addElement("navPoint");
            navPointElement.addAttribute("id", "num_" + i);
            navPointElement.addAttribute("playOrder", String.valueOf(i));

            navPointElement.addElement("navLabel").addElement("text").setText(navPoint.getTitle());
            navPointElement.addElement("content").addAttribute("src", StrUtil.nullToEmpty(navPoint.getResource().getPath()));

            List<Catalog.NavPoint> navPoints = navPoint.getNavPoints();
            if (navPoints != null && !navPoints.isEmpty()) {
                for (Catalog.NavPoint point : navPoints) {
                    addNavPoint(point, element, atomicInteger);
                }
            }
        }
    }
}
