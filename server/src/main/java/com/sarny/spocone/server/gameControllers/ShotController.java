package com.sarny.spocone.server.gameControllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wojciech Makiela
 */
@RestController
class ShotController {

    private final Gson gson;

    @Autowired
    public ShotController() {
        gson = new Gson();
    }

    @GetMapping(path = "/test")
    public String getTest() {
        return gson.toJson(new Test(1, "value"));
    }

    private class Test {
        int a;
        String b;

        Test(int a, String b) {
            this.a = a;
            this.b = b;
        }
    }
}
