package plus.myj.epub4j;

import plus.myj.epub4j.entity.Book;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestParse {
    public static void main(String[] args) throws Exception {
        // 运行该测试前，建议先运行其他两个中的任何一个

        Path path = Path.of("file/template.epub");
        if (Files.notExists(path)) {
            System.out.println("file is not found: " + path);

            path = Path.of("file/simpleEpub.epub");
            if (Files.notExists(path)) {
                System.out.println("file is not found: " + path);
            }
        }

        Book book = Epub4j.parseFile(path, StandardCharsets.UTF_8);
        System.out.println("解析完成，电子书名称：" + book.getTitle());
    }
}
