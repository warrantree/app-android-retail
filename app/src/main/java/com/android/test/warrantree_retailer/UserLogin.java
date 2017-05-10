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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLogin extends AppCompatActivity {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    int resendOTP_count = 0;

    Button bt_sendOTP, bt_submitOTP, bt_submitDetails, bt_submitAddress;
    EditText et_NumMob, et_OTP, et_ShopName, et_ShopEmail, et_ShopAddress, et_ShopAddress_City, et_ShopAddress_State, et_ShopAddress_ZIPCode, et_ShopEmployeeName, et_TIN_VAT_GST;
    TextView tv_header, tv_edithint;
    TextInputLayout et_NumMoblay, et_OTPlay, et_ShopNamelay, et_ShopEmaillay, et_ShopEmployeeNamelay, et_ShopVATGSTlay, et_ShopAddresslay, et_ShopAddressCitylay, et_ShopAddressStatelay, et_ShopAddressZIPlay;
    ImageButton ib_enableedit;

    RadioGroup radiogroup_for_shop_auth_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        //Referencing the EditText Views - in order of display
        et_NumMob = (EditText) findViewById(R.id.user_auth_MobNum);
        et_OTP = (EditText) findViewById(R.id.user_auth_OTP);
        et_ShopName = (EditText) findViewById(R.id.user_auth_ShopName);
        et_ShopEmail = (EditText) findViewById(R.id.user_auth_Email);
        et_ShopEmployeeName = (EditText) findViewById(R.id.user_auth_EmployeeName);
        et_TIN_VAT_GST = (EditText) findViewById(R.id.user_auth_vat_gst);

        et_ShopAddress = (EditText) findViewById(R.id.user_auth_Address);
        et_ShopAddress_City = (EditText) findViewById(R.id.user_auth_Address_city);
        et_ShopAddress_State = (EditText) findViewById(R.id.user_auth_Address_state);
        et_ShopAddress_ZIPCode = (EditText) findViewById(R.id.user_auth_Address_ZIP);

        //Referencing TextInputLayouts - in order of display
        et_NumMoblay = (TextInputLayout) findViewById(R.id.user_auth_MobNum_Lay);
        et_OTPlay = (TextInputLayout) findViewById(R.id.user_auth_OTP_lay);
        et_ShopNamelay = (TextInputLayout) findViewById(R.id.user_auth_ShopName_lay);
        et_ShopEmaillay = (TextInputLayout) findViewById(R.id.user_auth_Email_lay);
        et_ShopEmployeeNamelay = (TextInputLayout) findViewById(R.id.user_auth_EmployeeName_Lay);
        et_ShopVATGSTlay = (TextInputLayout) findViewById(R.id.user_auth_vat_gst_lay);

        et_ShopAddresslay = (TextInputLayout) findViewById(R.id.user_auth_Address_lay);
        et_ShopAddressCitylay = (TextInputLayout) findViewById(R.id.user_auth_Address_city_lay);
        et_ShopAddressStatelay = (TextInputLayout) findViewById(R.id.user_auth_Address_state_lay);
        et_ShopAddressZIPlay = (TextInputLayout) findViewById(R.id.user_auth_Address_ZIP_lay);

        //Referencing the Buttons - in order of execution
        bt_sendOTP = (Button) findViewById(R.id.btSendOTP);
        bt_submitOTP = (Button) findViewById(R.id.btSubmitOTP);
        bt_submitDetails = (Button) findViewById(R.id.btSubmitUsrDet);
        bt_submitAddress = (Button) findViewById(R.id.btSubmitShopAddress);

        //Referencing TextViews
        tv_header = (TextView) findViewById(R.id.user_auth_pageHeader);
        tv_edithint = (TextView) findViewById(R.id.tv_edit_hint);

/*        //Referencing the ImageButton
        ib_enableedit = (ImageButton) findViewById(R.id.btEnableEditing);*/

        //Referencing RadioButtons
        radiogroup_for_shop_auth_id = (RadioGroup) findViewById(R.id.radiogroupfor_shopauthid);


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
                onSendOTP();
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
                onSubmitOTP();
            }
        });

        bt_submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //usr_shopName = et_ShopName.getText().toString();
                //usr_shopEmail = et_ShopEmail.getText().toString();
                onSubmitDetails();
            }
        });

        bt_submitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitAddress();
            }
        });

        /*ib_enableedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                on
            }
        });*/
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
        return mobnum.length() == 10;
    }

    public boolean validateOTP(String currentotp) {
        return currentotp.length() == 4;
    }

    public void sendOTP() {
        Toast.makeText(getApplicationContext(), "OTP Sent! Check your SMS for the 4-digit OTP!.", Toast.LENGTH_SHORT).show();
        // TODO: login procedure;
    }

    public void completeLogin() {
        Toast.makeText(getApplicationContext(), "Woohoo! Authentication was successful!.", Toast.LENGTH_SHORT).show();
        // TODO: login procedure;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_gst:
                if (checked)
                    et_ShopVATGSTlay.getEditText().setHint("GST");
                // Pirates are the best
                break;
            case R.id.rb_vat:
                if (checked)
                    et_ShopVATGSTlay.getEditText().setHint("VAT");
                // Ninjas rule
                break;
            case R.id.rb_tin:
                if (checked)
                    et_ShopVATGSTlay.getEditText().setHint("TIN");
                // Ninjas rule
                break;
        }
    }

    public void onSendOTP() {

        //Show Next Section
        et_OTPlay.setVisibility(View.VISIBLE);
        bt_submitOTP.setVisibility(View.VISIBLE);
        //ib_enableedit.setVisibility(View.VISIBLE);
        disableEditText(et_NumMob);
        tv_edithint.setVisibility(View.VISIBLE);
        tv_edithint.setText(R.string.nootp1);

        //Hide Previous Section
        bt_sendOTP.setVisibility(View.GONE);


    }

    public void onSubmitOTP() {

        //Show Next Section
        et_ShopNamelay.setVisibility(View.VISIBLE);
        et_ShopEmaillay.setVisibility(View.VISIBLE);
        et_ShopEmployeeNamelay.setVisibility(View.VISIBLE);
        et_ShopVATGSTlay.setVisibility(View.VISIBLE);
        radiogroup_for_shop_auth_id.setVisibility(View.VISIBLE);
        bt_submitDetails.setVisibility(View.VISIBLE);

        //Hide Previous Section
        et_OTPlay.setVisibility(View.GONE);
        tv_edithint.setVisibility(View.GONE);
        //ib_enableedit.setVisibility(View.GONE);
        bt_submitOTP.setVisibility(View.GONE);
        tv_header.setText("Business/Shop Details");
    }

    public void onSubmitDetails() {
        //Show Next Section
        et_ShopAddresslay.setVisibility(View.VISIBLE);
        et_ShopAddressCitylay.setVisibility(View.VISIBLE);
        et_ShopAddressStatelay.setVisibility(View.VISIBLE);
        et_ShopAddressZIPlay.setVisibility(View.VISIBLE);
        bt_submitAddress.setVisibility(View.VISIBLE);

        //Hide Previous Section
        et_ShopNamelay.setVisibility(View.GONE);
        et_ShopEmaillay.setVisibility(View.GONE);
        et_ShopName.setVisibility(View.GONE);
        et_ShopVATGSTlay.setVisibility(View.GONE);
        et_ShopEmployeeNamelay.setVisibility(View.GONE);
        radiogroup_for_shop_auth_id.setVisibility(View.GONE);
        bt_submitDetails.setVisibility(View.GONE);
    }

    public void onSubmitAddress() {

        //Hide Current Section
        et_ShopAddresslay.setVisibility(View.GONE);
        et_ShopAddressCitylay.setVisibility(View.GONE);
        et_ShopAddressStatelay.setVisibility(View.GONE);
        et_ShopAddressZIPlay.setVisibility(View.GONE);
        bt_submitAddress.setVisibility(View.GONE);

        //Start New Activity
        startActivity(new Intent(UserLogin.this, Home.class));
    }

    public void resendOTP(View view) {
        if (resendOTP_count >= 2) {
            enableEditText(et_NumMob);

            //Show Previous Section
            bt_sendOTP.setVisibility(View.VISIBLE);

            //Hide Current Section
            bt_submitOTP.setVisibility(View.GONE);
            //ib_enableedit.setVisibility(View.GONE);
            et_OTPlay.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Attention! Max OTP Count Reached!", Toast.LENGTH_SHORT).show();
            tv_edithint.setText(R.string.nootp2);
        } else {
            resendOTP_count++;
        }
        Toast.makeText(getApplicationContext(), "OTP Re-Sent! Check your messages for the 4-digit OTP!.", Toast.LENGTH_SHORT).show();
    }
}
