/**
 * Author:   sunqiyuan
 * Date:     2019-04-21 19:33
 * Description:
 * History:
 */
package Factory.NormalFactory;

public class CarFactory implements Factory {
    @Override
    public Product produce() {
        return new Car();
    }
}