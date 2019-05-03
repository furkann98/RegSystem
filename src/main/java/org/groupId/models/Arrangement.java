package org.groupId.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Arrangement implements Serializable {

    //DATAFELT
    private ArrayList<Arrangement> arrangementer = new ArrayList<>();

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

    private int antallSolgte = 0;

    private String kontaktPersonNavn;
    private String programTekst;
    private String antallLedige;

    // KONSTRUKTØR
    public Arrangement(){
        super();
    }

    public Arrangement(Person kontaktPerson, Lokale lokale, String navn, String artist, String program, LocalDate tidspunkt, int billettPris, int billettSalg) {
        super();
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
        this.antallSolgte = 0;

        if(!(artist == "" || artist == null)){
            this.programTekst += "\n" + "Dagens artist er: " + artist;
        }

        this.antallLedigeInt = lokale.getAntallPlasser() - antallSolgte;
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

    public ArrayList<Arrangement> getArrangementer() {
        return arrangementer;
    }

    public void leggTilArrangement(Arrangement arrangement){
        arrangementer.add(arrangement);
    }

    public void fjernArrangement(Arrangement arrangement){
        arrangementer.remove(arrangement);
    }

    public int getAntallLedigeInt() {
        return antallLedigeInt;
    }

    public void leggTilSalg(int antall){
        this.antallSolgte += antall;
        this.antallLedigeInt = lokale.getAntallPlasser() - antallSolgte;
        this.antallLedige = antallLedigeInt + " av " + lokale.getAntallPlasser();
    }
    public void fjernSalg(int antall){
        this.antallSolgte -= antall;
        this.antallLedigeInt = lokale.getAntallPlasser() - antallSolgte;
        this.antallLedige = antallLedigeInt + " av " + lokale.getAntallPlasser();
    }

    public void nullstillSalg(){
        this.antallSolgte = 0;
        this.antallLedigeInt = this.lokale.getAntallPlasser();
        this.antallLedige = this.antallLedigeInt + " av " + this.lokale.getAntallPlasser();

    }

    public void clear(){
        arrangementer.clear();
        antallSolgte = 0;
    }
}
