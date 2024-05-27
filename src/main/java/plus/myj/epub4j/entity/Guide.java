package plus.myj.epub4j.entity;

public class Guide {
    private String type;
    private String title;
    private Resource resource;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Guide{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", resource=" + resource +
                '}';
    }
}
