package com.example.project;

public class ItemData {
    private String time= null;
    private String name = null;
    private String message = null;
    private String color = null;

    public ItemData(String time, String name, String message, String color) {
        this.time = time;
        this.name = name;
        this.message = message;
        this.color = color;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
