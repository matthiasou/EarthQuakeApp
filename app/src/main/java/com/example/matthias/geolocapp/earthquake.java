package com.example.matthias.geolocapp;

/**
 * Created by Matthias on 10/01/2017.
 */

public class earthquake {
    String nom;
    double latitude;
    double longitude;



    //Constructeur par défaut
    public earthquake(){
        System.out.println("Création d'un tremblemment de terre!");
        nom = "Inconnu";
        latitude = 0;
        longitude = 0;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
