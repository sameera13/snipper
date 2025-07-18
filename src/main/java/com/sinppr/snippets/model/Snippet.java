package com.sinppr.snippets.model;

public class Snippet {
    private int id;
    private String language;
    private String code;

    public Snippet() {}

    
    public Snippet(int id, String language, String code) {
        this.id = id;
        this.language = language;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
