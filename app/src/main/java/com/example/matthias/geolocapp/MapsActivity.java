package com.example.matthias.geolocapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<earthquake> earthquakeArrayList = new ArrayList<earthquake>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        System.out.println("je suis dans onCreate");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new LongOperation().execute("");




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("je suis dans onMapready");
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*Iterator<earthquake> it = earthquakeArrayList.iterator();

        while (it.hasNext()) {
            earthquake s = it.next();
            LatLng sydney = new LatLng(s.getLatitude(), s.getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title(s.getNom()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        }
        */
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    private class LongOperation extends AsyncTask<String, Void, ArrayList> {
        @Override
        protected ArrayList<earthquake> doInBackground(String... params) {



            try {
                String myurl= "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";

                URL url = new URL(myurl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
            /*
             * InputStreamOperations est une classe complémentaire:
             * Elle contient une méthode InputStreamToString.
             */
                String result = InputStreamOperations.InputStreamToString(inputStream);
                Log.d("MyApp",result);

                // On récupère le JSON complet
                JSONObject jsonObject = new JSONObject(result);
                // On récupère le tableau d'objets qui nous concernent
                JSONArray array = new JSONArray(jsonObject.getString("features"));
                Log.d("array", String.valueOf(array));
                // Pour tous les objets on récupère les infos
                for (int i = 0; i < array.length(); i++) {
                    // On récupère un objet JSON du tableau
                    JSONObject obj = new JSONObject(array.getString(i));
                    // On fait le lien Personne - Objet JSON
                    earthquake earth1 = new earthquake();
                    earth1.setNom(obj.getString("geometry"));
                    Log.d("type",obj.getString("geometry"));
                    JSONObject jsonObject2 = new JSONObject(String.valueOf(obj.getJSONObject("geometry")));
                    String str[]=jsonObject2.getString("coordinates").split(",");
                    String str2 = str[0].substring(1);
                    Log.d("type",str2);
                    Log.d("type",str[1]);
                    earth1.setLatitude(Double.valueOf(str2));
                    earth1.setLongitude(Double.valueOf(str[1]));


                    // On ajoute la personne à la liste
                    earthquakeArrayList.add(earth1);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return earthquakeArrayList;
        }


        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);

            Iterator<earthquake> it = earthquakeArrayList.iterator();

            while (it.hasNext()) {
                earthquake s = it.next();
                System.out.println(s.getLongitude()+"ici");
                mMap.addMarker(new MarkerOptions().position(new LatLng(s.getLongitude(), s.getLatitude())).title(s.getNom()));

            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


}
