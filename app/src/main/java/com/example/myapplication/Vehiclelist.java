package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Vehiclelist extends ArrayAdapter<Vehicle_List> {
    private Activity context;
    private List<Vehicle_List>VehicleList;
    public Vehiclelist(Activity context,List<Vehicle_List> VehicleList) {
        super(context,R.layout.list_layout,VehicleList);
        this.context=context;
        this.VehicleList=VehicleList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView viewName =  (TextView)listViewItem.findViewById(R.id.viewname);
        TextView viewType = (TextView) listViewItem.findViewById(R.id.viewtype);

        Vehicle_List vlist=VehicleList.get(position);

        viewName.setText(vlist.getVehicle_Name());
        viewType.setText(vlist.getVehicle_Type());

        return listViewItem;
    }
}
