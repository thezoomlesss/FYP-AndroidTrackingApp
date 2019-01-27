package com.example.thezo.fyp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // The elements present in this activity
    private EditText company_edit, vehicle_edit, pass_edit;
    private Button login_button;

    public String Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        company_edit = findViewById(R.id.idCompany);
        vehicle_edit = findViewById(R.id.idVehicle);
        pass_edit = findViewById(R.id.idPass);
        login_button = findViewById(R.id.idLogin);

        // On click -> Check if the fields are empty then route to the next activity
        // TODO Check login with DB
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !company_edit.getText().toString().isEmpty() && !vehicle_edit.getText().toString().isEmpty() && !pass_edit.getText().toString().isEmpty()){
                    Intent intent = new Intent (LoginActivity.this, MainScreen.class);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(LoginActivity.this, "Not Empty "+ company_edit.getText().toString() + " "+ vehicle_edit.getText().toString() + " "+ pass_edit.getText().toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Empty " + company_edit.getText().toString() + " "+ vehicle_edit.getText().toString() + " "+ pass_edit.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
