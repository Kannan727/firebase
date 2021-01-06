package com.example.myapplication;

import android.widget.Toast;

public class Vehicle_List {
    String Vehicle_ID;
    String Vehicle_Name;
    String Vehicle_Type;
    public Vehicle_List()
    {

    }
    public Vehicle_List(String vehicle_ID, String vehicle_Name, String vehicle_Type) {
        this.Vehicle_ID = vehicle_ID;
        this.Vehicle_Name = vehicle_Name;
        this.Vehicle_Type = vehicle_Type;
    }

    public String getVehicle_ID() {
        return Vehicle_ID;
    }

    public String getVehicle_Name() {
        return Vehicle_Name;
    }

    public String getVehicle_Type() {
        return Vehicle_Type;
    }
}
