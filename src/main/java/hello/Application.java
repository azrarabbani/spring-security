package hello;

import hello.data.config.ApplicationConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@ImportResource("classpath:spring.xml")
@EnableConfigurationProperties(ApplicationConfigurationProperties.class)
@EnableMBeanExport
//@PropertySource("classpath:application.properties")
public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) throws Throwable {
        log.info("Application name is *************************** ");
        SpringApplication.run(Application.class, args);
    }

//    public static void main(String[] args) {
//        System.out.print(256 >> 4);
//        int[] startArray = {11, 21, 31, 41, 51};
//        int[] finishArray = {61, 71, 81, 91, 101, 111, 121, 131};
//        System.arraycopy(startArray, 0, finishArray, 0, startArray.length);
//    }
}