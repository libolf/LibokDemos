package com.libo.libokdemos.Utils;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.libo.libokdemos.R;

/**
 * Created by libok on 2018-01-26.
 */

public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * item的View
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.sqlite_list_item, null);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView idText = view.findViewById(R.id.sqlite_list_item_id);
        TextView nameText = view.findViewById(R.id.sqlite_list_item_name);
        TextView ageText = view.findViewById(R.id.sqlite_list_item_age);

        idText.setText(cursor.getString(cursor.getColumnIndex("_id")) + "");
        nameText.setText(cursor.getString(cursor.getColumnIndex("name")));
        ageText.setText(cursor.getString(cursor.getColumnIndex("age")) + "");
    }
}
