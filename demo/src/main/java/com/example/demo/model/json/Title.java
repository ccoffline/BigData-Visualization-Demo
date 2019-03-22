package com.example.demo.model.json;

public class Title {

    private String text;
    private String subtext;

    public String getText() {
        return text;
    }

    public Title setText(String text) {
        this.text = text;
        return this;
    }

    public String getSubtext() {
        return subtext;
    }

    public Title setSubtext(String subtext) {
        this.subtext = subtext;
        return this;
    }
}
