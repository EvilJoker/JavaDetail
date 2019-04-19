/**
 * Author:   sunqiyuan
 * Date:     2019-04-19 18:12
 * Description:
 * History:
 */
package JavaToJson;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Java2Json_TreeModel {


    //文档 http://www.studytrails.com/java/json/jackson-create-json/
    public static void main(String[] args) throws Exception{
        // 创建node factory 生成node
        JsonNodeFactory factory = new JsonNodeFactory(false);

        // 创建 JsonFactory 去把 Treenode 转化为Json
        JsonFactory jsonFactory = new JsonFactory();
        // 生成器设定输出模式
        JsonGenerator generator = jsonFactory.createGenerator(System.out);
        ObjectMapper mapper = new ObjectMapper();

        //根节点 rootNode - album
        ObjectNode album = factory.objectNode();

        /**数据*/
        // 添加一般属性
        album.put("Album-Title","Kind Of Blue");
        // 添加数组节点
        ArrayNode links = factory.arrayNode();
        links.add("link1").add("link2");
        album.set("links", links);
        // 添加类
        ObjectNode artist = factory.objectNode();
        artist.put("Artist-Name", "Miles Davis");
        artist.put("birthDate", "26 May 1926");
        album.set("artist", artist);
        // 添加Map map和类格式很相似
        ObjectNode musicians = factory.objectNode();
        musicians.put("Julian Adderley", "Alto Saxophone");
        musicians.put("Miles Davis", "Trumpet, Band leader");
        album.set("musicians", musicians);



        //输出JSON 注意TreeMode模式下，Java2Json_ObjectMapper中类似的config设置将不起作用
        mapper.writeTree(generator, album);

    }
}
