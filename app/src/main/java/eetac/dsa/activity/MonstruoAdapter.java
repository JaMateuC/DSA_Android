package eetac.dsa.activity;

import android.app.Activity;
import android.widget.ArrayAdapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import eetac.dsa.R;
import eetac.dsa.juego.Controlador.Monstruo;

/**
 * Created by JesusLigero on 20/01/2018.
 */

public class MonstruoAdapter extends ArrayAdapter<Monstruo> {

    private Context context;
    private ArrayList<Monstruo> vectormontruo = new ArrayList<Monstruo>();
    int layoutResourceId;

    public MonstruoAdapter(Context context, int layoutResourceId, ArrayList<Monstruo> vector) {
        super(context, layoutResourceId, vector);
        this.vectormontruo = vector;
        this.context = context;
        this.layoutResourceId = layoutResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MonstruoHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new MonstruoHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (MonstruoHolder)row.getTag();
        }

        Monstruo monstruo = vectormontruo.get(position);

        holder.txtTitle.setText(monstruo.getTipo());


        //holder.imgIcon.setImageResource(weather.icon);
        holder.imgIcon.setImageResource(R.drawable.robot2);

        return row;
    }

    static class MonstruoHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }

}
