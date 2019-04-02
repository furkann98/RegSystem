package org.groupId.models;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class Lokale {


    // DATAFELT


    private ArrayList<Lokale> lokaler = new ArrayList<>();
    public ObservableList<Lokale> lokalerTest;
    private String navn;
    private String type;
    private int antallPlasser;


    // KONSTRUKTØR

    public Lokale(String navn, String type, int antallPlasser){
        this.navn = navn;
        this.type = type;
        this.antallPlasser = antallPlasser;
    }


    // METODER


    @Override
    public String toString() {
        return navn;
    }

    public String getNavn(){ return navn; }

    public String getType() {
        return type;
    }

    public int getAntallPlasser(){
        return antallPlasser;
    }

    public void leggTilLokal(Lokale lokal){
        lokaler.add(lokal);
    }

    public void fjernLokal(int valgtLokalIndex){
        lokaler.remove(valgtLokalIndex);
    }

    public ArrayList getArrayList(){
        return lokaler;

    }




}
