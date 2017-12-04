package com.example.mathijs.mathijsparmentierpset5;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Mathijs on 27/11/2017.
 */

public class RestoAdapter extends ResourceCursorAdapter {
    public RestoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_item, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView itemName = (TextView) view.findViewById(R.id.itemName);

        itemName.setText(cursor.getString(cursor.getColumnIndex("name")));
    }
}
