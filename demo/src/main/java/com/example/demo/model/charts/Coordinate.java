package com.example.demo.model.charts;

import com.example.demo.model.field.Category;
import com.example.demo.model.field.Field;
import com.example.demo.model.json.Axis;
import com.example.demo.model.json.AxisLabel;

public class Coordinate extends Chart {

    private String x;
    private String y;

    @Override
    public Chart setStructure(String location, String name) {
        if (location.equals("x"))
            x = name;
        else if (location.equals("y"))
            y = name;
        return this;
    }

    @Override
    public void addField(Field field) {
        if (field.getType().equals(Category.class.getSimpleName())) {
            if (field.getName().equals(x)) {
                getBuilder().addXAxis(new Axis()
                        .setName(field.getTitle())
                        .setType(field.getType())
                        .setData(field.getData())
                );
            }
        }
    }

}
