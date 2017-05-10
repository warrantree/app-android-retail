package com.android.test.warrantree_retailer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.test.warrantree_retailer.R;

import java.util.ArrayList;

/**
 * Created by anujgupta on 10/05/17.
 */

public class CRMAdapter extends RecyclerView.Adapter<CRMAdapter.CRM>{


    ArrayList<String> bill = new ArrayList<>();
    Context context;

    public CRMAdapter(ArrayList<String> bill, Context context) {
        this.bill = bill;
        this.context = context;
    }

    @Override
    public CRM onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(context).inflate(R.layout.card_crm,parent,false);
        CRM o = new CRM(v);
        return o;
    }

    @Override
    public void onBindViewHolder(CRM holder, int position) {


    }

    @Override
    public int getItemCount() {
        return bill.size();
    }

    public class CRM extends RecyclerView.ViewHolder{


        TextView tvBillId;
        TextView tvProductName;
        TextView tvCustNum;

        public CRM(View v) {
            super(v);


            tvBillId = (TextView) v.findViewById(R.id.tvCustName);
            tvProductName = (TextView) v.findViewById(R.id.tvCustItems);
            tvCustNum = (TextView) v.findViewById(R.id.tvCustDate);

        }
    }
}
