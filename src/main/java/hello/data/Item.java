package hello.data;

import hello.data.config.ApplicationConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arabbani on 11/16/16.
 */
@ManagedResource(objectName="jmxbean:name=ItemAzra",
        description="My Managed Bean",
        log=true,
        logFile="jmx.log",
        currencyTimeLimit=15,
        persistPolicy="OnUpdate",
        persistPeriod=200,
        persistLocation="foo",
        persistName="bar")
@Component
public class Item {
    private Logger log = LoggerFactory.getLogger(Item.class);

    private Map<String, List<String>> mapOfSubItems;

//    @Autowired
//    ApplicationConfigurationProperties applicationConfigurationProperties;

    @Autowired
    public Item(ApplicationConfigurationProperties properties){
        log.info("Application ^^^^^^^^^^^^^^^^^" + properties.getAppName());
    }


    @PostConstruct
    public void init() {
        log.info("Items Initilaized");
        mapOfSubItems = new HashMap<>();
        List<String> subItems = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            subItems = new ArrayList<>();
            mapOfSubItems.put("Item"+j, subItems);
            for (int i = 0; i < 10; i++) {
                subItems.add("Item"+j+i);
            }
        }

    }


    @ManagedOperation
    @ManagedOperationParameters({@ManagedOperationParameter(name = "itemId", description = "The status may be waiting, allowed, rejected or cancelled.")
            })
    public List<String> getListOfAllSubItems(String itemId) {
      return mapOfSubItems.get(itemId);
    }

}
