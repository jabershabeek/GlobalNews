package globalnews.jaf.com.globalnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_newsicon);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        final LinearLayout layout=(LinearLayout)findViewById(R.id.loading_layout);
        final ImageButton imgButton=(ImageButton)findViewById(R.id.imageButton);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setEnabled(false);
                layout.setClickable(false);
                imgButton.setEnabled(false);
                imgButton.setClickable(false);
                Intent myIntent = new Intent(LoadingActivity.this,GlobalNewsListActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
                finish();
            }
        });


        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgButton.setEnabled(false);
                imgButton.setClickable(false);
                layout.setEnabled(false);
                layout.setClickable(false);
                Intent myIntent = new Intent(LoadingActivity.this,GlobalNewsListActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
                finish();
            }
        });
    }
}
