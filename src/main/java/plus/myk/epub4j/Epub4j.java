package plus.myk.epub4j;

import plus.myk.epub4j.creator.NcxCreator;
import plus.myk.epub4j.creator.OpfCreator;
import plus.myk.epub4j.creator.ResourceFileCreator;
import plus.myk.epub4j.creator.StandardFIleCreator;
import plus.myk.epub4j.entity.Book;
import plus.myk.epub4j.entity.EpubFile;
import plus.myk.epub4j.entity.Resource;
import plus.myk.epub4j.parser.NcxParser;
import plus.myk.epub4j.parser.OpfParser;
import plus.myk.epub4j.parser.StandardFIleParser;
import plus.myk.epub4j.util.ZipUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Epub4j {
    public static byte[] generateByteArray(Book book) {
        Objects.requireNonNull(book);

        if (book.getCharset() == null) {
            book.setCharset(StandardCharsets.UTF_8);
        }

        List<EpubFile> epubFiles = new ArrayList<>();
        epubFiles.add(StandardFIleCreator.createMimetype(book.getCharset()));
        epubFiles.add(StandardFIleCreator.createContainer(book.getCharset()));
        epubFiles.add(OpfCreator.create(book));
        epubFiles.add(NcxCreator.create(book));
        epubFiles.addAll(ResourceFileCreator.create(book));

        try {
            return ZipUtil.zip(epubFiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateFile(Book book, Path path) {
        Objects.requireNonNull(path);

        try {
            Path parent = path.getParent();
            if (Files.notExists(parent)) {
                Files.createDirectories(parent);
            }

            byte[] bytes = generateByteArray(book);
            Files.write(path, bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Book parseByteArray(byte[] bytes, Charset charset) {
        Objects.requireNonNull(bytes);
        Objects.requireNonNull(charset);

        Book book = new Book();
        book.setCharset(charset);

        try {
            List<EpubFile> epubFiles = ZipUtil.unzip(bytes, charset);
            Map<String, EpubFile> epubFileMap = StandardFIleParser.convert(epubFiles);

            StandardFIleParser.parseMimetype(epubFileMap, book.getCharset());
            EpubFile opfEpubFile = StandardFIleParser.parseContainer(epubFileMap, book.getCharset());
            Resource ncxResource = OpfParser.parseOpf(opfEpubFile, epubFileMap, book);
            NcxParser.parseNcx(book, ncxResource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return book;
    }

    public static Book parseFile(Path path, Charset charset) {
        Objects.requireNonNull(path);

        if (Files.notExists(path)) {
            throw new RuntimeException("File not found: " + path);
        }

        try {
            byte[] bytes = Files.readAllBytes(path);
            return parseByteArray(bytes, charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
