package org.groupId.models;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Lokale {


    // DATAFELT
    private ArrayList<Lokale> lokaler = new ArrayList<>();
    private String navn;
    private String type;
    private int antallPlasser;


    // KONSTRUKTØRER
    public Lokale(){

    }

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

    public Lokale getSisteLokal(String navn){
        for (Lokale lokal : lokaler) {
            if(lokal.navn == navn){
                return lokal;
            }
        }
        return null;
    }

    public int getLengde(){
        return lokaler.size();
    }

    public void leggTilLokal(Lokale lokal){
        lokaler.add(lokal);
        System.out.println("ARRAYLIST:  " + lokaler.toString());
    }

    public void fjernLokal(int valgtLokalIndex){
        lokaler.remove(valgtLokalIndex);
        System.out.println("ARRAYLIST:  " + lokaler.toString());
    }

    public ArrayList<Lokale> getArrayList(){
        return lokaler;
    }




}
