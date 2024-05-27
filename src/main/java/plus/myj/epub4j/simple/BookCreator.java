package plus.myj.epub4j.simple;

import plus.myj.epub4j.entity.*;
import plus.myj.epub4j.util.StrUtil;
import plus.myj.epub4j.constant.ConstStr;
import plus.myj.epub4j.simple.entity.Chapter;
import plus.myj.epub4j.simple.entity.EBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookCreator {
    public static Book convertToBook(EBook ebook) {
        Objects.requireNonNull(ebook);
        Objects.requireNonNull(ebook.getCharset());
        Objects.requireNonNull(ebook.getChapters());

        Book book = new Book();
        book.setTitle(ebook.getTitle());
        book.setAuthor(ebook.getAuthor());
        book.setDescription(ebook.getDescription());
        book.setDate(LocalDate.now().toString());
        book.setCharset(ebook.getCharset());
        book.setResources(createResources(ebook, book));
        book.setSpines(createSpines(book));
        book.setCatalog(createCatalog(ebook));

        return book;
    }

    private static List<Resource> createResources(EBook ebook, Book book) {
        List<Chapter> chapters = ebook.getChapters();

        List<Resource> resources = new ArrayList<>();
        createCover(ebook, resources, book);
        resources.add(HtmlCreator.createCssResource(ebook.getCharset()));
        resources.addAll(HtmlCreator.create(chapters, ebook.getCharset()));


        return resources;
    }

    private static List<Spine> createSpines(Book book) {
        return book.getResources().stream()
                .filter(resource -> ConstStr.MediaType.xhtml.equals(resource.getMediaType()))
                .map(resource -> {
                    Spine spine = new Spine();
                    spine.setResource(resource);
                    return spine;
                }).collect(Collectors.toList());
    }

    private static Catalog createCatalog(EBook eBook) {
        List<Catalog.NavPoint> navPoints = eBook.getChapters().stream()
                .filter(chapter -> ConstStr.MediaType.xhtml.equals(chapter.getResource().getMediaType()))
                .map(chapter -> {
                    Catalog.NavPoint point = new Catalog.NavPoint();
                    point.setTitle(chapter.getTitle());
                    point.setResource(chapter.getResource());
                    return point;
                }).toList();

        Catalog catalog = new Catalog();
        catalog.setDepth(1);
        catalog.setNavPoints(navPoints);

        return catalog;
    }

    private static void createCover(EBook ebook, List<Resource> resources, Book book) {
        Resource coverHtmlResource;

        EBook.Cover cover = ebook.getCover();
        if (cover != null && StrUtil.notEmpty(cover.getName()) && StrUtil.notEmpty(cover.getImageMediaType()) && cover.getImageData() != null) {
            Resource resource = new Resource();
            resource.setPath("images" + StrUtil.addPrefixIfNot(cover.getName(), ConstStr.fileSeparator));
            resource.setContent(cover.getImageData());
            resource.setMediaType(cover.getImageMediaType());

            resources.add(resource);
            coverHtmlResource = HtmlCreator.createCoverHtmlResource(ebook, resource);
        } else {
            coverHtmlResource = HtmlCreator.createCoverHtmlResource(ebook, null);
        }

        resources.add(coverHtmlResource);


        Chapter coverChapter = new Chapter();
        coverChapter.setTitle("封面");
        coverChapter.setResource(coverHtmlResource);

        List<Chapter> chapters = new ArrayList<>();
        chapters.add(coverChapter);
        chapters.addAll(ebook.getChapters());
        ebook.setChapters(chapters);


        Guide guide = new Guide();
        guide.setTitle("封面");
        guide.setType("cover");
        guide.setResource(coverHtmlResource);
        book.setGuides(Collections.singletonList(guide));
    }
}
