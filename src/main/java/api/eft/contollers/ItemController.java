package api.eft.contollers;

import data.RetrieveData;
import data.objects.ETFObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("etf")
@Api(value = "ETF Item")
public class ItemController {

//    http://localhost:8080/swagger-ui.html#/greeting-controller

    @GetMapping("/item")
    @ApiOperation(value = "getItem", response = ETFObject.class)
    public ETFObject item(@RequestParam(value="name") String id ){
        RetrieveData retrieveData = new RetrieveData(id);
        return retrieveData.getData();
    }
}
