# epub4j

#### 介绍

`epub4j`是一个操作epub文件的java库，支持生成与解析epub文件.

通过`plus.myj.epub4j.Epub4j`类可进行生成与解析，另外`plus.myj.epub4j.simple.SimpleEpub4j`类提供生成简单epub文件的功能，参数更少。


#### 软件架构
* java项目，只依赖了 `dom4j`，除此之外没有其他依赖。
* 目前在`jdk17`上运行成功，项目中使用了文本块。但如果简单改改应该可以降到`jdk1.8`


#### 安装教程

下载源码运行

#### 使用说明

1.  在`plus.myj.epub4j`包中有三个以Test开头的类，类中演示了`epub4j`这三个功能的使用。
2. `TestGenerate`演示了通用的生成epub文件，`TestParse`演示了epub文件的解析，`TestSimpleGenerate`演示了生成简单epub文件的生成
3. 生成epub文件应创建一个`plus.myj.epub4j.entity.Book`对象，解析epub文件也会生成该对象
4. 生成简单epub文件应创建一个`plus.myj.epub4j.simple.entity.EBook`对象
5. `生成简单epub文件`本意是指创建一个只有文本，没有图片、样式等的电子书文件，通过特化生成结果来减少生成时的参数
6. `epub4j`不负责操作html文件，请自行解析、生成
7. file目录下的epub文件可以用来观察其文件结构，将其后缀改为zip并解压就可以

#### 实体类说明

* `Book`: 代表一个电子书
* `Catalog`: 表示目录，用来生成`epub`格式中的`.ncx`文件
* `Spine`: 表示页面顺序，用来生成`epub`格式中`.opf`文件中的`spine`标签
* `Guide`: 用来生成`epub`格式中`.opf`文件中的`guide`标签
* `Resource`: 表示资源，包括html、图片、css等，用来生成`epub`格式中`.opf`文件中的`manifest`标签，不包括`.ncx`文件
* `EpubFile`: 忽略，内部类
* `EBook`: 代表一个电子书，生成简单epub文件时使用，参数更少
* `Chapter`: 代表一个章节，生成简单epub文件时使用

#### 注意事项

1. 对于同一个html页面，应该只存在一个`Resource`对象。在创建`Catalog`、`Spine`、`Guide`时，其所含有的`Resource`对象应均能在`Book`的`resources`字段中找到，否则可能出现预想不到的问题。
2. 创建`EBook`生成简单epub文件，`epub4j`是采用字符串拼接的方式实现的生成html。也就是说，在章节内容中写html标签是会生效的。这一点在`TestSimpleGenerate`类中有所体现。
3. 在默认情况下，文件编码为`UTF-8`，除非指定编码
4. 在html文件中所引用的其他文件应以`Resource`对象的形式添加进`Book`的`resources`字段

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
