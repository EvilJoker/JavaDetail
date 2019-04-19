#jackson 教程 
官网介绍：https://github.com/FasterXML/jackson
json和xml解析工具

**作用**

java对象 ---- json

java对象 ---- xml

**对比**
jackson 比 JSON-lib 和Gson的解析速率要高

**方式**
三种方式处理JSON

1. 流式计算 开销最低    JsonParser读取数据 JsonGenerator 写入数据
2. 数模型  Json文件在内存里以树形式表示  ObjectMapper构建JsonNode 节点树 最灵活
3. 数据绑定 JSON和POJO之间互相转化  使用ObjectMapper读写 
    + 简单的数据绑定 - 转换JSON和Java Maps, Lists, Strings, Numbers, Booleans 和null 对象
    + 全部数据绑定 - 转换为JSON从任何JAVA类型
    
**类库**
Jackson是模块化的需要导入多个类库

jackson-core

jackson-databind

jackson-annotations
    // 引入XML功能
jackson-dataformat-xml
    // 比JDK自带XML实现更高效的类库
woodstox-core
    // Java 8 新功能
jackson-datatype-jsr310
jackson-module-parameter-names
jackson-datatype-jdk8

    compileOnly group: 'org.projectlombok', name: 'lombok', version: '1.16.22'
}





