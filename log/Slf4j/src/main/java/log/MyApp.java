package log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author:   sunqiyuan
 * Date:     2019-04-30 19:21
 * Description:
 * History:
 */

public class MyApp {

    // Define a static logger variable so that it references the
    // OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE

    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);
    public static void printSys(){

        logger.trace("trace-info!");
        logger.debug("debug-info!");
        logger.info("info-info!");
        logger.warn("warn-info!");
        logger.error("error-info!");

    }

    public static void main(final String... args) throws Exception{

        // Set up a simple configuration that logs on the console.

        while(true){
            MyApp.printSys();
            Thread.sleep(2);
            MyApp2.printSys();
        }





    }



}
