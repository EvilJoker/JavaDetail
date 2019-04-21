/**
 * Author:   sunqiyuan
 * Date:     2019-04-21 20:04
 * Description:
 * History:
 */
package Factory.AbstractFactory;

public class MainTest {
    public static void main(String[] args) {

            FactoryA factoryA = new FactoryA();
            factoryA.produceCar();

            FactoryB factoryB = new FactoryB();
            factoryB.produceTv();
    }
}


