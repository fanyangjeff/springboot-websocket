package yf.websokcet.controller;

/*
 * @author Jeff
 * @date 7/21/21 11:01 PM
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class InitController {

    @RequestMapping("/websocket")
    public String init() {
        return "websocket.html";
    }

}