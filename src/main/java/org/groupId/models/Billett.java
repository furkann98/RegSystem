package org.groupId.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.util.Date;

public class Billett {



    // DATAFELT
    private Arrangement arrangement;
    private String telefonNummer;
    private int plassNummer;

    private ObservableList<Billett> billettRegister = FXCollections.observableArrayList();
    private String arrangementNavn;
    private String sete;





    // KONSTRUKTØR
    public Billett() {
    }

    public Billett(Arrangement arrangement,String telefonNummer, int antall) {
        this.arrangement = arrangement;
        this.telefonNummer = telefonNummer;
        this.arrangementNavn = arrangement.getNavn();
        this.sete = antall + " stk";
    }

    // METODER
    public void lagBillett(Arrangement arrangement, String telefonNummer, int antall){
        billettRegister.add(new Billett(arrangement,telefonNummer,antall));
        arrangement.leggTilBillett(5);
    }

    public ObservableList<Billett> getBillettRegister() {
        return billettRegister;
    }

    public String getArrangementNavn() {
        return arrangementNavn;
    }

    public String getSete() {
        return sete;
    }

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
