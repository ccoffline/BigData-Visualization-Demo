package com.example.demo.model.field;

import com.example.demo.util.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Field {

    private String name;

    public String getName() {
        return name;
    }

    public Field setName(String name) {
        this.name = name;
        return this;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public Field setTitle(String title) {
        this.title = title;
        return this;
    }

    public static Map<String, Class<? extends Field>> getFieldsMap() {
        return fieldsMap;
    }

    public static void setFieldsMap(Map<String, Class<? extends Field>> fieldsMap) {
        Field.fieldsMap = fieldsMap;
    }

    public abstract void setData(String[] data);

    public abstract void setData(byte[] data);

    public abstract Object[] getData();

    public abstract byte[] toBytes();

    private final String type = getClass().getSimpleName().toLowerCase();

    public String getType() {
        return type;
    }

    private static Map<String, Class<? extends Field>> fieldsMap = new HashMap<>();

    static {
        for (Class<? extends Field> Field : ClassUtil.getSubClasses(Field.class)) {
            fieldsMap.put(Field.getSimpleName().toLowerCase(), Field);
            System.out.println(Field.getSimpleName());
        }
    }

    public static Field generateField(String name, String type) {
        try {
            return fieldsMap.get(type).getConstructor().newInstance().setName(name);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }
}
