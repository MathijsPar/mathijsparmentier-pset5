package com.example.mathijs.mathijsparmentierpset5;


import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends ListFragment {

    List<String> menuList = new ArrayList<String>();
    JSONArray menu = new JSONArray();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  Bundle
        Bundle arguments = this.getArguments();
        final String category = arguments.getString("category");

        // Instantiate Requestqueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://resto.mprog.nl/menu";

        // Make Request and put items in an array
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray items = null;
                        try {
                            items = response.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {
                                if(items.getJSONObject(i).optString("category").equals(category)) {
                                    JSONObject item = new JSONObject();
                                    String name = items.getJSONObject(i).optString("name");
                                    String price = items.getJSONObject(i).optString("price");
                                    item.put("name", name);
                                    item.put("price", price);
                                    menu.put(item);

                                    menuList.add(name);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        setUpArrayAdapter();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        queue.add(jsonObjectRequest);
    }

    private void setUpArrayAdapter() {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        menuList
                );
        this.setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String clickedItem = l.getItemAtPosition(position).toString();
        RestoDatabase db = RestoDatabase.getInstance(getContext());

        for (int i = 0; i < menu.length(); i++) {
            try {
                float price = Float.parseFloat(menu.getJSONObject(i).getString("price"));
                db.addItem(clickedItem, price);
                Toast addedItem = Toast.makeText(getContext(), clickedItem + " added!", Toast.LENGTH_LONG);
                addedItem.show();
                break;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
