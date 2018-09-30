package com.example.hp.quesec.Beans;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

public class QuestionBean
{
    private String queId,userId,title,description,username,categoryId,categoryname;
    private Date postdate;
    private int flag;
    private int anscount;
    private ArrayList<String> uri;
    private ArrayList<String> bmk;

    public int getAnscount() {
        return anscount;
    }

    public void setAnscount(int anscount) {
        this.anscount = anscount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostdate() {
        return postdate;
    }

    public void setPostdate(Date postdate) {
        this.postdate = postdate;
    }

    public ArrayList<String> getUri() {
        return uri;
    }

    public void setUri(ArrayList<String> uri) {
        this.uri = uri;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public ArrayList<String> getBmk() {
        return bmk;
    }

    public void setBmk(ArrayList<String> bmk) {
        this.bmk = bmk;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
