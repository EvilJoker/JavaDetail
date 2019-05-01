package log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Author:   sunqiyuan
 * Date:     2019-04-30 19:21
 * Description:
 * History:
 */

public class MyApp {

    // Define a static logger variable so that it references the
    // OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE
    private static final Logger logger = LogManager.getLogger(MyApp.class);
    public static void printSys(){

        //getenv 和 getproperties不是一回事
        //System.out.println(System.getenv());
        //System.out.println(System.getProperties());
        System.out.println("");

    }

    public static void main(final String... args) {
        printSys();

        // Set up a simple configuration that logs on the console.


        logger.trace("trace-info!");
        logger.debug("debug-info!");
        logger.info("info-info!");
        logger.warn("warn-info!");
        logger.error("error-info!");
        logger.fatal("fatal-info!");


    }



}
