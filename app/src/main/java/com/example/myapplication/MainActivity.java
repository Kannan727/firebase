package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String VEHICLE_NAME="vehiclename";
    public static final String VEHICLE_ID="vehicleid";
    EditText Vehicle_name;
    Spinner Vehicle_Type;
    Button ButtonAdd;
    DatabaseReference reference;

    ListView Listview;
    List<Vehicle_List>vehicleLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Vehicle_name=findViewById(R.id.Vehicle_name);
        Vehicle_Type=findViewById(R.id.Vehicle_type);
        ButtonAdd=findViewById(R.id.button);
        Listview=findViewById(R.id.ListViewTracks);

        vehicleLists=new ArrayList<>();
        reference= FirebaseDatabase.getInstance("https://my-application-24aea-default-rtdb.firebaseio.com/").getReference("vehicle");
        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            //calling the method vehiclelist
            //the method is defined below
            //this method is actually performing the write operation
            public void onClick(View v) {
                Addlist();
            }
        });
        Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vehicle_List Vlist=vehicleLists.get(position);
                Intent intent = new Intent(getApplicationContext(),AddtrackActivity.class);
                intent.putExtra(VEHICLE_ID,Vlist.getVehicle_ID());
                intent.putExtra(VEHICLE_NAME,Vlist.getVehicle_ID());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clear previous artist list
                vehicleLists.clear();
                //Iterating through all the nodes.
                 for(DataSnapshot vehicleSnapshot : dataSnapshot.getChildren()){
                     //getting vehicle list
                     Vehicle_List list = vehicleSnapshot.getValue(Vehicle_List.class);
                     //adding artist to the list.
                     vehicleLists.add(list);

                 }
                 //creating adapter
                Vehiclelist adapter = new Vehiclelist(MainActivity.this,vehicleLists);
                //attaching adapter to the listview
                 Listview.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    /*
      *this method is using to save new vehiclelist in
      * real time firebase.
    */

    private void Addlist()
    {
        //Getting the Values to save
        String name=Vehicle_name.getText().toString().trim();
        String type=Vehicle_Type.getSelectedItem().toString();
        //Check if the value is provided or not.
        if(!TextUtils.isEmpty(name))
        {
            //getting a unique id using push().getkey() method.
            //it will create a unique id and we will use it as the primary key for our vehicle.
            String id=reference.push().getKey();
            //creating an Vehiclelist object.
            Vehicle_List vehicle_list=new Vehicle_List(id,name,type);
            //saving the artist.
            reference.child(id).setValue(vehicle_list);
            //set vehicle name is blank again
            Vehicle_name.setText("");
            //displaying a success toast.
            Toast.makeText(this,"successfully  Added:",Toast.LENGTH_LONG).show();
        }
        else
        {
            //if the value is not given displaying a toast.
            Toast.makeText(this,"You should Enter a Name :",Toast.LENGTH_LONG).show();
        }
    }
}