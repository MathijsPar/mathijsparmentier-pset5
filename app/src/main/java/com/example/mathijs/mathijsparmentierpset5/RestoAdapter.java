package com.example.mathijs.mathijsparmentierpset5;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;

/**
 * Created by Mathijs on 27/11/2017.
 */

public class RestoAdapter extends ResourceCursorAdapter {
    public TodoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.fragment_menu, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
