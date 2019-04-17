import org.codehaus.jackson.JsonFactory;

/**
 * Author:   sunqiyuan
 * Date:     2019-04-17 10:31
 * Description:
 * History:
 */

public class module0Hello {
    public void hello(){
        System.out.println("hello,module0");
        // 使用parent0里面的内容
        new parent0Hello();
    }
}
