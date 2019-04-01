package org.groupId.models;

import java.util.ArrayList;

public class Lokale {


    // DATAFELT


    private ArrayList<Lokale> lokaler = new ArrayList<>();
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

    public String getNavn(){ return navn; }

    public String getType() {
        return type;
    }

    public int getAntallPlasser(){
        return antallPlasser;
    }

    public void leggTilLokal(Lokale lokal){
        /*
        throw IllegalArgumentException{

            if(!(navn instanceof String)){
                new IllegalArgumentException("Navn skal bare inneholde tekst");
            }
        }
        */

        lokaler.add(lokal);
    }

    public void fjernLokal(Lokale valgtLokal){
        lokaler.remove(valgtLokal);
    }




}
