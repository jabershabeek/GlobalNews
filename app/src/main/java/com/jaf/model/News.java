package com.jaf.model;

import com.orm.SugarRecord;

/**
 * Created by CIBIN on 20/8/2015.
 */
public class News extends SugarRecord<News> {
    private String item_id;

    public News(String item_id, String item_content, String item_title, String item_date, String item_url) {
        this.item_id = item_id;
        this.item_content = item_content;
        this.item_title = item_title;
        this.item_date = item_date;
        this.item_url = item_url;
    }

    public News() {
    }

    private String item_content;
    private String item_title;
    private String item_date;
    private String item_url;

    public String getItem_content() {
        return item_content;
    }

    public void setItem_content(String item_content) {
        this.item_content = item_content;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_date() {
        return item_date;
    }

    public void setItem_date(String item_date) {
        this.item_date = item_date;
    }

    public String getItem_url() {
        return item_url;
    }

    public void setItem_url(String item_url) {
        this.item_url = item_url;
    }


}
