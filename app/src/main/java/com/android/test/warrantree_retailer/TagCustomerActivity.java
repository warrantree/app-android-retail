package com.android.test.warrantree_retailer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TagCustomerActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView ivBill;
    Button btClickAgain,btSubmit;
    EditText etCustNum;
    private static final int CAMERA_REQUEST = 1888;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_customer);

        ivBill = (ImageView) findViewById(R.id.ivBill);

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ivBill.setImageBitmap(bmp);

        etCustNum = (EditText) findViewById(R.id.etCustNumber);
        btClickAgain = (Button) findViewById(R.id.btClickAgain);
        btSubmit = (Button) findViewById(R.id.btSubmitBill);
        btClickAgain.setOnClickListener(this);
        btSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btClickAgain:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.btSubmitBill:
                startActivity(new Intent(TagCustomerActivity.this,MainActivity.class));
                break;

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivBill.setImageBitmap(photo);
        }

    }
}
