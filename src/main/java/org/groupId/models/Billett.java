package org.groupId.models;

import java.util.Date;

public class Billett {

    // DATAFELT
    private Arrangement arrangement;
    private String telefonNummer;
    private int plassNummer;



    // KONSTRUKTØR

    public Billett(Arrangement arrangement,String telefonNummer,int plassNummer) {
        this.arrangement = arrangement;
        this.telefonNummer = telefonNummer;
        this.plassNummer = plassNummer;
    }

    // METODER

    public int getPlassNummer() {
        return plassNummer;
    }


    public String getTelefonNummer() {
        return telefonNummer;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }
}
