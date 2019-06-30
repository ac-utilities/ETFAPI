package api.greeting;

import java.util.concurrent.atomic.AtomicLong;
import data.RetrieveData;
import data.objects.ETFObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//http://localhost:8080/swagger-ui.html#/greeting-controller
//@RestController
//@RequestMapping("/api/v1")
//@Api(value = "Greeting Controller ")
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

//    @ApiOperation(value = "Hello world")
//    @ApiOperation(value = "View a list of available employees", response = ETFObject.class)
//    @GetMapping("/greeting")
    public ETFObject greeting (@RequestParam(value="name", defaultValue="World") String name) {
        RetrieveData retrieveData = new RetrieveData("AK-101_5.56x45_assault_rifle");
//        retrieveData.getData();
//        retrieveData = new RetrieveData("Izhmash_AK-74_dust_cover_(6P1_0-1)");
        return retrieveData.getData();
    }
}