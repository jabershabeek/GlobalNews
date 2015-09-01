package globalnews.jaf.com.globalnews;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jaf.globalnews.adapters.BaseInflaterAdapter;
import com.jaf.globalnews.adapters.CardItemData;
import com.jaf.globalnews.adapters.inflaters.CardInflater;
import com.jaf.model.News;
import com.jaf.news.apicall.NetworkCall;
import com.jaf.newsgenerator.NewsListGenerator;

import java.util.ArrayList;
import java.util.Vector;

public class GlobalNewsListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
	/**
	 * Called when the activity is first created.
	 */
    private SwipeRefreshLayout swipeRefreshLayout;
    private BaseInflaterAdapter<CardItemData> adapter;
    private ListView list;
    private ArrayList<News> listData;
	//private String JSON_DATA= "{'newslist':[{'header':'News Header','content':'News Content'},{'header':'News Header','content':'News Content'},{'header':'News Header','content':'News Content'},{'header':'News Header','content':'News Content'},{'header':'News Header','content':'News Content'},{'header':'News Header','content':'News Content'},{'header':'News Header','content':'News Content'},{'header':'News Header','content':'News Content'},{'header':'News Header','content':'News Content'},{'header':'News Header','content':'News Content'},{'header':'News Header','content':'News Content'},{'header':'News Header','content':'News Content'}]}";
	private NewsListGenerator newsListGenerator;
    private NetworkCall networkCall;

    private final static String NO_INTERNET_TITLE="No Internet Access";
    private final static String NO_INTERNET_MESSAGE="Check Your Internet Connect and Try Again..!";

    private final static String NETWORK_ERROR_TITLE="Network Error";
    private final static String NETWORK_ERROR_MESSAGE="Unable to Receive Server Response,Please Try Again Later..!";

    private final static String JSON_ERROR_TITLE="Data Not Supported";
    private final static String JSON_ERROR_MESSAGE="Unable to process the server response,Please Try Again Later..!";

    private RunNewsReaderTask newsReaderTask;
    private RunNewsReaderRefreshTask newsReaderRefreshTask;
    private Menu optionsMenu;

    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		newsListGenerator=NewsListGenerator.getNewsListInstance();
        networkCall=NetworkCall.getNetWorkCallInstance();
		list = (ListView)findViewById(R.id.list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle((Html.fromHtml("<font color=\"#2196F3\">" + "CaNEWS" + "</font>")));
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#E6EE9C"));
        actionBar.setBackgroundDrawable(colorDrawable);
        // list.addHeaderView(new View(this));
		//list.addFooterView(new View(this));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = listData.get(position);
                ContentActivity.headerText=news.getItem_title();
                ContentActivity.contentText =news.getItem_content();
                Intent myIntent = new Intent(GlobalNewsListActivity.this, ContentActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
               // Toast.makeText(GlobalNewsListActivity.this, "Heading :" + news.getItem_url(), Toast.LENGTH_SHORT).show();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        ArrayList<News> newsList=getNewsFromDb();
                                        if(newsList==null){
                                            if(isOnline()) {
                                                newsReaderTask=new RunNewsReaderTask();
                                                newsReaderTask.execute(new Boolean[]{true});
                                            }
                                            else
                                            {
                                                showNetworkError(NO_INTERNET_TITLE,NO_INTERNET_MESSAGE,false);
                                                swipeRefreshLayout.setRefreshing(false);
                                            }
                                        } else {
                                            loadNews(newsList);
                                            listData=newsList;
                                            notifyAdaptorChange();
                                            swipeRefreshLayout.setRefreshing(false);
                                        }


                                    }
                                }
        );
	}

    private ArrayList<News> getNewsFromDb(){
        try {
            ArrayList<News> newslist = (ArrayList<News>) newsListGenerator.generateNewsListFromSQLLiteDB();
            if (newslist == null || newslist.size() <= 0) {
                return null;
            } else {
                return newslist;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
	private void loadNews(ArrayList<News> newsList)
	{
		adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater());
		//ArrayList<News> newsList= (ArrayList<News>) NewsListGenerator.getNewsListInstance().generateNewsListFromJSONString(JSON_DATA);
		for (int idx = 0; idx < newsList.size(); idx++)
		{
			News news=newsList.get(idx);
			CardItemData data = new CardItemData(news.getItem_id(), news.getItem_content(),news.getItem_title(),news.getItem_date(),news.getItem_url());
			adapter.addItem(data, false);
		}
	}

    @Override
    public void onRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                if (isOnline()) {
                    newsReaderRefreshTask=new RunNewsReaderRefreshTask();
                    newsReaderRefreshTask.execute(new Boolean[]{true});
                } else {
                    showNetworkError(NO_INTERNET_TITLE, NO_INTERNET_MESSAGE, true);
                    swipeRefreshLayout.setRefreshing(false);
                }


                //swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void notifyAdaptorChange(){
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
	public void showNetworkError(String title,String content,boolean isPositiveButton)
	{
		AlertDialog.Builder networkErrorBuilder = new AlertDialog.Builder(this);
		networkErrorBuilder.setMessage(content);
        networkErrorBuilder.setTitle(title);
		networkErrorBuilder.setCancelable(false);
        if(isPositiveButton)
		networkErrorBuilder.setPositiveButton("Load From Cache",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		networkErrorBuilder.setNegativeButton("Exit",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						finish();
					}
				});

		AlertDialog networkErrorAlert = networkErrorBuilder.create();
		networkErrorAlert.show();
	}


	public boolean isOnline() {
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

    private class RunNewsReaderTask extends AsyncTask<Boolean, Void, ArrayList<News>> {
        @Override
        protected ArrayList<News> doInBackground(Boolean... isRefresh) {
            StringBuilder jsonRespASBuilder=networkCall.makeServerCallForNewsUpdate();
            if(jsonRespASBuilder!=null){
                ArrayList<News> newsList= (ArrayList<News>) newsListGenerator.generateNewsListFromJSONString(jsonRespASBuilder);
                return newsList;

            }
            else{
                return null;
                //showNetworkError(NETWORK_ERROR_TITLE, NETWORK_ERROR_MESSAGE,false);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<News> newsList) {
            if(newsList!=null) {
                loadNews(newsList);
                listData=newsList;
                notifyAdaptorChange();

            }
            else
                showNetworkError(NETWORK_ERROR_TITLE, NETWORK_ERROR_MESSAGE,false);

            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refreshmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRefresh:
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        if (isOnline()) {
                            newsReaderRefreshTask=new RunNewsReaderRefreshTask();
                            newsReaderRefreshTask.execute(new Boolean[]{true});
                        } else {
                            showNetworkError(NO_INTERNET_TITLE, NO_INTERNET_MESSAGE, true);
                            swipeRefreshLayout.setRefreshing(false);
                        }


                        //swipeRefreshLayout.setRefreshing(false);
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class RunNewsReaderRefreshTask extends AsyncTask<Boolean, Void, ArrayList<News>> {
        @Override
        protected ArrayList<News> doInBackground(Boolean... isRefresh) {

            StringBuilder jsonRespASBuilder=networkCall.makeServerCallForNewsUpdate();
            if(jsonRespASBuilder!=null){
                newsListGenerator.deleteAllNews();
                ArrayList<News> newsList= (ArrayList<News>) newsListGenerator.generateNewsListFromJSONString(jsonRespASBuilder);
                return newsList;
            }
            else{
                return null;
                //showNetworkError(NETWORK_ERROR_TITLE, NETWORK_ERROR_MESSAGE,false);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<News> newsList) {
            if(newsList != null) {
                loadNews(newsList);
                listData=newsList;
                notifyAdaptorChange();
               //swipeRefreshLayout.setRefreshing(false);
            }
            else
                showNetworkError(NETWORK_ERROR_TITLE, NETWORK_ERROR_MESSAGE,true);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
