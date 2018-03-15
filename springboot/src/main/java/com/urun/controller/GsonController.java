package com.urun.controller;

import com.google.common.base.Strings;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by IntelliJ IDEA.
 *
 * @Author: DeanWang
 * Date: 2017/12/21
 * Time: 16:47
 * **************************
 */
@RestController()
@RequestMapping("/common")
public class GsonController {
    private static final Logger logger = LoggerFactory.getLogger(GsonController.class);
    private static final String SUCCESS = "success";


    @RequestMapping(value = "/checkJson")
    public String checkJson(@RequestParam("json") String jsonbody) {

        if (isGoodJson(jsonbody)) {
            return SUCCESS;
        } else {
            throw new JsonParseException("jsonString Format filed");
        }
    }

    public static boolean isGoodJson(String json) {
        if (Strings.isNullOrEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            logger.error("bad json: " + json);
            return false;
        }

    }

}


