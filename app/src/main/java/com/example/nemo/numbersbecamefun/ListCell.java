package com.example.nemo.numbersbecamefun;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nemo on 27/03/2018.
 */

public class ListCell extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] jogo;
    private final Integer[] imageId;


    public ListCell(Activity context1, String[] jogo, Integer[] imageId) {
        super(context1, R.layout.list_cell,jogo);
        this.context = context1;
        this.jogo = jogo;
        this.imageId = imageId;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_cell,null,true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(jogo[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
