package globalnews.jaf.com.globalnews;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import globalnews.jaf.com.globalnews.util.URLImageParser;

public class ContentActivity extends AppCompatActivity {

    public static String headerText="";
    public static String contentText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content_screen);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle((Html.fromHtml("<font color=\"#2196F3\">" + "CaNEWS" + "</font>")));
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#E6EE9C"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setHomeButtonEnabled(true);
        TextView heading=(TextView)findViewById(R.id.headingView);
        TextView content=(TextView)findViewById(R.id.contentView);
        URLImageParser imageParser = new URLImageParser(content, content.getContext());
        contentText = contentText.replaceAll("<[^>]*>", "");
        Spanned htmlSpan = Html.fromHtml(contentText, imageParser, null);
        heading.setText(headerText);
        content.setText(htmlSpan);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      finish();
        return true;
    }
}
