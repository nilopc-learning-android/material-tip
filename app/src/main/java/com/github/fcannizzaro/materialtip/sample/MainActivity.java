package com.github.fcannizzaro.materialtip.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.fcannizzaro.materialtip.MaterialTip;
import com.github.fcannizzaro.materialtip.util.ButtonListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();

        final MaterialTip tip = (MaterialTip) findViewById(R.id.tip);

        tip
                .withTextRes(R.string.tip_description)
                .withBackgroundRes(R.color.tip_background)
                .withTextColorRes(R.color.tip_text)
                .withTitleColorRes(R.color.tip_title)
                .withButtonListener(new ButtonListener() {

                    @Override
                    public void onPositive(MaterialTip tip) {
                        System.out.println("positive");
                    }

                    @Override
                    public void onNegative(MaterialTip tip) {
                        System.out.println("negative");
                    }

                });


        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tip.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(findViewById(R.id.container), "Hello", Snackbar.LENGTH_LONG).show();
                    }
                }, 2000);

            }
        });

    }
}
