package com.example.samhi.firebasedemo;

public class FilterModel  {

    private int imageView;
    private String textView;
    private String opening;
    private String closing;
    private String cuisine;

    public FilterModel(int imageView, String textView, String opening, String closing,String cuisine) {
        this.imageView = imageView;
        this.textView = textView;
        this.opening=opening;
        this.closing=closing;
        this.cuisine=cuisine;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }
    public void setOpening(String opening) {
        this.opening = opening;
    }
    public String getOpening() {
        return opening;
    }
    public void setClosing(String closing) {
        this.closing = closing;
    }
    public String getClosing() {
        return closing;
    }
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
    public String getCuisine() {
        return cuisine;
    }
}
