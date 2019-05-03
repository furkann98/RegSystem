package org.groupId.models;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Lokale implements Serializable {


    // DATAFELT
    private ArrayList<Lokale> lokaler = new ArrayList<>();
    private String navn;
    private String type;
    private int antallPlasser;


    // KONSTRUKTØRER
    public Lokale(){
        super();
    }

    public Lokale(String navn, String type, int antallPlasser){
        super();
        this.navn = navn;
        this.type = type;
        this.antallPlasser = antallPlasser;
    }


    // METODER


    @Override
    public String toString() {
        return navn;
    }

    public void getOversikt(TextArea info){
        info.setText(this.navn + "\nType: " + this.type + "\n" + "Antall Plasser: " + this.antallPlasser + "\n\nArrangementer:");
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
    }

    public void fjernLokal(int valgtLokalIndex){
        lokaler.remove(valgtLokalIndex);
    }

    public ArrayList<Lokale> getLokaler(){
        return lokaler;
    }

    public boolean isEmpty(){
        return lokaler.isEmpty();
    }

    public void clear(){
        lokaler.clear();
    }




}
