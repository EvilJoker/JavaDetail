
官网：[https://github.com/FasterXML/jackson](https://github.com/FasterXML/jackson)
## 描述
java流行的JSON library。Jackson是一系列java数据处理工具（data-processing tools），包括流式的json解析/生成库（streaming JSON parser/generator library）,数据匹配绑定库（data-binding library:POJOS和JSON相互转化）,和
额外的模块去处理数据，支持如下格式的编码：`Avro, BSON, CBOR, CSV, Smile, (Java) Properties, Protobuf, XML or YAML; ` ;甚至通过大量的数据格式模块去支持被广泛使用的数据类型 如` Guava, Joda, PCollections`等等

实际使用中常包含的模块有
1. 三个核心包（core packages）:`streaming,databind,annotations`;
2. 数据格式化库（data format libraries）;
3. 数据类型库（data type libraries）
4. JAX-RS provider 支持JAS-RS接口格式，解析和生成json类型
5. 各种其他模块

## JackSON 项目设计模块
 1. 核心的模块
 Streaming  -- jackson-core :low-level的streaming API和 有关JSON方法具体的实现
 Annotations -- jackson-annotations :标准的Jackson 注解
 Databind -- jackson-databind :实现数据绑定（和对象序列化）,依赖 jackson-core和jackson-annotations packages实现
 
 2. 第三方数据类型模块  Third-party datatype modules
 >详见官方的链接 datatype
 
 3. 支持 JAX-RS
 >详见官方链接
 
 4. 数据格式化模块
 >详见官方链接 dataformat
 
 5. jvm 语言的模块
 >Kotlin
 Scala
 
 6. 支持 Schemas的插件
 >schemas本身是一个xml文件可以取代DTD来提供格式校验
 >比如ant编译配置json文件提供格式校验

 7. 其他稳定模块
 +  详见官方链接
 +  Jackson jr 专门为移动端准备的简化jackson
 
 ## 教程
 jackson-docs 
 [https://github.com/FasterXML/jackson-docs](https://github.com/FasterXML/jackson-docs)
 
 StudyTrails 告诉你jackson是什么
 [http://www.studytrails.com/java/json/java-jackson-introduction/](http://www.studytrails.com/java/json/java-jackson-introduction/)
 
 ## 使用方式
  [http://www.studytrails.com/java/json/java-jackson-introduction/](http://www.studytrails.com/java/json/java-jackson-introduction/)
 **java创建json**
 
1. 从java对象创建json
2. 从jsonNode树创建json
3. 创建一个json流
 
 **解析JSON**
 1. Streaming - 用JsonParser去解析JSON stream。提供了JSON 的元素 作为符号(利用JsonGenerator 去生成有关String,Integer,boolean等类型的JSON)(`json streaming 流式计算,开销最低,读写最快`)
 2. Tree Traversing - 完整的JSON可以解析成JsonNode（`数模型Json文件在内存里以树形式表示  ObjectMapper构建JsonNode 节点树 最灵活`)
 3. Data Binding 绑定json到POJO对象，注解可以标记在POJO的属性和构造器上 (`数据绑定 JSON和POJO之间互相转化  使用ObjectMapper读写 `)
 
 **注解和序列化（Annotation and Serialization）**
 1. List Serialization - 当type的是被保护的类型时，序列化List是比较困难的
 2. Annotation and Dynamic beans - 序列化未知的JSON属性到JAVA MAP中去
 3. Annotatin Filter - 使用data binding后，本该绑定的POJO properties 可以无视或过滤掉绑定内容，这些过滤器可以以注解形式被使用在POJO属性上，或者使用自定义filter
 4. Mix-in-Annotation - 被用来做POJO属性到JSON属性的映射以及去标记使用了data binding的构造器constructor。然而当POJO来自第三方，且不能被标记。Mix-in 可以为第三方的类标记注解
 5. Polymorphic Behaviour - 多态行为，如果一个JSON是来源于一个抽象类的具体实例，jackjon提供方式去创建具体的的子类
 ## MAVEN依赖
 > 从JackSon 项目设计模块来看,他是模块化(插件化的)，核心的三个依赖（`core、databinding、annotation`）是必需导入的。其余模块根据自己项目的需求导入。所以推荐使用的思路是：`核心三模块`--->`了解jackson官方模块功能`--->`按需求导入其他模块`
 
**maven 配置**
**参考一:** 官网 2013更新（太老）
[Using Jackson2 with Maven](https://github.com/FasterXML/jackson-docs/wiki/Using-Jackson2-with-Maven)
**参考二:** 简书上某作者的使用（比较清晰）
[Jackson 快速入门](https://www.jianshu.com/p/f3fc3bf6dd2b)
\* 虽然是gradle配置，参考引入的包即可

**参考三:** 极简配置
可去MAVEN仓库查询最新版本
[maven repository](https://mvnrepository.com/search?q=jackson)
``` html
...
    <properties>
        <jackson-2-version>2.9.8</jackson-2-version>
    </properties>
<dependencies>
    <!--核心三个包-->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>${jackson-2-version}</version>
    </dependency>
    <!-- Just the annotations 注解 -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-annotations</artifactId>
        <version>${jackson-2-version}</version>
    </dependency>

    <!-- databinding; ObjectMapper, JsonNode and related classes are here 数据绑定 -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>${jackson-2-version}</version>
    </dependency>
</dependencies>
...
```

 ## DEMO
 [githup:https://github.com/EvilJoker/JavaDetail/tree/master/jackson/src](https://github.com/EvilJoker/JavaDetail/tree/master/jackson/src)
 以下Demo基于 `MAVEN参考三配置`
 
 **[java to json](http://www.studytrails.com/java/json/jackson-create-json/)** 
 > 三种方式
 > 1. ObjectMapper 类和JSON对应互相转换
 > 2. Tree Mode 直接构造一颗简单的树来生成JSON
 > 3. JSON Streaming
 
### generator
#####  `方式1` Java2Json_ObjectMapper.java
>jackson 可以让对象和JSON相互转化

内容：
`普通类型到JSON`:String
`普通数组到JSON`:String[]
`集合列表到JSON`:List
`集合MAP到JSON`:Map<String,String>
`类到JSON`: artist.java

核心类：ObjectMapper
##### `方式2` Java2Json_TreeModel.java
> 利用简单的树模型去生成json。这可能对当你不想为了生成JSON去专门编写一个类时有帮助。
> 生成一颗树你首先需要这么做：
> + 创建 `JsonNodeFactory` 类去生成节点 `nodes`
> + 根据 `JsonFactory`类和`输出方式` 来创建 `JsonGenerator`类(生成器)
> + 创建 `ObjectMapper` 类，然后使用`JsonGenerator`
和`root node`去生成JSON

#### `方式3` Java2Json_Streaming.java
> Jackson提供一个low-level的API去把JSON解析成Java。这个API为每个JSON对象提供token标记。例如JSON开始的`'{'`是`Parser(解析器)`标记的第一个对象。key-value键值对则是新的对象。client端的代码可以使用这些标记（tokens）去获得JSON的属性值或者生成一个Java对象。low_level的API功能十分强大但需要一些代码编写。大多数示例中，Jackson树和数据绑定（`tree and data-binding`）的大小应该被明确指出。这个code中提供了两种示例：解析和生成。

核心类
``` java
JsonFactory factory = new JsonFactory();
JsonGenerator generator = factory.createGenerator(new FileWriter(new File("albums.json"))) `绑定输出`
```

### prase
#####  `方式1` Java2Json_ObjectMapper.java
>Java开发者最长要处理的是`Java POJO`。难道你不想让处理过程像一个黑盒一样，JSON从一边进然后POJO从一边出。这就是Jackson的`data binding`做的事情。最好的解释是通过一个示例。我们用一个`music`类做示例。它会提供一个API去获得albums对应的最新的JSON。我们将能够般json串读到Albums中去。这个Albums对象包含了一个`Dataset`。
>如下介绍了 `java data binding 如何工作`
>1. 第一步是创建`JAVA类` - 拥有 JSON data数据。这个`Albums`包含了数组，字符串等数据
>2. 创建一个`com.fasterxml.jackson.databind.ObjectMapper class`。这个类把JSON映射到一个JAVA对象上去
>3. 我们将会使用`readValue`方法去读。我们从一个`URL`获取数据。除此之外还以从`file、inputstream,string,byteArray`
>4. `ObjectMapper`会缓存序列化与反序列化的对象，所以公用一个`ObjectMapper`将会十分有效
>5. 如果你有一个`inputStream` ，可以直接使用Jackson 而不必去包装成 `InputStreamReader`。
##### `方式2` Java2Json_TreeModel.java
> Jackson 提供了树节点类：`com.fasterxml.jackson.databind.JsonNode`。而ObjectMapper 提供了一个方法去把JSON转化成Java tree模型。这种方式和DOM NODE和DOM Trees的关系雷系。这个示例提供两种方式从一个JSON串去构建出一棵树：直接从node获取；用Path去获取
> 
> 生成一颗树你首先需要这么做：
> + 创建 `JsonNodeFactory` 类去生成节点 `nodes`
> + 根据 `JsonFactory`类和`输出方式` 来创建 `JsonGenerator`类(生成器)
> + 创建 `ObjectMapper` 类，然后使用`JsonGenerator`
和`root node`去生成JSON

#### `方式3` Json2Java_Streaming.java
> stream 的 prase类。这个示例演示了从url资源获取json文件流，并逐步解析

## 注解
>慎用注解，不易过复杂，过于复杂的注解使用会对代码的维护和阅读造成困扰。
>以下是其他blog总结的常用注解
参考使用 ：[https://www.jianshu.com/p/1cd7cc9e94d7](https://www.jianshu.com/p/1cd7cc9e94d7)

教程：[http://www.studytrails.com/java/json/java-jackson-data-binding-filters/](http://www.studytrails.com/java/json/java-jackson-data-binding-filters/)
  
