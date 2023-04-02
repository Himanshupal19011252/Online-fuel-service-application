package com.example.ofsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class order extends AppCompatActivity implements LocationListener {

    Spinner spinner;
    Spinner spinner2;
    ImageButton arrow_button;
    Button button_location;
    Button submit_button;
    DatabaseReference ref;
    FirebaseDatabase database;
    EditText textView_location;
    EditText Current_phone;
    EditText many_rupees;
    EditText company;
    LocationManager locationManager;
    orderr orders;
    int maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        button_location = findViewById(R.id.locationb);
        textView_location = findViewById(R.id.locationt);
        arrow_button = findViewById(R.id.arrowButton);
        submit_button = findViewById(R.id.submit);
        Current_phone = findViewById(R.id.Cphone);
        company = findViewById(R.id.comp);
        many_rupees = findViewById(R.id.rupees);
        orders = new orderr();


        ref = database.getInstance().getReference().child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Order");

        List<String> categories = new ArrayList<>();
        categories.add(0, "Vehicle Type");
        categories.add("Truck");
        categories.add("Bus");
        categories.add("Car");
        categories.add("tampoo");
        categories.add("Bike");
        categories.add("Scooty");
        categories.add("Scooter");

        List<String> categories2 = new ArrayList<>();
        categories2.add(0, "Service Type");
        categories2.add("Tyre Puncture");
        categories2.add("General Service");
        categories2.add("Vehicle Diagnostic");
        categories2.add("Route Finding Problem");
        categories2.add("Petrol");
        categories2.add("Desel");
        categories2.add("Other Service");

        ArrayAdapter<String> dataAdapter;
        ArrayAdapter<String> dataAdapter2;

        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories2);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(dataAdapter2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Vehicle Type")) {
                    //do nothing
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Service Type")) {
                    //do nothing
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        arrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(order.this, Drawer.class);
                startActivity(intent);
            }
        });

        if (ContextCompat.checkSelfPermission(order.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(order.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_LONG).show();
                getLocation();
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    maxid = (int) dataSnapshot.getChildrenCount();
                }else{

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orders.setCurrent_ph(Current_phone.getText().toString());
                orders.setMany_rupees(many_rupees.getText().toString());
                orders.setCompany(company.getText().toString());
                orders.setCurrent_location(textView_location.getText().toString());
                orders.setSpinner(spinner.getSelectedItem().toString());
                orders.setSpinner2(spinner2.getSelectedItem().toString());
                if (!TextUtils.isEmpty(Current_phone.getText().toString()) && !TextUtils.isEmpty(many_rupees.getText().toString()) && !TextUtils.isEmpty(company.getText().toString()) && !TextUtils.isEmpty(textView_location.getText().toString()))
                {
                    if (!company.getText().toString().matches("[a-z,A-Z, ]*")) {
                        company.setError("Enter character Only");
                    } else if (!Current_phone.getText().toString().matches("[0-9]{10}")) {
                        Current_phone.setError("Enter Only 10 digit Mobile Number");
                    } else if (!many_rupees.getText().toString().matches("[0-9]+")) {
                        many_rupees.setError("Enter Only digits ");
                    } else {

                        Toast.makeText(getApplicationContext(), "Order Successfully", Toast.LENGTH_LONG).show();
                        ref.child(String.valueOf(maxid + 1)).setValue(orders);
                    }
                }
                else {

                    Toast.makeText(getApplicationContext(), "Please fill complete Detail", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try{
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,  order.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onLocationChanged(Location location){
        //Toast.makeText(this,""+location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_LONG).show();
        try{
            Geocoder geocoder=new Geocoder(order.this, Locale.getDefault());
            List<Address> addresses =geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address=addresses.get(0).getAddressLine(0);
            textView_location.setText(address);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {


    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplicationContext(), "Please open your phone location", Toast.LENGTH_LONG).show();

    }

}