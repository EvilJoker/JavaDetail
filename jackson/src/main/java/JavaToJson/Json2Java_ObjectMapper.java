/**
 * Author:   sunqiyuan
 * Date:     2019-04-20 12:37
 * Description:
 * History:
 */
package JavaToJson;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json2Java_ObjectMapper {

    public static void main(String[] args)
            throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
        // 从本地资源获取
        Path path = Paths.get("albums.json");
        URI uri = path.toUri();

        ObjectMapper mapper = new ObjectMapper();
        //关闭未知属性报错
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        //JSON 到 Albums.class类
        Albums albums = mapper.readValue(uri.toURL(), Albums.class);
        Dataset[] datasets = albums.getDataset();
        for (Dataset dataset : datasets) {
            System.out.println(dataset.getAlbum_title());
        }
    }

    //数据类
    public class Albums {

        private String title;
        private Dataset[] dataset;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDataset(Dataset[] dataset) {
            this.dataset = dataset;
        }

        public String getTitle() {
            return title;
        }

        public Dataset[] getDataset() {
            return dataset;
        }
    }
    public class Dataset {
        private String album_id;
        private String album_title;
        private Map<String , Object> otherProperties = new HashMap<String , Object>();

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getAlbum_title() {
            return album_title;
        }

        public void setAlbum_title(String album_title) {
            this.album_title = album_title;
        }

        public Object get(String name) {
            return otherProperties.get(name);
        }

    }
}

