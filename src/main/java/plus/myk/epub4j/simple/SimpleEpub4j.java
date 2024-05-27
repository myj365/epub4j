package plus.myk.epub4j.simple;

import plus.myk.epub4j.Epub4j;
import plus.myk.epub4j.entity.Book;
import plus.myk.epub4j.simple.entity.EBook;

import java.nio.file.Path;
import java.util.Objects;

public class SimpleEpub4j {
    public static void generateFile(EBook ebook, Path path) {
        Objects.requireNonNull(ebook);
        Objects.requireNonNull(path);

        Book book = BookCreator.convertToBook(ebook);
        Epub4j.generateFile(book, path);
    }

    public static byte[] generateByteArray(EBook ebook) {
        Objects.requireNonNull(ebook);

        Book book = BookCreator.convertToBook(ebook);
        return Epub4j.generateByteArray(book);
    }
}
