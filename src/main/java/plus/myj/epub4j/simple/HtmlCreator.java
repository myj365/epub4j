package plus.myj.epub4j.simple;

import plus.myj.epub4j.util.StrUtil;
import plus.myj.epub4j.constant.ConstStr;
import plus.myj.epub4j.entity.Resource;
import plus.myj.epub4j.simple.entity.Chapter;
import plus.myj.epub4j.simple.entity.EBook;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class HtmlCreator {
    public static List<Resource> create(List<Chapter> chapters, Charset charset) {
        Objects.requireNonNull(chapters);
        Objects.requireNonNull(charset);

        List<Resource> htmlResources = new ArrayList<>();
        for (int i = 0; i < chapters.size(); i++) {
            Resource htmlResource = createHtmlResource(chapters.get(i), charset, i);
            htmlResources.add(htmlResource);
        }
        return htmlResources;
    }

    private static Resource createHtmlResource(Chapter chapter, Charset charset, int index) {
        String htmlTemplate = """
                <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">
                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=%s"/>
                    <link rel="stylesheet" type="text/css" href="css/main.css"/>
                    <title>%s</title>
                </head>
                <body>
                <div>
                    <h3>%s</h3>
                %s
                </div>
                </body>
                </html>""";

        String title = StrUtil.nullToEmpty(chapter.getTitle());
        String content = StrUtil.nullToEmpty(chapter.getContent());

        StringBuilder builder = new StringBuilder();
        String[] split = content.split("\n");
        for (String s : split) {
            s = "    <p>" + StrUtil.nullToEmpty(s) + "</p>\n";
            builder.append(s);
        }

        content = builder.toString();


        final String html = String.format(htmlTemplate, charset.toString(), title, title, content);

        Resource resource = new Resource();
        resource.setPath("chapter" + StrUtil.getFormatNumber(index) + ".html");
        resource.setContent(html.getBytes(charset));
        resource.setMediaType(ConstStr.MediaType.xhtml);

        chapter.setResource(resource);
        return resource;
    }

    public static Resource createCssResource(Charset charset) {
        Objects.requireNonNull(charset);

        Resource resource = new Resource();
        resource.setPath("css/main.css");
        resource.setMediaType("text/css");
        resource.setContent(css.getBytes(charset));
        return resource;
    }

    private static final String css = """
            @font-face {
                font-family:"cnepub";
                src:url(res:///opt/sony/ebook/FONT/tt0011m_.ttf), url(res:///tt0011m_.ttf);
            }
            body {
                padding: 0%;
                margin-top: 0%;
                margin-bottom: 0%;
                margin-left: 1%;
                margin-right: 1%;
                line-height:130%;
                text-align: justify;
                font-family:"cnepub", serif;
            }
            div {
                margin:0px;
                padding:0px;
                line-height:130%;
                text-align: justify;
                font-family:"cnepub", serif;
            }
            p {
                text-align: justify;
                text-indent: 2em;
                line-height:130%;
            }
            .cover {
                width:100%;
                padding:0px;
            }
            .center {
                text-align: center;
                margin-left: 0%;
                margin-right: 0%;
            }
            .left {
                text-align: center;
                margin-left: 0%;
                margin-right: 0%;
            }
            .right {
                text-align: right;
                margin-left: 0%;
                margin-right: 0%;
            }
            .quote {
                margin-top: 0%;
                margin-bottom: 0%;
                margin-left: 1em;
                margin-right: 1em;
                text-align: justify;
                font-family:"cnepub", serif;
            }
            h1 {
                line-height:130%;
                text-align: center;
                font-weight:bold;
                font-size:xx-large;
            }
            h2 {
                line-height:130%;
                text-align: center;
                font-weight:bold;
                font-size:x-large;
            }
            h3 {
                line-height:130%;
                text-align: center;
                font-weight:bold;
                font-size:large;
            }
            h4 {
                line-height:130%;
                text-align: center;
                font-weight:bold;
                font-size:medium;
            }
            h5 {
                line-height:130%;
                text-align: center;
                font-weight:bold;
                font-size:small;
            }
            h6 {
                line-height:130%;
                text-align: center;
                font-weight:bold;
                font-size:x-small;
            }
            """;

    private static final String coverHtmlTemplate = """
            <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=%s"/>
                <link rel="stylesheet" type="text/css" href="css/main.css"/>
                <title>bookCover</title>
            </head>
            <body>
            <div>
                %s
                <h1>%s</h1>
                <h2>%s</h2>
                <b>简介</b>
                <p>%s</p>
            </div>
            </body>
            </html>
            """;
    public static Resource createCoverHtmlResource(EBook ebook, Resource coverImageResource) {
        Objects.requireNonNull(ebook);

        String charset = String.valueOf(ebook.getCharset());
        String coverImage = coverImageResource == null ? "" : String.format("<div style=\"text-align:center\"><img class=\"cover\" src=\"%s\"/></div>", coverImageResource.getPath());
        String title = ebook.getTitle();
        String author = ebook.getAuthor();
        String description = ebook.getDescription();

        String coverHtml = String.format(coverHtmlTemplate, charset, coverImage, title, author, description);
        Resource resource = new Resource();
        resource.setPath("coverPage.html");
        resource.setContent(coverHtml.getBytes(ebook.getCharset()));
        resource.setMediaType(ConstStr.MediaType.xhtml);

        return resource;
    }
}
