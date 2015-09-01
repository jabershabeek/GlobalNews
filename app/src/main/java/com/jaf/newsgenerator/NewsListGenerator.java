package com.jaf.newsgenerator;

import com.jaf.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CIBIN on 20/8/2015.
 */
public class NewsListGenerator {
    private List<News> newsList=new ArrayList<News>();
    private News news;
    private static NewsListGenerator newsListGeneratorObj;

    private NewsListGenerator(){}
    public static NewsListGenerator getNewsListInstance(){
        if(newsListGeneratorObj ==null)
            newsListGeneratorObj =new NewsListGenerator();
        return newsListGeneratorObj;
    }
    public List<News> generateNewsListFromJSONString(StringBuilder jsonData) {
        if(jsonData!=null)
            try {
                //JSONObject jSonObject=new JSONObject(jsonData);
                JSONArray jsonNewsArray = new JSONArray(jsonData.toString());
                JSONObject newsObject;
                for (int idx=0;idx<jsonNewsArray.length();idx++){
                    news=new News();
                    newsObject= (JSONObject) jsonNewsArray.get(idx);
                    news.setItem_title(newsObject.getString("item_id"));
                    news.setItem_title(newsObject.getString("item_title"));
                    news.setItem_content(newsObject.getString("item_content"));
                    news.setItem_date(newsObject.getString("item_date"));
                    news.setItem_url(newsObject.getString("item_url"));
                    news.save();
                    newsList.add(news);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                newsList=null;
            }
        return newsList;

    }

    public List<News> generateNewsListFromSQLLiteDB(){
        try {
            newsList = News.listAll(News.class);
            return newsList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteAllNews()
    {
        try {
            News.deleteAll(News.class);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
