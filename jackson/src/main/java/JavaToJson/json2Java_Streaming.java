package JavaToJson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class json2Java_Streaming {
    public static void main(String[] args) throws MalformedURLException, IOException {
        // 从网络资源获取
        //String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=5";
        // 从本地资源获取
        Path path = Paths.get("albums.json");
        URI uri = path.toUri();

        // JSON解析器
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(uri.toURL());

        // 持续解析直到 输出结束
        while (!parser.isClosed()) {
            //token 标记值得属性如Sring,array等 ，获取当前token对应的值：parser.getCurrentName()
            // token 迭代
            JsonToken token = parser.nextToken();
            // token 结束判断 if its the last token then we are done
            if (token == null) {
                break;
            }
            // 寻找某个域 we want to look for a field that says dataset
            String tokenValue = parser.getCurrentName();
            if (JsonToken.FIELD_NAME.equals(token) && "dataset".equals(parser.getCurrentName())) {
                // we are entering the datasets now. The first token should be
                // start of array [
                token = parser.nextToken();
                if (!JsonToken.START_ARRAY.equals(token)) {
                    // bail out
                    break;
                }
                // each element of the array is an album so the next token
                // should be {
                token = parser.nextToken();
                if (!JsonToken.START_OBJECT.equals(token)) {
                    break;
                }
                // we are now looking for a field that says "album_title". We
                // continue looking till we find all such fields. This is
                // probably not a best way to parse this json, but this will
                // suffice for this example.
                while (true) {
                    token = parser.nextToken();
                    if (token == null) {
                        break;
                    }
                    if (JsonToken.FIELD_NAME.equals(token) && "album_title".equals(parser.getCurrentName())) {
                        token = parser.nextToken();
                        System.out.println(parser.getText());
                    }

                }

            }

        }

    }
}

          
