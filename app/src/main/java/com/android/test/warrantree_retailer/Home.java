package com.android.test.warrantree_retailer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

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
                startActivity(new Intent(Home.this, Trends.class));
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            in1.putExtra("image",byteArray);
            startActivity(in1);
        }

    }
}
