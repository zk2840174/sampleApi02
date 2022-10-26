package org.zerock.sampleapi02.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/api/ping")
public class PingController {

    @GetMapping("/test")
    public String[] ping(){
       log.info("ping..................");

       return new String[]{"A","B","C"};
    }
}
