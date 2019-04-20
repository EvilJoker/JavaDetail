/**
 * Author:   sunqiyuan
 * Date:     2019-04-19 19:44
 * Description:
 * History:
 */
package JavaToJson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

/**
  *
  * In This example we look at generating a json using the jsongenerator. we will
  * be creating a json similar to
  * http://freemusicarchive.org/api/get/albums.json 
  * api_key=60BLHNQCAOUFPIBZ&limit=1, but use only a couple of fields
  *
  */
public class Java2Json_Streaming {

    public static void main(String[] args) throws IOException {
        // Json的生成器
        JsonFactory factory = new JsonFactory();
        JsonGenerator generator;
        //定义输出流 输出到文件
        //generator = factory.createGenerator(new FileWriter(new File("albums.json")));
        //输出到console 控制台
        generator = factory.createGenerator(System.out);

        // 开始符号 StartObject = {
        generator.writeStartObject();
        // 一个String值 "title":"Free Music Archive - Albums"
        generator.writeFieldName("title");
        generator.writeString("Free Music Archive - Albums");
        // 一个String值 与上等价
        generator.writeStringField("body","body rich");

        //数组写法:Map和List
        generator.writeFieldName("dataset");
        // start an array
        generator.writeStartArray(); // 等价于 [
        //key-value
        generator.writeStartObject(); // 等价于 {
        generator.writeStringField("album_title", "A.B.A.Y.A.M");
        generator.writeEndObject(); // 等价于 }
        //single_value
        generator.writeString("String");

        generator.writeEndArray(); // 等价于 ]

        generator.close();
    }

}
