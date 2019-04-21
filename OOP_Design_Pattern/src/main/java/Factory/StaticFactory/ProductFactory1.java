/**
 * Author:   sunqiyuan
 * Date:     2019-04-21 17:12
 * Description:
 * History:
 */
package Factory.StaticFactory;

import java.util.HashMap;
import java.util.Map;

//实现方式二：利用反射和配置文件，每次新增加直接修改配置文件即可
//可以利用Map预先加载配置文件来改进
public class ProductFactory1 {
    public static Product produce(String productName) throws Exception {

        //可改为从文件读取
        Map<String,String> classs = new HashMap<>();
        String pre = "com.sun.Factory.StaticFactory.";
        classs.put("car",pre + "Car");
        classs.put("YV",pre + "TV");


        try {
            Product product = (Product) Class.forName(classs.get(productName)).newInstance();
            return product;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new Exception("没有该产品");
    }

    public static void main(String[] args) throws Exception{
        ProductFactory.produce("tv");
    }
}
