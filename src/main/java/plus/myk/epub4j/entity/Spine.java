package plus.myk.epub4j.entity;

/**
 * 页面顺序
 */
public class Spine {
    private Resource resource;
    private Linear linear;

    public enum Linear {
        yes, no;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Linear getLinear() {
        return linear;
    }

    public void setLinear(Linear linear) {
        this.linear = linear;
    }

    @Override
    public String toString() {
        return "Spine{" +
                "resource=" + resource +
                ", linear=" + linear +
                '}';
    }
}
