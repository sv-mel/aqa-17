package com.example.aqa.endpoints;


import com.example.aqa.models.CustomObject;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleEndpoint {

    static String lastStr;

    @GetMapping(path = "/getString")
    public String getStringWithoutSpaces(@RequestParam(defaultValue = "empty string") String str) {
        lastStr = str;
        return str.trim();
    }

    @GetMapping(path = "/getInteger")
    public String getInteger(@RequestParam(defaultValue = "1") Integer str) {
        return str.toString();
    }

    @GetMapping(path = "/getLastStr")
    public String getLastStr() {
        if (lastStr != null) {
            return lastStr;
        } else {
            return "String is null";
        }
    }

    @DeleteMapping(path = "/deleteLastStr")
    public void deleteLastStr() {
        lastStr = null;
    }

    @GetMapping(path = "/getJson")
    public String getJson() {
        JSONObject newJsonObject = new JSONObject();
        newJsonObject.put("query", "coat");
        newJsonObject.put("page", "0");
        newJsonObject.put("size", "10");
        return newJsonObject.toString();
    }

    @GetMapping(path = "/getObject")
    public CustomObject getObject() {
        return new CustomObject("jacket", 3, 14);
    }

}