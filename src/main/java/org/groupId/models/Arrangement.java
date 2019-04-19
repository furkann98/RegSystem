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

    private String kontaktPersonNavn;
    private String programTekst;
    private String antallLedige;

    // KONSTRUKTØR
    public Arrangement(Person kontaktPerson, Lokale lokale, String type, String navn, String artist, String program, LocalDate tidspunkt, int billettPris, int billettSalg) {
        this.kontaktPerson = kontaktPerson;
        this.lokale = lokale;
        this.type = type;
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

        int ledige = this.lokale.getAntallPlasser() - billettSalg;
        this.antallLedige = ledige + " av " + lokale.getAntallPlasser();
    }

    // METODER

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
}
