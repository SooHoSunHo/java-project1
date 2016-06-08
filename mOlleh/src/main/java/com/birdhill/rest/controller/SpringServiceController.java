package com.birdhill.rest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lg on 2016-05-26.
 */
@RestController
@RequestMapping("/service/greeting")
public class SpringServiceController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String getGreeting(@PathVariable String name) {

        String result = "Hello"+name;
        return result;
    }
}
