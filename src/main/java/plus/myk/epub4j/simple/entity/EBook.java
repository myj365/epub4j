package plus.myk.epub4j.simple.entity;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 图书
 */
public class EBook {
    /**
     * 书名
     */
    private String title;
    /**
     * 电子书的作者
     */
    private String author;
    /**
     * 电子书的内容介绍
     */
    private String description;
    /**
     * 编码
     */
    private Charset charset;
    /**
     * 章节列表
     */
    private List<Chapter> chapters;
    /**
     * 封面图片
     */
    private Cover cover;

    /**
     * 封面数据
     */
    public static final class Cover {
        /**
         * 图片名称
         */
        private String name;
        /**
         * 图片类型
         */
        private String imageMediaType;
        /**
         * 图片数据
         */
        private byte[] imageData;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageMediaType() {
            return imageMediaType;
        }

        public void setImageMediaType(String imageMediaType) {
            this.imageMediaType = imageMediaType;
        }

        public byte[] getImageData() {
            return imageData;
        }

        public void setImageData(byte[] imageData) {
            this.imageData = imageData;
        }
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}
