package com.example.gameNews;

public class RecyclerViewModel {
    String image;
    String title;
    String news;

    public RecyclerViewModel(){}

    public String getNews() { return news; }

    public void setNews(String news) { this.news = news; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}
