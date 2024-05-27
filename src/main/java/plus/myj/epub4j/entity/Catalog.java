package plus.myj.epub4j.entity;

import java.util.List;

/**
 * 目录
 */
public class Catalog {
    private List<NavPoint> navPoints;
    /**
     * 目录层级深度
     */
    private int depth;

    public List<NavPoint> getNavPoints() {
        return navPoints;
    }

    public void setNavPoints(List<NavPoint> navPoints) {
        this.navPoints = navPoints;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public static final class NavPoint {
        private String title;
        private Resource resource;
        private List<NavPoint> navPoints;

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

        public List<NavPoint> getNavPoints() {
            return navPoints;
        }

        public void setNavPoints(List<NavPoint> navPoints) {
            this.navPoints = navPoints;
        }
    }
}
