package com.example.tony.opencvnonfree;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private static final String testImagesPath = Environment.getExternalStorageDirectory() + "/test_images";
    public static final String testImagesSrcPath = testImagesPath + "/src";
    public static final String testImagesDstPath = testImagesPath + "/dst";

    private Button surfBtn, grabCutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        surfBtn = (Button) findViewById(R.id.surf_btn);
        grabCutBtn = (Button) findViewById(R.id.grabcut_btn);
        surfBtn.setOnClickListener(this);
        grabCutBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(surfBtn)) {
            Intent intent = new Intent(MainActivity.this, SURF.class);
            startActivity(intent);
        } else if (v.equals(grabCutBtn)) {
            Intent intent = new Intent(MainActivity.this, GrabCut.class);
            startActivity(intent);
        }
    }
}