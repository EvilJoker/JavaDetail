/**
 * Author:   sunqiyuan
 * Date:     2019-04-21 17:11
 * Description:
 * History:
 */
package Factory.StaticFactory;
// 实现方式一：
//这样的实现有个问题，如果我们新增产品类的话，需要不断的在工厂类中新增case，这样需要修改的地方比较多，所以不建议使用这样的方法来实现工厂类。
public class ProductFactory {
    public static Product produce(String productName) throws Exception{
        switch (productName){
            case "tv":
                return new TV();
            case "car":
                return new Car();
                default:
                    System.out.println("wrong");
                    return null;
        }
    }

    public static void main(String[] args) throws Exception{
        ProductFactory.produce("car");
    }

}
