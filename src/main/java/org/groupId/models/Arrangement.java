package org.groupId.models;

import java.time.LocalDate;
import java.util.Date;

public class Arrangement {

    //DATAFELT
    private Person kontaktPerson;
    private Lokale lokale;
    private String type;
    private String navn;
    private String artist;
    private String program;
    private LocalDate tidspunkt;
    private int billettPris;
    private int billettSalg;
    private int antallLedigeInt;

    private int antallSolgte;

    private String kontaktPersonNavn;
    private String programTekst;
    private String antallLedige;

    // KONSTRUKTØR
    public Arrangement(Person kontaktPerson, Lokale lokale, String navn, String artist, String program, LocalDate tidspunkt, int billettPris, int billettSalg) {
        this.kontaktPerson = kontaktPerson;
        this.lokale = lokale;
        this.type = lokale.getType();
        this.navn = navn;
        this.artist = artist;
        this.program = program;
        this.tidspunkt = tidspunkt;
        this.billettPris = billettPris;
        this.billettSalg = billettSalg;
        this.programTekst = program;
        this.kontaktPersonNavn = kontaktPerson.getNavn();


        if(!(artist == "" || artist == null)){
            this.programTekst += "\n" + "Dagens artist er: " + artist;
        }


        this.antallSolgte = billettSalg;

        antallLedigeInt = lokale.getAntallPlasser() - antallSolgte;
        this.antallLedige = antallLedigeInt + " av " + lokale.getAntallPlasser();
    }

    // METODER


    @Override
    public String toString() {
        return navn;
    }

    public Person getKontaktPerson() {
        return kontaktPerson;
    }

    public String getType() {
        return type;
    }

    public String getNavn() {
        return navn;
    }

    public Lokale getLokale() {
        return lokale;
    }

    public String getKontaktPersonNavn() {
        return kontaktPersonNavn;
    }

    public String getAntallLedige() {
        return antallLedige;
    }

    public String getArtist() {
        return artist;
    }

    public String getProgram() {
        return program;
    }

    public String getProgramTekst() {
        return programTekst;
    }

    public LocalDate getTidspunkt() {
        return tidspunkt;
    }

    public int getBillettPris() {
        return billettPris;
    }

    public int getBillettSalg() {
        return billettSalg;
    }

    public int getAntallSolgte(){
        return antallSolgte;
    }

    public int getAntallLedigeInt() {
        return antallLedigeInt;
    }

    public void leggTilBillett(int antall){
        this.antallSolgte += antall;
        System.out.println(antallSolgte);
        this.antallLedige = (lokale.getAntallPlasser() - antallSolgte) + " av " + lokale.getAntallPlasser();
        System.out.println(antallLedige);

    }
    public void fjernBillett(int antall){
        this.antallSolgte -= antall;
        this.antallLedige = (lokale.getAntallPlasser() - antallSolgte) + " av " + lokale.getAntallPlasser();
    }
}
