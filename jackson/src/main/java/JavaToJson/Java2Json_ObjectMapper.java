/**
 * Author:   sunqiyuan
 * Date:     2019-04-19 16:33
 * Description:
 * History:
 */
package JavaToJson;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class Java2Json_ObjectMapper {
    //文档 http://www.studytrails.com/java/json/jackson-create-json/
    //这种模式使得一个类和JSON绑定，一个类对应一个JSON
    public static void main(String[] args) throws Exception{

        // 转换类
        ObjectMapper mapper = new ObjectMapper();

        //数据
        Album album = new Album();
        //普通String类
        album.setTitle("kind of blue");
        //普通数组
        album.setLinks(new String[] {"link1", "link2"});
        //List
        album.setSongs(Arrays.asList("So What", "Flamenco Sketches", "Freddie Freeloader"));
        //map
        album.addMusicians("Miles Davis", "Trumpet, Band leader");
        album.addMusicians("Julian Adderley", "Alto Saxophone");
        album.addMusicians("Paul Chambers", "double bass");
        //类
        album.setArtist(album.ArtistBuild("Miles Davis", "26-05-1926"));


        //设置
        //使输出的json格式便于阅读，即加了一些空格和换行
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        //使json key值的排列order 按照类定义的那样
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        //设置JSON中统一的日期格式
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        mapper.setDateFormat(outputFormat);
        //设置命名格式，JACKSON中json的命名默认是变量名，但是可以对默认的名称进行修改，通过覆盖函数
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategy(){
            // 对fild 的变量名称 的改变:比如这里改了 artist对象的属性名称
            @Override
            public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
                // package包名+类名+属性
                if (field.getFullName().equals("JavaToJson_ObjectMapper.Album$Artist#name"))
                    return "Artist-Name";
                return super.nameForField(config, field, defaultName);
            }
            //对方法级别的模式修改
            @Override
            public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
                if (method.getAnnotated().getDeclaringClass().equals(Album.class) && defaultName.equals("title"))
                    return "Album-Title";
                return super.nameForGetterMethod(config, method, defaultName);
            }
        });
        //对NULL与空值的处理 默认数字会输出0，String 输出null ,数组[]等，可以选择忽略为null的值
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);


        // 输出  控制台输出 ，album
        mapper.writeValue(System.out, album);

    }
}
