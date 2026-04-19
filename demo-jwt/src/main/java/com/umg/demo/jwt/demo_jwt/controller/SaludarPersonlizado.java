package com.umg.demo.jwt.demo_jwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludarPersonlizado {

    @RequestMapping("hola")
    public String saludar(@RequestParam(value="nombre", defaultValue="que tal estimados") String nombre ) {

        return "hola " + nombre + "!!";
    }

}
