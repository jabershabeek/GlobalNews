package com.jaf.news.apicall;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by CIBIN on 20/8/2015.
 */
public class NetworkCall {

    private static final String ACCESS_KEY="ACCESS_KEY";
    private static final String SERVER_GETNEWS_URL ="http://192.168.0.103/rssreader/getnews.php?access_key="+ACCESS_KEY;

    private static NetworkCall networkCall;

    private NetworkCall(){}
    public static NetworkCall getNetWorkCallInstance(){
        if(networkCall ==null)
            networkCall =new NetworkCall();
        return networkCall;
    }
    public StringBuilder makeServerCallForNewsUpdate()
    {
    StringBuilder content = new StringBuilder();
    try {
        URL twitter = new URL(SERVER_GETNEWS_URL);
        URLConnection tc = twitter.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                tc.getInputStream()));

        String line;
        while ((line = in.readLine()) != null) {
            content.append(line);
        }
    } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return content;

    }

}
