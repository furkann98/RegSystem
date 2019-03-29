package org.groupId.models;

public class Person {

    // DATAFELT

    private String navn;
    private int telefonfNummer;
    private String Epost;
    private String nettside;
    private Lokale lokale;
    private String opplysninger;


    // KONSTRUKTØR

    public Person(String navn, int telefonfNummer, String epost, String nettside, Lokale lokale, String opplysninger) {
        this.navn = navn;
        this.telefonfNummer = telefonfNummer;
        this.Epost = epost;
        this.nettside = nettside;
        this.lokale = lokale;
        this.opplysninger = opplysninger;
    }

    // METODER

    public String getNavn() {
        return navn;
    }

    public int getTelefonfNummer() {
        return telefonfNummer;
    }

    public String getEpost() {
        return Epost;
    }

    public String getNettside() {
        return nettside;
    }

    public Lokale getLokale() {
        return lokale;
    }

    public String getOpplysninger() {
        return opplysninger;
    }
}
