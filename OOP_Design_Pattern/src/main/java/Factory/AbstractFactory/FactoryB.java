/**
 * Author:   sunqiyuan
 * Date:     2019-04-21 20:03
 * Description:
 * History:
 */
package Factory.AbstractFactory;

public class FactoryB implements Factory {

    @Override
    public TV produceTv() {
        return new Sony();
    }

    @Override
    public Car produceCar() {
        return new Audi();
    }
}