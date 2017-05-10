package com.android.test.warrantree_retailer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.test.warrantree_retailer.adapters.CRMAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class CRMActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.Adapter adapter;
    LinearLayoutManager llm;

    ArrayList<String> Crm = new ArrayList<>(Arrays.asList("","","",""));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm);

        rv = (RecyclerView) findViewById(R.id.rvCRM);
        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        adapter = new CRMAdapter(Crm,this);

        rv.setAdapter(adapter);

    }
}
