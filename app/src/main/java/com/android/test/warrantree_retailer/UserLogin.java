package com.android.test.warrantree_retailer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLogin extends AppCompatActivity {


    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    Button bt_sendOTP, bt_submitOTP, bt_submitDetails, bt_submitAddress;
    EditText et_NumMob, et_OTP, et_ShopName, et_ShopEmail, et_ShopAddress_L1, et_ShopAddress_L2, et_ShopAddress_City, et_ShopAddress_State, et_ShopAddress_PINCode, et_ShopAddress_Country;
    TextView tv_header, tv_edithint;
    TextInputLayout et_NumMoblay, et_OTPlay, et_ShopNamelay, et_ShopEmaillay, et_EmployeeNamelay, et_ShopVATGSTlay, et_ShopAddresslay;
    ImageButton ib_enableedit;

    String usr_MobNum, usr_OTP, usr_shopName, usr_shopEmail, usr_UserNAme, usr_shopAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        //Referencing the EditText Views - in order of display
        et_NumMob = (EditText) findViewById(R.id.user_auth_MobNum);
        et_OTP = (EditText) findViewById(R.id.user_auth_OTP);
        et_ShopName = (EditText) findViewById(R.id.user_auth_ShopName);
        et_ShopEmail = (EditText) findViewById(R.id.user_auth_Email);
        et_ShopAddress_L1 = (EditText) findViewById(R.id.user_auth_Address_L1);
        et_ShopAddress_L2 = (EditText) findViewById(R.id.user_auth_Address_L2);
        et_ShopAddress_City = (EditText) findViewById(R.id.user_auth_Address_city);
        et_ShopAddress_State = (EditText) findViewById(R.id.user_auth_Address_state);
        et_ShopAddress_PINCode = (EditText) findViewById(R.id.user_auth_Address_PIN);
        et_ShopAddress_Country = (EditText) findViewById(R.id.user_auth_Address_country);

        //Referencing TextInputLayouts - in order of display
        et_NumMoblay = (TextInputLayout) findViewById(R.id.user_auth_MobNum_Lay);
        et_OTPlay = (TextInputLayout) findViewById(R.id.user_auth_OTP_lay);
        et_ShopNamelay = (TextInputLayout) findViewById(R.id.user_auth_ShopName_lay);
        et_ShopEmaillay = (TextInputLayout) findViewById(R.id.user_auth_Email_lay);
        et_EmployeeNamelay = (TextInputLayout) findViewById(R.id.user_auth_EmployeeName_Lay);
        et_ShopVATGSTlay = (TextInputLayout) findViewById(R.id.user_auth_vat_gst_lay);
        et_ShopAddresslay = (TextInputLayout) findViewById(R.id.user_auth_Address_lay);

        //Referencing the Buttons - in order of execution
        bt_sendOTP = (Button) findViewById(R.id.btSendOTP);
        bt_submitOTP = (Button) findViewById(R.id.btSubmitOTP);
        bt_submitDetails = (Button) findViewById(R.id.btSubmitUsrDet);
        bt_submitAddress = (Button) findViewById(R.id.btSubmitShopAddress);

        //Referencing TextViews
        tv_header = (TextView) findViewById(R.id.user_auth_pageHeader);
        tv_edithint = (TextView) findViewById(R.id.tv_edit_hint);

        //Referencing the ImageButton
        ib_enableedit = (ImageButton) findViewById(R.id.btEnableEditing);


        //Handling Clicks
        bt_sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr_MobNum = et_NumMoblay.getEditText().getText().toString();
                if (!validateMobNum(usr_MobNum)) {
                    et_NumMoblay.setError("Not a valid Mobile Number!");
                } else {
                    et_NumMoblay.setErrorEnabled(false);
                    sendOTP();
                }
                // TODO: API calls for checking if number exists in DB goes here
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
                String currentOTP = et_OTPlay.getEditText().getText().toString();
                if (!validateOTP(currentOTP)) {
                    et_OTPlay.setError("Not a valid OTP!");
                } else {
                    et_OTPlay.setErrorEnabled(false);
                    completeLogin();
                }
                // TODO: API calls for submitting OTP goes here

                // TODO: Make elements invisible once OTP based auth is done.
                et_ShopNamelay.setVisibility(View.VISIBLE);
                et_ShopEmaillay.setVisibility(View.VISIBLE);
                et_ShopAddresslay.setVisibility(View.VISIBLE);
                et_ShopVATGSTlay.setVisibility(View.VISIBLE);
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
                usr_shopName = et_ShopName.getText().toString();
                usr_shopEmail = et_ShopEmail.getText().toString();
            }
        });

        bt_submitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usr_shopAddress = et_ShopAddress_L1.getText().toString();

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

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //Method to validate emails using the regular expression fetched from wikipedia
    //Corresponding ariables are on top of the class file.
    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validateMobNum(String mobnum) {
        return mobnum.length() > 10 || mobnum.length() < 10;
    }

    public boolean validateOTP(String currentotp) {
        return currentotp.length() > 4 || currentotp.length() < 4;
    }

    public void sendOTP() {
        Toast.makeText(getApplicationContext(), "OTP Sent! Check your SMS for the 4-digit OTP!.", Toast.LENGTH_SHORT).show();
        // TODO: login procedure;
    }

    public void completeLogin() {
        Toast.makeText(getApplicationContext(), "Woohoo! Authentication was successful!.", Toast.LENGTH_SHORT).show();
        // TODO: login procedure;
    }
}
