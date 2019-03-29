package org.groupId.models;

import java.util.Date;

public class Arrangement {

    //DATAFELT
    private Person kontaktPerson;
    private String type;
    private String navn;
    private String artist;
    private String program;
    private String sted;
    private Date tidspunkt;
    private int billettPris;
    private int billettSalg;

    // KONSTRUKTØR
    public Arrangement(Person kontaktPerson, String type, String navn, String artist, String program, String sted, Date tidspunkt, int billettPris, int billettSalg) {
        this.kontaktPerson = kontaktPerson;
        this.type = type;
        this.navn = navn;
        this.artist = artist;
        this.program = program;
        this.sted = sted;
        this.tidspunkt = tidspunkt;
        this.billettPris = billettPris;
        this.billettSalg = billettSalg;
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

    public String getArtist() {
        return artist;
    }

    public String getProgram() {
        return program;
    }

    public String getSted() {
        return sted;
    }

    public Date getTidspunkt() {
        return tidspunkt;
    }

    public int getBillettPris() {
        return billettPris;
    }

    public int getBillettSalg() {
        return billettSalg;
    }
}
