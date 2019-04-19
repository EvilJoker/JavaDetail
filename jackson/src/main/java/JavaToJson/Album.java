/**
 * Author:   sunqiyuan
 * Date:     2019-04-19 11:14
 * Description:
 * History:
 */
package JavaToJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Album {
    // 常规属性
    private String title;
    // 数组属性
    private String[] links;
    // 集合列表
    private List<String> songs;
    // 类 class
    private Artist artist;
    // Map
    private Map<String, String> musicians = new HashMap<String, String>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getLinks() {
        return links;
    }

    public void setLinks(String[] links) {
        this.links = links;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Map<String, String> getMusicians() {
        return Collections.unmodifiableMap(musicians);
    }

    public void addMusicians(String key, String value) {
        musicians.put(key, value);
    }

    public void setMusicians(Map<String, String> musicians) {
        this.musicians = musicians;
    }

    public Artist ArtistBuild(String name, String birthDate){

        //Artist a =new Artist(name,birthDate);
        //System.out.println(a.getClass());
        return new Artist(name,birthDate);

    }

    class Artist {
        public String name;
        public Date birthDate;

        // 归一化构造函数
        public void init(String name, Date birthDate) {
            this.name = name;
            this.birthDate = birthDate;
        }

        public Artist(String name, Date birthDate) {

            init(name, birthDate);

        }

        public Artist(String name, String birthDate) {

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try {
                init(name, format.parse(birthDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }
}
