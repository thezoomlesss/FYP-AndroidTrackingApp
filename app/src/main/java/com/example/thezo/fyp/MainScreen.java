package com.example.thezo.fyp;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainScreen extends FragmentActivity implements MapFragment.OnFragmentInteractionListener, OnMapReadyCallback {

    private FusedLocationProviderClient client;

    private LocationRequest mLocationRequest;

    private Button getLocation;
    private long UPDATE_INTERVAL = 1 * 1000;  /* 1 sec */
    private long FASTEST_INTERVAL = 200; /* .2 sec */
    private TextView textView, companyNameText, numberPlateText, vehicleStatusText;
    private int counter = 0;
    private String companyName, number_plate, vehicleStatus, companyID, pass;
    private View topBar, detailToggle;
    private ImageView logOut;
    private int topBarInitHeight;
    private RequestQueue queue;
    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        companyID = getIntent().getExtras().getString("companyID");
        companyName = getIntent().getExtras().getString("companyName");
        number_plate = getIntent().getExtras().getString("numberPlate");
        vehicleStatus = getIntent().getExtras().getString("vehicleStatus");
        pass = getIntent().getExtras().getString("pass");
        companyNameText = findViewById(R.id.idcompanyNameField);
        numberPlateText = findViewById(R.id.idnumberPlateField);
        vehicleStatusText = findViewById(R.id.idVehicleStatus);
        getLocation = findViewById(R.id.getLocation);
        detailToggle = findViewById(R.id.detailToggle);
        topBar = findViewById(R.id.idDetailBar);
        logOut = findViewById(R.id.idLogOutImage);


        SupportMapFragment fragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fragment.getMapAsync(this);

        queue = Volley.newRequestQueue(this);

        companyNameText.setText(companyName);
        numberPlateText.setText(number_plate);
        vehicleStatusText.setText(vehicleStatus);

        // Changing the colour of the status TextView depending on the value
        if(vehicleStatus.equals("Active")) {
            vehicleStatusText.setTextColor(Color.GREEN);
        }else{
            vehicleStatusText.setTextColor(Color.RED);
        }

//        Toast.makeText(this, companyName+number_plate+vehicleStatus, Toast.LENGTH_SHORT).show();
        startLocationUpdates();
        // Textview used to display the current location
        textView = findViewById(R.id.location);
        // Post request to URL in order to update the location


        /*
            After the layout is created and inflated, get the height of the top bar that we expand/collapse
         */
        ViewTreeObserver vto = topBar.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                topBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                topBarInitHeight = topBar.getMeasuredHeight();

            }
        });






//      Icon that acts as a button to expand/collapse the top bar that contains the details
        detailToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topBar.getHeight() < topBarInitHeight){

                    expand(topBar);
                    detailToggle.animate().rotation(0).start();
                }else{
                    collapse(topBar);
                    detailToggle.animate().rotation(180).start();
                }

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Add the request to the RequestQueue.
    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(MainScreen.this,
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        getFusedLocationProviderClient(MainScreen.this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    startLocationUpdates();
                    Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainScreen.this, "Permission denied to access location", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        textView.setText(msg + " " + counter);
        counter++;
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        String urlVehicleLocUpdate = SecretKeys.urlVehicleLocUpdate + "?companyID="+companyID+"&plate="+number_plate+"&pass="+pass+"&lat="+Double.toString(location.getLatitude())+"&long="+Double.toString(location.getLongitude());

        // Request a string response from the provided URL.
        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, urlVehicleLocUpdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                Toast.makeText(MainScreen.this, "Connection made", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(MainScreen.this, "Error timeout!", Toast.LENGTH_SHORT).show();

                }else if( error instanceof NoConnectionError) {
                    Toast.makeText(MainScreen.this, "Error no connection!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(MainScreen.this, "Error auth!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof ServerError) {
                    Toast.makeText(MainScreen.this, "Error ServerError!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NetworkError) {
                    Toast.makeText(MainScreen.this, "Error Network!", Toast.LENGTH_SHORT).show();

                } else if (error instanceof ParseError) {
                    Toast.makeText(MainScreen.this, "Error Parse!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainScreen.this, "IDK FAM!", Toast.LENGTH_SHORT).show();

                }
            }
        });

//        queue.add(stringRequest);

    }


    // Used to expand a view with an animation
    public static void expand(final View v) {
        v.measure(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ActionBar.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    // Used to collapse a view with an animation
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    // Needed to implement the map
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(40.702765, -74.032574)).zoom(16).bearing(0).tilt(45).build();
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
        gMap.addMarker(new MarkerOptions().position(new LatLng(40.702765, -74.032574)).title("The Statue of Liberty").snippet("Test"));

        // add marker from here

    }
}

