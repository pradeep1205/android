package com.example.pradeepkumar.adpost;

/**
 * Created by pradeep kumar on 1/21/2017.
 */
public class Blog {
    private String title,Desc,Image;

    public Blog(){

    }

    public Blog(String title, String desc, String image) {
        this.title = title;
        Desc = desc;
        Image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
