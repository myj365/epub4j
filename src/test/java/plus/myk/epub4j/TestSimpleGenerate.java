package plus.myk.epub4j;

import plus.myk.epub4j.simple.SimpleEpub4j;
import plus.myk.epub4j.simple.entity.Chapter;
import plus.myk.epub4j.simple.entity.EBook;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TestSimpleGenerate {
    public static void main(String[] args) {
        Path path = Path.of("file/result/simpleEpub.epub");

        EBook eBook = createEBook();
        SimpleEpub4j.generateFile(eBook, path);
    }

    private static EBook createEBook() {
        EBook book = new EBook();
        book.setTitle("简易电子书");
        book.setAuthor("佚名");
        book.setDescription("简介");
        book.setCharset(StandardCharsets.UTF_8);

        List<Chapter> chapters = new ArrayList<>();
        chapters.add(createChapter1());
        chapters.add(createChapter2());

        book.setChapters(chapters);
        return book;
    }

    private static Chapter createChapter1() {
        Chapter chapter = new Chapter();
        chapter.setTitle("第一章 新的开始");
        chapter.setContent("""
                第一行
                第二行
                第三行
                """);
        return chapter;
    }
    private static Chapter createChapter2() {
        Chapter chapter = new Chapter();
        chapter.setTitle("第二章 旧的回忆");
        chapter.setContent("""
                第一行
                第二行
                第三行
                aaa<strong>bbb</strong>ccc
                第五行
                """);
        return chapter;
    }
}
