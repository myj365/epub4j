package plus.myk.epub4j.parser;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import plus.myk.epub4j.entity.Book;
import plus.myk.epub4j.entity.Catalog;
import plus.myk.epub4j.entity.Resource;

import java.util.*;

public class NcxParser {
    public static void parseNcx(Book book, Resource ncxResource) throws Exception {
        Objects.requireNonNull(book);
        Objects.requireNonNull(book.getCharset());
        Objects.requireNonNull(ncxResource);

        String ncxXml = new String(ncxResource.getContent(), book.getCharset());
        Element root = DocumentHelper.parseText(ncxXml).getRootElement();

        Catalog catalog = new Catalog();
        parseHead(root, catalog);
        parseNavMap(root, catalog, book);
        book.setCatalog(catalog);
    }

    private static void parseHead(Element root, Catalog catalog) {
        List<Element> elements = Optional.ofNullable(root)
                .map(element -> element.element("head"))
                .map(element -> element.elements("meta"))
                .orElse(new ArrayList<>());

        for (Element element : elements) {
            String name = element.attributeValue("name");
            if ("dtb:depth".equals(name)) {
                try {
                    String content = element.attributeValue("content");
                    int depth = Integer.parseInt(content);
                    catalog.setDepth(depth);
                    break;
                } catch (Exception ignore) {}
            }
        }
    }

    private static void parseNavMap(Element root, Catalog catalog, Book book) {
        Element navMap = root.element("navMap");
        if (navMap != null) {
            Map<String, Resource> resourcePathMap = createResourcePathMap(book.getResources());
            List<Catalog.NavPoint> navPoints = new ArrayList<>();

            List<Element> elements = navMap.elements("navPoint");
            for (Element element : elements) {
                Catalog.NavPoint point = new Catalog.NavPoint();
                navPoints.add(point);
                parseNavPoint(element, point, resourcePathMap);
            }

            catalog.setNavPoints(navPoints);
        }
    }

    private static void parseNavPoint(Element navPointElement, Catalog.NavPoint navPoint, final Map<String, Resource> resourcePathMap) {
        String title = Optional.ofNullable(navPointElement.element("navLabel"))
                .map(element -> element.element("text"))
                .map(Element::getText)
                .orElse("");

        String src = Optional.ofNullable(navPointElement.element("content"))
                .map(element -> element.attributeValue("src"))
                .orElse("");


        List<Catalog.NavPoint> navPoints = new ArrayList<>();

        List<Element> elements = navPointElement.elements("navPoint");
        for (Element element : elements) {
            if (element != null) {
                Catalog.NavPoint point = new Catalog.NavPoint();
                navPoints.add(point);
                parseNavPoint(element, point, resourcePathMap);
            }
        }

        navPoint.setTitle(title);
        navPoint.setResource(resourcePathMap.get(src));
        navPoint.setNavPoints(navPoints);
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
