package plus.myk.epub4j.simple.entity;

import plus.myk.epub4j.entity.Resource;

/**
 * 章节
 */
public class Chapter {
    /**
     * 章节名
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 资源<br>
     * 内部使用
     */
    private Resource resource;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
