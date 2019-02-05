package com.example.thezo.fyp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LoginActivity extends AppCompatActivity {

    // The elements present in this activity
    private EditText company_edit, vehicle_edit, pass_edit;
    private Button login_button, demo_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String baseUrl = SecretKeys.urlVehicleLogin;

        company_edit = findViewById(R.id.idCompany);
        vehicle_edit = findViewById(R.id.idVehicle);
        pass_edit = findViewById(R.id.idPass);
        login_button = findViewById(R.id.idLogin);
        demo_button = findViewById(R.id.demoButton);


        demo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                company_edit.setText(SecretKeys.companyID1);
                vehicle_edit.setText(SecretKeys.vehicle1);
                pass_edit.setText(SecretKeys.pass1);
                demo_button.setClickable(false);
                login_button.performClick();
                login_button.setClickable(false);
            }
        });

        /*
            Asking for permission the first time the user opens the app (or everytime when the permission is not granted)
         */
        if (ActivityCompat.checkSelfPermission(LoginActivity.this,
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        /* On click it will check with the database if the details are correct */
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !company_edit.getText().toString().isEmpty() && !vehicle_edit.getText().toString().isEmpty() && !pass_edit.getText().toString().isEmpty()){
                    String urlToSend = baseUrl + "?cid="+company_edit.getText().toString()+"&plate="+vehicle_edit.getText().toString()+"&pass="+pass_edit.getText().toString();

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, urlToSend, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                            if(response.substring(0, 3).equals("200")){
                                // Getting the company name and status of the vehicle from this request
                                String[] details = response.split(" ");
                                String vehicleStatus = details[details.length-1];
                                String companyName="";
                                for(int i = 1; i< details.length-1; i++){
                                    companyName += details[i] +" ";
                                }

                                Intent intent = new Intent (LoginActivity.this, MainScreen.class);

                                intent.putExtra("companyID", company_edit.getText().toString());
                                intent.putExtra("numberPlate", vehicle_edit.getText().toString());
                                intent.putExtra("companyName", companyName);
                                intent.putExtra("vehicleStatus", vehicleStatus);
                                intent.putExtra("pass",pass_edit.getText().toString());
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Wrong Login Data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof TimeoutError) {
                                Toast.makeText(LoginActivity.this, "Error timeout!", Toast.LENGTH_SHORT).show();

                            }else if( error instanceof NoConnectionError) {
                                Log.e("ERROR", error.toString());
                                Toast.makeText(LoginActivity.this, "Error no connection!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(LoginActivity.this, "Error auth!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof ServerError) {
                                Toast.makeText(LoginActivity.this, "Error ServerError!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof NetworkError) {
                                Toast.makeText(LoginActivity.this, "Error Network!", Toast.LENGTH_SHORT).show();

                            } else if (error instanceof ParseError) {
                                Toast.makeText(LoginActivity.this, "Error Parse!", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(LoginActivity.this, "IDK FAM!", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);

                    //Toast.makeText(LoginActivity.this, "Not Empty "+ company_edit.getText().toString() + " "+ vehicle_edit.getText().toString() + " "+ pass_edit.getText().toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Empty " + company_edit.getText().toString() + " "+ vehicle_edit.getText().toString() + " "+ pass_edit.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
