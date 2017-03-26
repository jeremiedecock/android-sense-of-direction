package org.jdhp.android.senseofdirection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jdhp.android.senseofdirection.data.Poi;
import org.jdhp.android.senseofdirection.data.PoiAdapter;

import java.util.ArrayList;

public class RemovePoiActivity extends AppCompatActivity {

    EditText etKeyToRemove;
    TextView tvPoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_poi);

        etKeyToRemove = (EditText) findViewById(R.id.et_key_to_remove);
        tvPoi = (TextView) findViewById(R.id.tv_poi_rm);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListOfPoi();
    }

    private void updateListOfPoi() {
        ArrayList<Poi> listOfPoi = PoiAdapter.getAllPoi(this);

        tvPoi.setText("");

        for(Poi poi: listOfPoi) {
            tvPoi.append("" + poi.getKey() + ": " + poi.getLabel() + "\n");
            tvPoi.append("    " + GpsCoordinatesFormat.formatDMSCoordinates(poi.getLatitude(), poi.getLongitude()) + "\n\n");
        }
    }

    public void removePoi(View view) {
        boolean isValid = true;

        // Check etLatitude
        int key = 0;
        try {
            key = Integer.parseInt(etKeyToRemove.getText().toString().trim());
        } catch(NumberFormatException e) {
            isValid = false;
            Toast.makeText(this, "Please enter a valid ID number", Toast.LENGTH_SHORT).show();
        }

        if(isValid) {
            boolean success = PoiAdapter.removePoi(key, this);

            if(success) {
                Toast.makeText(this, "POI removed", Toast.LENGTH_SHORT).show();
                etKeyToRemove.setText("");
                updateListOfPoi();
            } else {
                Toast.makeText(this, "Please enter a valid ID number", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
