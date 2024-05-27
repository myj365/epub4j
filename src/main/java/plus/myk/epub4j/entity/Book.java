package plus.myk.epub4j.entity;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

public class Book {
    /**
     * 书名
     */
    private String title;
    /**
     * 电子书的作者
     */
    private String author;
    /**
     * 出版社
     */
    private String publisher;
    /**
     * 语言
     */
    private String language;
    /**
     * 事件日期
     */
    private String date;
    /**
     * 主题词或关键字
     */
    private String subject;
    /**
     * 电子书的内容介绍
     */
    private String description;
    /**
     * 图书资源或素材的来源
     */
    private String source;
    /**
     * 版权描述
     */
    private String rights;
    /**
     * 编码<br>
     * 默认为 utf-8
     */
    private Charset charset;
    /**
     * 资源列表
     */
    private List<Resource> resources;
    /**
     * 页面顺序列表
     */
    private List<Spine> spines;
    private List<Guide> guides;
    /**
     * 目录
     */
    private Catalog catalog;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Spine> getSpines() {
        return spines;
    }

    public void setSpines(List<Spine> spines) {
        this.spines = spines;
    }

    public List<Guide> getGuides() {
        return guides;
    }

    public void setGuides(List<Guide> guides) {
        this.guides = guides;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(publisher, book.publisher) && Objects.equals(language, book.language) && Objects.equals(date, book.date) && Objects.equals(subject, book.subject) && Objects.equals(description, book.description) && Objects.equals(source, book.source) && Objects.equals(rights, book.rights) && Objects.equals(charset, book.charset) && Objects.equals(resources, book.resources) && Objects.equals(spines, book.spines) && Objects.equals(guides, book.guides) && Objects.equals(catalog, book.catalog);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(author);
        result = 31 * result + Objects.hashCode(publisher);
        result = 31 * result + Objects.hashCode(language);
        result = 31 * result + Objects.hashCode(date);
        result = 31 * result + Objects.hashCode(subject);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(source);
        result = 31 * result + Objects.hashCode(rights);
        result = 31 * result + Objects.hashCode(charset);
        result = 31 * result + Objects.hashCode(resources);
        result = 31 * result + Objects.hashCode(spines);
        result = 31 * result + Objects.hashCode(guides);
        result = 31 * result + Objects.hashCode(catalog);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", language='" + language + '\'' +
                ", date='" + date + '\'' +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", source='" + source + '\'' +
                ", rights='" + rights + '\'' +
                ", charset=" + charset +
                ", resources=" + resources +
                ", spines=" + spines +
                ", guides=" + guides +
                ", catalog=" + catalog +
                '}';
    }
}
