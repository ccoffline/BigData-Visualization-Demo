package com.example.demo.controller;

import com.example.demo.service.ChartService;
import com.example.demo.service.LayoutService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/operator/chart")
public class ChartController {

//    private static final String CONNECTION_ERROR_MESSAGE = "数据库连不上，查个毛啊查";
//    private ChartDao chartDao;
//
//    public OperatorController(ChartDao chartDao) throws IOException {
//        this.chartDao = chartDao;
//    }
//
//    @RequestMapping("/search")
//    public String searchChartNames(@RequestParam String keys) {
//        StringBuilder builder = new StringBuilder();
//        try {
//            for (String name : chartDao.searchNamesByKeys(keys)) {
//                builder.append(",").append(name);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return CONNECTION_ERROR_MESSAGE;
//        }
//        return builder.length() > 0 ?
//                builder.substring(1) :
//                "";
//    }

    private Gson json = new Gson();
    private ChartService chartService;
    private LayoutService layoutService;

    public ChartController(ChartService chartService, LayoutService layoutService) {
        this.chartService = chartService;
        this.layoutService = layoutService;
    }

    @RequestMapping("/search")
    public String searchChartNames(@RequestParam String keys) {
        return chartService.searchNamesByKeys(keys);
    }

    @RequestMapping("/types")
    public String searchValidTypes(@RequestParam String name) {
        String[] results = new String[]{"line", "bar", "scatter"};
        return json.toJson(results);
    }

    @RequestMapping("/data")
    public String searchData(@RequestParam String name) {
        return chartService.getDataByName(name);
    }

    @RequestMapping("/submit")
    public void submitChart(@RequestBody String chart) throws IOException {
        layoutService.addChart(chart);
    }

    @RequestMapping("/")
    public String all() {
        return layoutService.getChartsInSelectedLayout();
    }

    @RequestMapping("/set")
    public String setPosition(@RequestParam Integer index, @RequestParam(required = false) Integer position) throws IOException {
        return layoutService.display(index, position);
    }
}