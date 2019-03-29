package org.groupId.models;

import java.util.Date;

public class Billett {

    // DATAFELT
    private int plassNummer;
    private String lokale;
    private Date tidspunkt;
    private int pris;
    private int telefonNummer;


    // KONSTRUKTØR

    public Billett(int plassNummer, String lokale, Date tidspunkt, int pris, int telefonNummer) {
        this.plassNummer = plassNummer;
        this.lokale = lokale;
        this.tidspunkt = tidspunkt;
        this.pris = pris;
        this.telefonNummer = telefonNummer;
    }

    // METODER

    public int getPlassNummer() {
        return plassNummer;
    }

    public String getLokale() {
        return lokale;
    }

    public Date getTidspunkt() {
        return tidspunkt;
    }

    public int getPris() {
        return pris;
    }

    public int getTelefonNummer() {
        return telefonNummer;
    }
}
