package com.android.test.warrantree_retailer;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.test.warrantree_retailer.adapters.OrdersAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Aditya on 5/4/2017.
 */

public class Home extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    RecyclerView rv;
    RecyclerView.Adapter adapter;
    LinearLayoutManager llm;
    FloatingActionButton tag_a_customer, view_trends;
    ArrayList<String> bills = new ArrayList<>(Arrays.asList("bill1", "bill1", "bill1", "bill1"));
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_home_toolbar);
        setSupportActionBar(mytoolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment mydrawerfragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.frag_nav_drawer);

        //To set up the custom navigation drawer we need to pass the following arguments:
        // 1. ID of the fragment tah in the main activity
        // 2. TypeCasted drawerlayout as we defined in the layouts with the fragment's layout
        // 3. variable reference to the toolbar we intend to use with in the activity with the nav drawer
        // These will be passed through a function which shall be defined in a seperate fragment file, Example "setUp();"
        mydrawerfragment.setUp(R.id.frag_nav_drawer, (DrawerLayout) findViewById(R.id.my_drawer_layout), mytoolbar);


        tag_a_customer = (FloatingActionButton) findViewById(R.id.tag_a_customer);
        view_trends = (FloatingActionButton) findViewById(R.id.view_trends);

        tag_a_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        view_trends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, TrendsActivity.class));
            }
        });

        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rv = (RecyclerView) findViewById(R.id.rvPastOrders);
        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        adapter = new OrdersAdapter(bills, this);
        rv.setAdapter(adapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Intent in1 = new Intent(Home.this, TagCustomerActivity.class);
            in1.putExtra("image", byteArray);
            startActivity(in1);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user_profile:
                // User chose the "Settings" item, show the app settings UI...
                startActivity(new Intent(this, ProfileActivity.class));
                return true;

            case R.id.action_crm:
                // User chose the "Favorite" action, mark the current item
                startActivity(new Intent(this, CRMActivity.class));
                // as a favorite...
                return true;
/*
            case R.id.action_rate:
                btnRateAppOnClick(item);
                return true;*/

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }

    //On click event for rate this app button
    public void btnRateAppOnClick(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Try Google play
        intent.setData(Uri.parse("market://details?id=amitech.twok16.amitech16"));
        if (!myNewActivityStarter(intent)) {
            //Market (Google play) app seems not installed, let's try to open a webbrowser
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=amitech.twok16.amitech16"));
            if (!myNewActivityStarter(intent)) {
                //Well if this also fails, we have run out of options, inform the user.
                Toast.makeText(this, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean myNewActivityStarter(Intent activityStartIntent) {
        try {
            startActivity(activityStartIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}
