/**
 * Author:   sunqiyuan
 * Date:     2019-04-21 20:02
 * Description:
 * History:
 */
package Factory.AbstractFactory;

public class FactoryA implements Factory {
    @Override
    public TV produceTv() {
        return new LeTV();
    }

    @Override
    public Car produceCar() {
        return new BMW();
    }
}
