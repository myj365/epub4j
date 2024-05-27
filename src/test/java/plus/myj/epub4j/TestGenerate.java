package plus.myj.epub4j;

import plus.myj.epub4j.constant.ConstStr;
import plus.myj.epub4j.entity.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class TestGenerate {
    public static void main(String[] args) {
        Path path = Path.of("file/template.epub");

        Book book = makeBook();
        Epub4j.generateFile(book, path);
    }



    private static Book makeBook() {
        Charset charset = StandardCharsets.UTF_8;

        Resource chapter1 = getChapter1(charset);
        Resource chapter2 = getChapter2();
        Resource css = getCss(charset);
        Resource cover = getCover(charset);
        Resource coverImage = getCoverImage();

        List<Resource> resources = Arrays.asList(chapter1, chapter2, css, cover, coverImage);

        Book book = new Book();
        book.setTitle("模板");
        book.setAuthor("佚名");
        book.setLanguage("zh-CN");
        book.setDescription("图书描述……");
        book.setDate(LocalDate.now().toString());
        book.setResources(resources);
        book.setCharset(charset);
        book.setSpines(getSpines(cover, chapter1, chapter2));
        book.setCatalog(createCatalog(cover, chapter1, chapter2));
        book.setGuides(createGuides(cover));

        return book;
    }


    private static Resource getChapter1(Charset charset) {
        String s = """
                <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">
                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                    <link rel="stylesheet" type="text/css" href="css/main.css"/>
                    <title>第一章 新的开始</title>
                </head>
                <body>
                <div>
                    <h3>第一章 新的开始</h3>
                    <p>第一行</p>
                    <p>第二行</p>
                    <p>第三行</p>
                </div>
                </body>
                </html>
                """;
        Resource resource = new Resource();
        resource.setPath("chapter1.html");
        resource.setMediaType(ConstStr.MediaType.xhtml);
        resource.setContent(s.getBytes(charset));
        return resource;
    }
    private static Resource getChapter2() {
        String s = """
                <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">
                <head>
                    <link rel="stylesheet" type="text/css" href="css/main.css"/>
                    <title>第二章 旧的回忆</title>
                </head>
                <body>
                <div>
                    <h3>第二章 旧的回忆</h3>
                    <p>第一行</p>
                    <p>第二行</p>
                    <p>第三行</p>
                </div>
                </body>
                </html>
                """;

        Resource resource = new Resource();
        resource.setPath("chapter2.html");
        resource.setMediaType(ConstStr.MediaType.xhtml);
        resource.setContent(s.getBytes(StandardCharsets.UTF_8));
        return resource;
    }
    private static Resource getCss(Charset charset) {
        String s = """
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
        Resource resource = new Resource();
        resource.setPath("css/main.css");
        resource.setMediaType(ConstStr.MediaType.css);
        resource.setContent(s.getBytes(charset));
        return resource;
    }
    private static Resource getCover(Charset charset) {
        String s = """
                <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">
                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                    <link rel="stylesheet" type="text/css" href="css/main.css"/>
                    <title>bookcover</title>
                </head>
                <body>
                <div>
                    <div style="text-align:center"><img class="cover" src="images/cover.jpg"/></div>
                    <h1>EPUB模板</h1>
                    <h2>这里是作者名</h2>
                    <b>简介</b>
                    <p>图书描述……</p>
                </div>
                </body>
                </html>
                """;
        Resource resource = new Resource();
        resource.setPath("coverpage.html");
        resource.setMediaType(ConstStr.MediaType.xhtml);
        resource.setContent(s.getBytes(charset));
        return resource;
    }
    private static Resource getCoverImage() {
        String s = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCALvAgUDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+iiigAooooAKKKKACiikyKAFopMiql5qVnZIzTzqu3qAcn8hzSbS1YJX2LmR60hIAySMVyV74xC/LZwbufvP3FczrerahNqMSNezCN4iTGrlVPPoOtYyxEVtqbRoSe+h6Be67p9mXEk6s6naUUgkH3rBufGUj/8AHrbqo55k5P5VySksSTjJ5PuakAxWEq8ntobxoRW+pp3WsXt7uE052EYKKOK1dO509OnArmxyK6PTx/xLl+lQm3qy2ktET/bbpSMTyYHQbuKsJrF2AASrD3HWqBHNAFPnkupPJF9DXXWZsf6uMfganXWkLAGJgM8nI4rGHSlqvazXUTpQOgTU7V2xux7nirUc8UhISVGI9DXKDijcRypOfrirWIfVGboLozrsjOM0ZHqK5NZpk5EjA+zmpEvrtCcTPj3OatYhdUL2D7nU5ornYdVuYydxEn+9x/KrMet/KPMhOfY1SrQZDozRs5orOj1i2YZfKH0IJ/lVmO9t5F3LKuPrWinF7Mhxkt0WKKarqwyrAilyKokWikyPWjIoAWijNFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUGgAopM1UvtUtNOt2nuJVCL6HJNJtLVjSvsXKTNcdcePod7C2s2kX+F2fGfwxxWLeeKNSvdwExiQtkBOCB6VjLEQW2pqqE3ud/datY2a5nuFXjoMsfyFctd+PoC5WwtzIASpeQbQfp3rmYjgSHHRDjnmsawJMbcnG4/zrB4mT20No4eK3Ohuta1C/fdLcOFPGxTgYqqBgn9KhTtUwGSax5m3qbJJbC55/GotV/wCQnbH/AKYmpcYqPVBnUbb/AK4mkxjk61KOaiTrUy8UCHLxXSad/wAg5fpXOAV0enf8g5KuOwpA1A60GgdRQBL2xRS44zSUABpMUtFIBtFKetJQAUUUUAB6U32GMfSnHpTaAEOSKkE8ygBZnGOwNMop3YrFxdVu1AXzMgDHIqwNdkyMwrjvg1lYpKpVJrqS6cX0Ogj1y3ZgHR19TjNTDV7IkDzsE+qmuZpc+3NaKvIh0InVpe20jbUlVj6A1MJFboQfoa439PpT0lkjbcrsD6g1SxD6oh0OzOwByM0ZFcvHqV1GeJS3+9zViPWrhWzIquPyq1iIkuhLodDRWMuvYHNuT9G/+tU0Wt27gl1ZD6datVYPqS6U10NOiqUeq2kgJ8wLj+9xVlJ4pF3JIpHqDVqSezIcWtySikDZ6cilzTEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQBWmvreAEyXESAHB3MOOK53UPGcMalLFPNfrvfhRzWN41RY9ZhjRQqC3U7QMDO5ucVz61yVK0k2kdVKimlJmxP4l1W5JDXTKjHIVVUAfjjNU7h2l0O5Z2LEuoyT9aqr1qzIM6Fcf76/1rBybTuzflS2RlxHKirAqGMYUVMKysaE8Z+SX/cNZFlxGfcn+daw/1Uv+4f5Vk2PMf4n+dC3EaK9RU1QL94VMvSquId2NM1P/AI/7U/8ATE/zp/8AAabqn/IQtf8ArhQ9UALwalqJOtTDrQIcK6bTRnS0NczXSaaT/Zq1cSWwbrQOooPWgdKBkw+7SUq/dpKGAUUUUAGKaetOpD1pAJRRTqYDaaadRSAbSgcUlO/hoAbSYpaKAG0U7FG2gBtFKaSgAooooAQijrS0UAIQKUMw6MR9DSUUATJe3MShEmcKOgzUo1a+Ax55/wC+R/hVM9aWnzSXUnli+hpJr1woG6NG9x3qyviCPjdC2e/NYWKDz1q/bTXUl0oPodMms2chA80qT6irS3cDEATISegzXH0n3TleD6irWIl1IdBdGdtuz3/KlOQO5ri1uJlOVmkB9QxqWPULyNtwnkJ/2mJFX9ZXVEewfc7CiuXh1y7ViX2yD34qzH4ibOZYMD/ZNWq8GS6MzforLg120lB3sYyPXvVhNSs5BkXMY/3mxVqpF9SHCS6FyioVuIXUMkyMp6EMKlByAQciruSLRRRQAUUUUAFFFFABRRRQAUUUUAeeeOP+Q5B/17r/AOhNXPDpXReOP+Q5D/17L/6E1c8tedV+NnfS+BCjrVqXP9h3OP76/wBarjrViY50O4/31/rU9GaLcyo+gzVgVAg4FTrWZROn+rl/3DWVY/c/E1qD/VSf7p/lWXY/6v8AE/zp9RF5amFQiph0FMTFP3DTdR/5CFt/1wp/RTTNRH+n22enkmhgKvSplFQr1qnNqgtPEVtpTRk/aIy6yE9x2piNUdRXSaZ/yC1rmx97HcGuk07/AJBKfWqWxDButIOlK3LUg6igomH+rooX7lFMBKKWkoAKQ9aWipAKO1FFUA2kNLiipAbS9qWkI5oASiiigAooHPSlIIoASiikNACHrRRRQAUUUUAFJRRQAU2g96KACiiigBDSUppMUgCilwaaRTASiiikAlFKaTNACHrT47qeEbY5nUdcA1GetJii7WwWvuaC63fRx7d4OO5WrCeJJVQK0KM3cgkZrHJ4pmK0VWa6k+zi+h03/CS2xxmCb3xj/Gpl1+xZgNzgn+8vFcnQapV5kOhBnbLqdmzbRcw5PQbhVoOpxhhz7158c9iKckskbho3ZWHfNWsT3RDoLoz0HIozXER6vfRvuFwxwOjcg1YTxLfISWSFvYqR/WrWIj1IdCR19Fcn/wAJVef88IP1op/WIC9jMyPHH/Idh9rdf/Qmrn1roPHH/Idh97df/Qmrnlrmq/GzqpfAh46irE3GhT+8i/1qAVPOM6DN/wBdF/rWfRl9TOj6CpVFQp2FSJPF5xh8xfMH8PfFZlk4P7qX/cP8qzLLiMfjWl0hlP8AsH+VZtn/AKv8SKaeoF4fdJ9Bmiyu4b+1FxbOHjzjIqC+v4dK0ue+ncIsaHGfXFc78NYbiLwu0k6lBLKWQHuPX9a0jG6bIb1sdieVNM1LH262P/TE08fdNN1If6dae8J/nUsYsQ+YbhwOv0rgLTVH8RfFjMZLWtlGwBHQAAD+ddnqZL6ZNbRHEtyhiU+mRgmuT+G0drZX+tWCTo0scvyDuUBGT+ZrSmkryZlLdI9CzlyfU10Wn/8AIJT6muc710WnHOkp9aUSmKepooPU0UDJU5WihOFopiuFJiloNIYlFFFIAoooqhCE0lLikqRhRRR2oAb3pcUYozQAg4ozmiigBCM0YpaKAG0Up60lABRRRQAYpKcOhptACEc0YpTRQA2iiikAhGaOlLSEZoAM0meaXFNxzQAh5NFFFAAaaRTjSUANpM0p60mKGMRutITStTaAuFFFFAriUUGikA3tTT0p3amnpQMbRRRRoK5N44/5DkH/AF7r/Nq54Y9cema6HxxxrsPtbr/6E1eeeN9VbSPDyTwybLhpAF9xWlTWoyab9xHSrnOCKnn/AOQDN/10X+tQxkskbMMEqCRU1x/yAZf+uq1LVkyk7mdGpLADnnGK40aj9q+KcUNuT5aLsfB4J6V10twLS1knA3OoO0eprk/h5pCeXeavch2vmk2HJ4FTTS1bKk7WO6b/AFEo/wBk1nWZKx4Azya0GP7mT/dNZ1scIuPWpW5Rzvj1ZtR0VoYpFSCAgyserH0rtIoY4LOCOEKI1jGAvSvO/iLrlta2J0i3BM8rbpW9Ov8AjXY+GRGPDdoqXHn4HLE5weOK219nfoZXXOa2f3ZpNQOb609oT/OlP3cU3UeL2zx3hP8AOs3sWZniDVF0TRLjUDAZmRSFXoAfXNec/C+7tz4vuZ7mVhPLA6xqFzuJYHr9Aa9Rv0tn0e+N6oa2EDFw3TGK5H4Z+HYbW3l1x4z5s5aOENyFTOcj8q3pOPJIxmnzI9BxjvXQ6b/yCU+tcpfXTWtmZwoOGA5rq9NOdKTjg81mi2OPU0UHqaKBolX7hPpRQn+rpcUwEoNFFDFcSiiikMKKKKEAHpTadSEUMBO9FFFAAKbTjxSYoASiiigApCaWjFIBtFB60UAFFFFABSUtJQAd6KKKAG0d6B1pSKAEooopAFN706m96YDaKKKQAaSlNNOe1AAetJTVljabyg4Mn93PNLnmgBGptONNoATNOxxmmU8H5eaBjaKU47UlADe1Np3am0WEJiilooAk8cDOvQjp/oy8/wDAmrxrWYpvE3jqLTZfMXT4CSoOdrY7+ley+OQTrcYBwfsy8/8AAmrxXVNalj8f28GmFZGiAifHQnua2/5fMyX8NHp27LDtjjFSXX/IvS4/56rTCPn4FOuR/wAU/L7yrWb2ZouhkXE1pa232q/lRLeLJw54Y1xPw1vb26vb/wAy5P2ZssI2bqfYV2OpaZb6vpU9pcnbHjcWz0Ncr8OPDb2du2r3LEF+IoweCPU0UkuSVxzfvI79/wDUSf7p/lWfbfcFXpJFUMhH+sQlfyrOF3b2NsZrgsIwedqkn9KyRoznPiFpVhc2Vt5cUf8AalxIFXaPnI9+9djpumWukWEVjaoEVRyR3Ncbd2ku4+Jb1X2eeDDGf4Is8HH0xXa3N7aWUH2u5uY0txzvBycZ7CtXJ8iiZpK9x0MvmzTp/wA8yAfxzT9R/wCPuyyOPJOT6c1haBczajq2q6isTxadKFEBfgsRnnHX0rf1E/6VZZHHkn+dS1Yo5TUW1HxHq8dhFazwaTASJ2YFRcY7e4q/4Pv7c6QNNeWOK6smZWhZgpC5689q3FYgKBgBemBWNrHhLSdZuUvZt9tNnEjRttD+xq01a2xDWtyr4t1gXulPpGhgX99MRl7Y7xD9SOh6fnXovhuOaHwpYw3LbrlIlWUk5JYAZ/Wud0/TbPTIRDZW6Rr3bHzE+ua6rTM/2Wv1NNWSshMeetIOtBoHWpGiVB8p5p1NUZQmimAUUUGgBKKKKTGFFFFACc0lOpDQAlFFFIQU3mnd6KBjaKcelJg0AJRRRQAh60lOptABRRRQAGkpaKAEoNFFADaUnIpMUUAIaBS0UgEPWkpSOaSgBtFB60UABpBncAOT6Cg1FcXcFjayXVy4SKIFix9qEJnkXjzXZ9A+IlrcWlwQuVMqBuCMjOR9K9X028j1LTIL1MbZVB4r5o8Vawda8TX1+vMbyMY89lzxXt/w0uJrjwbb+ZnA4XJ7VpWhZRZEJ8zaOvptPxTTWZqhpFLRQaADtSUUUAwpje1Pph6UCG80UtFA7Evjhd+txjOAbZRx/vNXgq+E7pviBcWlleFVicyvNt5QE52+9e0fFHWYdIvo5Cd072wEUfdjvYVxvhSF49S1BrgmS9nAlfIwRnnA9hXQpctWTMEr00jpZHEQLsSVGMmrFypOgkjoZRzWR4iuV0/w3d3LkAhRsHdmyMVasrqS78EW1xKpDuw3DpWT1i2ardHLarezavcNoen7kHWe4HQDH3RW54eKzaDaiE/cUKQB7d6ltUjtyTEioScsQOtZn/CMvDOX07ULi0tZW/fQpyGB64J6VktrItrUZr+rmPUbKy04efcKSZ1U/cTvmtSAB4l3op9j60tho+naTHO1lbhJHQhnZixb8TSW5yoJpppKwO5YljjuLZ7e4G+FxhlI/wA4rC0/wTY2U6yz3c94ifchmPyr+Gf84rbuLyHT7Z7qc/u4xkj19q5nwHJd36ahfX08jTmTb5bdEHPaqjezYtNjshtEWxQEjA+VVHGKTUf+Pix/65N/OlxlfbFJqJ/0ix/65N/Ok3caBetcZ4l1j+19c0/SrN5Y7WK5Xzrkfxvg4XFdWb6ziuhbzXCJKV3BGOMiuY1DxFpL3+mrDaSGzhnMzyImNxClc/maI/ES3od3tCMEBJ2jFb+mf8gof7xrlrLVdN1cu+n30U+OSoPIrqdKOdI/4GapdRPVD260lDHmgetAImjP7thSUJ9xjRTGFFFFACUUGikwCiiikAUUUnWgBDRQeKKACijtRQAmaM80YpDQAUUUUAFJilpM0AJRRRQAUUUZoASiiigANNxTqQnNACUUUUgCm96XNNzzTASig8UUgDGSPWvGPi14td79NEtHIhjG6VlbqxzxXo/jHX/7B0JpIsm7nysCDqT64/EV5BqXga8TwxN4i1edlu5XLbGGMjit6EE5amVWTS0OHtbSa9u4reKNnaR1QYHQk19NeFNIOieGrOyc/vFUM319K5L4Q6Xp58OPemBHui/3mHIxXo9KtUU3ZdBU4cuomaQ9KKKwNhtBpSMUhpjEooooCwmab607FNNFxCUUUUBcZ4whhPiZ5WiUyCNAGI5A56VxmuaBJqU8V5Yzm2voxjcD94ehrt/F+B4ib/rmp/nXBeK/ES6NpLpbB2vJOFKjhc9zW0/iaM4fCizpvhlYbhL7Urh7u9/2/urXQXhzobD0lWqtlHJFawJNJ5koT5mHQ1Zu/wDkBtz/AMtRSm/daGt0zPj528dTjPpXJ3mr3Gp+N9OsbSSSO0ilBdsEB8Hke9dGNQs4bsW8s6pLtJwx6j1rnpdZsJvEdlcfZriC0t4yd5jA69+D0rnja+pudqeY5eP4DVC2GY1PrUtjqNpqUEslpOkqhDu29RUdsdqj0FMTGnV9Nig84zJc7XKiJPmLMOMYH41h+GdXtLHT7h9SDWLzzM++ZSFbn+90/wD11R1+4tfD1hY6jbKJC08jsF7sf8Ca0PD8417R7nRtUtY3WHAyCDnv9a0UHa/QjmVzq45ElhWSJ1kjcZV1OQR6ik1H/X2J/wCmTfzqOxtIbCzS1t/9VEMKPSpdR5lsT/0zb+dS0hmFqulWN7fXF5PJH50FnJ5aFgGyEJzj0rlPDni/yr/TmjtHeGGE2bsoJGWOf6V1t9p0NxqM96zMGEYtwB/tDBrmPEnguzs/sNppN68N1PcKRESem0ktnrW0GmtTKVzvP7B02LXv7ZhhMd0ybTjgEeuPWuw0r/kGn3Y1yum2EmmWMdpJcNcPH96Ru5rqNKJ/sw/7xpNtsq1kSsMmihjzQOlQUSx/cNFEf3TS4pgJRRRSAQ0UtIaACiiigAPSm06kIoAOtIaM0UAJmjNGOaSkA7pTaM0UAHNJkiloPNACZpKD1pcUAJRRRQAUYoooASiiigBM5oIo6UmaQBRRRQAmKTHNOpvemA080jdKWgcHt+NIDKg0vdrM+oXYEjAAQA9EHfH6VxXxi1ZYNGt9PB/eSZbBrrtc8XaP4bmWPU5nWRlLKqLnIrw/xz4xj8WaylxDCyQRLsjDgDuef1reim5XMarsj034XXulQeFbayF9ALxzloTIA2T2xXeHPcYPpXy74emmTxbpnkEhvtMfQ/7Qr6iAPlpu64GazqwcJ+pVOXNEaeKDSkUlZmg3NFKRSU0AlFBooAKYafSEDFKwDKKQ9aKB2IvHN1Ba68z3EmxSiKD781wHibU9N+zSwpbmedztMiKDge3eu4+IttaXmsJBd7BGTGRvOO1eS6jrrafrN1YWdqJPs90XJ/2fQ1vJe+zGPwo9FstU0+/CJZXCMwGNmeRir10D/YRzjHnCsxNH0976DVorSOG6Ay3lAKDke1ad0C2gnOM+bnFKatFlR3OV1DSrC5uri6mb/SEgIVR2HrXN+GfEs+o6tHFLYQm1uR5ByM4HQ4rrbqC2N608se53xGPcYrA1DQtJl8V6dZ6a7xFHDSpE2AoB5Ax61nTcbWZpK+6Oo0jw/Y6Gbs2SSIJUOVY8D6U+JtsRJ7VeQBEkReQEPX6VQX/j1k4z8p4qLtsp7FAaPpl+PsVwm6CFfNYZ6MeTisjwXotq+qXmsWdxcNbJIViQvwxwRz61reEC09jdXk4JeaYqVPI2jIwat6AoifUYUULGk3yqowBW12lYztdm0P8AV59qZqX37D/cb+dPH3T9KZqX39P/ANxv51mUcheapPL44ttEiXbGJkuJHzzgHOK2dbtrdNS0/UljzcfaRCX7BSp6fkKvfYrVr0Xn2dPtAXaJNvOPrUerIHGmoev2sH/x1jTdugvM13PznknvW7pP/INP+8awicyNmt7S/wDkGH/eNNMTHt1pKVutKOlIZLH9w0URfdaiqAKKU0lIANIOlFFABRRRSAbRRRQAUUUhoAWm06m0wCiiikAUUUUAFNp1FADaKdSGgBB1oopKAFopKKQDSaKdSGmAlIaDQOlIBR0pvenU3vQA2g9M0UUDPO/iv4WfW9Otr+3DNc2xIZAM7lP/AOqvCpA8bbJEKFeMEV9cYyCCMg9Qa8R+NFtFD4ishDDHHvhy2xQAeT1rsoVE1ys5a0NbmT8Orjw/baxFPqpYXKyL5JJG3OeK9/SeK4iWaCQPG4BVq+b/AAn4M1HxPfRtHE0VoHAeXBAxntX0HoujxaHpsdjBIzhRjczVjiF717l0fhsaFI3Slpp61gbDaDSmkNMBKKQUtABQaKQ9KQDT1opaKAKPxAhin19VnUGNY1cj3APNcH4i0vRDobymMpd3Iym0H5z2rpfi9Nex63bLZoWEuyN8dh3P61T1WBF8NqpUM8MaKpI6YArpldNsxjrFFzSdLj0iwjtEd3IGSzHqav3RxoZHfzBRnOD3IBpLvjSD/wBdAKibumaQWqONlae68cCzZWW1hAlD9icDitG8VYfE1g0cagv9/wB60lQB/MwN5GC2Ko3P7zxDaEg/LGWziue+yNeprr92X/cNU7c4XpmrqdJP9w1ThHFCBliJVXCqAoz2qjoZDG/fs05wavbljXezAAc5NFrbJarIifxNuJ+tXcksj7ppmog508+qPj86evIxRqX3NMP++P1NCAYtNmgFw8D8Yhk3Af8AASP606Llh7muavtetPDWq3z3szvHI6pHEByCef6ULVgdfnJJ9a3tK/5Bh/3jXOWdwl3ZxXMYISRQwB610Okn/iXv/vmmiWTnqaKD1NJnBpjJ4h8rUZpI2+U8UUwCiilxxQAlIaWkNIAozRTaAHHpTaXNJSAKKKKADNNpcUlABRRQeKACikzRmgBaKKKAA9KQcUtITmgAJpKKKAEooopAJ1pMUuMUE5oAaaBS0hOKAFzTaKKAG0AFjgAk1l63r9l4fthcXm8qegRc5rzfWfjLJPbz22l6U8bsNqTNJkj/AIDiqUXJ2QOVjtvFPjnSfC0eyaUSXZBKwrkn8a8Pv76/8eeK0aUFXmIVB02qD/8AXrf8E+Dbvxlq02oazJMsCEMS6535z69OldJ4ksLLSPiToUNtCsEPkAAqOvzGt0o079zGTcmux6NpVhHpOkWmnIOLeJUOO5A61axgYp8vEjfWmVyt3NUkhMGojLGHEe4bjyBUpbAzjpWBqUy23iGwBf8A1nGKBm5QaM57UGncYlFFFFxWCmkHFOpM0gGmilooAb4yVT4gYFQf3ankexrl9Y/5BUigD5iAfzFdR4wP/FQv/wBc1/rXPNJbzzG0c5fG7ae9bTfvNGcF7qLmMEDOcUXo/wCJPn/pqKReD+NOvOdEP/XUVMtmVH4igpGKjgEFzMt1HlmUbASeBUsIJbAGTnpXJ2Ws3/8AaxstLs2ntfPxPKFyqjPJzWCTexudon3ZP9w1Th6Crifdk/3TVOHpVJakvYdeIkmm3KucKEOSe1Yvh3xNda1dmEWBS0jTicjqcf8A1qzfGetauM6Np1lLunXHmop+YY9a3PCuu6dqWjw2tsyrcQpiWMDAyK0Ufd5mZ82tjfGMEik1I/Jpw9A5/U0qnK8im6j/AMw4f7L/AMzUlFa8v4NJspL254iiG4+9cN4V1S08VeOL+61K2Q5gL2wk4AAYDp6812WuGH/hGtUa5iWWJLZztYZ525zXC6bBpvirWNLstMZoUtbZpLiSIbScEcZHXkitKUU02RN6o9SUbRgAAADAHSt3SP8Ajwcf7RrCjRY40RM7UUKMnmt3SP8Ajwf/AHzUIp7Fg9abilPU0UAPj6EU7vREMhqB1p3AUClopDQAhGKSlpKdwCkxS0UAIRSU49KbUgFFFFABTaXNJQAUUUUAJijFLRQAUmaWjAoATNJTsCkIoASiiigBKKKKQAelNpx6U2gAoxRRQMTFNJxTieaQjNAiCe2gulC3EKSqOzjNVhoejq4ddLtQ46HZV6impNbA0mMRVjTZGiovooxXnXxYsZVj0/WYVO+1bBK9QM5r0eoLy0hv7SS1uI1eORSvzDOKqEve1JktNCpoWqQa1otpeQyhy8SlwD0OOf1rQyK8Za01/wCHOvPcQRzT6OZNzKuWULn9K6v/AIW54eaDeFk83vGEJ/rUypyi7bjUkzucZ4yK8++Iurpouq6VcsVJRs7c9qpX3xdaSMLpelSySHjJiPXtXFXOjeMPGt+b24s7pgTx5ilVX6CtIU2/i0RMp9j3u0vIr+0juoQQkgyM1NVLR7aay0e2tZ9u+JMHAq7WEU7amrExiiiimSHam07tTaACiiigY3xhn/hImA/55r/WvPdcvryLxBFaaXGHvniHLdFGO9eheMw765MIyBJ5K7Se3WvEruTVPDupxeIXkF1cXEzQmIdDjI4xXQo81RowTtBHqVqLhYI1uyhnHDlOmanuv+QN/wBtRUMEjSxxyMmwsM7fSprv/kDD/rsKiezNImBrN5e6fo0l1YRq8q8HPYetcr4P1h9A1CTw/qSSSXNzKu14xlQT65xXSeJftDeGLsWuTIP4QM5FZPgmymub6/1bU4gb4kAYXAUY6ioppcjuVK91Y7X7qyf7pqlATgVdQfLJ/uGqcI4FZo0Zy/jLWrvRtX02aG3My+Wdq+9bHg7RINK0KKQRKLmfLyPt5Oe2asSX9hdXU9l8klzbxsxHUqazhrl7pmoaLYy20b2F5EMSqp3A4/L0rZS93lRly+8dWvRqbqP3dO+j/wAzT8beM5puo/6vTvo/8zUWKK9zbR3thc2k2BHPEUfnopGM1zVrFpfg3VLkJBIYLazBdo1BZizLVzXbTUtU1DT7C1cw2ZIeeReuR2qhot/PqPjrVtL1S3jU/YSmVBG9Qy4JrSF7eRMnZnVaRq1nrmnLf2DMYWO0h+Cp9CK6rRv+PB/941yGg6HaeHtPls7JpDE8vmkSEEg4xgY7V12j/wDHk3+9U2V9AkWD1pKQ9aUdKQyWP7r/AEo70RdH+lFMB1FIKDSADTaWkpoAooooAD0ptOPSm0gDtQKQdaWgAptO7im0AB6UgpaKAENAoNJQA6igdKKACikHWg0ABpKXsabQAUUUdOlIApDSHrRQAUUUh60DsB60lFNpMQUUDpRQMSkNLRQDI54o7m3e3nQPE42kMOorBg8F+Hbe5My6ZExPZhV3xFrlv4d0l7+4G4D7qZxurF8LePLHxTdvawQmKZU3kE8Ecf41fPKK8ieVM6GLS9NgGIrGBPbYP8Ktg7I9qDavovFB6GmjrS5m9xpJCUUppKkYlFLSdeKLgNop1B6UAhtFFFAxvjD/AJD8oHUxKB9cGvI9K0TUT4ojXVJPMtVlkkiiY9OTg16b8SdU/snUJJUiMs7qiRoozk81xEWow6vrlvZyyNHeG2KyIRgo2OldKbU20c6V4o7Ak7ueoPTFOuz/AMSZR/01Fc/4Ut9Us9LMGrHMiP8AI27JK1v3qn+xkbIx5lZT0izRLU5/U9ctdJkt7aQb5ZmHyYyAM96x5bjUrXTtSvdKkG6G53MoUNuXvXSw2FqdT+2SRB5GTYcjtWN4P0e70NNQimKrHLLui75Has42saM6W0maexSZ12vJESR6VBAdvNWeoc99pqpEflFJblMxrrw3DDpeo3VszLqUheTzgTkg9sdK1dFX7RoOmyXcAaeKMbSw5Uirq8gcEmnRurglWVscHBzV82ljO2pMGzknOTSaj/q9N+r/AMzQBRqA/c6ac/xP/M0D0I4+AD3qOLT7WPVX1NYQLuSPymf/AGcg/wBBUkfOADzVDWNSmsLzSbaJf+PuXDE+m0n+gqU3shtI2hjFb2j82T/7xrDZdrkYxg1t6P8A8eT/AO8apCZK3WgHilYc0mKBFiAja/0pMUkH8f0p3amAZpGoFKRSAbSUtFACUUUUAB6U2nHpTaACiiigAptOptAAelNxTqQ9KAEooooAcOlJilHSigAPSm0403FAAaQUuKSgAooopAB6U2nGm4oGFIRS0UMBtFKRSUANoooGT2pAJ0qhq2s2Gh2TXl/JtjA4A6k1oMpFZmraHYa2kSX0QkSM5ANVFJvUTPA/F/i7UPFl/KAx+xRkiONRgAeprufhD4eSG3l1uRt0jqYlAPGCQen4V13iey0rRPBmqy2trHCWtnUYXvg1ifCNJY/CzM6kK7kqT35rapJeztExinzts76g9KUikPSudm42g0UGmhoSigUHpSYgoPSig9KAGiiiigZD40hjk8RkugbaikA+vNca/h2OTxTFrccoQqpDJt+8a7Xxh/yML84/dr/WuW1TVE0qGCSQZ859iit5Sak7GUEuRGnkngknjFOvTnRIwB/y0poGGKnrTrvJ0lBxgSVD+FlLRlJen4Uw3kAv/sfmL523fs9qSSdI4ZJAwYxDLAdq5e5vrKw+IEU13Iy/aIlVMep4H86xirmh2ich/wDcNUo+lXk6uP8AYNUCrG3fY21tpwfSmkMtxzRQyJ5rhdxwM965zwOzfZNQEs6vP9pYeWW54NWppEh03SH1KVQVO5n9eM1ieBNDguJX8S/a5S7TPtgU/Lzxz+daRWjuZuWp3mc06/X/AEbTDjqz/wAzTQOOTUl83+h6b/vv/M0kMwYdZMus32li1eN7eIuJi2Q3GRxWHrt5qK6L4dmtovtOpmZpETH8G0itSO8ZPFF9p0sWx7m2LQy4+9xjFY72uq6F4q8PyyO05kVodpHyqPb3qoRV7kyZ2+nS30+nwy6jAsN0w+ZBzium0c4s3/3qxpT++cZ6nOK2dH5s2/3qSYywetJSnrSUgJoBw59qWiDlXop3ATFKTSkYpCKLANPNFBooAQ8UUHmg9aQAelNpx6U2mAhOKM0uKTHNIBabTqTFACUh6UtFADaUDNBGKB0oAWiiigApD0paQ9KAEoxRRQMKSlpKVhBSE5paQjAoGJSE4ozxR1oAWm96dTe9Ahtc/wCN9RvNI8I3WoWOPOh25z6bgP610FZHimD7V4X1CDyzJvjxsHU8inG19QbOd8CfEBfFObK6h8m8iXOd2RJ69uKueKfH+neFr5bKSF7qdlDbY2xjP/6qp/D/AMBW+g2/9oXHmG7nXIUkfIPasHTdHs774t36XpMvlAMgbBzVcsU2+hKkyDxj46bWPD8dm+jXFolyyjzJH4wT16V6N4Xis4fDNjFaSRuFQE7D3wK4fxyY9Y8daR4eZVW1WRN6oOcZ6Voapo2oeCU/tHw1vuID8strN8wA9RjHpTkrpKJMXqzvCRSHpXn+hfFSz1G7W11C2FpK2AOuMn6134ZHjWSNgyPypHesmmnZmiEooooKDFIRmlpKBBQelFB6UANooooDUp+MtQt4vGS2Tk+bLCrKMcEc1554wvWTSbW5eEu8N4ypHjlgD1rufHF5Fa/EG2SWJsyW4VJccKeeK8316HVbDxFps1xiW2a4YRoueBk1s1eozKLtBHe2ly93bR3DwvAzjJRwQR9c1ZvDjR4z/wBNaa5y7c9TmnXX/IJhx/z2qJbMtbo54x26LrEsJ3zEjfGD0GB2rnNBlt9X8cXLapahbiJAYI5BnZjpwehrbsZEHjm/tkBBe3TzMeuBUekraXni7V9QQBGhUIWPTjgmlF2RT1OqT7z/AO4apxJ5qNHnG7IzVuIhgxBBUxnBHes+4v10zTbi+kUssSE4rMp7EOi20GoaatveBZxazPGC4z0JHf2qj4B0xtP0W5cvlJZzsGegB/8Ar0mnTDS/BxvYyJbjUG80BDnDPyAfpmjSYtRtYrfRLaKRFtZS1zcODtcei+ta67GatudUOlPv8fY9NHfe/wDOmHAyBxT73i001j93e+Prk0kimchqk73PxA0i3tVMphjzOV52j3rSn1GO68eWtuWSS3tIGLPn5UcgdfQ9a5ey1HWfD3iXWZf7InuYLmd2V405xk4Iqlb61oUelvZa7bapbTy3RneUQgA8H5ck+9XyO6Ickel6fqlrq0c01o5dI5TGzY4JHpXTaR/x5v8A71ed6b4y8Nslvp+kLO7MQqRpGOPc816JpGfsjA9d5pWsO90WT1pKU9aSkxk0H8dL3pLf+On0wA0w9acelNNACUlLRSsAlFFFIAoopD0pgBpBRSGkAtNp1J2oASiiihgIaB0oNAoAWinDpTaAA9KQUtIelAAaSiigBKKKbSAd15pD0paQ0DuJRRSGgGB60lKOlJQIbWV4j1f+wtHbUHiMkSMvmADOAWA/rWq3NYfiq1bU9Ck0eM7Zb4hA3ZcEHn8qcQZpaXqllrFnFdWNxHLGTyFYZX2OK8a8Va43hn4pyX0CZ2opdQfvcmvQ/CXhY+B9MvhJdLcebtYbeACAfX615r4v8F69faz9sgjNyLhd+9c/KMnjpW1JJtpmVXuipYeJ7u98ZXniZLJ5liBkKkZCAfyr2Hwp4kg8T6SZ0C7+jxjmvLtKk0vwb4Z1K11OYS6hewugiiGSmVxg5xWt8INOgU3V8L5Q5Up5BYccjmirFW5kKm3sy/8AELwBLrEtve6JbxRzIG8xYxtLdMdO9dR4Mivrbwra22pK4uYiyneSTjJx1rf7A9jSHpWU5uSV+hpGPLsJRRRUmglLSUUhBQelFFADaKQ9aKBnNfFGZ38eaZa2675dquw9F5rD1K9F14tsrTy90VuC7ueitg1ofEo6/YfExb/TLQXEItEQggHpuzXIjXbS1Wf+3tMuY5LiYs8iZGOenBrea94xhrFHdabqMOqW7T2+7ZuIGR1q9c8aRG56LIT9a5zSPFGi388Gn6QjsCDwEwFAHeujvc/2LEBx+8PBqJKyZcXqjz3TPFemWOo6tcXgKX7MQu5SRgDjmr2naTazWMcNtq9uqXC5uz/G2eSBzW3Jouk3BLT6fBI7H5iR1rJ1DwBo14BJZK9lMDyyO2P51KcX5Dd4nT2zWyM1rDKjGKM5CtuwKoXlmup6TLZSsUWUYJAzijRNBstDhlFt5jSyId8jsSW/M1Zi+6KnaWhZycHw6aGOOODxFcKqnIVochfTjdUs8/jzTb5LaN4r6NzxPsCgfXmuvTJIAP41T0XXoNaFyLbeogk2MD/+utOdvcy5EtibTLa8gsj/AGhKkl1IdzFOi+1aN/g6dpuSc736fU1GO/OadqGfsWm/77/zqU+xdiON2BHOfrUepadZa1ZG2vrYSxk8N0IP1psk8dtbSXErbY4lLMfQCsnxDcXLNoF1Y3DrbzXKqyL0YEMf6URbvuS0i3oHhXR/DxZrK3LylifMkOSPYflXaaRk2z5z96sd12yt3rZ0f/j1f/ep3beoWstCy3WgHilbrTaTGTQAnzMemaXvSWpI83t8tOxVWFcD0ppGacelJnFADaSlopAJRRRRYYUh6UtJ1oASkPWlo70gCjtRSZoASiiigBDQKCM0oHFADuoptOFNoAKQ9KWkPSgBKO1FFAAOlJS0lAAelNpx6U2gAooopBcQjmkp1N70ANHWsy6k3eI7CH/ZZv8Ax01p1hajKsXjPR1LhRKsi5/4C1CFIj8Yal9g0qGMj95cyrGoz+dbluojtYIyF+WMdvavNvFeqjXPiHpelW582GzZncD14H9K9NwA6g9gBx9KbTTDdHI+LfBWj6tYXl/NFsnhid9y9yBmvHfB2gatrmrTQ6ZM9usYJaXsOfrXZeIfEHjCaTWNPtLVvsW6RGl2DhO+D9K7L4eaRFpXhO3ZUAmnG537mtudxhZ9TO15XRr6LZXWn6XHbXlz9plQY8zGM1odKdikPSuc2EODSZooxigYUlLSUbCYUEcUUtNgMooZgDRSCxH4xJHiJ8f881x+tYE80AjWK7ZCjnhJMYNbvjZvL12WQDJWJTj86878SRi+TQrwsYy8ikgnoK0n8bIhblR1FtY2lihS1tY4c9SqgE1ZvMjRoR28w0jgCQjvk0t2f+JVCD08w0pNtO5cVqijLcRWsYkncImcbjWNHdah/wAJzJYu/wDoghLKB0q5q22eC3tGK7pZABk9KyYNUkvfiButotsHlFWaTjIHcVnFFM65eQ/+6aqxHA/Orafx/wC6aqQ8kfX+tAMqyNcHxJYRwyHbt3OorL8G3NoZ9XghcmUXJLccfnUttfS33ibU7a2XLwL5YY9skZxVTwDEkaaoqKF2zAdct+NaJe7czbd7HZKeDT7/AJstO/33/nTAck1Jf/8AHhp59Hb+dTEt7GdqDKmlzJn/AF+IQCepY4rnvF19Ko0WxsCVMN2gac8IG2t8v1wT+VXfERkuZbCyt2A8u4jllYn+HeP1qDxbZwWuraCVVmeW7BIJ+U/KevvVw3MpHYDOF3sGfaNxHc81uaP/AMer/wC9WLINspwBW1oozayH/apLVldCy1NHSnEU3pQMlg43+4xUlRwfx1JT1FYRuBTSc04jNIVAoAbRQaKBiUYpaSkAUgGKWigBDSUppKQBTadSYoASiiimAU7GKbTs5pAFNpSaMUwEpD0paQ9KQCUUUUAJRRRQAHpTacelNoGwooooEFN706m96AGnoa8t+M1rOLazv4Vk2RcF1ONuT1r1I+lUNc0qHXdGuNNnOFmXAP8Ad5B/pVUpKMrsmabWh5h8E7RZbnVdQcbpECIrnnrur1wnkHoTya57wd4Si8IWM9tFN5pmcMzEY6Z/xroiM0pu8m0EVpYy/EjuPCerbeCLaQ8f7pqh4GvBeeDbA5+ZF2mtLxCP+KX1UetrKP8Ax01z/wANU2+DoPdz/OplsgWjOuzSUUUixOlBOaWkIxTGJRiiihisAGaUjApFpX4WkBAx+aikPJop6jKHxMvFsbyeVj95EQfjmvOPEkr3c2kWzHZBHIoRUGGbjrmu4+Ksf2jXhHJ/qEjjdh6nnFcZ4umSO+0RkbbGroHx6YrVr32ZR+BHdvwxA6D160XvOkQY6mU0HBcnB78mlvTjSoP+uhqJOyZpE5O+imvfFVlGMLbwjlgeSap6cix/ESe3i8x1ji43N936Vt3Fmyzw3a5IicySAdSMVjaZZfZ78eJdUv44BIvEY6kdgaiHYJPU7Nf4/wDcNU1cxRNJgEqMge9W4pEliMkbBkZTg+tVY8Y5HFSimZOiypZ6xeySRhWMXnu3TOf/ANdZ3gHdHNqiLC5ikk3pKRjd14rY8SSWFros8tzhZnACFOGb2J9KzLS28Wah9lnMtvplohB+zxfLlffFap3JOxHU9/epb3mx08f7bfzqIY6ccDBI71JfkDT9PO4cO+fzqUO5kTaaWM0yndK8iEA9gDmsW8ik8V6tK1xPHptjp0ykSPw+8eme3XtW7f6rb2UEaxnzbq4+S3iH8T9Bn2rB0Xw39s1/UpvEEhupFYZt0b93k4wSPUdPxqo9zOSOtstQsdSjdrG7W5EJ8t2X19a6XRf+PWX/AH65uz06w01ZI7CygtUc7mWJcZPvXRaL/wAesv8AvVUbdBtlw9aaetDdaSpGTW4yX/3acDmmwfxU+qEIaSlPSmmkAHrSUUUhhSUtJTAKQ0tFSA2g0ppKYBR2oPSikIbRSnpSU7BcQ0q0Uo60WGKRSfw0tBpANooooAQ0lKaSmAlFFLSASkPFLQaAG0UUUgCm96dTe9ADaTHelooQxKO1BooAzPETY8Laqc4/0WT/ANBNUPBNslr4RsVj53qWOfXmp/GBZfCGplTjdEy/mKn8Pwrb+G9Oix0iH9aHsieppYFNPWnUh6UmUNNJTqKAY2kpxptCC4q0P9yhaH+4aEIgPWijvRTHcj8bQRT69IkgwrRoCfTg15paXcUes36WmmNqd0JW2yNwqDsOa7X4kyXh8QXPmEQafFFGxlHVzg5FYGm31rplld3ioVtvlPy9SCM5rWfxMzh8CNXSptQntC2pWy29xn7inIx9at6jPHbaGssrBUVzkntUdpcwX1ql1bS+bEw4YVLfRxzaNGkqB0LkFSODWb2LRgnW/tsRg0nEkjqf3xGFX8T1qv4a8KaZDpiXMkQup5clnl5HuQKztcNzo62dpo9g7W8DmeUqeo9KkuJLy58I6bqukzOrwuC0K/xjPSiMXbQbfc7SFVUFFUKqoQF7YrC1VdU+xwnSljeVH3MjsBmt2BzLbxyMNrmIlh71Vj4YHNRsyjmoLqG1uJ7S8ZH1S4t2lmLMCI2x90fnW5ceIrOy1S00y7EqS3KAxyEZBP4Vz+veCU1KS+1GG6kW+cbkx0HtW9f6LFrEel3F0THc2oDfKBz7Vp7u5GtzZPBx6cU7UFDaXYh13I0rBvpTAxYkmpdQ/wCQTYf9dW/nUrcbOO1LT7Dwro95NYRyNcXz7IwAWKZPaorK9Ouwa2+iTNHdpGhG9Srbh1611+1GZN6hthyMjPNZ+laFBpWuahqVux/0wANGRwp9a0UlbUizuVvA2s32saDM2pKRdW9wYSxGN3ArvtF/49pv9+sKOOOJWWJFQM24gDGTW5oufs83+/TVr6DexcJzTcGnEYJpO9QCHwfeapKZD940+mAh6UlKelJRcQhFJTqQ9aChuRRRgUUCDNIelAFKaWwxB0paBSZpAGKQ0uaSnYQ2ilxSUDClHWkpRxSAWjFHWlzTAQ9Kbind6Q9KGAlIaCM0lIApKWkoAKQ9KWkPSgBKKKKACm96dSYoAZRRRU2ASiiquo3i6fZSXcilo4xlgDTAreILeS90C6toV3PIu3FWrOI2+nWsHeNAD9aSwvYdS0+G/tmLRTIGH5VYxjjrRuFgpuKdQaQ7jD0pKcRxSdKbEJRS9aSgAHWlPSkpw6UAVicE5op0g+Y0UAZ/xH0qPWtUeymldI2VCdpxmuO8P6ZNbHVNMvcy2e4pDv8A7vbn6V3/AIx/5GJ/+ua/1rEUfr7VpOVpMmC91FTRdLTRdLSxRy6qeCTWhdZOkRHH/LU00VJdtjRrcY6ympbumV1Kq45BAIYbTx1FMsLSDTrJLS2jCQoOF609emakHNRexVkSx/xf7pqjGcj86vR87/8AcNUYxgUmMmWpV6k1GKkWgB4OKlvz/wASmx/66t/OogKffn/iV2I/6at/Oqj5iaI161MBgVCvWpl54oEPHatzRTm0lP8AtViAcitrRuLWUf7VXEllxuppB60rdaSkCJIfvmn0yAfP9afVWC4HmkIxS0hoENJxSU7FNNIoKTvS0mKADn0oo/E0UgCkPWgnFA55oEJRRSZouMU9KbTj0ptABRRRQA7pRRRSuAd6Q9KXvSHpQAlIetLRimA2jNBopAJSHpS0GgBooo70UAJmjNB60YoAZRQeDRSADUNzEk9pPDIoZHQgg/SpqjuHEVnPIx4CH+VOK1E9jlvh2Jf7CnhJJhjuZEiH91QSAK6tgQORzXjcvjiDS/BUtppl3s1OSd+Iz8y5Y+lP8G+NPE9zrNvFqSXM9o4wWZGx9c1TptLmEpJux7BRRRUFCE5pppxFNNAAOlJ3pVpKACnA8U2gGgBSoJoozRQBX8Zf8jG//XNf61ir2ra8Y/8AIwv/ALi/1rFWrqfExU/hQ8dakux/xJoD2EpqL1qS7z/Y0X/XQ0l8LK6oqL0qVaYvNSLWZTJYfvH/AHDVFSf1q7Hw7f7pqipz+Zo6BcnHSpFqMdKlXpSAeOlPvwBpdi3/AE1b+dRdqlvj/wASqwz/AM9W/nVxFIiHUVMv3hUQqVaCSStrRObabP8Ae/xrFTlq2tD/AOPaf/eq4bky2LjdaaTz0pzUg6UmUSwffP0p1Mh/1lPqkIOlGKKKLCENNPWnHrSUhobRRRQMKSlpKGAhpKdRQA2kxS02gQp6UlFFAwooopAOHSigdKKQBSHpS0U0A2ilNJQAh60lKetIaQBTTTqaelACUUUUAGKKKbSAaetGcUdhSHpQBBLe29vdLBPMsRK7sucDFec+OvibYQWlxpWlEz3DcNKPuD6GtH4neHNU1u2s7jSmPmxBkdQ23IOP8K8v0TwJf6l4jOi3v7h1Xe5HJwen8q6KUF8TMaknsjoPhXoWiardNdX0pku4n3CE9D717SscUcYSKJUUdAoxivAPDDDwj8SPsd7LsihnMLOfQHvXudhrOn6q0gsbpZjH9/bUVbqbuVT2LtFFFZGghpKdRQA3oKTFONJQA2kpaKACiiigCDxl/wAjG/8A1zX+tYq1teMf+Rjf/rmv9axB0q5/ExQ+BD/en3X/ACCLf1801GKfd/8AILt/+uh/pU/ZZXVEC9KfUSkBeewzTo7iKVgiOGYjIA9KhFMsw/fP+4aop1P1NXofvn/cNUEIyfqaARODUimq1xOLa1edvuoO9UrLxDYXd+1mtwhl42quTnrmmBsdqlvADpNnz/y3NREccVLd5GkWYx/y3NERMiXrUoBx0qOMFmGPWudvPFtlaXdxBLdIHhmUFQrH5e/QVSVyW7HVr1FbOg5+zXGD/GK5+1u4r6ETwB/Lf7pdSCeOvPaui0DH2S59d4xVx3FLYtnPemU9uppvSkwRNbkrMGp1NgI307NO+ggpDS0hNJjE7Ugpe1NpCCiiiiwwpKO9FMApD1pc0ZFK4xp6UUZFITmhAB6UlFFABRRRmgAp1Npcii4C0Gmg80pNAAetJRRSAQ9aSlPWkoAQ9KbT6aelACUUUZoAKb3oPWmmkAUUUUwKGtatFoej3F/K+0Ivy57ntXC/CWSbW77Vtcu5d87SbASP4Rgj+ddF8QrP7b4Mu1AyyYYcV872VzeQHZayyxuxwRFnJreFPmhoYzlyyOg+IoDfEHUViO5nmwNvqTXcfC3w1r2j6g17cp5VpPAVKN1OSCOtcj8O9Ie88cwG7Rw0D+a2/qSDnmvoNzuJznHTGaK0/sBSjpzCUUUVzmwUhpaKAG0UppM0ANooxRQAlFLRQMh8Xrv8SOvrGo/nXndz4tt7a8ktwHeSOYoVQZ4HFeheMs/8JDLt+95S4+uDXk6yONdkgzBlpMOFj54OevrVy1qNEw+FHfRMXRX2FdwztPap7of8SiFu/mGouj8Yz7VJdE/2Tb56eaaRRQnlaGykcdSpVSegNcho7XF3rFsj6vkxfKY4lxwD65rrL5I5NOnMgBVFz+Ncf4bureLVYpIYhK0kojDxx4UA0odQZ6HFzJ/wA1QT7x+prRhH73/gBrOTqfqallFDxDuk00wmJ5IpOG2HBrK8HQw/amZbGG2kRMNhtxzz19K2Ndjmk0SdIE3uR0zjisbwe0gltlBt0WRWaSJQd/TjcSef/wBdVF6OxPU7FelTXv8AyB7M/wDTc1CvTFT3pH9jWY/6bmlEb2IGLi3kMZHmbTt+uK4O7V7W9kP2e1i8/wDdkF97s+R82MccZrvo85G0ZbsPWuCmS4svEdyZ/s1spV2+0iMtIDxhck4/TtVQvfQh7HocCeXbW65zsiA/StrQj/o8/wDvCua0cSf2TbGaQySMhbex5IOf06V0uhf8e8/+8KqO4nsXz603qacelNFSNEsA/eYp2KbbsRMOBTx71XQS3A02nUhGKVyhKQ9aXtTaVxWCiiii4xO9FHeigBCM0h9KUmk6k0WATFGKWinYBtJmlPSm0rgOBzSHrSZxRQA4dKKTNLSAKKKKACkJxS0h60wEoPNFFACUh6UueaQ9KQCUhGaWigBtNPNPPWmd6QBRmihVLHA60xoZIkc0bRSoHjYYZT3rl9M+Huh2OuS6mULMx3LGR8q0/wAQeONJ0WOZPOWS6jIGwdjW7pt4t7pUV8CCHj3nHTpVJyWhEkmcP4LslvvGniDV8hRHO8SoB0GcV6BnPODzXBfD15mTxFdx43NcSsgI6kZI/WsnTPilfQeI5rDXooVt1ygZEKlW7Z5+tJxk22CtY9ToqG0vLa/to7m1k3wyfdNSmp32KFoIzSA0E4pAGKbilzRTGJmkpcUlAgxRRRRYCDxgceI3OQP3a8k8DrXk0ljKuq3kjSXMkckh8vyicdevHUV6r41jEmvSoWIBiUHB7YNc7BZRxXG9QMBAmCOlXLSbYoL3EXAApCjsAM1Nd86Pb/8AXU1Cox0qW6/5BFv/ANdTU9GUt0Z13I6WpSOHzWf5Qu3I+tZOlaI1vfJJG7JDC+TGOFY+tbyDnNTgYGKS0BksHMhP+yazkOCfqa0YP9Yf9w1mxc5zzyf51LKRFc28t1LHEZNtt1cKcFvajS9Njt8zvAqXByuQMYWrqjNSgYpiFAwKnvVb+wLWQAYFxjNQdqmvMnRLQZOPtB4poGVZQ7W8iRPtkIIVh1X3qpHo0I8qGRFnjKN57uMmRz3Jq+nbNTL1FCdhWFjVY1REAVVXaoHYYra0IfuLjnHzCsdeta+hECK5BHVhirhuSy+1NBOKc1IetKwIkgJ81cVJUdsQs6k9KdVaWEKaSiikUFNPWlNJSAKQnmlpKACiikNFwFxTT1oopXAKbmnHpTaYAelNp1IaQCUUUUAFOptFADqKKKYBSHrS0h60XASiiikA00macab3oAKQmlpD1pAJTe9OpvegArnvHN5caf4K1K6tHdJ0jG1kOCMsBx+ddD3NQXdpBfWr21ygkhfhlPfnNUnZiZ4b4J8CXHi8Xd9qc1xCuQFlkBO8nPc/hXe6X4gsvBVlL4f1yYxtEC0MjD/WJ7flXa2trBYWy21tCIoV6KOlcV8U/Dv9saGt9CgNzbZwScfL3FaRfPNXIkmo6EHwsvVuYNXMODC107Rnvg5rI+K3hCSTy9Y023eWR2CSpDHkjg88VnfBu8nj1O5s1QGF+WPpXtHJGMAip5uSowS5oo4n4WW97B4Wdb1Jkyw8tJQQQOegNdt2xSBQoAUAAdAKWs2aoQ8UDmlpDSGBpKKKBMbminU2ncLhRRRQBV8bOI9clc9FiUn8jXCt4us8mO0tp7iVThwAVA/Su78cDOtzj/piv9a88DQQB3JSMypgEgDJq5/GyIfCjq1PA7d8VLd/8gi3/wCupqLBwM+9SXf/ACCYf981PRldUVGEpiYW7qk38LMucVVt9Gu471bu71Wa5YdEEe1P59KtMsnlsI22SfwsFBxUdnp00EgmudQnupCMHIAUfgKlDkacP+s/4Aaz4up/3jWhBxLz/dNZyclv940MaH3CzSWzJbTrBMR8shXdiqmm6RJYzvPPqU95Iw58zAA+lXkx/EwUDuTiq6azprXqWcd5HJO5ICoc9P0oEX1wcY6Cpb3/AJAtt/18Uz2qW+OPD8GBz9ppxGQCp1R1UlsgDqT2qu4LI6qxUt0YdR9KpP4fsrlcX1xd3hz/AMtJmUfkuKEI1Y2WQB0dXXkZU5FbegLm3uvYisCzs7WwhWC0i8qIZOzJP6mug8P/APHvej6VcVqJlx/vGm0rdaSkwJYP9ZTqLUD7QoJp3enbS4uoUhoPSkpWGIetJSnrSUWAKSiiiwBRRRQAhpKdRQA09KbTu9FADT0ptOPWkJoASiijFIAooooAKUUlFADqQ0qsBnI6ikPagBKDRRQA1qaelKRzSHpSABQetAIFISM0AFNpT1pKACmnpTqaO9Fh3EqO5iW4sprduRIpXBHrUppKadncRzvhTwfaeGIJWV/MuJiS7AYGPSujHAxSjpTe9J6u40rBRRRQAUhpaKQxBSUpFLQKxHRTqbii4BRRRTsMq+OP+QzcAZyYVxj6GuBlTVpdLtLdNJiWBMfvHdS31xmvQfGX/IxP/wBc1/rWGCG4xwOlVP42Zw+FDyck9+c5qS6wdEjP/TXio8k9alugP7Bg46zEU1syuqMy6e9SDNjHFJOOgc4xVODT9bluIp73VlARgxiiQYx6ZrXXnHr2pYplkDBHyVOCPSosO5YjP73P+yaz0OCT/tGtCIZk/wCAms9P4v8AeNJjTI7rTrPUQgvI/MCHIXtmr0McUKbIIUiUDACDGKjQEkAY/GmWl9BdqfKJyOqkc0WC+pa4B4HAqa9/5F2I9vtFQ9qsXik+G4j6XNVGwmVl6CpVFRp2oa4jikjSQ7WkBK56HFShlgDnPtWzoKhobw/SsgdvcdfwrY0Af6Pen6VpDciWiLr/AHjTM4NPf7xpmKQye2YrOrDoKeeuajt+ZQKk9aethCE0lFL2pAmNPWkoPWigYUUZpKNQAd6KKDxQAUUDmkzSATvR2oo7UANPSm049KbQAUc5oooAKKKKACiiigAooooAKSlpKAENNPSnE8U0nNADTSd6U0makANFFGaYBmm9jSnikzQNCUUHgZpwHzrnpmgTMjUfE2kaVew2d3eKlxK4RIwpJJJwO1aucjI6GvB/GMzXXxXSONuVukUEngEMK94QbYYweTtGfyq5x5bCUrhRRRUalBRRRSGFJmlpvegQUHpRQelADaKKKLjIPGILeI3A6+UuP1rhpNWZNTwsihd2wo579OK7jxkSPEEpCliI14HU8HivJUYyatII47WOWWZgDcSBnQ5PY9K1kvfZlF2gj0LaalumH9i20ffzmP8AKokDKFV2BYDBIqW6A/saM4583rSeiZfYqPKIbWR2fYFGd2K5nQNUe+1OOQW8wik++4GF3fjW/qIdtJm2XCwsBneygjFcx4fktVuoEuHvUhONjTsyoX7DHuaUFcTO6iP7z8Kz06t/vGtCD/WD/dNZ6feb6mpZSI9Rl8iwklAkLKMqEGTmsTw5Ndyagk1zbJbQTIRGWf5ifp+Vamu7BpMha5mhIHy+SxDPx0461ieH7iFNSP2nTxFG/wAtu7neQ2Ox7GnFXTE2tjtP4iCee9TXmf8AhHohngXFQjp6++Klu2xoMY/6eKIobIUwWGeme1cZq2tvLrMthbwz3rRj5REvKDvXYAM0bBX2sV4b0PrXA3Ie31ectd315KrhS9qhRNh6gsvU06auxNnodi7PZwMylW2fMpOSpx0NdDoH+ovR9K5vTf7PNkn9myq8ORuxJvIbuG9x/Wul8ODNrqJ9MfypwWopbFtjljTCac33j9aaetSMmtceeme9PPVsdqit/wDXpT2+831qr6CsAOTS02ikMU9aSiii4CUHrRRSuAUh6UtIelFwAdKMUDpS0ANo7Ud6O1ADaTFLSGgAxSGiigAoNFFABSZpaKAEzRmlHWkPWgAoprUZpDENIaU9KaelMQhNNJpxpp60hig8UUDpRQAxmAXcxAA5JPasix8U6NqWoGxtLtXuMkBc9a2tqt8rKCp4II6ivGbTw5eTfFQzaZbPBaW8m5pEXaqjB9KuKTTE3Z2PZDnBxwQD1Fc94P1651tNQ+0ogNtdGJSgOCPx710TnEEpL8BTknp0rk/hxCF0fUX/AOel9Ic59MVHQXU878Vx2lt8XbWW4bbCbiOSQjt81ezw6jp93KIbO6jkYKCFDAnFeBePPM1T4g3sNmjXEvm+UiRjcSc47Vt+APC3iTTvEsN5cWN1bQgHe0qlRjI65rorRVua/Qzpt81j2qilNJXMbXCiiigYUmKWigBtJmn9qYelAhKKKKBlXxsZF16QRD94Y1AOenWvMW09Irm4WUwjLH95uG/eT6V6j4y/5GNv+ua/1rlZNH0+a7F09uGmzu3Z71rJ2mzKC9xF+KJoYUjZ2dlUAsT1qediNEjjJ4801EOpqS5GdHjPbzKW6ZZSltVuCisx2A5KjvXO6RFbi9hhaOaX59zrJEQI2HTk1068cjrUg44GPwGKmMrMGrk8H+t/Cs8feb6mr8H+t/CqA6t/vGkNCSWqTnfIqsVUhAT7VmaJGIruONLCeOJQSzSoAA2O3rWyuexIqfcSBk5oTtoDVxwGKfef8gIf9dxUY6GpbsH/AIR7PYXAzREGVJIxLBJGPlLgru+tc5qcdhpV2heeSDZGNgCM3mnHTgevP4V0ydqlCo3+sRJMcguuaE7BYg0iG2h06FreEReb+8cYIyx6n+VdV4XkCRagD0Kj+VYI9O2PTAFbPh8gC9B7qMVdN2ZM1dF1uGNMPWnyffNMqRk9tjz4/SntjzHx60y1P+kxf71Pk/1sn++f51XQnqJSGgmkpXKCkpaSi4BRRRSAKKKKAENJTs0nWgBKO1FHagBtFFFFgENJTqM0AxtFB60UgCiiigYUlKaShiQh60lKetJQMbUL3EaXcVqzYmlUlFx1xj/Gp9pPGOtcTrus39v4hS5soo/JgjMPnSnjcfTH0ppEs623uY7oyiJtxify346MO360+uJ0q18QvqZt5tRitkuD9rPlxk7x0wfTpXbYKgKTkgYJ9al72GtUOHSg0DpRmmhir94fWuK8Ja7BN4o1fTGIEvmEoMdfpXarw4PvXkvizwzc+FNcj8U2FyTEZR5kZGDycdfxqoRUk11Jk7NHpmrtINFvfJ+/5ZwK8z8PeJdRXwbc6follJPepLIZn4UJnHc969Stbhb3TIJ8ArPGGIFZ2ieHLbRLPUILVwTeSF9xXG0kdP0pxashNX1PFPh1e28HjtJ9Vn2SvJgFwTmQnv8AjX0GxJ5yCD0xXzNe6fcaV44+ySA+fHegqw7/AD8V9KW+77JDu+9sXP1xV4hJSv3JovQfRRRWFzUKM0UhoGBFLSDiloAO1NPSlzSHpQA2iiigCDxl/wAjE/8A1zX+tYajFbnjL/kYn/65r/WsMHitZ/EzOHwIeKluP+QHHx/y2xUQ6VPOCNCXHI+0AUR2Y76oqCpBUanPFS9qySKuS2/+uX3FUQRvYf7Rq9bn98n0qjjEr/7xpgSjGKlXpUQ6YqVelIY5e9T3n/ItN/18LUC96nvM/wDCNNj/AJ+FqoiZUU9KnWq69RVhagZIOlbOgKGF7nqEH9axf4TW34eI/wBOyP4B/WtaVr6ky2LcmN/FNp7jLUypAmtuLiL/AHqkl/1z/wC8ajg4uISOu7FSS8TSf75quguoyiiikMQ0Up6Ug6UAFFFJmkAtFB4FIDmkAEUopCcUtMBMUnUUuaToKAEPSkpc0lFwCkIpaKAG0Up60lABRRRQAGkoooBCHrRig9aM0hjHBZCoJBPGRXnPi/TrOximtL3WZobZgJ44VQEyP9cE16QOorz/AMfrq8F415aRWzxGLYgmjEjE99gI/lVRZMiXwK9vcXklzF9rZPICCa56EjPSu1PXmvM9Ks7o/wBjrNrVw8zvuazD7Ao/3R2/z2r01xhiKh7scdhAaSiigodnmvP/AIvztH4WtwAdhkUtz6MDXfqNxxzivD/iJ4gn8SeIU0jTGaeBeAkfIZu/StaK967Mqr0sep+DblLvwhp8ifwx7Tzmt5OGycADkk1zXgrQJfD3h2K2llZ5JAHKsfue1WvFmuQ6D4euLl5VSVlZUBOCxx2qI2bsir+6ef6XbW/ir4sahdzAGOzl/dj1KmvWyd2CK8S+GiahZ+LI5ry2njW/BdWdSA2ec167q+tWGhQxTX86wRSOEVm6Z/yKdS7m0iaeiL1FVrHULPUrcXFlcRzwno6HIqzWZqFGcUHik60wDrS0nSloAbSGlNITmgQlFFFAakHjL/kY2/65r/WsMDitzxl/yMbf9c1/rWIOla1PiZEPgQ9eoqedtugquMgz7qrr1qW650OP/rrQtEwa1RWUYNS1EvFS9qzuXYlt/wDXpVNh+9f/AHjV21/4+U+tU5Ri4kA6bqOghwqVOlRCpU6UDHDvVu6/5FVz/wBPC1VFWrsH/hE5SO1ytOImZ6dRU6cmoE61MnWoLJe2K19BIDXmc8pWQOla+g/euv8ArnVw3IlsaL43cUw9ae/3qYetDBEkJ/fxezZqWUfvpM9d5qGLieP/AHqmkAEsgBz8xqulieoyiig0WKE7UDpRRUgJmjFLiigAPSmjg5petBFIYlOHSm0ZpiEJ5pe1GKKAG0UpHFJQAUhNLSHrSASiiigAoopKACiikyaBgetJRRQFhrkqhIBYgdB1NYi2F3e3VxqWoKg8uIrawA5K+pPv0rcPSkLHr/Si4mjm9J0eKaWx1WZNlxAjR4PfnP8AWuhPJJ5oAAXaBgUtIaG4pKdTe9AzL8UXD2nhm+mjcxusbYYdRxXh3w21PT9L8YQ3WpEqhBCuwyASp5P5173qVjFqmmT2MxIWVCMjsSMV4ddfCvxHbNsSJZohnBB5ropuPI4t2MKnNzJo96gmhvolltpFmQ/xIcgV5L4yin8X/EODRYHK2tsi+YG6dTmsPS7Xx74WheWyt5RCDkoylhx+NRaR42vdI8RXGranpu+7mXaflZQPwpRhyp8r1BybZ6Z4rWfw6NJ1S0gM0GnAJIidSo9K5zULHWPiddRyyR/YNJi+dRMSGOOOAAfWrNl8YNKvnEGo2XlxPwT1FdhaeLvDt3bj7PqEMadlyFqPejsi9GT6FoltoGmrY2mdifeJ7mtOo1uLV4vNFzEU9S4qpYaxaanPOlo/mJCcFx0JrO1i7l+jGKB0pDQMXFFJk0tADTzTacabQAUU0k5ooAi8Zf8AIxt/1zX+tYg6Vt+Mv+Rif/rmv9awlrWfxszh8CJAcCn3TH+yYgOnmGmCpZ8f2Imf+etT0ZXUrLnvT+1RpUuKgssWmPtkeemeaqXQxfT7fu7zj9KtWZH2uL/eqrMB9plA6hzVfZJFXpT170ypF4pBccKs3bY8KTD/AKeFqutW7kZ8JTnPS4WqjpcUtjOTtU61CnQVOtQUPHStbQfv3f8A1zFZIrV0Hme5A/uVUNyZbGm/3qYetPfrUZ60mCJIv9fH9alk/wBa/wDvGoovvp/vCpJOJZM/3qpC6iUGg0lAwoopCOaQCU7tRTaAFHWlptFAA3WgdKKKACiiigApDQTxSUAFIetLSHmkAlFFJTAWkoopAFFFFAIaetFKetJQUFIelLx3rGm15hK8Nppl1cyRnDcBR+ZosI1jTa56fUPFdwCLTRIbcf3pZkz+hreg8428RuABNsHmAdN2OaQ0xx60tFFAhvSnBmHQmkPWjBpDF3vjBPHp61SuNK068ObiwglPQll5q3ilwaaFY4HxN8LdJ1OCSfSIRa3gU4QMcMcehrxvU9A1PQ7ow3tpImCcMOhr6h5/XFDYdMPGrjtuANbRrNaMzdNM+cdE0fxJrqmKx+1LAMAkt8vNe6eE9BXw74egssfvuWlPcknNbShUXaiqq+ijFHespzci4x5QpGpaKRQ2ndqbRQAGmmndqbQAlFGaKAIfGPPiJx/0zX+tYa8VueMePEb/APXNf61h9s1rU+NmcPgQ8VJcc6Gn/XbFRZqeZSdByP4bgUlsx9UVUqTNQoe9S1mWTWn/AB8x/Wq83F5N/vkfyqxan/Sowem6orkKL6cL03n+lVfQQg6U8CoxUopAOFW7gMfCN0QOBcLVOrdxMyeE7pARgzKTxVx8yZbFCM8Cp1qBBgCp19azLH44Favh1gL6cE8GOsvqMVo6EoOoSg/3KunuTPY1m6VHT34yO9MpMEPj4dP94Gppv+PiUjpuNQxn51/3hU84xcSj0Y1SegupH1o70tBpMYlFFFIBM0lHelxQAlFFFABRRR2oAKKKBzQAmKSnHpTaAENANKRmmnigANFFFIBKXNFJQAuaQmigigY3OeaQ9KcF4pCKBgeKA7gYVsfQdaKaRjvQxWELN3JJpKUkHpSUgCkpaSiwwNHaijtQAgpe4GOtQXV1FZWctzM2EQZNUtA1208RWLXdm2VjkKMM9DRbS4jA8V/EWy8L3os2tXuZ8ZwrhQP0rodE1aLXNHg1CFTGso5QnOK8++L2hWa2sOrIreexw5HSur+H8ar4KscZGV5rScUoprqSnq0dNS4pO9OPSsy7jaKKKAEIoxQTijNACdqbS5pKAGkUUtFAEHjI/wDFRv8A9c1/rWGDxW74yH/FRSH/AKZr/WsIdK0n8TIpr3EO7VakB/4Rx3yOLkVV7VZlAPh9gen2kU42swe6KaAYqSokPFSZNYmlie0/4/Iv96oZ2D3krY5LGprTm8i/3qguBtvZlwPvnGKtbEsBUoqMdKkFIQtWboA+Fro/9NVqtVm4/wCRUvfaVcVURMpKelTp0quvarCdKgskFaWhf8hCU/7FZnQitLQj/psx/wBiqhuTLY1X5Y0zpUkn3zxTD1oYkOjHzKf9oVPP/wAfMnu1V1OGX61YnBFw4zk5qugdRlJSmkpDCiikJ5pAGKXtSZNLQA2ilI4pKACilApD1oAKBRRQAnY0lKelJQAUh60GkoAKKKKQBRijpSZoGLSUtFACU3+LFOpvegAJwKYzZBTKiQqSoz1p54rkvHGpppgsZUFybhGYqIk3fLjnNNK7A0NM1xbhVjuh5c7ysiDpuAx/jW0Rg4rg9Ek1W5ks9STTkS0QHMl04QnJzkCu6DiUCRSCG5ypyKTQhaSlooGJRRR9aQyvfLb/ANnzm5jSSJUJIk6dK474Xwxroep3CKESa9fYBwMDHStXx1qUWm+GZkkYh7giNMe9XfDunRaT4btYVKgCPzGOeMnrTeyRHUxPigV/4Q99wGC4/DmtjwpJaHwtYpazROFiG4IQcHjr6V5r8SPGtvrVpJpen21w8cDkSXBX5Dj0xWl8JtA1C3iOrS3K/Y54yqRbiTksCDjpWlSKUE2TF+8z1ClzRikrI2CkJxS0hoEHWm5pabQAUUUUAJRRRQBD4x/5GGT/AK5r/WsIdK2/GX/Ixv8A9c1/rWIOlaT+JkQ+BDj92rEuf+Edlx/z8iq56VYkUnw9Ic8C5FOOiYPdFNOlS1COtSdqyNCxaf8AH3D9agnx9vuMf3z/AEqWzP8ApsP+9UVz/wAf9x/vn+lUtiWA61IKYOlPFIQtW5f+RVvv+ui9qqVbkx/wi998pJ3r3qkJlCOplqCPoKnWoLJe1amgf8fk3+5WV2rV8P4N9LkjOw1UdWTI1ZOtR+tPfr+FRnrTEhy9Vqxcf8fMn+9VdasTk/aJeMfN2+lP7Nwe6GGkooqRhRRRQA2nU2nUAFIaSigBR0oPWkooAKKKKQAelNpxpDTASiiigaGnrQaD1opAIetFFFFwFHWikooAKKKKAG965PxpdanZ2QZZrO2tJG8sSuuWGetdZ9a4zxHanXbW5mvYiLW1dVtomBG5j1J/IU0KRh2cGnXGvQ6Ne67cXtsYAylPlVWOfkPP+c16PbWsdlaxW0X+riUKvOeAK4WPTA9leRXdlFavvRInj47cMD/npXb6faPY2MVu9xJOUXG9+ppS3FHYsHpSUp60lIsKTGeBS1HcXEdrbSXErARxqWJJ9KEB5Z8Y75ftei2W5uCzvzx2rrr+8nudMstI0+FvOubZQZh0jBHNeba/Y3HiWK48TXLkILkRWyYwNvf617NpcbQaXZDADCFQSPpV1Gla3Qyj1ueYeI/EOj+GtFvPC9rpLTXYgMclxgD5iMbjxyc81t/Cc37eFy07g2mT5S45FQfEjVbPSbG7jGlBrq7jMf2gqeMjFM+D1/PNoc9pIhEcTZVsYFVP+GrdxR+M9HooorI2CkNLSGgBKKKbQAUUUUAJRRRQBB4yH/FSP/1zX+tYQBre8Zf8jE//AFzX+tYQ4rSfxMiHwIU1bbH/AAjcoPX7SKqVYnH/ABIWPYXAoWzBopr7dKkpidKfWSNETWePtkX+9Udyc30/H8Z/pUll/wAfsX+9UVzxfXH++f6Va2ExRTxUampF5FIkWrp58NXw/wBpapirRH/FO6h/vLVREzPj+6v0qdagi+4v0FTpUFkgrU0AD+0JSf7lZY61oaOSt8wHdTVw3Ja0Nl+v4VHUj/e/CmHrQxIcDwKsTkefIKrdqmmObh/r/Sn9kb3Q2iiipAKQ9aWkPWgBKKXFGKAEooooAKMUUoNACUUUdqADIpCaSikAUhoIzSYxQAUlLmkoHcCaBQKKTAWkpaKdxMSm96cabQMD0qpqViNS02azaQoZBw/90+tW6CM0A0cZ/wAIlrNy8Eeoa6ZLWBsrGoPOPWuwUbVAznAAp2KSkCQhHNJTqb3oGA681z3jZnl8MPYw5+0XpWNFHU/OCf0zXQjrVG7sDc6rYXbOCloSwQ9zgj+tGwmcd47tk0XwBYWsS48mRd3ua7HTLlZ9FtLjPyGBTn6Csfx5pVxrXhqW3tlDShw4U1Hb61a+EPB9i+shxhNrIi7if5U7XsidjH+J1jc6/olt/ZUL3JTJOzoPzqr8JtbD2lxoNwBHdQbmA7kZwazLO28U+JJribw3dPBpFw7MpnO3aD7c10ng34dS+HNXbUb2+W4uGQqdvTkg/wBK1nKPJyvclJuVzuKKUrgZyKSsTUKRulLSE0AJ2ptOpvegBcgim96UcDFJQAUUUUAQ+Mv+Rif/AK5p/WsKtDxBNJN4hu/MbdskKL7AdBWfWk/iZEPhQvbNWZufD0meguRVap5v+QEw7faBxRHZg90VF4FPzTE+7UmKyNSa04vYf96o7wY1C5H+2f6U+0P+mwf74pt7/wAhK4/3z/IVXQljBUi9KjFSr0oEKDirqqW8O6gewK1Sq7EM6BqXoNtUhGZEcov0FWF4FV4fuJ9BVhetQUSLzV/SeL5fcGqKir2lf8hFR2waqO4nsbT9AajPWnnkCmHrTYhc1YlA85yPXNVqsSZ81+MdKdtAY3vRRRSsAUmKWikAUmaWjAoAbRSkcUlABRRRQAUdqKKAEIpKXrQRQAlIaWkPWgBMUYoooASilpKQC4oopDQAHmkxS0maBiUGg9aQ9KAAHNLim0uTSGhDTe9OpvrQDCijBzx096ZHMkyb4WDp6g0WEPHByOtZPiHQbLXtMljvEZvLRmXacdqtahq1hpEXnajdRW6HoHbBP4HrXB6jdeKfGtxImhvNYaWvy+dkp5nqR7YxVwWt3oTJ6FDRfiDpvhbwp9h2tJdQu6CPHcZxmqUnxO8VaxBJHpelQoo6t5RJx+dafgPwLplxbXdxrFql1cRXDLucbskGvSLaysbCPZaWUEC+iRAf0pucIv3URGMmtWeWeBfEPiiTxAbO/jZ4ZcGTzEPy+4r1ntTRFEH3iKMPjG4IAfzp1RJuTuaoQ9KSlPSkpDsJmkpTSUAJSZ60tIelABmikooCxla3/wAjBf8A/XY1Tq9rv/Iev/8ArsaojpWkviZEX7otWZf+Rfl/6+BVbuKsykf8I7OD3uFpx0TE+hSWpKjWpO1YmhJa/wDH3D/v0l9/yEbj/f8A6Cn23/H3D/vU2+BGpXGc/f8AX2FPoDI1qUCohUi0CHZzV2Hd/YOp4HHy5qlV62/5Aep/RauImZcH3E/3RVgdarw/6tPoKsLUFEi1oaT/AMhBfoaz161paP8A8hEYP8Jqo7iexrt2+lMPWpX6D6VEetNiF/hqeZt0z/hUHap5hiXA9AaBdRlFFFIYUUUUAFNoooAKKKKACk70tFABRRRQAUh6UDrQelACUh60tIetACUUUUgCiikoGLSUtIaBBRRTaBhRRRQAUhpaQ9aQxKKKKAOQ+IGtatpGjqdKtZJHkyHkXPyelcjD8XlstJisbfSnW8C4+cgAt6164cY5AI9PWsPU/CGh6ndJcy2YWRDn93xWimktUQ4tvc5XSPCN7rNwuv8AiudXi2744M7go9/0rtNB1nTtZspX0r5ba3cxFQMDI6/zrD8e+IIPDHhkW6JmScbIkLdv8mud+DGqQrY6hZyuiStN5gBOMjihU24qbJclsdB4DZmTWVYk4vZOp9zXVh1Z/KDqX/u7hmuQ8BtuvfEOCChvXAx/vGuJ+I1hqvh/xCNXtb2RYp2ATA4U4zilCHNJopysrns1FedfDnxtea1PPpmpAPJGqmKUfjkY/KvRT96plHldhp3VxD0pKcelNpFCHrSUp602gLhRRRQMKKKKAsZWuf8AIevv+uxqiKva5/yHr/8A66mqQHFaS+JmUdkAqzKB/wAI9cZ6/aFxVap5wTocuP8An4FNfCx9UVF56VJ0qOPipTWJoPtWP2uD13ii/OdSuM9d/wDhSWo/02H/AHxS33/ITuf97/CqvoJka08daYDing0hDhxV+2J/sbUcegrPq7bHGkaiPVRVReopGdB/qo/90VYWq1v/AKiL/dH8qsrUlD60NG41A8/wms8GtDRv+P8AP+6aqO4nsbbfdH0qI9akbp+FR02xDu1Tzf6wntgVXzU0gG78BQAlFHQUZpAFFJmjNAC5ptFFA7BRSZpfegQUGiigAoooNADR1pSaMUlABSHrS0hFACUUUUAFJS5ozSGFJRRQAUUUmaBiYopSaSgQUhFLRSYxtGaU9aZ3oAKKDyaKAPNfil4a1LWtS0lrNDLHhlYA8JnHNYPifwJL4U8P2uoafPLHdAYuCjH8+a9oz6GkZY5EMUqh43GGBFaRqSTS6GcoJnkHwf1mRr66sZG3ed+8LHqTXf8AjLQR4j0CSzwPORhJGfcAjH61j+E/Bj6F4n1O/kCiKSRjD9Cciu29CODUykudyiNR0szyn4Z+FdS03W7q7v4/LCABc9+a9WPLE0ZJAyTx2zRScnJ3ZSSSshD0pKcaYwxSKA02looAKTvRRQIKKKKAMrXP+Q/ff9dTVP2q5rf/ACH77/rsapjrWsviZnHZBjFWJRnQpj6XC1AelSzf8gKXn/lutNL3WD3RVHBp9MXnmn1gakltxdw/74o1DjVLn/e/wpLY/wCmw/8AXQUt/wA6pc/73+FC2EyIHingZpgHFPBxTAcOKuWo3aZqA/2AapZq7ZHOn6iP9iqiSzNtz/o8P+6P5VZU1Vth/o8P+6P5VaAwakoevWtHRv8AkIgeoIrOXrWjov8AyE1qovUT2No8gH2qM9alIwox6Coz1oYhKsSffH+6Kr1YcfMOf4RTQDaQ0tGM0gEAzRilopgNopcUlIBMYozS0mKQxRzRRRTEJnBpTzSYpaAA9KbSmkoAKQnFLRigBtFBopABGKSlNJQMKKWkoAKTFLRQgExSU49KbQMKQnFLSHrSEJSd6WkxQMSilxSUAApD046+tLSGgQmMD/GjHGaWjtQMTFJTu1NNABTTzS54pO2aAGniilNJQAUnelpO9A7BRS4ooAydc/5D99/12NUxV3XP+Q9ff9djVNa2l8TMofCIelTTjOhyc8+euKZT5f8AkBv/ANdl/rQvhYPdFNT8pqWolqUVgajof+PuH/fFO1HjUrjH9/8AoKIv+PqH/fFO1P8A5Cdx/vf0FPoBXFSVGKkoEwxmr1iQthqGe8dUhV2x5tL/AP651UdxMy7Q5toP90fyq2OtVLX/AI9of90fyq0vWpGPHWtDSeNRX6Gs8dav6T/yEV+hqkJ7G+fuD6VDUj9F+lR0MEOqVuq/7tRdqlbqv+7TQhKKKKQBRRRQAU2jvRQAUUUUAFHagUUAFFFFABSGlpDSASiiimAU2g9aKQwooooEFFBpKBhRRTTQMD1ooooYBRRRSAaetFOptADaKKKACkNLSGgAooooAbRRQelACHpSUUdqAENJRRQNBSUtFAhKKKKAMvXP+Q9f/wDXU1SHSrut8a9f/wDXVqpdq2luZQ+EUHmrDAnQJgP+eq/1qsvWpn/5Ach54lX+tL7LGtyoOtSdqjUVJ2rFGo+I/wCkw/8AXQU7U8/2rcZ9f6Co4j/pMX/XQVLqn/IVnHuP5Cn0F1K4qSmAU7NIYuavabzDfj/pkf5VQ61e0v7l8P8Apkf5VUSWZlpxbQ/7o/lVodarWo/0eH/dFWhwaQxwq9pJ/wCJkn0NUM5q9pRxqKH2NUtxPY6B+i/Soqkb7o+lR0MSHdqmY/c/3ahHPFTMPuf7tNANooooAKM0Uh60XASlzSUUgA9KQcUtIeaQxaKBRQIKKKKYAelNxTqDSAbSEU4nNJTATBpKdTaQBSikoFA2HekNLSGhgFNIJp1JmgBKKMY5ooGFFGcUUAIRzSUuaMUgGYopc0lABSGlpDQFwoopM0ALSHkUvam0ANxRSnpSUAGabSkUlABRRRQAUUUUAZWuH/if34/6amqQ6Vd1wf8AE/vv+upqjnFbT+JmUFohQcGpm50Kb/rqv8zUHTmpjn+xJwMY85RQvhYdSstP7VEhNS1iaiRf8fUX/XQVPqYzqk5+n8qhj4uYj/tg/rU+qNjU5R67f5U3sHUrLxTqaO9OFSMAav6Vz9t/64mqFaGj8veD/pif5VSE1oZtqf3EY9AKs1Wth+5i91qzUgOAq5pnGoJ9DVQVa00/8TFPxqo7g9joX+6KZinv0WmZpslCjg1M/wDB7rUNTtykfstNAMooopAFJilooATFIeKXNJQAUd6KKACiiikAUUUUwCkPSlNNzSAKKKKYxM0lKetJSAKM0UYoAM0lFFAgpvelJoxQNCZzxRR0NFAwIzQOKKKAExRmlpvekAmKSlzSUAFIaWkoAKb3p1IRQAZpKKKAEJzSUuKSgBCcUlOxmm0AFFFFABRRRQMyddP/ABUF9/12NUqva7/yH77/AK7GqNbS+JmUfhQoGTUjDOizH/pqv9aiqYDOhz/9dV/rS+yw6lReKlqNeKkFZI0EjP7+L/eH86n1UEapJkdlP6VXjz58X++P51a1Y/8AE0k/3V/lQ9h9SqKWkXpTqWwwq/o5xNd/9cT/ACrPrQ0f/j4uPeI00Jmdb/6mL/dqyvNV4P8AVR/7tT0AyQVZ0/i/iIqqtWbH/j/h+tNbks6InKrTQOKc3Sm02IWp2+4n+7UFSt/q4/pTQCUUUUgCiikPWgBO9FFFAxAeaDQetJQFhRTgOKaOlLSEFLikoNAAelNp1IaYxKKKKQCHrSUp60lAgpKWigYlFFFILBiiiimAmBQRS03vQMKKKKQBTe9KetLQBHRTqbQAGkpTSUAFITSntRQA2iiigBD0pKdSGgdxKMUGg0CG0U6m9aBhRRnHFFAGRro/4qC9/wCuxqp2q3rpz4gvv+uxqmDxWs37xlHYUcGpTzok/wD10X+ZqGph/wAgS4/66L/Wl0Y3uVVBxmpBzTFPyipMccVmihqf6+L/AHxVnVv+QnJ/ur/Kqq8TR/7wqzq3/ISb/cWh7D6lZaU0gpaRQlaOijN9KO/lGs7vWjoZzqLj1iaqQmUY+g9uKlqCPOT/ALx/nU9IBwqxZ/8AH7D/ALwquKntP+P2H/epoT2OlPMfFNwad0QU3NNkoeOgqQ8Rp9KizxUrf6tPagBtFJ1oAxQAuaTrQetANAxKKKPWkIQnikooxQMUdKWkHSloEFGaKOKBhSHpSn71IelMQlFFFIYh60lKetJQAUlLRSYCUUUUAFFFFMANNwRS5oNIYlIRS0UXEJikp2ab3oGFNpcj/JpDQAUhpevQ5pyxvI2EUsfQUCuMozVkWF2f+Xd+uOlTjQr0jO1ef9qqUJPoLniupmjmj8K1l8P3jAktCv1J/wAKkTw3OX/ezRBf9kE0/ZVOxPtYdzF6deKCM10A8NKD/wAfPHfCDmrA8P2fcMT65q1h5sl14LY5bB9KACTgAk+g5rsItHs4lK+Urc5y4yanisbaFsxwop9hVrDS6sl4hdjiFhkdgqoSScAVY/sy9/595PwrtdvsKXGf/wBdUsMurI+svojjU0e/kXcLdh9XAorsSD2x+dFP6tHuL6xI8o10Y1+/P/TYiqQ6V39/4Otr6+luvtEkZkO5lAGM1lP4HuxIwjuYimflLZBx71E6Ur3RcasbWOVHWp/+YJc/9dF/rWhL4Y1WKR1FqXx3Vhz/AFqJtM1BNFuQ9lcKdykfuz2zUOMknoacyfUyl6U/mhYJggYwuPUkEf0oB+mfrWKNRo/1qf7wq3q4xqR/65rVTOJEPuKt6ycamOOsKmjoC3Kope1NB4pRSuUBrQ0LjVPrE1ZxrQ0U41Jf90imhMpL/rHHo5/nU1QgYnkHpIf51NQA4VNa/wDH5D/vioRU1scXcJ/2hQtxPY6dhgAUzGakf1qMGqIQ7HFSHlFFMFScbBQMYBiloooAQjNGKWigBtFHeikAmKU80UUDAcUUUUxWCiiigA70Hmg9KTPIoACMUlPMcnZCfoDThbTsMrDIR7KaLMV0QnrSVZWwunJxA/4jFSLpV2zAGPAPcnpT5JPoLniupSorT/sOf/nolTR6ECn7yUhv9kVSozfQn2sF1MWj6c10MOiW6bt7M+enbFTrpNmrZ8vPsxyKtYeQvbxOXPHWkzx0rrhY2oGPs8X/AHwKeLaEDAijx/uiqWGfcn6x5HILFIwysbN9BT1trh2CiF8n1U116oqjCqB9BS49zVLDLqyXiH2OX/si9/55r/31VhdCmKgmVAccgjpXQYoxVLDwRLrz6GJFoAIPmzNkf3AKmXQLcHLSyEe2B/StaiqVGC6EurN9TL/sG07vMf8AgQ/wqwNLtBj9yvFXKKpU4roTzy7kEVnBCCI41XPXipBGoOQoB9QKfRVWSFdiYoxS0UxCY6UtFFABRRRQAUUUUAFFFFABRRRQAmBRtHpS0UAGKQgHrS0UAMeGKRCrxqykYIIrNn8O6VPE0Zsolz/Egwa1aMD0pOKe6Gm1scrc+CtPlVDbySQlW3Ek7sj0qrrPg+e5njmtZlYhAhV+Oneu0wKMCs3Rg1sWqs09zzSTwhq8eAsCyZ/uSAAfnVWXw9q8LbWsZP8AgPzfyzXqmAewpcVm8NHoaLES6njlxaXFrII54XifGcMMcVZ0fJ1JABx/9avV2ijY5aNSfcVUfSbB7lrg20fmnqwGD6VP1a2zK+sd0eUn/j6m/wCujfzqX0ru5fB2mSSySAyqzsWwH4BP4VTfwOMkpfEegMX/ANlWboTNFXgcl3qW3OLqL/eFb03g2+jiLRzRSN6Yx/WqreH9RtNs80UYVWzw4qfZyi9UP2kXszVk6tUa1LOCJGB7UxU3dDSGthelO/gFWF025dFYBcEZHzVZTR5WQZdVPp1q1Tk9kS6kV1M6itMaNJn5plA9lp/9iL/z8nH+5T9jPsT7WCe5k0hPNbq6Rb7eSxPrmpU022QYMYb601QkJ14nNknoOT9KcASQApJPTiunjsreI5SFAfpU2xP7q/lVLD+ZLr+RzH2K5xnyX59qcunXjDIhOPcgGunxRgelX9Xj3J+sSOcXSrxjzGF+rCnjR7kkDKY74NdBgelFNYeAnXmY/wDYg/57H8qmTR7dVw5Zj61o4HoKWrVKC6EOpJ9SjHpdqjhthOOxORU4srYEEQpke1T0VShFdBc0u4m0YxgYpcUUVRImBS4FFFACYFLiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA//Z";

        Resource resource = new Resource();
        resource.setPath("images/cover.jpg");
        resource.setMediaType(ConstStr.MediaType.jpg);
        resource.setContent(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)));
        return resource;
    }

    private static List<Spine> getSpines(Resource cover, Resource chapter1, Resource chapter2) {
        List<Spine> spines = new ArrayList<>();

        Spine spine0 = new Spine();
        spine0.setResource(cover);
        spines.add(spine0);

        Spine spine1 = new Spine();
        spine1.setResource(chapter1);
        spines.add(spine1);

        Spine spine2 = new Spine();
        spine2.setResource(chapter2);
        spines.add(spine2);

        return spines;
    }
    private static Catalog createCatalog(Resource cover, Resource chapter1, Resource chapter2) {
        Catalog catalog = new Catalog();
        catalog.setDepth(1);

        List<Catalog.NavPoint> navPoints = new ArrayList<>();
        catalog.setNavPoints(navPoints);

        Catalog.NavPoint point0 = new Catalog.NavPoint();
        point0.setTitle("封面");
        point0.setResource(cover);
        navPoints.add(point0);

        Catalog.NavPoint point1 = new Catalog.NavPoint();
        point1.setTitle("第一章 新的开始");
        point1.setResource(chapter1);
        navPoints.add(point1);

        Catalog.NavPoint point2 = new Catalog.NavPoint();
        point2.setTitle("第二章 旧的回忆");
        point2.setResource(chapter2);
        navPoints.add(point2);

        return catalog;
    }
    private static List<Guide> createGuides(Resource resource) {
        Guide guide = new Guide();
        guide.setTitle("封面");
        guide.setType("cover");
        guide.setResource(resource);

        return Collections.singletonList(guide);
    }
}
