package com.mohammed.dailyviral;

public class Video {

    private String URL;
    private String Description;

    public Video() {

    }

    public Video(String URL, String Description) {
        this.URL= URL;
        this.Description = Description;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}