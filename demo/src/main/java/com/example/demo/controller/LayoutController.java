package com.example.demo.controller;

import com.example.demo.service.LayoutService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/operator/layout")
public class LayoutController {

    private LayoutService layoutService;

    public LayoutController(LayoutService layoutService) {
        this.layoutService = layoutService;
    }

    @RequestMapping("/")
    public String all() {
        return layoutService.getLayouts();
    }

    @RequestMapping("/submit")
    public void submitLayout(@RequestParam String name, @RequestParam String title, @RequestParam String theme) throws IOException {
        layoutService.addLayout(name, title, theme);
    }

    @RequestMapping("/select")
    public void selectLayout(@RequestParam Integer index) throws IOException {
        layoutService.selectLayout(index);
    }

    @RequestMapping("/clear")
    public void clearLayout(@RequestParam Integer index) throws IOException {
        layoutService.clear(index);
    }
}
