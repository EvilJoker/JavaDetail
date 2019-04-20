/**
 * Author:   sunqiyuan
 * Date:     2019-04-20 12:38
 * Description:
 * History:
 */
package JavaToJson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json2Java_TreeModel {

    public static void main(String[] args) throws MalformedURLException, IOException {

        // 从本地资源获取
        Path path = Paths.get("albums.json");
        URI uri = path.toUri();
        // 获取字符串
        String genreJson = IOUtils.toString(uri.toURL(), "UTF-8");
        /**方式一:直接从Node 获取**/
        //生成树
        // create an ObjectMapper instance.
        ObjectMapper mapper = new ObjectMapper();
        // use the ObjectMapper to read the json string and create a tree
        JsonNode node = mapper.readTree(genreJson);

        // 打印节点类型

        System.out.println("nodeType:" + node.getNodeType()); // prints OBJECT
        // is it a container
        System.out.print("isContainNode:" + node.isContainerNode()); // prints true
        // 打印 field
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            System.out.println(fieldName);// prints title, message, errors,
        }

        // we now know what elemets the container has. lets get the value for
        // one of them
        System.out.println("value: " + node.get("title").asText()); // prints
        // "Free Music Archive".

        //打印arraryNode
        // Lets look at the dataset now.
        JsonNode dataset = node.get("dataset");
        // what is its type
        System.out.println(dataset.getNodeType()); // Prints ARRAY

        /**打印集合中数组元素*/
        // so the dataset is an array. Lets iterate through the array and see
        // what each of the elements are
        Iterator<JsonNode> datasetElements = dataset.iterator();
        while (datasetElements.hasNext()) {
            JsonNode datasetElement = datasetElements.next();
            // what is its type
            System.out.println(datasetElement.getNodeType());// Prints Object
            // it is again a container . what are the elements
            Iterator<String> datasetElementFields = datasetElement.fieldNames();
            while (datasetElementFields.hasNext()) {
                String datasetElementField = datasetElementFields.next();
                System.out.println(datasetElementField); // prints album_id,
                // album_title,
                // album_handle,
                // album_url,
                // album_type,
                // artist_name,
                // artist_url,
                // album_producer,
                // album_engineer,
                // album_information,
                // album_date_released,
                // album_comments,
                // album_favorites,
                // album_tracks,
                // album_listens,
                // album_date_created,
                // album_image_file,
                // album_images

            }
            break;
        }
        /**方式二:Path方式 获取，更方便**/


        // not the use of path. this does not cause the code to break if the
        // code does not exist
        Iterator<JsonNode> albums = node.path("dataset").iterator();
        while (albums.hasNext()) {
            System.out.println(albums.next().path("album_title"));

        }

    }
}


