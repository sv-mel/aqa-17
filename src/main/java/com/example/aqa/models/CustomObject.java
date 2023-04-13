package com.example.aqa.models;

public class CustomObject {
    public String query;

    public Integer page;

    public Integer size;

    public CustomObject(String query, Integer page, Integer size) {
        this.query = query;
        this.page = page;
        this.size = size;
    }
}
