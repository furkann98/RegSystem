package org.groupId.models;

import java.util.ArrayList;

public class Person {

    // DATAFELT
    private ArrayList<Person> personer = new ArrayList<>();

    private String navn;
    private String tlfNummer;
    private String Epost;
    private String nettside;
    private ArrayList<Arrangement> arrangementer = new ArrayList<>();
    private String opplysninger;

    private String antallArrangementer =  "Ingen arrangementer";



    // KONSTRUKTØR
    public Person(){
    }

    public Person(String navn, String tlfNummer, String epost, String nettside, String opplysninger) {
        this.navn = navn;
        this.tlfNummer = tlfNummer;
        this.Epost = epost;
        this.nettside = nettside;
        this.opplysninger = opplysninger;
    }

    // METODER


    @Override
    public String toString() {
        return navn;
    }

    public String getNavn() {
        return navn;
    }

    public String getTlfNummer() {
        return tlfNummer;
    }

    public String getEpost() {
        return Epost;
    }

    public String getNettside() {
        return nettside;
    }

    public String getOpplysninger() {
        return opplysninger;
    }

    public ArrayList<Arrangement> getArrangementer(){return arrangementer;}



    public void LeggTilArrangement(Arrangement arrangement){
        arrangementer.add(arrangement);
        System.out.println(arrangementer.toString());
        antallArrangementer =  arrangementer.size() + " arrangementer";
        System.out.println(antallArrangementer);
    }

    public void FjernArrangement(Arrangement arrangement){
        arrangementer.remove(arrangement);
        if(arrangementer.isEmpty()){
            antallArrangementer =  "Ingen arrangementer";
        }else {
            antallArrangementer =  arrangementer.size() + " arrangementer";

        }
    }

    public void leggTilPerson(Person person){
        personer.add(person);
    }

    public void fjernPerson(Person person){
        personer.remove(person);
    }

    public ArrayList<Person> getPersoner(){
        return personer;
    }

    public String getAntallArrangementer() {
        return antallArrangementer;
    }
}
