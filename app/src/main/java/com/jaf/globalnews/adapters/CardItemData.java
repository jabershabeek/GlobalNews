package com.jaf.globalnews.adapters;

/**
 * Created by Justin on 2/2/14.
 */
public class CardItemData {


	private final String news_id;
	private final String news_date;
	private final String news_url;
	private String newsHeader;

	private String newsDescription;

	public CardItemData(String item_id, String item_content, String item_title, String item_date, String item_url) {
		this.news_id = item_id;
		this.newsDescription = item_content;
		this.newsHeader = item_title;
		this.news_date = item_date;
		this.news_url = item_url;
	}
	public String getNews_id() {
		return news_id;
	}

	public String getNews_date() {
		return news_date;
	}

	public String getNews_url() {
		return news_url;
	}

	public void setNewsHeader(String newsHeader) {
		this.newsHeader = newsHeader;
	}

	public void setNewsDescription(String newsDescription) {
		this.newsDescription = newsDescription;
	}
	public String getNewsDescription() {
		return newsDescription;
	}

	public String getNewsHeader() {
		return newsHeader;
	}
}