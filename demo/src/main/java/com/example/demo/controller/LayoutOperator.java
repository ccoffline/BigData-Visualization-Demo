package com.example.demo.controller;

import com.example.demo.websocket.ChartController;
import com.example.demo.websocket.LayoutController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/operator/layout")
public class LayoutOperator {

    private static String title;

    public static void reset() throws IOException {
        ChartController.setTitle(title);
    }

    @RequestMapping("/submit")
    public void submitLayout(@RequestParam String name, @RequestParam String theme, @RequestParam String title) throws IOException {
        System.out.println(name);
        LayoutController.setLayout(name);
        LayoutOperator.title = title;
    }

    @RequestMapping("/")
    public void all() throws IOException {
        System.out.println("get all layouts");
    }
}
