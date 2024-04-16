package com.example.project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddressAdapter extends BaseAdapter{
    private static final String TAG = "AddressAdapter";
    private final Activity activity;
    private ArrayList<ItemData> arrayList = new ArrayList<>();

    public AddressAdapter(Activity activity) {
        this.activity =activity;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearItem() {arrayList.clear();}

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView called");
        if(convertView == null) {
            Log.i(TAG, "getView : convertView == null");
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.layout_item, parent, false);
        }

        LinearLayout linearLayout = (LinearLayout)convertView;

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int itemHeight = size.x / 5;

        ViewGroup.LayoutParams param = linearLayout.getLayoutParams();
        param.height = itemHeight;
        linearLayout.setLayoutParams(param);

        ItemData itemData = arrayList.get(position);
        ImageView imageView = linearLayout.findViewById(R.id.imageView);
        TextView textViewName = linearLayout.findViewById(R.id.textViewName);
        TextView textViewMessage = linearLayout.findViewById(R.id.textViewMessage);
        TextView textFirstName = linearLayout.findViewById(R.id.textFirstName);

        String bgColor = itemData.getColor();
        imageView.setBackgroundColor(Color.parseColor(bgColor));
        textViewName.setText(itemData.getName());
        textFirstName.setText(itemData.getName().substring(0, 1));
        textViewMessage.setText(itemData.getMessage());

        return linearLayout;
    }

    public void addItem(String time, String name, String message, String color) {
        ItemData item = new ItemData(time, name, message, color);
        arrayList.add(item);
    }


}
