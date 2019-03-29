package org.groupId.models;

public class Lokale {


    // DATAFELT

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

    public String getNavn(){
        return navn;
    }

    public String getType() {
        return type;
    }

    public int getAntallPlasser(){
        return antallPlasser;
    }




}
