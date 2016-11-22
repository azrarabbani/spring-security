package hello.data.controlller;

/**
 * Created by arabbani on 11/19/16.
 */


import hello.data.holo.HoloDto;
import hello.data.holo.HoloScenario;
import hello.data.holo.HoloScenarioFactory;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * The AzraController class an interactive simulator generalized to handle a variety of simulation scenarios.
 */
@RestController
public class AzraController {

    // Logger
    private static final Logger log = LoggerFactory.getLogger(AzraController.class);

    private static final Map<String, Map<HttpMethod, HoloScenario>> urlToHolodeckScenarioMap = new HashMap<>();

    private HoloScenarioFactory holodeckScenarioFactory = new HoloScenarioFactory();



    private synchronized void setupScenario(HoloDto holodeckScenarioDto) {
        final String requestPathKey = holodeckScenarioDto.getHolodeckRequestPath();
        if (!urlToHolodeckScenarioMap.containsKey(requestPathKey)) {
            urlToHolodeckScenarioMap.put(requestPathKey
                    , new EnumMap<HttpMethod, HoloScenario>(HttpMethod.class));
        }
        final HoloScenario holodeckScenario =
                holodeckScenarioFactory.createHolodeckScenario(
                        holodeckScenarioDto.getHolodeckScenarioType()
                        , holodeckScenarioDto.getHolodeckScenarioConfiguration());

        final HttpMethod method = holodeckScenario.getMethodResponsePair().getHttpMethod();
        urlToHolodeckScenarioMap.get(holodeckScenarioDto.getHolodeckRequestPath()).put(method, holodeckScenario);
    }



    private synchronized HoloScenario retrieveScenario(
            final String scenarioUrlKey
            , final HttpMethod methodKey) {
        return urlToHolodeckScenarioMap
                .get(scenarioUrlKey)
                .get(methodKey);
    }



    /**
     * The postHolodeckScenario methods receives a new holodeck scenario and that is activated.
     *
     */
    @RequestMapping(value = "/v1/holodeck", method = RequestMethod.POST)
    public ResponseEntity<HoloDto> postHolodeckScenario(
            @RequestBody HoloDto holodeckScenarioDto) {

        log.info("POST holodeckScenarioDto: {}", holodeckScenarioDto);
        this.setupScenario(holodeckScenarioDto);
        log.info("Holodeck scenario written succesfully");

        return new ResponseEntity<HoloDto>(holodeckScenarioDto, HttpStatus.CREATED);
    }



    /**
     * The Holodeck simulation for POST.
     *
     */
    @RequestMapping(value = "/**", method = RequestMethod.POST)
    public ResponseEntity<Object> postHolodeckSimulation(
            HttpServletRequest httpServletRequest
            , @RequestBody Object requestBody
    ) {

        log.info("POST postHolodeckSimulation requestBody: {}", requestBody);
        log.info("postHolodeckSimulation httpServletRequestUri: {}", httpServletRequest.getRequestURI());

        final HoloScenario scenario = retrieveScenario(
                httpServletRequest.getRequestURI()
                , HttpMethod.POST);

        log.info("Holodeck Scenario {}", scenario);

        return
                new ResponseEntity<Object>(
                        scenario.getScenarioResponse(requestBody)
                        , scenario.getMethodResponsePair().getHttpStatus());
    }




    /**
     * The Holodeck simulation for PUT.
     *
     */
    @RequestMapping(value = "/**", method = RequestMethod.PUT)
    public ResponseEntity<Object> putHolodeckSimulation(
            HttpServletRequest httpServletRequest
            , @RequestBody Object requestBody) {

        log.info("PUT postHolodeckSimulation requestBody: {}", requestBody);
        log.info("putHolodeckSimulation httpServletRequestUri: {}", httpServletRequest.getRequestURI());

        final HoloScenario scenario = retrieveScenario(
                httpServletRequest.getRequestURI()
                , HttpMethod.PUT);

        log.info("Holodeck Scenario: {}", scenario);

        return
                new ResponseEntity<Object>(
                        scenario.getScenarioResponse(requestBody)
                        , scenario.getMethodResponsePair().getHttpStatus());
    }




    /**
     * The Holodeck simulation for GET.
     *
     * @return the ResponseEntity<Object>
     */
    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public ResponseEntity<Object> getHolodeckSimulation(
            HttpServletRequest httpServletRequest
            , @RequestBody(required = false) Object requestBody) {

        log.info("GET getHolodeckSimulation requestBody: {}", requestBody);
        log.info("getHolodeckSimulation httpServletRequestUri: {}", httpServletRequest.getRequestURI());

        final HoloScenario scenario = retrieveScenario(
                httpServletRequest.getRequestURI()
                , HttpMethod.GET);

        log.info("Holodeck Scenario: {}", scenario);

        return
                new ResponseEntity<Object>(
                        scenario.getScenarioResponse(requestBody)
                        , scenario.getMethodResponsePair().getHttpStatus());
    }


}