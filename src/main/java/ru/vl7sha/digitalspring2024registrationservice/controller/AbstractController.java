package ru.vl7sha.digitalspring2024registrationservice.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractController {
    protected void logRequest(Object object){
        logRequest(object, null);
    }

    protected void logRequest(Object obj, String requestMessage) {
        try {
            logRequest((requestMessage != null ? requestMessage + ", " : "") + (obj == null ? null : objToJSON(obj)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    protected void logRequest(String message){
        log.info(message);
    }

    private String objToJSON(Object object){
        return new Gson().toJson(object);
    }
}
