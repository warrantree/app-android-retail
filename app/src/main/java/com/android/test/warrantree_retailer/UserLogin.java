package com.android.test.warrantree_retailer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserLogin extends AppCompatActivity {

    Button bt_sendOTP, bt_submitOTP, bt_submitDetails;
    EditText et_NumMob, et_OTP, et_ShopName, et_ShopEmail, et_ShopAddress;
    TextView tv_header, tv_edithint;
    TextInputLayout et_NumMoblay, et_OTPlay, et_ShopNamelay, et_ShopEmaillay, et_ShopAddresslay;
    ImageButton ib_enableedit;

    String usr_MobNum, usr_OTP, usr_shopName, usr_shopEmail, getUsr_shopAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        //Referencing the EditText Views - in order of display
        et_NumMob = (EditText) findViewById(R.id.user_auth_MobNum);
        et_OTP = (EditText) findViewById(R.id.user_auth_OTP);
        et_ShopName = (EditText) findViewById(R.id.user_auth_ShopName);
        et_ShopEmail = (EditText) findViewById(R.id.user_auth_Email);
        et_ShopAddress = (EditText) findViewById(R.id.user_auth_Address);

        //Referencing TextInputLayouts - in order of display
        et_NumMoblay = (TextInputLayout) findViewById(R.id.user_auth_MobNum_Lay);
        et_OTPlay = (TextInputLayout) findViewById(R.id.user_auth_OTP_lay);
        et_ShopNamelay = (TextInputLayout) findViewById(R.id.user_auth_ShopName_lay);
        et_ShopEmaillay = (TextInputLayout) findViewById(R.id.user_auth_Email_lay);
        et_ShopAddresslay = (TextInputLayout) findViewById(R.id.user_auth_Address_lay);

        //Referencing the Buttons - in order of execution
        bt_sendOTP = (Button) findViewById(R.id.btSendOTP);
        bt_submitOTP = (Button) findViewById(R.id.btSubmitOTP);
        bt_submitDetails = (Button) findViewById(R.id.btSubmitUsrDet);

        //Referencing TextViews
        tv_header = (TextView) findViewById(R.id.user_auth_pageHeader);
        tv_edithint = (TextView) findViewById(R.id.tv_edit_hint);

        //Referencing the ImageButton
        ib_enableedit = (ImageButton) findViewById(R.id.btEnableEditing);


        //Handling Clicks
        bt_sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr_MobNum = et_NumMob.getText().toString();
                //API calls for checking if number exists in DB goes here
                et_OTPlay.setVisibility(View.VISIBLE);
                ib_enableedit.setVisibility(View.VISIBLE);
                bt_submitOTP.setVisibility(View.VISIBLE);
                bt_sendOTP.setVisibility(View.GONE);
                disableEditText(et_NumMob);
                tv_edithint.setVisibility(View.VISIBLE);
            }
        });
        bt_submitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr_OTP = et_OTP.getText().toString();
                //API calls for submitting OTP goes here

                //Make elements invisible once OTP based auth is done.
                et_ShopNamelay.setVisibility(View.VISIBLE);
                et_ShopEmaillay.setVisibility(View.VISIBLE);
                et_ShopAddresslay.setVisibility(View.VISIBLE);
                bt_submitDetails.setVisibility(View.VISIBLE);
                et_OTPlay.setVisibility(View.GONE);
                tv_edithint.setVisibility(View.GONE);
                ib_enableedit.setVisibility(View.GONE);
                bt_submitOTP.setVisibility(View.GONE);
                tv_header.setText("Business/Shop Details");
            }
        });

        bt_submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr_shopName = et_ShopAddress.getText().toString();
                usr_shopEmail = et_ShopEmail.getText().toString();
                getUsr_shopAddress = et_ShopAddress.getText().toString();

                startActivity(new Intent(UserLogin.this, Home.class));
            }
        });

        ib_enableedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditText(et_NumMob);
                bt_sendOTP.setVisibility(View.VISIBLE);
                bt_submitOTP.setVisibility(View.GONE);
                ib_enableedit.setVisibility(View.GONE);
                et_OTPlay.setVisibility(View.GONE);
            }
        });
    }

    //To Disable an EditTextView
    public void disableEditText(EditText et) {
        //et.setFocusable(false);
        et.setEnabled(false);
    }

    //To Enable an EditTextView
    public void enableEditText(EditText et) {
        et.setEnabled(true);
        //et.setFocusable(true);
    }
}
