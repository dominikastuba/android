package org.tensorflow.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.tensorflow.demo.R;

public class LocationActivity extends Activity{

    private TextView edTx;
    private TextView edTx2;
    private TextView positionListLabel;
    private Button zapiszPozycje;
    Double obecnaLatitude;
    Double obecnaLongitude;
    Pozycje[] tablicaPozycji = new Pozycje[100];
    int index = 0;
    String listaText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.location_activity);

        for (int i = 0; i < tablicaPozycji.length; i++){
            tablicaPozycji[i]  = new Pozycje();
        }

        edTx = (TextView) findViewById(R.id.label);
        edTx2 = (TextView) findViewById(R.id.label2); 
        positionListLabel = (TextView) findViewById(R.id.listaPozycji);
        zapiszPozycje = (Button) findViewById(R.id.zapisz);
        positionListLabel.setText("");
        zapiszPozycje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index > 0){
                    Location obecna = new Location("punkt A");
                    obecna.setLatitude(obecnaLatitude);
                    obecna.setLongitude(obecnaLongitude);

                    Location innaLokacja = new Location("punkt B");
                    innaLokacja.setLongitude(tablicaPozycji[index-1].longitude);
                    innaLokacja.setLatitude(tablicaPozycji[index-1].latitude);

                    float bearing = obecna.bearingTo(innaLokacja);
                    float dystans = obecna.distanceTo(innaLokacja);

                }

                positionListLabel.setText("");
                listaText = "";
                tablicaPozycji[index].latitude = obecnaLatitude;
                tablicaPozycji[index].longitude = obecnaLongitude;
                index++;

                int i = 0;
                for(Pozycje poz :tablicaPozycji){
                    if (i >= index)
                        break;
                    Location obecna = new Location("punkt A");
                    obecna.setLatitude(obecnaLatitude);
                    obecna.setLongitude(obecnaLongitude);

                    Location innaLokacja = new Location("punkt B");
                    innaLokacja.setLongitude(poz.longitude);
                    innaLokacja.setLatitude(poz.latitude);

                    float bearing = obecna.bearingTo(innaLokacja);
                    float dystans = obecna.distanceTo(innaLokacja);

                    listaText += String.valueOf(i+1) + ") ";
                    listaText += String.valueOf(poz.latitude);
                    listaText += "  ";
                    listaText += String.valueOf(poz.longitude);
                    listaText += "\n";
                    listaText += "Odleglosc  " + String.valueOf(dystans);
                    listaText += "\n";
                    positionListLabel.setText(listaText);
                    i++;
                }
            }
        });

        LocationManager lm;
        LocationListener locationListener;
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

     //   if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
         //   return;
       // }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        //   EditText edTx2 = (EditText)findViewById(R.layout.entry);



    }


    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged (Location loc){

            obecnaLatitude = loc.getLatitude();
            obecnaLongitude = loc.getLongitude();
            edTx.setText(String.valueOf(obecnaLatitude));
            edTx2.setText(String.valueOf(obecnaLongitude));



        }
        @Override
        public void onProviderDisabled (String provider){

        }
        @Override
        public void onProviderEnabled(String provider){

        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras){

        }
    }
    @Override
    protected void onDestroy(){

        super.onDestroy();
    }

    class Pozycje{
        Double longitude;
        Double latitude;
    }
}
