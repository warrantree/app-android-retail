package com.android.test.warrantree_retailer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivProfileImage;
    TextView tvName,tvPhoneNo,tvAddress,tvVat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName = (TextView) findViewById(R.id.tvProfileName);
        tvPhoneNo = (TextView) findViewById(R.id.tvPhoneNo);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvVat = (TextView) findViewById(R.id.tvVAT);
    }
}
