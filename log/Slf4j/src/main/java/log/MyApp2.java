/**
 * Author:   sunqiyuan
 * Date:     2019-04-30 22:54
 * Description:
 * History:
 */
package log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyApp2 {
    // Define a static logger variable so that it references the
    // OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE
    private static final Logger logger = LoggerFactory.getLogger(MyApp2.class);
    public static void printSys(){


        logger.trace("app-trace-info!");
        logger.debug("app-debug-info!");
        logger.info("info-info!");
        logger.warn("warn-info!");
        logger.error("error-info!");

    }

    public static void main(final String... args) {
        printSys();

        // Set up a simple configuration that logs on the console.






    }

}
