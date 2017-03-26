package org.jdhp.android.senseofdirection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jdhp.android.senseofdirection.data.Poi;
import org.jdhp.android.senseofdirection.data.PoiAdapter;

public class AddPoiActivity extends AppCompatActivity {

    EditText etLatitude;
    EditText etLongitude;
    EditText etLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poi);

        etLatitude = (EditText) findViewById(R.id.et_latitude);
        etLongitude = (EditText) findViewById(R.id.et_longitude);
        etLabel = (EditText) findViewById(R.id.et_label);
    }

    public void addPoi(View view) {
        boolean isValid = true;

        // Check etLatitude
        double latitude = 0;
        try {
            latitude = Double.parseDouble(etLatitude.getText().toString().trim());
            latitude = latitude % 90;
        } catch(NumberFormatException e) {
            isValid = false;
            Toast.makeText(this, "Latitude should be a decimal number", Toast.LENGTH_SHORT).show();
        }

        // Check etLongitude
        double longitude = 0;
        try {
            longitude = Double.parseDouble(etLongitude.getText().toString().trim());
            longitude = longitude % 180;
        } catch(NumberFormatException e) {
            isValid = false;
            Toast.makeText(this, "Longitude should be a decimal number", Toast.LENGTH_SHORT).show();
        }

        // Check etLabel
        String label = etLabel.getText().toString();
        if(label.isEmpty() || label.length() > 63) {
            isValid = false;
            Toast.makeText(this, "Label should contain 1 to 63 characters", Toast.LENGTH_SHORT).show();
        }

        if(isValid) {
            boolean success = PoiAdapter.addPoi(new Poi(latitude, longitude, label), this);

            if(success) {
                Toast.makeText(this, "POI added", Toast.LENGTH_SHORT).show();
                etLatitude.setText("");
                etLongitude.setText("");
                etLabel.setText("");
            }
        }
    }
}
