package hello.data.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by arabbani on 11/16/16.
 */


@ConfigurationProperties(prefix = "appConfig", ignoreUnknownFields = false)
public class ApplicationConfigurationProperties {

    private String appName;

    public String getAppName(){
        return appName;
    }



    public void setAppName(String appName){
        this.appName=appName;
    }
}
