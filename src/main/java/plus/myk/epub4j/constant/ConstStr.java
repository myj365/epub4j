package plus.myk.epub4j.constant;

public class ConstStr {
    public static final String fileSeparator = "/";

    /**
     * 容纳所有相关文件的文件夹名称
     */
    public static final String folderName = "OEBPS";

    public static final String opfFileName = "content.opf";
    public static final String ncxFileName = "toc.ncx";

    public static final String opfFilePath = folderName + fileSeparator + opfFileName;
    public static final String ncxFilePath = folderName + fileSeparator + ncxFileName;

    public static final String containerFilePath = "META-INF/container.xml";

    public static final class MediaType {
        public static final String css = "text/css";
        public static final String xhtml = "application/xhtml+xml";
        public static final String jpeg = "image/jpeg";
        public static final String jpg = "image/jpeg";
        public static final String ncx = "application/x-dtbncx+xml";
        public static final String opf = "application/oebps-package+xml";
    }
}
