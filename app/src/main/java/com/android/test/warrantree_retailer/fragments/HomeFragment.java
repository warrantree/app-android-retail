package com.android.test.warrantree_retailer.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.test.warrantree_retailer.PastOrdersActivity;
import com.android.test.warrantree_retailer.R;
import com.android.test.warrantree_retailer.TagCustomerActivity;

import java.io.ByteArrayOutputStream;

/**
 * Created by anujgupta on 23/04/17.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    Button btTagCust,btTrends,btPastOrders;
    private static final int CAMERA_REQUEST = 1888;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        btTagCust = (Button) v.findViewById(R.id.btTagCustomer);
        btTrends = (Button) v.findViewById(R.id.btTrends);
        btPastOrders = (Button) v.findViewById(R.id.btPastOrders);

        btTagCust.setOnClickListener(this);
        btTrends.setOnClickListener(this);
        btPastOrders.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btTagCustomer:

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.btTrends:
                break;

            case R.id.btPastOrders:

                startActivity(new Intent(getActivity(), PastOrdersActivity.class));
                break;
        }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Intent in1 = new Intent(getActivity(), TagCustomerActivity.class);
            in1.putExtra("image",byteArray);
            startActivity(in1);
        }

    }

}
