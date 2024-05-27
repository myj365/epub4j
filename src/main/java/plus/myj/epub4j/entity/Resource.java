package plus.myj.epub4j.entity;

/**
 * 资源，指在电子书中出现的文件
 */
public class Resource {
    /**
     * 资源id<br />
     * 自动填充
     */
    private String id;
    /**
     * 资源文件路径，包括文件名
     */
    private String path;
    /**
     * 文件类型<br />
     * 如：
     * <ul>
     *     <li>xxx.css: text/css</li>
     *     <li>xxx.html: application/xhtml+xml</li>
     *     <li>xxx.jpg: image/jpeg</li>
     * </ul>
     */
    private String mediaType;
    /**
     * 内容，字节数组
     */
    private byte[] content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
