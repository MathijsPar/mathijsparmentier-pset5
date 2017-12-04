package com.example.mathijs.mathijsparmentierpset5;


import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends DialogFragment implements View.OnClickListener {

    RestoDatabase db;
    Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ListView listView = (ListView) view.findViewById(R.id.orderList);

        db = RestoDatabase.getInstance(getContext());
        cursor = db.selectAll();

        RestoAdapter adapter = new RestoAdapter(getContext(), cursor);
        listView.setAdapter(adapter);

        Button sendButton = (Button) view.findViewById(R.id.sendOrder);
        Button cancelButton = (Button) view.findViewById(R.id.cancelOrder);
        Button clearButton = (Button) view.findViewById(R.id.clearOrder);

        sendButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendOrder:
                System.out.println("sending order");
                showWaitTime();
                break;
            case R.id.clearOrder:
                System.out.println("clearing database");
                db.clear();
                this.dismiss();
                break;
            case R.id.cancelOrder:
                System.out.println("canceling order");
                this.dismiss();
                break;
        }
    }

    private void showWaitTime() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://resto.mprog.nl/order";

        StringRequest postRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            String responseText =
                                    res.getString("preparation_time") + " minutes remaining";
                            Toast waitingTime = Toast.makeText(
                                    getContext(),
                                    responseText,
                                    Toast.LENGTH_LONG);
                            waitingTime.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        queue.add(postRequest);
    }
}
